
public class Basket {

    private static int count = 0;
    private String items = "";
    private int totalPrice = 0;
    private int limit;
    private double totalWeight = 0;
    private static int totalCostToBasket = 0;
    private static int totalGoodsToBasket = 0;

    public Basket() {
        increaseCount(1);
        items = "Список товаров в одной корзине:";
        this.limit = 1000000;
    }

    public Basket(int limit) {
        this();
        this.limit = limit;
    }

    public Basket(String items, int totalPrice) {
        this();
        this.items = this.items + items;
        this.totalPrice = totalPrice;
    }
    public static void sumTotalCostToBasket(int totalPrice){
        totalCostToBasket += totalPrice;
    }

    public static void sumTotalGoodsToBasket(int count){
        totalGoodsToBasket += count;
    }

    public static int averagePriceOfGoodsToBasket(){
        int result = totalCostToBasket / totalGoodsToBasket;
        System.out.println("Средняя стоимость товара со всех корзин " + result);
        return result;
    }

    public static void averagePriceToBasket(){
        int result = totalCostToBasket / count;
        System.out.println("Средняя стоимость корзины равна " + result);
    }

    public static void increaseCount(int count) {
        Basket.count = Basket.count + count;
    }

    public static int getTotalCostToBasket() {
        return totalCostToBasket;
    }

    public static int getTotalGoodsToBasket() {
        return totalGoodsToBasket;
    }

    public void add(String name, int price) {
        add(name, price, 1);
    }

    public void add(String name, int price, int count) {
        boolean error = false;
        if (contains(name)) {
            error = true;
        }

        if (totalPrice + count * price >= limit) {
            error = true;
        }

        if (error) {
            System.out.println("Error occured :(");
            return;
        }

        items = items + "\n" + name + " - " + " кол-во " +
            count + " шт. - " + " цена " + price + " руб. " + " Общий вес в корзине " + totalWeight;
        totalPrice = count * price;
    }

    public void add(String name, int price, int count, double weight){
        totalWeight = totalWeight + count * weight;
        add(name, price, count);
        sumTotalCostToBasket(totalPrice);
        sumTotalGoodsToBasket(count);
    }

    public void clear() {
        items = "";
        totalPrice = 0;
    }

    public boolean contains(String name) {
        return items.contains(name);
    }

    public void print(String title) {
        System.out.println(title);
        if (items.isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            System.out.println(items);
        }
    }
}
