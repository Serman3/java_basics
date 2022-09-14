import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Dimensions dimensions = new Dimensions(10, 20, 30);
        System.out.println(dimensions);
        dimensions.volumeOfCargo();

        Dimensions dimensionsCopy = dimensions.setHeight(100);
        System.out.println(dimensionsCopy);
        dimensionsCopy.volumeOfCargo();

        InfoWeight infoWeight = new InfoWeight(dimensionsCopy, 50, "Street Sezam", true, "A555AA55", true);
        System.out.println(infoWeight);

        InfoWeight infoWeight1 = infoWeight.setAddress("Street Val");
        System.out.println(infoWeight1);

        InfoWeight infoWeight2 = new InfoWeight(infoWeight.getDimension(),
                infoWeight1.getMass(),
                infoWeight1.getAddress(),
                infoWeight1.isProperty(),
                infoWeight1.getNumber(),
                infoWeight1.isFragileOrNot());
        System.out.println(infoWeight2);

        Elevator elevator = new Elevator(-3, 26);
        while (true) {
            System.out.print("Введите номер этажа: ");
            int floor = new Scanner(System.in).nextInt();
            elevator.move(floor);
            System.out.println("Текущий этаж " + elevator.getCurrentFloor());
        }
    }
}
