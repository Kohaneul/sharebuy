package sharebuy.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharebuy.domain.user.entity.Users;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<Users, UUID>{
}
