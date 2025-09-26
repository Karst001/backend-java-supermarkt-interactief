public class Product {
    //attribute definition
    public String name;
    public double unitPrice;
    public int amount;

    //constructor that can be called from another class or method
    public Product(String name, double unitPrice, int amount) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }
}
