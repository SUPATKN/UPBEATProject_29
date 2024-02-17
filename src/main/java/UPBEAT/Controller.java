package UPBEAT;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    MapCell map = new MapCell(2,2);

    @GetMapping("/Map")
    public Cell getCell(){
        return map.getRandomEmptyCell();
    }
}
