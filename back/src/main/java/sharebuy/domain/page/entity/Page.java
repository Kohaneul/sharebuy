package sharebuy.domain.page.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.post.type.PageType;

import java.awt.*;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id",nullable = false)
    private Menus menu;

}
