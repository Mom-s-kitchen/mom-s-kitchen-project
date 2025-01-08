package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
/*import java.sql.Connetion;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;*/
import java.io.IOException;
import java.util.ArrayList;

import il.cshaifasweng.OCSFMediatorExample.entities.*;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private Menu menu;
	public SimpleServer(int port) {
		super(port);
		menu = new Menu(10); // Initialize the menu (let's assume the menu can have up to 10 items)
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msgString.startsWith("add client")) {
			SubscribedClient connection = new SubscribedClient(client);
			SubscribersList.add(connection);
			try {
				client.sendToClient("client added successfully");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else if (msgString.startsWith("remove client")) {
			if (!SubscribersList.isEmpty()) {
				for (SubscribedClient subscribedClient : SubscribersList) {
					if (subscribedClient.getClient().equals(client)) {
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		}
		//from here to line 82 is an addition
		// Fetch menu from the server and send to client
		else if (msgString.startsWith("fetch menu")) {
			MenuItem[] items = menu.getMenuItems();
			try {
				client.sendToClient(items); // Send the menu items (array) to the client
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Update a menu item
		else if (msgString.startsWith("update menu item")) {
			String[] parts = msgString.split(";");
			int id = Integer.parseInt(parts[1]);
			double newPrice = Double.parseDouble(parts[2]);
			updateMenuItem(id, newPrice); // Update the menu item in the array
			try {
				client.sendToClient("Menu item updated successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Method to update a menu item based on the given ID and price
	private void updateMenuItem(int id, double newPrice) {
		MenuItem[] items = menu.getMenuItems();
		for (int i = 0; i < menu.getItemCount(); i++) {
			if (items[i].getId() == id) {
				items[i].setPrice(newPrice); // Update the price of the menu item
				break;
			}
		}
	}
	public void sendToAllClients(String message) {
		try {
			for (SubscribedClient subscribedClient : SubscribersList) {
				subscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
