package sharebuy.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.domain.TopNavComponent;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "top_nav_menus")
public class TopNavMenus extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "left_col" ,columnDefinition = "text")
    @Enumerated
    private List<TopNavComponent> left;

    @Column(name = "center_col" ,columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private List<TopNavComponent> center;

    @Column(name = "right_col" ,columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private List<TopNavComponent> right;

    @OneToOne
    @JoinColumn(name = "menu_id",nullable = false)
    private Menus menu;

    @Enumerated
    private RoleType roleType;

}