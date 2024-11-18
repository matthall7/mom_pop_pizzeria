public class Item {
    private String type;
    private String crust;
    private String size;
    private boolean[] toppings;
    private int amount;
    private float price;
    Item(String crust, String size, boolean[] toppings, int amount) {
        type = "pizza";
        this.crust = crust;
        this.size = size;
        this.toppings = toppings;
        this.amount = amount;

        // Calculates the price
        price = 0;
        boolean atLeastOne = false;
        switch (size) {
            case "small" -> {
                price += 5;
                for(int i=1; i<toppings.length; i++) {
                    if(toppings[i]) {
                        atLeastOne = true;
                        price += 0.75f;
                    }
                }
                if(atLeastOne) {
                    price -= 0.75f;
                }
            }
            case "medium" -> {
                price += 7;
                for(int i=1; i<toppings.length; i++) {
                    if(toppings[i]) {
                        atLeastOne = true;
                        price += 1f;
                    }
                }
                if(atLeastOne) {
                    price -= 1f;
                }
            }
            case "large" -> {
                price += 9;
                for(int i=1; i<toppings.length; i++) {
                    if(toppings[i]) {
                        atLeastOne = true;
                        price += 1.25f;
                    }
                }
                if(atLeastOne) {
                    price -= 1.25f;
                }
            }
            default -> {
                price += 11;
                for(int i=1; i<toppings.length; i++) {
                    if(toppings[i]) {
                        atLeastOne = true;
                        price += 1.50f;
                    }
                }
                if(atLeastOne) {
                    price -= 1.50f;
                }
            }
        }
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

    @Override
    public String toString() {
        if(type.equals("pizza")) {
            return "<html>"+size+" "+crust+" pizza<br/>"+toppingsToText(size, toppings)+"<html>";

        } else if(type.equals("side")) {

        } else if(type.equals("drink")) {

        } else {
            return "hi";
        }
        return null;
    }

    private String toppingsToText(String size, boolean[] toppings) {
        String text = "";
        switch (size) {
            case "small" -> {
                //
            }
            case "medium" -> {
                //
            }
            case "large" -> {
                //
            }
            default-> {
                //
            }
        }
        boolean atLeastOne = false;
        for(int i=0; i<toppings.length; i++) {
            if((toppings[i]) && i==0){
                text += "<html>Cheese           $0.00<br/><html>";
            }
            if((toppings[i]) && i==1){
                atLeastOne = true;
                if(atLeastOne) {
                    
                }
                text += "<html>Ham           $0.00<br/><html>";
            }
            if((toppings[i]) && i==2){
                atLeastOne = true;
                text += "<html>Tomato<br/><html>";
            }
            if((toppings[i]) && i==3){
                atLeastOne = true;
                text += "<html>Pepperoni<br/><html>";
            }
            if((toppings[i]) && i==4){
                atLeastOne = true;
                text += "<html>Green Pepper<br/><html>";
            }
            if((toppings[i]) && i==5){
                atLeastOne = true;
                text += "<html>Mushroom<br/><html>";
            }
            if((toppings[i]) && i==6){
                atLeastOne = true;
                text += "<html>Sausage<br/><html>";
            }
            if((toppings[i]) && i==7){
                atLeastOne = true;
                text += "<html>Onion<br/><html>";
            }
            if((toppings[i]) && i==8){
                atLeastOne = true;
                text += "<html>Pineapple<br/><html>";
            }
        }
        return text;
    }
}
