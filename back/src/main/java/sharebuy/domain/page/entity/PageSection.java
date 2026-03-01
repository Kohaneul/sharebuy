package sharebuy.domain.page.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.post.type.PageSectionType;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "page_section")
public class PageSection extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "page_id",nullable = false)
    private Page page;

    @Enumerated(STRING)
    @Column(name = "page_type")
    private PageSectionType pageSectionType;

    @Column(name = "sort_order",nullable = false)
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType; // 해당 섹션 접근 가능 권한

    @Column(name = "data_url")
    private String dataUrl;
}
