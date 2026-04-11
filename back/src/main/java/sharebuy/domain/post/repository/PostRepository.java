package sharebuy.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharebuy.domain.post.entity.Post;

import java.util.List;
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
    SELECT * FROM post p
    WHERE status IN ('RECRUITING', 'CLOSED')
      AND (6371 * acos(
          LEAST(1.0, GREATEST(-1.0,
              cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) +
              sin(radians(:latitude)) * sin(radians(latitude))
          ))
      )) < :radius
    """, nativeQuery = true)
    List<Post> findNearbyPosts(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius
    );
}
