package sharebuy.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharebuy.common.payload.CardResponse;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    /**
     * 취소건을 제외하고 반경 2키로미터 이내의 게시글 조회
     * @param latitude
     * @param longitude
     * @param radius
     * @return
     */
    @Query(value = """
        SELECT
            p.id as id,
            p.title as title,
            u.nick_name as nickName,
            u.login_id as loginId,
            u.avatar as avatar,
            p.content as content,
            i.imageurl as imgUrl,
            p.status as status,
            COALESCE(COUNT(pu.id), 0) as currentParticipants,
            COALESCE(p.max_participants,0) as maxParticipants
        FROM post p

        INNER JOIN users u
            ON p.user_id = u.id

        LEFT JOIN purchase pu
            ON p.id = pu.post_id
            AND pu.status = 'RECRUITING'

        LEFT JOIN (
            SELECT post_id, MIN(imageurl) AS imageurl
            FROM image
            GROUP BY post_id
        ) i
            ON p.id = i.post_id

        WHERE p.status IN ('RECRUITING', 'CLOSED')
          AND (6371 * acos(
              LEAST(1.0, GREATEST(-1.0,
                  cos(radians(:latitude)) * cos(radians(p.latitude)) *
                  cos(radians(p.longitude) - radians(:longitude)) +
                  sin(radians(:latitude)) * sin(radians(p.latitude))
              ))
          )) < :radius

        GROUP BY
            p.id,
            p.title,
            u.nick_name,
            u.login_id,
            u.avatar,
            p.content,
            i.imageurl,
            p.status,
            p.max_participants
""", nativeQuery = true)
    List<CardResponse> findNearbyPosts(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius
    );

    @Query("select p from Post p left join fetch p.imgUrl where p.id = :id")
    Optional<Post> findByIdWithImages(@Param("id")UUID id);


    @Modifying
    @Query("""
    UPDATE Post p
       SET p.currentParticipants = p.currentParticipants + 1
     WHERE p.id = :id
       AND p.maxParticipants > p.currentParticipants
    """)
    int participate(@Param("id") UUID id);

    @Modifying(clearAutomatically = true)
    @Query("""
    UPDATE Post p
       SET status = :status,
       updatedAt = CURRENT_TIMESTAMP
     WHERE p.id = :id
    """)
    int changeStatus(@Param("id") UUID id , @Param("status")PostStatus postStatus);
}
