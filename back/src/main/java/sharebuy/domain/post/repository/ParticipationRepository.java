package sharebuy.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharebuy.domain.post.entity.Participation;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, UUID> {

    boolean existsByPostIdAndUserId(UUID postId, UUID userId);

    @Query("select p from Participation p join fetch p.user where p.post.id = :postId")
    List<Participation> findByIdPostIdWithUser(@Param("postId") UUID postId);
}
