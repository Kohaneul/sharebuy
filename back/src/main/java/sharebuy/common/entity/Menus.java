package sharebuy.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;

import java.util.List;
import java.util.UUID;

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
    private String type;

    @Column(name = "top_nav")
    private int topNav; // 0 또는 1

    @Column(name = "is_active")
    private int isActive;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<MenuRoles> roles;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

}