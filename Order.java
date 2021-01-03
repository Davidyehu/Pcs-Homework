package util.Orders;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Order {

    private String name;
    private Address address = new Address();
    private final Item[] instanceItems = new Item[15];
    private static double taxRate = .17; //that's the tax rate on most things here in Israel

    /**
     * @deprecated use the constructor that sets fields
     */
    public Order() {
    }

    public Order(String name, String street, String city, String state, String zip, int... itemId) {
        this.name = name;
        this.address = new Address(street, city, state, zip);
        for (int i : itemId) {
            for (int j = 0; j < instanceItems.length; j++) {
                if (instanceItems[j] == null) {
                    instanceItems[j] = ItemDatabase.getInstance().getItemById(i);
                    if (instanceItems[j] == null) {
                        System.out.println(i + " is out of stock!!");
                    }
                    break;
                }
            }
        }
    }

    /**
     * @deprecated
     * @param index
     * @param name
     * @param description
     * @param price
     */
    public void insertItemInArray(int index, String name, String description, double price, boolean taxable) {
        if (index < instanceItems.length && instanceItems[index] != null) {
            instanceItems[index].setName(name);
            instanceItems[index].setDescription(description);
            instanceItems[index].setPrice(price);
            instanceItems[index].setTaxable(taxable);
        }
    }

    /**
     * @deprecated
     * @param index
     * @param item
     */
    public void setItemInArray(int index, Item item) {
        instanceItems[index] = item;
    }

    public Item[] getInstanceItems() {
        return instanceItems;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * @deprecated set address by passing address into Order constructor
     * @param street
     * @param city
     * @param state
     * @param zip
     */
    public void setAddress(String street, String city, String state, String zip) {
        address.street = street;
        address.city = city;
        address.state = state;
        address.zip = zip;
    }

    public double getSubTotal() {
        double total = 0;
        for (Item i : instanceItems) {
            if (i != null) {
                total += i.getPrice();
            }
        }
        return total;
    }

    public double getTax() {
        double tax = 0;
        for (Item i : instanceItems) {
            if (i != null && i.getIsTaxable()) {
                tax += i.getPrice() * taxRate;
            }
        }
        return tax;
    }

    public double getTotal() {
        return getSubTotal() + getTax();
    }

    public void print() {
        System.out.printf("Customer name: %s, Customer address: %s, %s, %s %s. Costumer order:\n",
                name, getAddress().street, getAddress().city, getAddress().state, getAddress().zip);
        for (Item i : instanceItems) {
            if (i != null && i.getName() != null) {
                i.print();
            }
        }
//        DecimalFormat decimalFormat = new DecimalFormat("####.##");
//        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
//        String subTotal = decimalFormat.format(getSubTotal());
//        String tax = decimalFormat.format(getTax());
//        String total = decimalFormat.format(getTotal());
        System.out.printf("Subtotal: %.2f, Tax: %.2f, Total: %.2f \r\n\r\n%n", getSubTotal(), getTax(), getTotal());
    }

    public class Address{
        private String street;
        private String city;
        private String state;
        private String zip;

        public Address() {
        }

        public Address(String street, String city, String state, String zip) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", zip='" + zip + '\'' +
                    '}';
        }
    }
}
