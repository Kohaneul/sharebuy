package sharebuy.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharebuy.domain.menu.entity.Menus;

import java.awt.*;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menus, UUID> {

    Menu findByMenu();
}
