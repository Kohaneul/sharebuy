package sharebuy.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.entity.Users;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menus, UUID> {

    Object findMenuItemsByMenu(Users user, UUID id);
}
