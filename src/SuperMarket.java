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

    //method to update stock qty and print result
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
}
