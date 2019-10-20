package pl.adcom.cargui.service;

import pl.adcom.cargui.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAllCars();
    Optional<Car> getFirst(long id);
    List<Car> getAllCarsByColor(String color);
    boolean addCar(Car car);
    boolean changeCar(Car myCar);
    boolean changeColorOfCar(String color, long id);
    boolean deleteCar(long id);
}
