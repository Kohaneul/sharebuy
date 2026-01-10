package sharebuy.domain.page.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.post.type.PageType;

import java.util.Map;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "page_component")
public class PageComponent extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "page_id",nullable = false)
    private Page page;

    @Enumerated(STRING)
    @Column(name = "page_type")
    private PageType pageType;

    @Column(name = "sort_order",nullable = false)
    private Integer sortOrder;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "meta")
    private Map<String,Object> meta;

}
