package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

public class SimpleClient extends AbstractClient {

	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof Warning) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		} else if (msg instanceof String) {
			handleServerResponse((String) msg);
		} else {
			System.out.println("Unhandled message type: " + msg.getClass());
		}
	}

	private void handleServerResponse(String message) {
		// Example response format: "UPDATE_PRICE_SUCCESS;Pizza;50.00"
		if (message.startsWith("UPDATE_PRICE_SUCCESS")) {
			String[] parts = message.split(";");
			String foodName = parts[1];
			String newPrice = parts[2];
			//EventBus.getDefault().post(new UpdatePriceEvent(foodName, newPrice));
			System.out.println("Price updated successfully: " + foodName + " -> " + newPrice);
		} else if (message.startsWith("UPDATE_PRICE_FAILURE")) {
			String reason = message.split(";")[1];
			//EventBus.getDefault().post(new UpdatePriceFailureEvent(reason));
			System.out.println("Failed to update price: " + reason);
		} else {
			System.out.println("Server message: " + message);
		}
	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

	public static void sendUpdatePriceRequest(String foodName, double newPrice) {
		try {
			String request = "UPDATE_PRICE;" + foodName + ";" + newPrice;
			getClient().sendToServer(request);
		} catch (Exception e) {
			System.err.println("Failed to send update price request: " + e.getMessage());
		}
	}
}
