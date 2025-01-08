package il.cshaifasweng.OCSFMediatorExample.entities;

public class MenuItem {
    private int id;
    private String name;
    private String ingredients;
    private String preferences;
    private double price;

    public MenuItem(int id, String name, String ingredients, String preferences, double price) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.preferences = preferences;
        this.price = price;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
