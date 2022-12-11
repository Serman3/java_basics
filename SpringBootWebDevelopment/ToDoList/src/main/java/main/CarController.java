package main;

import main.model.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Car;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/cars/")
    public List<Car> allCars(){
        Iterable<Car> carIterable = carRepository.findAll();
        CopyOnWriteArrayList cars = new CopyOnWriteArrayList();
        for(Car car : carIterable){
            cars.add(car);
        }
        return cars;
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity getCar(@PathVariable int id){
        Optional<Car> carOptional = carRepository.findById(id);
        if(!carOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(carOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/cars/")
    public Car addPostCar(Car car){
        Car newCar = carRepository.save(car);
        return newCar;
    }

    @PostMapping("/cars/{id}")
    public ResponseEntity postCarId(){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @PutMapping("/cars/")
    public List<Car> carAllPut(){
        ReentrantLock locker = new ReentrantLock();
        locker.lock();
        int min = 1980;
        int max = 2022;
        int diff = max - min;
        Random random = new Random();
        Iterable<Car> carIterable = carRepository.findAll();
        CopyOnWriteArrayList cars = new CopyOnWriteArrayList();
        for(Car car : carIterable){
            int i = random.nextInt(diff + 1);
            i += min;
            car.setYear(i);
            car.setName("car " + i);
            carRepository.save(car);
            cars.add(car);
        }
        locker.unlock();
        return cars;
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity carPutId(@PathVariable int id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if(!carOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Car car = carOptional.get();
        car.setName("new name");
        carRepository.save(car);
        return new ResponseEntity(car, HttpStatus.OK);
    }

    @DeleteMapping("/cars/{id}")
    public List<Car> deleteCar(@PathVariable int id){
        carRepository.deleteById(id);
        Iterable<Car> carIterable = carRepository.findAll();
        CopyOnWriteArrayList cars = new CopyOnWriteArrayList();
        for(Car car : carIterable){
            cars.add(car);
        }
        return cars;
    }

    @DeleteMapping("/cars/")
    public ResponseEntity deleteAllCars(){
        carRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
