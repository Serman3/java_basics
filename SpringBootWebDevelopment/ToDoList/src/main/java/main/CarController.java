package main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.Car;
import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {

    @GetMapping("/cars/")
    public List<Car> allCars(){
        return Storage.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity getCar(@PathVariable int id){
        Car car = Storage.getCar(id);
        if(car == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(car, HttpStatus.OK);
    }

    @PostMapping("/cars/")
    public Car addPostCar(Car car){
        return Storage.addNewCar(car);
    }

    @PostMapping("/cars/{id}")
    public ResponseEntity postCarId(){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @PutMapping("/cars/")
    public List<Car> carAllPut(){
        return Storage.allUpdate();
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity carPutId(@PathVariable int id) {
        Car car = Storage.updateCar(id);
        if(car == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(car, HttpStatus.OK);
    }

    @DeleteMapping("/cars/{id}")
    public List<Car> deleteCar(@PathVariable int id){
        return Storage.deleteCar(id);
    }

    @DeleteMapping("/cars")
    public ResponseEntity deleteAllCars(){
        Storage.deleteAllCar();
        return ResponseEntity.noContent().build();
    }
}
