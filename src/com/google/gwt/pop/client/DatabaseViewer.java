package com.google.gwt.pop.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.pop.shared.DrinkingFountain;
import com.google.gwt.pop.shared.PointOfInterest;
import com.google.gwt.pop.shared.UserInformation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

//this creates the database viewer
public class DatabaseViewer extends Composite{
	//GWT calls
	private final static POIServiceAsync poiService = GWT.create(POIService.class);
	private final static DrinkingServiceAsync drinkService = GWT.create(DrinkingService.class);
	private final static UserInfoServiceAsync userInfoService = GWT.create(UserInfoService.class);
		
	//FlexTable and horizontal panel
	private FlexTable dbTable = new FlexTable();;
	private VerticalPanel addPanel = new VerticalPanel();
	private FlexTable buttonTable = new FlexTable();
	private FlexTable layoutTable = new FlexTable();
	
	//Buttons
	private Button fountainData = new Button("View Fountain Data");
	private Button userData = new Button("View User information");
	private Button bikeData = new Button("BikeRack data");
	private Button impButton = new Button ("Import poi p1");
	private Button impButtonp2 = new Button ("import poi p2");
	private Button impButtonp3 = new Button ("import poi p3");
	private Button impButtonp4 = new Button ("import poi p4");
	private Button drinkButton = new Button("import drink dat");
	private Button drinkdelButton = new Button("delete drink data");
	private Button delPointsButton = new Button("delete POI data");
	private Button pullData = new Button("refresh data");
	
	//set pop
	Pop pop = new Pop();
	
	//local values
	private static ArrayList<PointOfInterest> allPoints = new ArrayList<PointOfInterest>();
	private static ArrayList<DrinkingFountain> allFountains = new ArrayList<DrinkingFountain>();
	private static ArrayList<UserInformation> allUsers = new ArrayList<UserInformation>();
	
