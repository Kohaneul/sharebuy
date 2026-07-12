package sharebuy.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.user.dto.ViewerResponse;
import sharebuy.domain.user.service.UserService;

@RestController
@RequestMapping("/rest/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ViewerResponse viewerResponse(@RequestParam(name = "latitude") Double latitude,
                                         @RequestParam(name = "longitude") Double longitude,
                                         @AuthenticationPrincipal CustomUserDetail principal){
        return userService.getViewerResponse(principal,latitude,longitude);
    }


}
