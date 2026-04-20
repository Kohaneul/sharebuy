package sharebuy.domain.page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.entity.TopNavItem;
import sharebuy.domain.page.entity.Page;

import java.util.List;
import java.util.UUID;

@Repository
public interface TopNavItemRepository extends JpaRepository<TopNavItem, UUID> {
    List<TopNavItem> findByMenuIsNullAndRoleType(RoleType roleType);
}
