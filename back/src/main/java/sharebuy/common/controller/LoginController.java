package sharebuy.common.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sharebuy.common.auth.config.CustomUserDetail;

@Controller
@RequestMapping("/")
public class LoginController {

    public String login(@AuthenticationPrincipal CustomUserDetail principal){
        if(principal ==null){
            return "redirect:/login";
        }
        return "redirect:/board";
    }
}
