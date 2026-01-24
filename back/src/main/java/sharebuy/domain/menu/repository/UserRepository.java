package sharebuy.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.Users;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    Address findByAddress(UUID userId);
}
