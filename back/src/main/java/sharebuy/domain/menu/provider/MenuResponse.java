package sharebuy.domain.menu.provider;

import sharebuy.domain.menu.domain.MenuLayoutType;

import java.util.List;

public record MenuResponse (
        // 1. 메뉴 전체에 대한 정보 (메타 데이터)
        String name,        // 메뉴판 이름 (예: "메인 상단바")
        String icon,        // 메뉴판 대표 아이콘
        String route,       // 기본 경로
        MenuLayoutType type,      // 메뉴 구분 (TOP_NAV 등)
        int menuOrder,      // 메뉴판 간의 노출 순서

        // 2. 그 안에 포함된 구체적인 요소들 (알맹이)
        List<NavElement> elements
){}