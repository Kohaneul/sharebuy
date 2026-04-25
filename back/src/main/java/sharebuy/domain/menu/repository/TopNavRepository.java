package sharebuy.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.page.entity.TopNavItem;

import java.util.List;
import java.util.UUID;

public interface TopNavRepository extends JpaRepository<TopNavItem, UUID> {

    @Query("""
            select t
            from TopNavItem t
            where t.page.id = :pageId
              and t.roleType = :roleType
            order by t.position, t.displayOrder
            """)
    List<TopNavItem> findTopNavItems(@Param("pageId")UUID pageId, @Param("roleType") RoleType roleType);
}
