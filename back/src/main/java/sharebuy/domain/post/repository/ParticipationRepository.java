package sharebuy.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharebuy.domain.post.entity.Participation;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, UUID> {

    boolean existsByPostIdAndUserId(UUID postId, UUID userId);
}
