package sharebuy.domain.menu.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.entity.TopNavItems;

import java.util.List;
import java.util.UUID;

public interface TopNavRepository extends JpaRepository<TopNavItems, UUID> {

    @Query("""
            select t
            from TopNavItems t
            where t.menu.id = :menuId
              and t.roleType = :roleType
            order by t.position, t.displayOrder
            """)
    List<TopNavItems> findTopNavItems(@Param("menuId")UUID menuId, @Param("roleType") RoleType roleType);
}
