public class Elevator {
    int currentFloor = 1;
    int minFloor;
    int maxFloor;

    public Elevator(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void moveDown() {
        currentFloor -= 1;
    }

    public void moveUp() {
        currentFloor += 1;
    }

    public void move(int floor) {
        if (floor >= minFloor && floor <= maxFloor) {
            if (currentFloor <= floor) {
                for (int i = currentFloor; i < floor; i++) {
                    moveUp();
                    System.out.println(currentFloor + " этаж");
                }
            } else {
                for (int i = currentFloor; i > floor; i--) {
                    moveDown();
                    System.out.println(currentFloor + " этаж");
                }
            }
        }else {
            System.out.println("Нет таких этажей");
        }
    }
}
