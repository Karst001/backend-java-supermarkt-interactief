import java.util.*;


public class Main {
    static Customer customer;
    static String productName;
    static int amount;

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
        String superMarketChoice = "";
        SuperMarket selectedSuperMarket;

        //start the interface with the user to prompt for input
        while(!isExit) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1 - Pick a supermarket");
            System.out.println("2 - buy a product");
            System.out.println("3 - restock a product");
            System.out.println("4 - exit");
            int selectedChoice = scanner.nextInt();
            scanner.nextLine();

            switch(selectedChoice) {
                case 1:
                    System.out.println("Which supermarket do you want to go to?");
                    System.out.println("Pick one of the following:");
                    System.out.println("- Albert Heijn");
                    System.out.println("- Jumbo");
                    System.out.println("- Aldi");
                    superMarketChoice = scanner.nextLine().trim().toLowerCase();

                    //validate selection
                    if (validateStore(superMarketChoice)) {
                        //get the selection from the Map that holds the available Supermarkets
                        selectedSuperMarket = availableMarkets.get(superMarketChoice);

                        //select the selected supermarket
                        customer.goToSuperMarket(selectedSuperMarket);
                    } else {
                        System.out.println("Sorry, this supermarket does not exist.");
                    }

                    break;

                case 2:
                    //make sure a SuperMarket was selected
                    if(customer.getSuperMarket() == null) {
                        System.out.println("Please pick a supermarket first.");
                        break;
                    }

                    //validate input
                    if(setProductName(customer.getSuperMarket().getSuperMarketName(), "purchase")) {
                        System.out.println("Sorry, this supermarket does not sell this product.");
                    } else {
                        setAmount("purchase");

                        //make the purchase
                        customer.buyItem(productName, amount);
                    }

                    break;

                case 3:
                    System.out.println("Which supermarket do you want to restock?");
                    System.out.println("Pick one of the following:");
                    System.out.println("- Albert Heijn");
                    System.out.println("- Jumbo");
                    System.out.println("- Aldi");
                    superMarketChoice = scanner.nextLine().toLowerCase();

                    //validate selection
                    if (validateStore(superMarketChoice)) {
                        //get the selection from the Map that holds the available Supermarkets
                        selectedSuperMarket = availableMarkets.get(superMarketChoice);

                        //select the selected supermarket
                        customer.goToSuperMarket(selectedSuperMarket);

                        //validate input
                        if(setProductName(customer.getSuperMarket().getSuperMarketName(), "restock")) {
                            System.out.println("Sorry, this supermarket does not sell this product, restock not allowed.");
                        } else {
                            setAmount("restock");

                            //return the product
                            customer.restockItem(productName, amount);
                        }
                    } else {
                        System.out.println("Sorry, this supermarket does not exist.");
                    }

                    break;

                case 4:
                    System.out.println("Thank you for using our online services.");
                    isExit = true; //end the while loop
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    //local helper method to update stock qty and print result
    private void buyItem(Product product, int amount) {
        if (amount <= 0) {
            System.out.println("Warning: you cannot order a negative quantity.");
            return;
        }

        if (amount > product.amount) {
            System.out.println("You cannot buy " + amount + " units of product " + product.name + ", unfortunately we only have " + product.amount + " in stock.");
        } else {
            //deduct the sold amount from existing product amount
            product.amount -= amount;
            double totalOrderValue = amount * product.unitPrice;
            System.out.println("You bought " + amount + " pcs. of " + product.name + " for a total of " + totalOrderValue + " euros.");
        }
    }

    //helpers to prompt the messages to user
    private static boolean setProductName(String superMarketName, String transactionType){
        if (transactionType.equals("purchase")) {
            System.out.println("Which product do you want to purchase from " + superMarketName + "?");
        } else if(transactionType.equals("restock")) {
            System.out.println("Which product do you want to return to " + superMarketName + "?");
        }

        productName = scanner.nextLine().trim();

        //validate product as not all markets sell the same
        SuperMarket superMarket = availableMarkets.get(superMarketName.trim().toLowerCase());
        return !superMarket.hasProduct(productName);
    }

    private static void setAmount(String transactionType){
        if (transactionType.equals("purchase")) {
            System.out.println("How many do you want to purchase of product " + productName + "?");
        } else if(transactionType.equals("restock")) {
            System.out.println("How many do you want to return of product " + productName + "?");
        }

        amount = Integer.parseInt(scanner.nextLine().trim());
    }


    private static boolean validateStore(String superMarketName) {
        String key = superMarketName.trim().toLowerCase();
        return Main.availableMarkets.containsKey(key);
    }
}
