package util.Orders;


public class ItemDatabase extends Item {
    private static ItemDatabase itemDatabase = new ItemDatabase();
    Item[] items = new Item[1000];
    int[] quantity = new int[items.length];

    private ItemDatabase() {
        Item item = new Item();
        item.setName("Soda");
        item.setPrice(2.99);
        item.setTaxable(true);
        item.setDescription("Coca Cola");
        insertItemInDatabase(item,1);
        insertItemInDatabase(new Item("Milk", "1 quart", 3.99, false), 25);
        insertItemInDatabase(new Item("sugar", "Domino 1 kilo", 5.99, false), 2);
        insertItemInDatabase(new Item("candy", "Paszkesz jelly beans", 2.99, true), 3);
//        Item item = new Item();
//        item.setName("Soda");
//        item.setPrice(2.99);
//        item.setTaxable(true);
//        item.setDescription("Coca Cola");
//        insertItemInDatabase(item,1);
    }

    public static ItemDatabase getInstance() {
        return itemDatabase;
    }

    private void insertItemInDatabase(Item item, int quantity) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                this.quantity[i] = quantity;
                break;
            }
        }
//        items[item.getId()] = item;
//        this.quantity[item.getId()] = quantity;
    }

    public Item getItemById(int id){
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getId() == id) {
                if (quantity[i] > 0) {
                    quantity[i] -= 1;
                } else {
                    items[i].setDescription(items[i].getDescription() + " is out of stock!");
                    items[i].setPrice(0);
                }
                return items[i];
            }
        }
        return null;
    }

}