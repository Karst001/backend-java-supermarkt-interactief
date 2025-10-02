

public class Customer {
    //define attributes
    private String name;
    private SuperMarket superMarket;

    //constructor
    public Customer(String name) {
        this.name = name;
    }

    //public methods
    public void goToSuperMarket(SuperMarket superMarket) {
        this.superMarket = superMarket;
    }

    //helper to return superMarket
    public SuperMarket getSuperMarket() {
        return superMarket;
    }

    //public method called from Main
    public void buyItem(String productName, int amount){
        //Check if customer already selected a SuperMarket, if not exit
        if(superMarket == null){
            System.out.println("Select a supermarket to go to first");
            return;
        }

        //iterate through the superMarket.products and check for each product in superMarket.products if product.name === String productName that was passed as param
        for ( Product product : superMarket.products) {
            if (product.name.equalsIgnoreCase(productName)) {
                //pass the values to superMarket.buyItem method
                superMarket.buyItem(product, amount);

                //product found so exit
                return;
            }
        }

        //product not found so warn user
        System.out.println("The selected supermarket' " + superMarket + "' does not sell '" + productName + "'");
    }

//    //method to restock item and print result
//    public void restockItem(String productName, int amount){
//        //iterate through this.products and check for each product in this.products if product.name === String productName that was passed as param
//        for ( Product product : superMarket.products) {
//            if (!product.name.equalsIgnoreCase(productName)) {
//                //restock the product
//                product.amount += amount;
//
//                //product found so exit
//                System.out.println("Product '" + productName + "' was returned to the store with a total of " + amount + " pieces.");
//                return;
//            }
//        }
//
//        //product not found so warn user
//        System.out.println("The selected product '" + productName + "' was not found.");
//    }
}
