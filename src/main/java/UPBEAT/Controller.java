package UPBEAT;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    MapCell map = new MapCell(5,5);

    @GetMapping("/Map")
    public MapCell getCell(){
        return map;
    }
}
