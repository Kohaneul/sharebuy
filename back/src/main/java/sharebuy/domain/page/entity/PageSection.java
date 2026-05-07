package sharebuy.domain.page.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.ActionType;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.page.domain.Position;
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
    @Column(name = "page_section_type")
    private PageSectionType pageSectionType;

    @Column(name = "sort_order",nullable = false)
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType; // 해당 섹션 접근 가능 권한

    @Column(name = "title")
    private String title;

    @Column(name = "route_url")
    private String routeUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    private ActionType actionType;  //버튼일경우, 버튼을 누르면 단순 vue-router로 이동하거나 서버로 가는 방식 분리

    @Column(columnDefinition = "TEXT",name = "json_config")
    private String jsonConfig;

    @Column(name = "action_param")
    private String actionParam; //데이터 조회(API)를 위한 식별자

    @Enumerated(EnumType.STRING)
    @Column(name = "data_source_type")
    private DataSourceType dataSourceType;  //조회할 테이블

    @Column(columnDefinition = "TEXT",name = "mapping_key")
    private String mappingKey;  //input일 경우 어떤 데이터값과 매핑할지

    @Column(name = "position")
    @Enumerated(STRING)
    private Position position;
}
