package main.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    private CarRepository carRepository;

    @Value("${someParameter}")
    private Integer someParameter;

    @RequestMapping("/")
    public String index(Model model){
        Iterable<Car> carIterable = carRepository.findAll();
        List<Car> cars = new ArrayList<>();
        for(Car car : carIterable){
            cars.add(car);
        }
        model.addAttribute("cars", cars);
        model.addAttribute("carsCount", cars.size());
        model.addAttribute("someParameter", someParameter);
        return "index";
    }
}

