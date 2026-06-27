package sharebuy.domain.page.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.entity.Menu;

import java.util.List;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "page")
public class Page extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id",nullable = false)
    private Menu menu;

    @Column(name = "route",nullable = false)
    private String route;

    @Column(name = "role_type")
    @Enumerated(STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "page",fetch = LAZY,cascade = CascadeType.ALL)
    private List<PageSection> pageSectionList;

    @OneToMany(mappedBy = "page",fetch = LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TopNavItem> topNavItems;


}
