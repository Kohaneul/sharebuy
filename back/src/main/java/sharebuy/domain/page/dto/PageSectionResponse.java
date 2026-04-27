package sharebuy.domain.page.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public record PageSectionResponse(
     double lat,
     double lon,
     @RequestParam Map<String,Object> params
) {
}
