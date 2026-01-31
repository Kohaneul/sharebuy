package sharebuy.domain.page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharebuy.domain.page.entity.Page;

import java.util.UUID;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {
    Page findByMenuId(UUID menuId);
}
