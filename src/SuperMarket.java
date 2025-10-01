import java.util.List;

public class SuperMarket {
    //define the attributes that use class Product
    private String name;
    public List<Product> products;


    public SuperMarket(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }


    //helper to return superMarket name
    public String getSuperMarketName() {
        return name;
    }

    //method to update stock qty and print result called from Customer
    public void buyItem(Product product, int amount) {
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

    //method to restock item and print result
    public void restockItem(String productName, int amount, SuperMarket superMarket) {
        //iterate through this.products and check for each product in this.products if product.name === String productName that was passed as param
        for ( Product product : superMarket.products) {
            if (product.name.equalsIgnoreCase(productName)) {
                //restock the product
                product.amount += amount;

                //product found so exit
                System.out.println("Product '" + productName + "' was returned to the store with a total of " + amount + " pieces.\nThere are now " + product.amount + " in stock.");
                return;
            }
        }

        //product not found so warn user
        System.out.println("The selected product '" + productName + "' was not found.");
    }

    public boolean hasProduct(String productName) {
        //check if products is not null
        if (products == null) {
            return false;
        }

        //iterate through products
        for (Product prod : products) {
            if (prod.name != null && prod.name.equalsIgnoreCase(productName.trim())) {
                return true;
            }
        }
        return false;
    }


    public void setUnitPrice(String productName, double unitPrice, SuperMarket superMarket) {
        //iterate through this.products and check for each product in this.products if product.name === String productName that was passed as param
        for ( Product product : superMarket.products) {
            if (product.name.equalsIgnoreCase(productName)) {
                //update product price
                double currentUnitPrice = product.unitPrice;

                //set new price
                product.unitPrice = unitPrice;

                //product found so exit
                System.out.println("Product '" + productName + "' previous price was " +  currentUnitPrice + " and is now changed to " + product.unitPrice);
                return;
            }
        }

        //product not found so warn user
        System.out.println("The selected product '" + productName + "' was not found.");
    }
}
