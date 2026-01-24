package sharebuy.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.entity.Users;

import java.util.List;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menus, UUID> {

    @Query("select t from menus t where t.id = :id" +
            "order by t.position,t.display_order")
    List<Menus> findMenuItemsByUser(Users user, UUID id);
}
