package net.luna;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vending{

    private static final List<Product> products = Product.getProdcuts();
    private static final List<Coin> coins = Coin.getCoins();
    private static boolean valCoin = false;
    private static double money = 0;
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        vendingMachine();
        
    }

    public static void vendingMachine(){
        System.out.println("VENDING MACHINE");
        System.out.println("<Products available>:");
        products.forEach(p -> System.out.println(p.getId() + "." + p.getName() + " Price $" + p.getPrice() + " DLS"));
        System.out.println("<Coins/Bills accepted>:");
        coins.forEach(c -> System.out.println(c.getName()));
        insertCoin();
    }

    public static double insertCoin(){
        double coin = 0;
        if(money>0)
            System.out.println("--insert 0 to select product!");
        System.out.println("<Insert coin>:");
        coin = scan.nextDouble();      
        validateCoin(coin);
        return coin;
    }

    public static boolean validateCoin(double coin){
        if(coin == 0 && money > 0){
            selectProduct(money);
            return true;
        }
        valCoin = false;
        for(Coin cn : coins)
            if(coin == cn.getValue()){
                valCoin = true;
                money += coin;
                System.out.println("Credit available: $" + money + " DLS");
            }     
        if(!valCoin)
            System.err.println("Invalid coin!");         
        insertCoin();
        return valCoin;
    }

    public static String cancelTransaction(double credit){
            money = 0;
            System.out.println("Devolution $" + credit + " DLS");
            vendingMachine();
        return "Canceled transaction";
    }

    public static String selectProduct(double credit){
        String result = "";
        System.out.println("Select Products:");
        System.out.println("To cancel select: 0");              
        products.forEach(p -> System.err.println(p.getId() + "." + p.getName() + " $" + p.getPrice() + " DLS"));

        double selection;
        selection = scan.nextDouble();

        if(selection == 0)
            return cancelTransaction(credit);

        boolean prodisPresent = false;
        double change = 0;
        for(Product prod : products){
            if(selection == prod.getId()){
                prodisPresent = true;
                if(credit >= prod.getPrice()){
                    result = "Product selected: " + prod.getName();
                    change = credit - prod.getPrice();
                    if(change > 0)
                        result = result + ". Your change: " + change;
                }
                else
                    result = credit + " is insuficient for " + prod.getName();
                break;
            }
        }
        if(prodisPresent){
            money = 0;
            System.out.println(result);
            vendingMachine();
        }
        else {
            System.err.println("Product does not exist");
            selectProduct(credit);
        }

        return result;
    }
}

class Coin{

    private String name;
    private double value;

    public Coin(String name, double value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }

    public static List<Coin> getCoins(){
        List<Coin> coins = new ArrayList<>();
        coins.add(new Coin("PENNY", 0.01));
        coins.add(new Coin("NICKEL", 0.05));
        coins.add(new Coin("DIME", 0.1));
        coins.add(new Coin("QUARTER", 0.25));
        coins.add(new Coin("HALF", 0.5));
        coins.add(new Coin("1 DOLLAR BILL", 1));
        coins.add(new Coin("2 DOLLAR BILL", 2));
        return coins;
    }
}

class Product{

    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public static List<Product> getProdcuts(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1,"CANDY", 0.5));
        products.add(new Product(2,"SNACK", 1));
        products.add(new Product(3,"NUTS", 1));
        products.add(new Product(4,"COKE", 1.5));
        products.add(new Product(5,"PEPSI", 1.2));
        products.add(new Product(6,"SODA", 0.9));
        return products;
    }

}