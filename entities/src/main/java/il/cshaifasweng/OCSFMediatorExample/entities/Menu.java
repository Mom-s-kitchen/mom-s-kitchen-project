package il.cshaifasweng.OCSFMediatorExample.entities;

public class Menu {
    private MenuItem[] menuItems;
    private int itemCount; // To track the actual number of items in the menu

    // Constructor with a fixed size
    public Menu(int maxItems) {
        menuItems = new MenuItem[maxItems];
        itemCount = 0;
    }

    // Add a menu item
    public void addMenuItem(MenuItem item) {
        if (itemCount < menuItems.length) {
            menuItems[itemCount] = item;
            itemCount++;
        } else {
            System.out.println("Menu is full, cannot add more items.");
        }
    }

    // Get all menu items
    public  MenuItem[] getMenuItems() {
        return menuItems;
    }

    // Get item count
    public int getItemCount() {
        return itemCount;
    }
}
