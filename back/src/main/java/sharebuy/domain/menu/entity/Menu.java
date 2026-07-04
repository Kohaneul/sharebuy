package sharebuy.domain.menu.entity;

import jakarta.persistence.*;
import lombok.*;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.RoleType;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "menu")
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "icon")
    private String icon;

    @Column(name = "is_active")
    private int isActive;

    @Column(name = "role_type")
    @Enumerated(STRING)
    private RoleType roleType;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "menu_order",nullable = false)
    private int menuOrder;

    @Column(name = "route_name",nullable = false)
    private String routeName;

    @Column(name = "title",nullable = false)
    private String title;

}