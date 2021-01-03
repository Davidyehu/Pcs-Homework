package util.Orders;

public class Item {
    private static int availableID = -1;//I think
    private String name;
    private String description;
    private double price;
    private boolean isTaxable;
    private int id;

    protected Item() {
        this.id = availableID++;
    }

    protected Item(String name, String description, double price, boolean isTaxable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isTaxable = isTaxable;
        this.id = availableID++;
    }

    public int getId() {
        return id;
    }

    /**
     * @deprecated is used only in Database so no need to change
     * @param name
     * @param description
     * @param price
     */
    public void setFields(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsTaxable() {
        return isTaxable;
    }

    public void setTaxable(boolean taxable) {
        isTaxable = taxable;
    }

    public void print() {
        System.out.printf("%s, %s, %s, is Taxed: %s\n", name, description, price, isTaxable);
    }
}
