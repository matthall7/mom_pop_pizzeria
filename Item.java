public class Item {
    private String type;
    private String crust;
    private String size;
    private boolean[] toppings;
    private int amount;
    private float price;
    Item(String crust, String size, boolean[] toppings, int amount) {
        type = "pizza";
        price = 0;
        this.crust = crust;
        this.size = size;
        this.toppings = toppings;
        this.amount = amount;
    }

    Item(String side, int amount) {
        type = "side";
        this.amount = amount;
    }

    Item(String drink, String size, int amount) {
        type = "drink";
        this.size = size;
        this.amount = amount;
    }

    Item(int amount) {
        type = "dessert";
        this.amount = amount;
    }
}
