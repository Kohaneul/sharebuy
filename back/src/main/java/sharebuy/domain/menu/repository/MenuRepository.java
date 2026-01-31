package sharebuy.domain.menu.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.entity.Menu;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    @Query("select m from Menus m " +
            "where roleType = :roleType and m.id = :menuId " +
            "order by m.name")
    Optional<Menu> findMenuByUser(@Param("roleType") RoleType roleType, @Param("menuId") UUID menuId);

}
