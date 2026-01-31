package sharebuy.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.domain.TopNavPosition;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "top_nav_items")
public class TopNavItems extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "position")
    @Enumerated(STRING)
    private TopNavPosition position;

    @Column(name = "component" ,columnDefinition = "text")
    @Enumerated(STRING)
    private TopNavComponent component;

    @Column(name = "display_order",nullable = false)
    private Integer displayOrder;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id",nullable = false)
    private Menu menu;

    @Enumerated(STRING)
    @Column(name = "role_type",nullable = false)
    private RoleType roleType;

}