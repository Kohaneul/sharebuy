package sharebuy.domain.page.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.post.type.PageSectionType;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "page_section")
public class PageSection  extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    @Enumerated(EnumType.STRING)
    private PageSectionType pageSectionType; // GRID, CARD, INPUT, SEARCH_FORM

    @Column(name = "display_order",nullable = false)
    private Integer displayOrder;

    @Column(name = "name",nullable = false)
    private String name;
}
