package sharebuy.domain.menu.dto;

import sharebuy.domain.menu.domain.TopNavComponent;

public record NavElement (
        TopNavComponent componentType, // ALARM, SEARCH 등
        Object value,                  // 실제 데이터 (5, "군포시" 등)
        String actionUrl               // 클릭 시 이동 주소
){}
