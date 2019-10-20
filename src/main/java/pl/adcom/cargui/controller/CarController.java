package pl.adcom.cargui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.adcom.cargui.model.Car;
import pl.adcom.cargui.service.CarService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String getCars(Model model){
        model.addAttribute("cars", carService.getAllCars());
        return "cars";
    }

    @GetMapping("/edit/{id}")
    public String showEditCar(@PathVariable("id") long id, Model model){
        Car car = carService.getFirst(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
        model.addAttribute("car", car);
        return "update-car";
    }

    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable("id") long id, @Valid Car car, BindingResult result, Model model, @RequestParam(value="action", required=false) String action){
        if (result.hasErrors()) {
//            car.setId(id);
            return "update-car";
        }

        if(action.equals("update")){
            carService.changeCar(car);
        }
        return "redirect:/";

//        carService.changeCar(car);
//        model.addAttribute("cars", carService.getAllCars().sort(car, id));
//        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") long id, Model model){
        Car car = carService.getFirst(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        carService.deleteCar(id);
        model.addAttribute("car", carService.getAllCars());
        return "redirect:/";
    }

    @GetMapping("/add")
    public String showAddNewCar(Car car){
        return "add-car";
    }

    @PostMapping("/save")
    public String addCar(@Valid Car car, BindingResult result, Model model, @RequestParam(value="action", required=false) String action){

        if (result.hasErrors()) {
            return "add-car";
        }

        if(action.equals("save")){
            model.addAttribute("car", carService.addCar(car));
        }
        return "redirect:/";
    }

    @GetMapping(value = "/search", params = "color")
    public String searchColorCar(@RequestParam String color, Model model){
        List<Car> carsColor = carService.getAllCarsByColor(color);

        if(!carsColor.isEmpty()){
            model.addAttribute("cars", carsColor);
            return "cars";
        }

        return "redirect:/";
    }

}
