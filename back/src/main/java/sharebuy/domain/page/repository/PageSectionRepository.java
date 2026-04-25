package sharebuy.domain.page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.page.entity.PageSection;

import java.util.List;
import java.util.UUID;

@Repository
public interface PageSectionRepository extends JpaRepository<PageSection, UUID> {
    List<PageSection> findByPageIdAndRoleTypeIn(UUID pageId,List<RoleType> roleType);
}
