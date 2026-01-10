package sharebuy.domain.menu.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.menu.entity.TopNavItems;

import java.util.List;
import java.util.UUID;

public interface TopNavRepository extends JpaRepository<TopNavItems, UUID> {

    @Query("select t from top_nav_items t where t.menu_id = :menuId" +
            "order by t.position,t.display_order")
    List<TopNavItems> findAllByMenuId(@Param("menuId")UUID menuId);
}
