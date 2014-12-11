package com.google.gwt.pop.client;

import java.util.ArrayList;

import com.google.gwt.pop.shared.DrinkingFountain;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DrinkingServiceAsync {

	void deleteDrinkData(AsyncCallback<Void> callback);

	void getDrinkpoints(AsyncCallback<ArrayList<DrinkingFountain>> callback);

	void importDrinkingData(AsyncCallback<Void> callback);

}
