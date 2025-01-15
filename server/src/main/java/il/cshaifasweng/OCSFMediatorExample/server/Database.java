package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.entities.MenuItem;
import org.hibernate.Session;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.ArrayList;


public class Database{
    private static Session session;
    private List<MenuItem> menuItems;

    public Database() {
        this.menuItems = new ArrayList<>();
        initializeMenu();
    }
    private static SessionFactory getSessionFactory(String password) throws HibernateException {

        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/MyFirstDatabase?serverTimezone=UTC");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.hbm2ddl.auto", "create");

        configuration.setProperties(settings);
        // Add ALL of your entities here
        configuration.addAnnotatedClass(MenuItem.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    private void initializeMenu() {
        // Manually add menu items as if you were pulling them from a database
        menuItems.add(new MenuItem(1, "Pizza", "Cheese, Tomato, Onions, Mushroom", "Vegetarian", 60.00));
        menuItems.add(new MenuItem(2, "Hamburger", "Beef, Lettuce, Tomato", "No Cheese", 65.00));
        menuItems.add(new MenuItem(3, "Vegan Hamburger", "Vegan patty, Tomato, Pickles, Lettuce", "Vegan", 60.00));
        menuItems.add(new MenuItem(4,"SOUR CREAM SPINACH PASTA","Sour cream,Garlic,Spinach","Gluten-Free",55));
        menuItems.add(new MenuItem(5,"CEASAR SALAR","Lettuce,Chicken breast,Parmesan cheese,Onions","Keto-Friendly",60));
        menuItems.add(new MenuItem(6,"FATTO TIRAMISU","","",18));
        menuItems.add(new MenuItem(7,"SCUGNIZIELLI NUTELLA GELATO","","",15));
        menuItems.add(new MenuItem(8,"LEMON MEGINGUE","","",17));
        menuItems.add(new MenuItem(9,"CHOCOLATE SALTED CARAMEL","","",15));
        menuItems.add(new MenuItem(10,"ESPRESSOO","","",8));
        menuItems.add(new MenuItem(11,"MACCHIATO","","",10));
        for (MenuItem meal : menuItems) {
            session.save(meal);
        }
        session.flush(); // Ensure data is saved
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public void updateMenuItem(int id, String name, String ingrediants, String prefrences, double price) {
        for (MenuItem item : menuItems) {
            if (item.getId() == id) {
                item.setName(name);
                item.setIngredients(ingrediants);
                item.setPreferences(prefrences);
                item.setPrice(price);
                break;
            }
        }
    }

    public void deleteMenuItem(int id) {
        menuItems.removeIf(item -> item.getId() == id);
    }
}
