package sharebuy.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.menu.domain.MenuLayoutType;
import sharebuy.common.domain.RoleType;

import java.util.List;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menus")
public class Menus extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String icon;
    private String route;

    @Column(name = "menu_layout_type")
    @Enumerated(STRING)
    private MenuLayoutType menuLayoutType;

    @Column(name = "is_active")
    private int isActive;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<MenuRoles> roles;

    @Column(name = "role_type")
    @Enumerated(STRING)
    private RoleType roleType;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "menu_order",nullable = false)
    private int menuOrder;

    public boolean isActive(){
        return isActive==1;
    }


}