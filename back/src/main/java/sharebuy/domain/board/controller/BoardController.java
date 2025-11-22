package sharebuy.domain.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/board")
public class BoardController {
    
    @GetMapping
    public Map<String,Object> test(){
        Map<String,Object> map = Map.of("hi","hello");
        return map;
    }
}
