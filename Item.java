import java.util.Arrays;

public class Item {
    private String type;
    private String crust;
    private String size;
    private String side;
    private String drink;
    private boolean[] toppings;
    private float price;
    Item(String crust, String size, boolean[] toppings) {
        type = "pizza";
        this.crust = crust;
        this.size = size;
        this.toppings = Arrays.copyOf(toppings, toppings.length);

        // Calculates the price
        price = 0;
        boolean atLeastOne = false;
        switch (size) {
            case "Small" -> {
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
            case "Medium" -> {
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
            case "Large" -> {
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

    Item(String side) {
        type = "side";
        this.side = side;
        if(side.equals("breadStick")) {
            price = 4f;
        } else {
            price = 2f;
        }
    }

    Item(String drink, String size) {
        type = "drink";
        this.drink = drink;
        this.size = size;
        price = 1.75f;
    }

    Item() {
        type = "dessert";
        price = 4f;
    }

    @Override
    public String toString() {
        if(type.equals("pizza")) {
            if(size.equals("Small")) {
                return "<html>"+size+" "+crust+" Pizza ........................................ $5.00<br/>"+toppingsToText(size, toppings)+"</html>";
            } else if(size.equals("Medium")) {
                return "<html>"+size+" "+crust+" Pizza ................................. $7.00<br/>"+toppingsToText(size, toppings)+"</html>";
            } else if(size.equals("Large")) {
                return "<html>"+size+" "+crust+" Pizza .................................... $9.00<br/>"+toppingsToText(size, toppings)+"</html>";
            } else {
                return "<html>"+size+" "+crust+" Pizza ............................ $11.00<br/>"+toppingsToText(size, toppings)+"</html>";
            }
        } else if(type.equals("side")) {
            if(side.equals("breadStick")) {
                return "Breadsticks .......................................... $4.00";
            } else {
                return "Breadtick Bites ....................................... $2.00";
            }
        } else if(type.equals("drink")) {
            return size+" "+drink+"................................ $1.75";
        } else {
            return "Mega Chocolate Chip Cooke ...................... $4.00";
        }
    }

    private String toppingsToText(String size, boolean[] toppings) {
        String text = "";
        switch (size) {
            case "Small" -> {
                boolean atLeastOne = false;
                for(int i=0; i<toppings.length; i++) {
                    if((toppings[i]) && i==0){
                        text += "Cheese  ....................................................... $0.00<br/>";
                    }
                    if((toppings[i]) && i==1){
                        if(atLeastOne) {
                            text += "Ham  ........................................................... $0.75<br/>";
                        } else {
                            text += "Ham  ........................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==2){
                        if(atLeastOne) {
                            text += "Tomato  ....................................................... $0.75<br/>";
                        } else {
                            text += "Tomato  ....................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==3){
                        if(atLeastOne) {
                            text += "Pepperoni  ................................................... $0.75<br/>";
                        } else {
                            text += "Pepperoni  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==4){
                        if(atLeastOne) {
                            text += "Green Pepper  ............................................. $0.75<br/>";
                        } else {
                            text += "Green Pepper  ............................................. $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==5){
                        if(atLeastOne) {
                            text += "Mushroom  ................................................... $0.75<br/>";
                        } else {
                            text += "Mushroom  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==6){
                        if(atLeastOne) {
                            text += "Sausage  ..................................................... $0.75<br/>";
                        } else {
                            text += "Sausage  ..................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==7){
                        if(atLeastOne) {
                            text += "Onion  ......................................................... $0.75<br/>";
                        } else {
                            text += "Onion  ......................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==8){
                        if(atLeastOne) {
                            text += "Pineapple  ................................................... $0.75<br/>";
                        } else {
                            text += "Pineapple  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                }
            }
            case "Medium" -> {
                boolean atLeastOne = false;
                for(int i=0; i<toppings.length; i++) {
                    if((toppings[i]) && i==0){
                        text += "Cheese  ....................................................... $0.00<br/>";
                    }
                    if((toppings[i]) && i==1){
                        if(atLeastOne) {
                            text += "Ham  ........................................................... $1.00<br/>";
                        } else {
                            text += "Ham  ........................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==2){
                        if(atLeastOne) {
                            text += "Tomato  ....................................................... $1.00<br/>";
                        } else {
                            text += "Tomato  ....................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==3){
                        if(atLeastOne) {
                            text += "Pepperoni  ................................................... $1.00<br/>";
                        } else {
                            text += "Pepperoni  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==4){
                        if(atLeastOne) {
                            text += "Green Pepper  ............................................. $1.00<br/>";
                        } else {
                            text += "Green Pepper  ............................................. $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==5){
                        if(atLeastOne) {
                            text += "Mushroom  ................................................... $1.00<br/>";
                        } else {
                            text += "Mushroom  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==6){
                        if(atLeastOne) {
                            text += "Sausage  ..................................................... $1.00<br/>";
                        } else {
                            text += "Sausage  ..................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==7){
                        if(atLeastOne) {
                            text += "Onion  ......................................................... $1.00<br/>";
                        } else {
                            text += "Onion  ......................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==8){
                        if(atLeastOne) {
                            text += "Pineapple  ................................................... $1.00<br/>";
                        } else {
                            text += "Pineapple  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                }
            }
            case "Large" -> {
                boolean atLeastOne = false;
                for(int i=0; i<toppings.length; i++) {
                    if((toppings[i]) && i==0){
                        text += "Cheese  ....................................................... $0.00<br/>";
                    }
                    if((toppings[i]) && i==1){
                        if(atLeastOne) {
                            text += "Ham  ........................................................... $1.25<br/>";
                        } else {
                            text += "Ham  ........................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==2){
                        if(atLeastOne) {
                            text += "Tomato  ....................................................... $1.25<br/>";
                        } else {
                            text += "Tomato  ....................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==3){
                        if(atLeastOne) {
                            text += "Pepperoni  ................................................... $1.25<br/>";
                        } else {
                            text += "Pepperoni  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==4){
                        if(atLeastOne) {
                            text += "Green Pepper  ............................................. $1.25<br/>";
                        } else {
                            text += "Green Pepper  ............................................. $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==5){
                        if(atLeastOne) {
                            text += "Mushroom  ................................................... $1.25<br/>";
                        } else {
                            text += "Mushroom  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==6){
                        if(atLeastOne) {
                            text += "Sausage  ..................................................... $1.25<br/>";
                        } else {
                            text += "Sausage  ..................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==7){
                        if(atLeastOne) {
                            text += "Onion  ......................................................... $1.25<br/>";
                        } else {
                            text += "Onion  ......................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==8){
                        if(atLeastOne) {
                            text += "Pineapple  ................................................... $1.25<br/>";
                        } else {
                            text += "Pineapple  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                }
            }
            default-> {
                boolean atLeastOne = false;
                for(int i=0; i<toppings.length; i++) {
                    if((toppings[i]) && i==0){
                        text += "Cheese  ....................................................... $0.00<br/>";
                    }
                    if((toppings[i]) && i==1){
                        if(atLeastOne) {
                            text += "Ham  ........................................................... $1.50<br/>";
                        } else {
                            text += "Ham  ........................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==2){
                        if(atLeastOne) {
                            text += "Tomato  ....................................................... $1.50<br/>";
                        } else {
                            text += "Tomato  ....................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==3){
                        if(atLeastOne) {
                            text += "Pepperoni  ................................................... $1.50<br/>";
                        } else {
                            text += "Pepperoni  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==4){
                        if(atLeastOne) {
                            text += "Green Pepper  ............................................. $1.50<br/>";
                        } else {
                            text += "Green Pepper  ............................................. $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==5){
                        if(atLeastOne) {
                            text += "Mushroom  ................................................... $1.50<br/>";
                        } else {
                            text += "Mushroom  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==6){
                        if(atLeastOne) {
                            text += "Sausage  ..................................................... $1.50<br/>";
                        } else {
                            text += "Sausage  ..................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==7){
                        if(atLeastOne) {
                            text += "Onion  ......................................................... $1.50<br/>";
                        } else {
                            text += "Onion  ......................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                    if((toppings[i]) && i==8){
                        if(atLeastOne) {
                            text += "Pineapple  ................................................... $1.50<br/>";
                        } else {
                            text += "Pineapple  ................................................... $0.00<br/>";
                            atLeastOne = true;
                        }
                    }
                }
            }
        }
        return text;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
