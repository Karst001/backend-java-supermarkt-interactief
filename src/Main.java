import java.util.*;
import helpers.Helpers;

public class Main {
    static Customer customer;
    static String productName;
    static int amount;
    static double unitPrice;

    static Scanner scanner = new Scanner(System.in);

    //next line is put here so it can be used throughout Main
    private static final Map<String, SuperMarket> availableMarkets = new HashMap<>();

    public static void main(String[] args) {
        //create the List of products
        List<Product> productsAlbertHeijn = new ArrayList<>();
        List<Product> productsJumbo = new ArrayList<>();
        List<Product> productsAldi = new ArrayList<>();

        //load the arrays with data
        productsAlbertHeijn.add(new Product("Wit brood", 1.75, 25));
        productsAlbertHeijn.add(new Product("Peren", 1.95, 15));
        productsAlbertHeijn.add(new Product("Toiletpapier", 2.25, 5));
        productsAlbertHeijn.add(new Product("Tandpasta", 3.15, 10));

        productsJumbo.add(new Product("Bananen", 3.25, 80));
        productsJumbo.add(new Product("Peren", 2.15, 20));
        productsJumbo.add(new Product("Kanzi appels", 3.85, 110));
        productsJumbo.add(new Product("Kiwi", 3.25, 10));

        productsAldi.add(new Product("Pizza Hawaii", 3.95, 25));
        productsAldi.add(new Product("Scheerschuim", 4.15, 25));
        productsAldi.add(new Product("Cola", 2.25, 18));
        productsAldi.add(new Product("Wijn", 3.25, 0));


        //setup supermarkets
        availableMarkets.put("albert heijn", new SuperMarket("Albert Heijn", productsAlbertHeijn));
        availableMarkets.put("jumbo", new SuperMarket("Jumbo", productsJumbo));
        availableMarkets.put("aldi", new SuperMarket("Aldi", productsAldi));

        //set a default value for customer
        customer = new Customer("Bob");

        boolean isExit = false;

        //start the interface with the user to prompt for input
        while(!isExit) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1 - Pick a supermarket");
            System.out.println("2 - Purchase a product");
            System.out.println("3 - Restock a product");
            System.out.println("4 - Change product price");
            System.out.println("5 - Add a product");
            System.out.println("6 - Add new supermarket");
            System.out.println("7 - Exit");
            int selectedChoice = scanner.nextInt();
            scanner.nextLine();

            switch(selectedChoice) {
                case 1:
                    promptMenu();
                    break;

                case 2:
                    //make sure a SuperMarket was selected
                    if(customer.getSuperMarket() == null) {
                        System.out.println("Please pick a supermarket first.");
                        break;
                    }

                    validateInput("purchase");
                    break;

                case 3:
                    if (promptMenu()) {
                        validateInput("restock");
                    }
                    break;

                case 4:
                    if (promptMenu()) {
                        validateInput("price_change");
                    }
                    break;

                case 5:
                    if (promptMenu()) {
                        validateInput("add_product");
                    }
                    break;

                case 6:
                    addNewSupermarket();
                    break;

                case 7:
                    System.out.println("Thank you for using our online services.");
                    isExit = true; //end the while loop
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }


    //helpers to prompt the messages to user
    private static boolean setProductName(String superMarketName, String transactionType){
        if (transactionType.equals("purchase")) {
            System.out.println("Which product do you want to purchase from " + superMarketName + "?");
        } else if(transactionType.equals("restock")) {
            System.out.println("Which product do you want to return to " + superMarketName + "?");
        } else if(transactionType.equals("price_change")) {
            System.out.println("Which product do you want change the price for?");
        }

        if(!transactionType.equals("add_product")) {
            productName = scanner.nextLine().trim();

            //validate product as not all markets sell the same
            SuperMarket superMarket = availableMarkets.get(superMarketName.trim().toLowerCase());
            return !superMarket.hasProduct(productName);
        } else {
            return false;
        }
    }


    private static boolean setAmount(String transactionType){
        if (transactionType.equals("purchase")) {
            System.out.println("How many do you want to purchase of product " + productName + "?");
        } else if(transactionType.equals("restock")) {
            System.out.println("How many do you want to return of product " + productName + "?");
        }

        String entry = scanner.nextLine().trim();

        //validate to ensure the entry is numeric
        if(Helpers.isInt(entry)) {
            amount = Integer.parseInt(entry);
            return true;
        } else {
            System.out.println("Invalid input. You must enter a number.");
            return false;
        }
    }


    private static boolean setUnitPrice () {
        System.out.println("Please enter new product price?");
        String entry = scanner.nextLine().trim();

        //validate to ensure the entry is double
        if(Helpers.isDouble(entry)) {
            unitPrice = Double.parseDouble(entry);
            return true;
        } else {
            System.out.println("Invalid input. You must enter a double or whole number.");
            return false;
        }
    }


    private static boolean validateStore(String superMarketName) {
        String key = superMarketName.trim().toLowerCase();
        return Main.availableMarkets.containsKey(key);
    }


    //common helper called by the switch statement
    private static boolean promptMenu() {
        System.out.println("Which supermarket do you want to go to?");
        System.out.println("Pick one of the following:");

        //since we now have the option to add a new market, it ca no longer be hardcoded
        List<SuperMarket> allMarkets = new ArrayList<>(availableMarkets.values());
        for (SuperMarket market : allMarkets) {
            System.out.println("- " + market.getSuperMarketName());
        }

        String superMarketChoice = scanner.nextLine().trim().toLowerCase();

        //validate selection
        if (validateStore(superMarketChoice)) {
            //get the selection from the Map that holds the available Supermarkets
            SuperMarket selectedSuperMarket = availableMarkets.get(superMarketChoice);

            //select the selected supermarket
            customer.goToSuperMarket(selectedSuperMarket);
            return true;
        } else {
            System.out.println("Sorry, this supermarket does not exist.");
            return false;
        }
    }


    //input validation
    private static void validateInput(String transactionType) {
        //validate input
        if (setProductName(customer.getSuperMarket().getSuperMarketName(), transactionType)) {
            if (transactionType.equals("purchase")) {
                System.out.println("Sorry, this supermarket does not sell this product.");
            } else if(transactionType.equals("restock")) {
                System.out.println("Sorry, this supermarket does not sell this product, restock not allowed.");
            }
        } else {
            if (transactionType.equals("purchase")) {
                if(setAmount("purchase")) {
                    //return the product
                    customer.buyItem(productName, amount);
                }
            } else if (transactionType.equals("restock")) {
                if(setAmount("restock")) {
                    //return the product
                    SuperMarket selectedSuperMarket = customer.getSuperMarket();
                    selectedSuperMarket.restockItem(productName, amount, selectedSuperMarket);
                }
            } else if (transactionType.equals("price_change")) {
                if (setUnitPrice()) {
                    SuperMarket selectedSuperMarket = customer.getSuperMarket();
                    selectedSuperMarket.setUnitPrice(productName, unitPrice , selectedSuperMarket);
                }
            } else if (transactionType.equals("add_product")) {
                SuperMarket selectedSuperMarket = customer.getSuperMarket();

                System.out.println("Please enter new product name?");
                productName = scanner.nextLine().trim();

                //check if superMarket already has this product, if not, add the product
                if (!selectedSuperMarket.hasProduct(productName)) {
                    System.out.println("Please enter the product price");
                    unitPrice = Double.parseDouble(scanner.nextLine().trim());

                    System.out.println("Please enter initial stock quantity");
                    amount = Integer.parseInt(scanner.nextLine().trim());

                    //finally, add the product to the selected superMarket
                    selectedSuperMarket.products.add(new Product(productName, unitPrice, amount));
                    System.out.println("New product '" + productName + "' has been added.");
                } else {
                    System.out.println("Sorry, product '" + productName + "' is already exists in this supermarket.");
                }
            }
        }
    }

    private static void addNewSupermarket(){
        System.out.println("Please enter the name of the new supermarket:");
        String marketName = scanner.nextLine().trim();

        //do some checking
        if (marketName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        String marketKey = marketName.toLowerCase();

        //check if this supermarket exists already
        if (availableMarkets.containsKey(marketKey)) {
            System.out.println("Sorry, that supermarket already exists.");
            return;
        }

        //create empty product list
        List<Product> productsForNewMarket = new ArrayList<>();

        //create new market with the empty product list
        SuperMarket newMarket = new SuperMarket(marketName,productsForNewMarket);

        //add the new market to the existing list of markets so it can be used
        availableMarkets.put(marketKey, newMarket);

        System.out.println("Added supermarket '" + marketName + "', you can now add products");
    }
}

