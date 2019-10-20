package pl.adcom.cargui.service;

import org.springframework.stereotype.Service;
import pl.adcom.cargui.model.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private List<Car> listCars;

    public CarServiceImpl() {
        listCars = new ArrayList<>();
        listCars.add(new Car(1L,"Opel", "Corsa",  "czarny"));
        listCars.add(new Car(2L, "Nissan", "Juke", "biały"));
        listCars.add(new Car(3L, "Honda", "Civic", "czarny"));
        listCars.add(new Car(4L, "Honda", "Civic", "srebrny"));
        listCars.add(new Car(5L, "Ford", "Eskord", "srebrny"));
    }

    @Override
    public List<Car> getAllCars() {
        return listCars;
    }

    @Override
    public Optional<Car> getFirst(long id) {
        return listCars.stream().filter(car -> car.getId() == id).findFirst();
    }

    @Override
    public List<Car> getAllCarsByColor(String color) {
        return listCars.stream().filter(car1 -> car1.getColor().equals(color)).collect(Collectors.toList());
    }

    @Override
    public boolean addCar(Car car) {
        long lastId = 0;

        if(!listCars.isEmpty()){
            lastId = listCars.stream().max(Comparator.comparing(v -> v.getId())).get().getId();
            lastId += 1;
        }else{
            lastId += 1;
        }
        car.setId(lastId);
        return listCars.add(car);
    }

    @Override
    public boolean changeCar(Car myCar) {
        Optional<Car> modCar = listCars.stream().filter(car1 -> car1.getId() == myCar.getId()).findFirst();

        if(modCar.isPresent()){
            modCar.get().setId(myCar.getId());
            modCar.get().setMark(myCar.getMark());
            modCar.get().setModel(myCar.getModel());
            modCar.get().setColor(myCar.getColor());
            return true;
        }
        return false;
    }

    @Override
    public boolean changeColorOfCar(String color, long id) {
        Optional<Car> modCarColor = listCars.stream().filter(car -> car.getId() == id).findFirst();

        if(modCarColor.isPresent()){
            modCarColor.get().setColor(color);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCar(long id) {
        Optional<Car> delCar = listCars.stream().filter(car -> car.getId() == id).findFirst();

        if(delCar.isPresent()){
            listCars.remove(delCar.get());
            return true;
        }
        return false;
    }
}