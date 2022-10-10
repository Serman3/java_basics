import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Flight> list = findPlanesLeavingInTheNextTwoHours(Airport.getInstance());
        System.out.println(list);

    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        return airport.getTerminals().stream().flatMap(terminal -> terminal.getFlights()
                        .stream()).collect(Collectors.toList()).stream()
                        .filter(type -> type.getType().equals(Flight.Type.DEPARTURE))
                        .filter(dateUp -> dateUp.getDate().toInstant().isBefore(Instant.now().plusSeconds(7200)))
                        .filter(dateDown -> dateDown.getDate().toInstant().isAfter(Instant.now()))
                        .collect(Collectors.toList());
        //TODO Метод должден вернуть список рейсов вылетающих в ближайшие два часа.
    }

}