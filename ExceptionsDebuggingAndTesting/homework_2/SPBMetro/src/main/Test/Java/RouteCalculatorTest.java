import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    List<Station> route;
    StationIndex stationIndex;

    Line line1 = new Line(1, "Первая");
    Line line2 = new Line(2, "Вторая");
    Line line3 = new Line(3, "Третья");

    Station station1 = new Station("A1", line1);
    Station station2 = new Station("A2", line1);
    Station station3 = new Station("A3", line1);
    Station station4 = new Station("B1", line2);
    Station station5 = new Station("B2", line2);
    Station station6 = new Station("B3", line2);
    Station station7 = new Station("C1", line3);
    Station station8 = new Station("C2", line3);
    Station station9 = new Station("C3", line3);

    @Override
    protected void setUp() throws Exception {
        //Схема тестовой линии
        //[l1]            [l2]            [l3]
        //[l1]-[transfer]-[l2]            [l3]
        //[l1]            [l2]-[transfer]-[l3]
        route = new ArrayList<>();
        route.add(station1);
        route.add(station2);
        route.add(station3);
        route.add(station4);
        route.add(station5);
        route.add(station6);
        route.add(station7);
        route.add(station8);
        route.add(station9);
    }

    public void testCalculateDuration(){
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 22.0;
        Assert.assertEquals(expected,actual,0);
    }

    public void testGetShortestRoute(){
        RouteCalculator calculator = getRouteCalculator();
        Station from = stationIndex.getStation("B1", 2);
        Station to = stationIndex.getStation("B3", 2);

        List<Station> actual = calculator.getShortestRoute(from, to);
        List<Station> expected = Arrays.asList(route.get(3), route.get(4), route.get(5));
        Assert.assertEquals(expected,actual);
    }

    public void testGetShortestRouteOneConnection(){
        RouteCalculator calculator = getRouteCalculator();
        Station from = stationIndex.getStation("A1", 1);
        Station to = stationIndex.getStation("B2", 2);

        List<Station> actual = calculator.getShortestRoute(from, to);
        List<Station> expected = Arrays.asList(route.get(0), route.get(1), route.get(3), route.get(4));
        Assert.assertEquals(expected,actual);
    }

    public void testGetShortestRouteTwoConnection(){
        RouteCalculator calculator = getRouteCalculator();
        Station from = stationIndex.getStation("A1", 1);
        Station to = stationIndex.getStation("C3", 3);

        List<Station> actual = calculator.getShortestRoute(from, to);
        List<Station> expected = Arrays.asList(route.get(0), route.get(1),route.get(3), route.get(4),
                route.get(5), route.get(7), route.get(8));
        Assert.assertEquals(expected,actual);
    }

    public  RouteCalculator getRouteCalculator(){
        createIndex();
        return new RouteCalculator(stationIndex);
    }

    private void createIndex(){
        stationIndex = new StationIndex();

        stationIndex.addStation(station1);
        stationIndex.addStation(station2);
        stationIndex.addStation(station3);
        stationIndex.addStation(station4);
        stationIndex.addStation(station5);
        stationIndex.addStation(station6);
        stationIndex.addStation(station7);
        stationIndex.addStation(station8);
        stationIndex.addStation(station9);

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        line1.addStation(station1);
        line1.addStation(station2);
        line1.addStation(station3);
        line2.addStation(station4);
        line2.addStation(station5);
        line2.addStation(station6);
        line3.addStation(station7);
        line3.addStation(station8);
        line3.addStation(station9);

        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationIndex.getStation("A2")
                , stationIndex.getStation("B1"))));
        stationIndex.addConnection(new ArrayList<>(Arrays.asList(stationIndex.getStation("B3")
                , stationIndex.getStation("C2"))));
    }

}