	public DatabaseViewer(ArrayList<PointOfInterest> points, ArrayList<DrinkingFountain> fountains){
		
		
		fountainData.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				dbTable.removeAllRows();
				initFountainTable();
				displayFountainPoints(allFountains);
				initWidget(dbTable);
			}
		});
		
		// Needs Chris' final copy
		userData.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				dbTable.removeAllRows();
				initUserTable();
				displayUserPoints(allUsers);
				initWidget(dbTable);
				
			}
		});
		
		bikeData.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("success bd");
				bikeData.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						dbTable.removeAllRows();
						initPointsTable();
						displayPoints(allPoints);
						initWidget(dbTable);
					}
				});
				
			}
		});
		
		impButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				poiService.importDatap1(new AsyncCallback<Void>(){
					
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert("We couldn't import data1");
					}
					@Override
					public void onSuccess(Void result)
					{
						Window.alert("data1 is imported");
					}
				});
				
			
			}
		});
		
		//Imports data part 2 into datastore
				impButtonp2.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						poiService.importDatap2(new AsyncCallback<Void>(){
							
							@Override
							public void onFailure(Throwable caught)
							{
								Window.alert("We couldn't import data2");
							}
							@Override
							public void onSuccess(Void result)
							{
								Window.alert("data2 is imported");
							}
						});
						
					
					}
				});
				
				//Imports data part 3 into datastore
				impButtonp3.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						poiService.importDatap2(new AsyncCallback<Void>(){
							
							@Override
							public void onFailure(Throwable caught)
							{
								Window.alert("We couldn't import data3");
							}
							@Override
							public void onSuccess(Void result)
							{
								Window.alert("data3 is imported");
							}
						});
						
					
					}
				});
				
				// Imports data part 4 into datastore
				impButtonp4.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						poiService.importDatap4(new AsyncCallback<Void>(){
							
							@Override
							public void onFailure(Throwable caught)
							{
								Window.alert("We couldn't import data4");
							}
							@Override
							public void onSuccess(Void result)
							{
								Window.alert("data4 is imported");
							}
						});
						
					
					}
				});
				
				//Imports all the drink points into the datastore
				drinkButton.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event){
						drinkService.importDrinkingData(new AsyncCallback<Void>(){
							
							@Override
							public void onFailure (Throwable caught)
							{
								Window.alert("we couldn't import drinking data");
							}
							
							@Override
							public void onSuccess(Void result)
							{
								Window.alert("drinking data is imported");
							}
						});
					}
				});
				
		// deletes all the drink points from the datastore
				drinkdelButton.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						drinkService.deleteDrinkData(new AsyncCallback<Void>(){
							@Override
							public void onFailure (Throwable caught)
							{
								Window.alert("we couldn't delete drinking data");
							}
							
							@Override
							public void onSuccess(Void result)
							{
								Window.alert("drinking data is deleted");
							}
						});
						}
						
					});
				
				delPointsButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						poiService.deleteallPOI(new AsyncCallback<Void>(){

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("We couldn't delete all the data");
								
							}

							@Override
							public void onSuccess(Void result) {
								Window.alert("data is all deleted");
								
							}
							
						});
						
					}
					
				});
				
				pullData.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						drinkService.getDrinkpoints(new AsyncCallback<ArrayList<DrinkingFountain>>(){
							
							@Override
							public void onFailure(Throwable caught)
							{
								Window.alert("didn't refresh fountainlist");
							}
							//plots the ALL the fountains
							@Override
							public void onSuccess(ArrayList<DrinkingFountain> result)
							{
								Window.alert("refreshed fountain data");
								allFountains = result;
							}
						
						});
					    
						//Austin's method
						// Retrieves the data from datastore so we can use it locally
						poiService.getPoints(new AsyncCallback<ArrayList<PointOfInterest>>(){

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("poi didn't refresh"); 
							}
							// next try putting it in a button...
							@Override
							public void onSuccess(ArrayList<PointOfInterest> result) {
								// TODO Auto-generated method stub
								Window.alert("poi refreshed");
								allPoints = result;
								}
						});
						
						userInfoService.getAllUsers(new AsyncCallback<ArrayList<UserInformation>>(){

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("users didn't refresh"); 
							}
							// next try putting it in a button...
							@Override
							public void onSuccess(ArrayList<UserInformation> result) {
								// TODO Auto-generated method stub
								Window.alert("users refreshed");
								allUsers = result;
								}
						});
						
						//pop.setDatavalues(allPoints, allFountains);
						}
						
					});
				
		
		
		allFountains = fountains;
		allPoints = points;
		//initTable();
		createButtonTable();
		initWidget(buttonTable);
	}
	
	
	public void initPointsTable(){
		dbTable.setStyleName("dbviewerflex");
		ScrollPanel scrollPanel = new ScrollPanel();
		dbTable.setWidth("300%");
		scrollPanel.setSize("300", "200");
		dbTable.addStyleName("flextable");
		
		//Column labels
		dbTable.setText(0, 0, "Address");
		dbTable.setText(0, 1, "Lattitude");
		dbTable.setText(0, 2, "Longitude");
		dbTable.setText(0, 3, "Frequency");
		dbTable.setText(0,  4, "Key");

		dbTable.getRowFormatter().addStyleName(0, "watchDBHeader");
		dbTable.setCellPadding(6);	
		dbTable.setCellSpacing(5);
		scrollPanel.add(dbTable);
		RootPanel.get().add(scrollPanel);
		
	}
	
	public void initFountainTable(){
		dbTable.setStyleName("dbviewerflex");
		ScrollPanel scrollPanel = new ScrollPanel();
		dbTable.setWidth("300%");
		scrollPanel.setSize("300", "200");
		dbTable.addStyleName("flextable");
		
		//Column labels
		dbTable.setText(0, 0, "Address");
		dbTable.setText(0, 1, "Lattitude");
		dbTable.setText(0, 2, "Longitude");
		dbTable.setText(0, 3, "key");

		dbTable.getRowFormatter().addStyleName(0, "watchDBHeader");
		dbTable.setCellPadding(6);	
		dbTable.setCellSpacing(5);
		scrollPanel.add(dbTable);
		RootPanel.get().add(scrollPanel);
		
	}
	
	public void initUserTable(){
		dbTable.setStyleName("dbviewerflex");
		ScrollPanel scrollPanel = new ScrollPanel();
		dbTable.setWidth("300%");
		scrollPanel.setSize("300", "200");
		dbTable.addStyleName("flextable");
		
		//Column labels
		dbTable.setText(0, 0, "First Name");
		dbTable.setText(0, 1, "Last name");
		dbTable.setText(0, 2, "email");
		dbTable.setText(0, 3, "description");
		dbTable.setText(0, 4, "Distance");

		dbTable.getRowFormatter().addStyleName(0, "watchDBHeader");
		dbTable.setCellPadding(6);	
		dbTable.setCellSpacing(5);
		scrollPanel.add(dbTable);
		RootPanel.get().add(scrollPanel);
		
	}
	
	public void createButtonTable(){
		buttonTable.setText(0, 0, "Choose Data");
		buttonTable.setText(0, 1, "Administrator buttons");
		buttonTable.setCellPadding(6);
		buttonTable.setCellSpacing(3);
		buttonTable.setWidget(1, 0, fountainData);
		buttonTable.setWidget(2, 0, userData);
		buttonTable.setWidget(3, 0, bikeData);
		
		
		buttonTable.setWidget(1, 1, impButton);
		buttonTable.setWidget(2, 1, impButtonp2);
		buttonTable.setWidget(3, 1, impButtonp3);
		buttonTable.setWidget(4, 1, impButtonp4);
		buttonTable.setWidget(5, 1, drinkButton);
		buttonTable.setWidget(6, 1, drinkdelButton);
		buttonTable.setWidget(7, 1, delPointsButton);
		buttonTable.setWidget(8, 1, pullData);
	}

	//displaying bike racks
	public void displayDatabasePoint(PointOfInterest poi){
		
		//add the data to the table
		int row = dbTable.getRowCount();
		dbTable.setText(row, 0, poi.getStNum()+" "+poi.getStName());
		dbTable.setText(row, 1, poi.getLat());
		dbTable.setText(row, 2, poi.getLng());
		String frequency = ""+ poi.getFreq();
		dbTable.setText(row, 3, frequency);
		dbTable.setText(row, 4, ""+poi.getKey());
		
	}
	
	public void displayPoints (ArrayList<PointOfInterest> listpoints)
	{
		for (PointOfInterest poi : listpoints)
		{
			displayDatabasePoint(poi);
		}
	}
	
	//displaying user info
	public void displayUserPoint(UserInformation userInfo){
		
		//add the data to the table
		int row = dbTable.getRowCount();
		dbTable.setText(row, 0, userInfo.getFirstName());
		dbTable.setText(row, 1, userInfo.getLastName());
		dbTable.setText(row, 2, userInfo.getEmailAddress());
		dbTable.setText(row, 3, userInfo.getDescription());
		dbTable.setText(row, 4, ""+userInfo.getDistance());
	}
	
	public void displayUserPoints (ArrayList<UserInformation> users)
	{
		for (UserInformation userinfo : users)
		{
			displayUserPoint(userinfo);
		}
	}
	
	//displaying fountains
	public void displayFountainPoint(DrinkingFountain df){
		
		//add the data to the table
		int row = dbTable.getRowCount();
		dbTable.setText(row, 0, df.getAdd());
		dbTable.setText(row, 1, df.getDlat());
		dbTable.setText(row, 2, df.getDlon());
		dbTable.setText(row, 3, ""+df.getKey());
		
	}
	
	public void displayFountainPoints (ArrayList<DrinkingFountain> fountains)
	{
		for (DrinkingFountain df : fountains)
		{
			displayFountainPoint(df);
		}
	}
	
}