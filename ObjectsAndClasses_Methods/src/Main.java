public class Main {

    public static void main(String[] args) {
        Basket basket1 = new Basket();
        basket1.add("Cola", 95, 2, 2.5);
        basket1.add("Pizza", 950, 1, 0.9);
        basket1.add("Apple", 52, 1, 1.5);
        basket1.add("Milk", 79, 4, 9.5);
        basket1.print("Корзина 1");

       // System.out.println("Общий вес всех товаров в корзине 1 равен " + basket1.getTotalWeight());
        System.out.println("Общая стоимомть товаров со всех корзин равна " + Basket.getTotalCostToBasket());
        System.out.println("Общее количество товаров со всех корзин " + Basket.getTotalGoodsToBasket());

        Basket basket2 = new Basket();
        basket2.add("Juice", 60, 3, 2.5);
        basket2.add("Chocolate", 100, 3, 0.9);
        basket2.add("Cheese", 5, 1, 8.5);
        basket2.add("Bananas", 140, 2, 2.9);
        basket2.print("Корзина 2");

       // System.out.println("Общий вес всех товаров в корзине 2 равен " + basket2.getTotalWeight());
        System.out.println("Общая стоимомть товаров со всех корзин равна " + Basket.getTotalCostToBasket());
        System.out.println("Общее количество товаров со всех корзин " + Basket.getTotalGoodsToBasket());

        Basket basket3 = new Basket();
        basket3.add("Flower", 10, 2, 1.5);
        basket3.add("Icecream", 60, 5, 2.9);
        basket3.add("Fish", 59, 3, 8.5);
        basket3.add("Chips", 14, 2, 22.5);
        basket3.print("Корзина 3");

        // System.out.println("Общий вес всех товаров в корзине 3 равен " + basket3.getTotalWeight());
        System.out.println("Общая стоимомть товаров со всех корзин равна " + Basket.getTotalCostToBasket());
        System.out.println("Общее количество товаров со всех корзин " + Basket.getTotalGoodsToBasket());

        Basket.averagePriceOfGoodsToBasket();
        Basket.averagePriceToBasket();


    }
}
