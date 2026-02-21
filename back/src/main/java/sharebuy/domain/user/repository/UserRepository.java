package sharebuy.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.User;

import java.util.Optional;
import java.util.UUID;
public interface UserRepository extends JpaRepository<User, UUID>{
//    Address findByAddress_Id(UUID userId);
    Optional<User> findByLoginId(String loginId);

}
