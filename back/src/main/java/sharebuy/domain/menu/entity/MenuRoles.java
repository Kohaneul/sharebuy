package sharebuy.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.RoleType;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_roles")
public class MenuRoles extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(STRING)
    @Column(name = "role_type")
    private RoleType roleType;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

}
