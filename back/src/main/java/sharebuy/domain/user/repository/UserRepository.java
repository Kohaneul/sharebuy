package sharebuy.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.Users;

import java.util.Optional;
import java.util.UUID;
public interface UserRepository extends JpaRepository<Users, UUID>{
    Address findByAddress(UUID userId);
    Optional<Users> findByUsername(String userName);

}
