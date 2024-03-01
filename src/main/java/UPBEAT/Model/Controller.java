package UPBEAT.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    MapCell map = new MapCell(5,5,1000000);

    @GetMapping("/Map")
    public MapCell getCell(){
        return map;
    }
}
