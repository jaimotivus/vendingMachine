package net.luna;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

/**
 * Vending machine application that allows users to insert coins and purchase products.
 */
public class Vending {
    private static final List<Product> PRODUCTS = Product.getProducts();
    private static final List<Coin> ACCEPTED_COINS = Coin.getCoins();
    private static final Scanner SCANNER = new Scanner(System.in);
    
    private static double currentCredit = 0;

    public static void main(String[] args) {
        startVendingMachine();
    }

    /**
     * Displays the vending machine interface and starts the interaction.
     */
    public static void startVendingMachine() {
        displayWelcomeMessage();
        handleCoinInsertion();
    }

    private static void displayWelcomeMessage() {
        System.out.println("VENDING MACHINE");
        System.out.println("<Products available>:");
        PRODUCTS.forEach(p -> System.out.println(p.getId() + "." + p.getName() + " Price $" + p.getPrice() + " DLS"));
        System.out.println("<Coins/Bills accepted>:");
        ACCEPTED_COINS.forEach(c -> System.out.println(c.getName()));
    }

    /**
     * Handles the coin insertion process.
     */
    public static double handleCoinInsertion() {
        if (currentCredit > 0) {
            System.out.println("--insert 0 to select product!");
        }
        
        System.out.println("<Insert coin>:");
        double insertedValue = SCANNER.nextDouble();
        
        processCoinInput(insertedValue);
        return insertedValue;
    }

    /**
     * Processes the inserted coin value.
     */
    public static boolean processCoinInput(double coinValue) {
        // If user enters 0 and has credit, proceed to product selection
        if (coinValue == 0 && currentCredit > 0) {
            selectProduct(currentCredit);
            return true;
        }
        
        // Validate if the inserted coin is accepted
        boolean isValidCoin = false;
        for (Coin coin : ACCEPTED_COINS) {
            if (coinValue == coin.getValue()) {
                isValidCoin = true;
                currentCredit += coinValue;
                System.out.println("Credit available: $" + currentCredit + " DLS");
                break;
            }
        }
        
        if (!isValidCoin) {
            System.err.println("Invalid coin!");
        }
        
        // Continue with coin insertion
        handleCoinInsertion();
        return isValidCoin;
    }

    /**
     * Cancels the current transaction and returns the inserted money.
     */
    public static String cancelTransaction(double credit) {
        System.out.println("Devolution $" + credit + " DLS");
        currentCredit = 0;
        startVendingMachine();
        return "Canceled transaction";
    }

    /**
     * Handles product selection based on available credit.
     */
    public static String selectProduct(double credit) {
        System.out.println("Select Products:");
        System.out.println("To cancel select: 0");
        PRODUCTS.forEach(p -> System.err.println(p.getId() + "." + p.getName() + " $" + p.getPrice() + " DLS"));

        double selection = SCANNER.nextDouble();

        // Handle cancellation
        if (selection == 0) {
            return cancelTransaction(credit);
        }

        // Find the selected product
        Optional<Product> selectedProduct = PRODUCTS.stream()
                .filter(p -> p.getId() == selection)
                .findFirst();

        if (selectedProduct.isPresent()) {
            Product product = selectedProduct.get();
            String result;
            
            if (credit >= product.getPrice()) {
                double change = credit - product.getPrice();
                result = "Product selected: " + product.getName();
                
                if (change > 0) {
                    result = result + ". Your change: " + change;
                }
            } else {
                result = credit + " is insufficient for " + product.getName();
            }
            
            currentCredit = 0;
            System.out.println(result);
            startVendingMachine();
            return result;
        } else {
            System.err.println("Product does not exist");
            return selectProduct(credit);
        }
    }
}

/**
 * Represents a coin or bill accepted by the vending machine.
 */
class Coin {
    private String name;
    private double value;

    public Coin(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Returns the list of accepted coins and bills.
     */
    public static List<Coin> getCoins() {
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

/**
 * Represents a product available in the vending machine.
 */
class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the list of available products.
     */
    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "CANDY", 0.5));
        products.add(new Product(2, "SNACK", 1));
        products.add(new Product(3, "NUTS", 1));
        products.add(new Product(4, "COKE", 1.5));
        products.add(new Product(5, "PEPSI", 1.2));
        products.add(new Product(6, "SODA", 0.9));
        return products;
    }
}