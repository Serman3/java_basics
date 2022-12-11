package main;

import main.model.Car;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {

    private static AtomicInteger currentId = new AtomicInteger(1);
    private static Map<Integer, Car> cars = Collections.synchronizedMap(new HashMap<>());

    public static List<Car> getAllCars(){
        CopyOnWriteArrayList<Car> allCar = new CopyOnWriteArrayList<>();
        allCar.addAll(cars.values());
        return allCar;
    }

    public static Car getCar(int id){
        if (cars.containsKey(id)){
            return cars.get(id);
        }
        return null;
    }

    public static List<Car> deleteCar(int id){
        CopyOnWriteArrayList<Car> list = new CopyOnWriteArrayList<>();
        if(cars.containsKey(id)){
            cars.remove(id);
        }
        list.addAll(cars.values());
        return list;
    }

    public static void deleteAllCar(){
        if(!cars.isEmpty()){
            cars.clear();
        }
    }

    public static Car addNewCar(Car car){
        int id = currentId.incrementAndGet();
        car.setId(id);
        cars.put(id, car);
        return car;
    }

    public static List<Car> allUpdate(){
        ReentrantLock locker = new ReentrantLock();
        locker.lock();
        int min = 1980;
        int max = 2022;
        int diff = max - min;
        Random random = new Random();
        CopyOnWriteArrayList<Car> list = new CopyOnWriteArrayList<>();
        for(Map.Entry<Integer, Car> entry : cars.entrySet()){
            int i = random.nextInt(diff + 1);
            i += min;
            Car car = entry.getValue();
            car.setYear(i);
            car.setName("car " + i);
            list.add(car);
        }
        locker.unlock();
        return list;
    }

    public static Car updateCar(int id){
        if(!cars.isEmpty()){
            for(Map.Entry<Integer, Car> entry : cars.entrySet()){
                if(entry.getKey().equals(id)){
                    entry.getValue().setName("new String");
                    return entry.getValue();
                }
            }
        }
         return null;
    }
}
