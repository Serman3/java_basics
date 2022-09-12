public class Main {

    public static void main(String[] args) {
        Basket basket1 = new Basket();
        basket1.add("Cola", 95, 2, 2.5);
        basket1.add("Pizza", 950, 1, 0.9);
        basket1.add("Milk", 40);
        basket1.add("Potato", 45, 20, 1.1);
        basket1.print("Корзина 1");
        System.out.println("Общий вес всех товаров в корзине 1 равен " + basket1.getTotalWeight());

        Basket basket2 = new Basket();
        basket2.add("Juice", 60);
        basket2.add("Chocolate", 100, 3, 0.9);
        basket2.add("Apple", 50);
        basket2.add("Lime", 30, 2, 0.5);
        System.out.println();
        basket2.print("Корзина 2");
        System.out.println("Общий вес всех товаров в корзине 2 равен " + basket2.getTotalWeight());
    }
}
