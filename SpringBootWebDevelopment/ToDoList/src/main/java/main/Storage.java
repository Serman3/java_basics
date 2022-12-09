package main;

import response.Car;
import java.util.*;

public class Storage {

    private static int currentId = 1;
    private static Map<Integer, Car> cars = new HashMap<>();

    public static List<Car> getAllCars(){
        List<Car> allCar = new ArrayList<>();
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
        List<Car> list = new ArrayList<>();
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
        int id = currentId++;
        car.setId(id);
        cars.put(id, car);
        return car;
    }

    public static List<Car> allUpdate(){
        int min = 1980;
        int max = 2022;
        int diff = max - min;
        Random random = new Random();
        List<Car> list = new ArrayList<>();
        for(Map.Entry<Integer, Car> entry : cars.entrySet()){
            int i = random.nextInt(diff + 1);
            i += min;
            Car car = entry.getValue();
            car.setYear(i);
            car.setName("car " + i);
            list.add(car);
        }
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
