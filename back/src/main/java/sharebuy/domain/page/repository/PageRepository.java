package sharebuy.domain.page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharebuy.domain.page.dto.TopNavItemDto;
import sharebuy.domain.page.entity.Page;

import java.util.List;
import java.util.UUID;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {
    @Query("""
    select new sharebuy.domain.page.dto.TopNavItemDto(
        t.roleType,
        t.component,
        t.displayOrder,
        t.position
    )
    from TopNavItem t
    where t.page.id = :pageId
""")
    List<TopNavItemDto> findTopNavItems(@Param("pageId") UUID pageId);

}
