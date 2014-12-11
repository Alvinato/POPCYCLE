package com.google.gwt.pop.client;

//symbol text box, checkbox 
//reset butoon 
//padding 
//drake in the login screen 

import java.util.ArrayList;

import com.google.gwt.ajaxloader.client.AjaxLoader;
import com.google.gwt.ajaxloader.client.AjaxLoader.AjaxLoaderOptions;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.pop.client.racks.Racks;
import com.google.gwt.pop.shared.DrinkingFountain;
import com.google.gwt.pop.shared.PointOfInterest;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.DirectionsLeg;
import com.google.maps.gwt.client.DirectionsRenderer;
import com.google.maps.gwt.client.DirectionsRequest;
import com.google.maps.gwt.client.DirectionsResult;
import com.google.maps.gwt.client.DirectionsRoute;
import com.google.maps.gwt.client.DirectionsService;
import com.google.maps.gwt.client.DirectionsStatus;
import com.google.maps.gwt.client.Distance;
import com.google.maps.gwt.client.Duration;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.GeocoderRequest;
import com.google.maps.gwt.client.GeocoderResult;
import com.google.maps.gwt.client.GeocoderStatus;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeControlOptions;
import com.google.maps.gwt.client.MapTypeControlStyle;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.Marker.DblClickHandler;
import com.google.maps.gwt.client.Marker.MouseOutHandler;
import com.google.maps.gwt.client.Marker.MouseOverHandler;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;
import com.google.maps.gwt.client.Size;
import com.google.maps.gwt.client.TravelMode;
import com.google.maps.gwt.client.ZoomControlOptions;
import com.google.maps.gwt.client.ZoomControlStyle;
import com.google.gwt.pop.shared.UserInformation;
//import org.eclipse.jetty.server.Request;


public class Pop implements EntryPoint {
	
	//All the async calls
	private final static POIServiceAsync poiService = GWT.create(POIService.class);
	private final static UserInfoServiceAsync userInfoService = GWT.create(UserInfoService.class);
	private final static DrinkingServiceAsync drinkService = GWT.create(DrinkingService.class);
	
	//Local arrays
	ArrayList<LatLng>  arrayOfLatLong;
	static ArrayList<PointOfInterest> fetchedPOIs = new ArrayList<PointOfInterest>();
	static ArrayList<DrinkingFountain> lodf = new ArrayList<DrinkingFountain>();
	static ArrayList<PointOfInterest> lopoi = new ArrayList<PointOfInterest>();
	static ArrayList<PointOfInterest> lopoi2 = new ArrayList<PointOfInterest>();
	static ArrayList<Marker> alom = new ArrayList<Marker>();
	static ArrayList<PointOfInterest> sortedFreqList =new ArrayList<PointOfInterest>(); 
	static ArrayList<UserInformation> sortedDistList = new ArrayList<UserInformation>();
	
	// will hold the list of markers so we can hide them after they are overlayed...
	static JsArray<JsArray<Marker>> lomarkers = (JsArray<JsArray<Marker>>) JsArray.createArray();
	static JsArray<Marker> lomarkers2 = (JsArray<Marker>) JsArray.createArray();
	
	static ArrayList<String> addresses = new ArrayList<String>();
	private static LoginInfo loginInfo = null;
	DecoratorPanel userDec = new DecoratorPanel(); 
	DecoratorPanel adminDec = new DecoratorPanel(); 
	TabPanel signmein = new TabPanel(); 
	Label tab1 = new Label("user"); 
	Label tab2 = new Label("admin sign in here");
	String tabone = "User"; 
	String tabtwo = "Administrator"; 
	//HTML adminMessage = new HTML ("Administator, Please sign in here"); 
	FocusPanel adminPanel = new FocusPanel(); 
	FocusPanel userPanel = new FocusPanel(); 
	static JsArray<LatLng> bikerackwithlatlngs;
	//Design elements for the Main Screen  
	static HorizontalPanel mainPanel = new HorizontalPanel(); 
	static TabPanel mainTab = new TabPanel(); 
	 
     static String mainTab1 = "About Popcycle";
	static String mainTab2 = "How To Find Bike Racks"; 
	static String mainTab3 = "How To Find a Route"; 
	static String mainTab4 = "How To Find a Fountain"; 
	static String mainTab5 = "How To Create a Profile"; 
	static String mainTab6 = "How To Share on Social Media"; 
	static String mainTab7 = "How To reset the map"; 
	static HorizontalPanel boxHelper = new HorizontalPanel(); 
	
	
	static HorizontalPanel flextable2Helper = new HorizontalPanel();
     
	
	static FlexCellFormatter flextable2Formatter; 
      static FlexCellFormatter flextable3Formatter; 
	// private static FlexTable flextable2 = new FlexTable();
	 private static FlexTable flextable3 = new FlexTable();
	
	 
	 static double hours;
	static double minutes;
	static double second;
	static int Radius;
	private static FlexTable log = new FlexTable();
	static int z = 0;
	private static TextBox searchBar = new TextBox();
	private static FlexTable flextable = new FlexTable(); 
	private static FlexTable flextable2 = new FlexTable();
	private static String address1;
	static LatLng position;
	static LatLng destination1;
	static double lat; 
	static double lng;
	static boolean clear = false;
	static GoogleMap map;
	
	static CheckBox cb1;
	static CheckBox cb2;
	static CheckBox cb3;
	static CheckBox cb4;
	static CheckBox cb5;
	static CheckBox cb6;
	static CheckBox cb7;
	static CheckBox cb8;
	static CheckBox cb9;
	static CheckBox cb10;
     static CheckBox defaultSettings = new CheckBox ("Default Settings Activated!"); 
	 
	// holds all the latlngs for the route...
	static JsArray<LatLng> overview;
	private static Anchor signOutLink = new Anchor("Sign Out");
	public static double totalDistance;
	public static double totalTime;
	static double distance;
	static double seconds;
	static boolean routeButtonpressed = false;
	static boolean racksButtonpressed = false;
	static boolean routeFountainButoonPressed;
	static boolean fountainsButtonpressed = false;
     static boolean settingsButtonPressed = false; 
	static boolean settingsPushButton = false;
	
	static String admin1 = new String("imranhabib14@gmail.com");
	  static String admin2   = new String("iamchristopherpang@gmail.com"); 
	  static String admin3   = new String("acm.liu11@gmail.com");
	  private Anchor signInLink = new Anchor("Sign In");
	  private Anchor signInLink2 = new Anchor ("Admin Sign In");
	  
	  private static boolean routerRun = false;

	  
	  
	public static class MainPage extends Composite {
		
		public MainPage(VerticalPanel panel) {
			
			initWidget(panel);
			
		}
	}
	
	public static class EditUserProfile extends Composite{
		
		private Button submit;
		private Button changePasswordButton;
		private Button redButton;
		private Label cancel;
		private TextBox firstName;
		private TextBox lastName;
		private TextArea description;
		private FlexTable inputTable;
		private FlexTable routeTable;
		private static FlexTable frequentTable;
		private static FlexTable distanceTable;
		private HandlerRegistration submitRegistration;
		private static UserInformation info;
		private static UserInfoServiceAsync userInfoService;
	    
	    public EditUserProfile(UserInformation details) {
	    	userInfoService = GWT.create(UserInfoService.class);
	    	if (details == null) {
	    		this.info = new UserInformation();
	    		this.info.setEmailAddress(loginInfo.getEmailAddress());
	    	} else {
	    		this.info = details;
	    	}

	    	
	        initComponents(details);
	        createInputTable();
	        addSubmitClickHandler(new ClickHandler(){
	        	@Override
	        	public void onClick(ClickEvent event) {
	        		//RootPanel.getBodyElement().getStyle().setBackgroundColor("#000000");
	        		userInfoService.updateUser(firstName.getText(), lastName.getText(), loginInfo.getEmailAddress(), 
	        				description.getText(), new AsyncCallback<Void>() {
	        			@Override
	        			public void onFailure (Throwable caught) {
	        				Window.alert("didn't add user information properly");
	        			}
	        			
	        			@Override
	        			public void onSuccess(Void result) {
	        				Window.alert("Successfully Updated!");
	        			}
	        		});
	            }
	        	
	        });
	        HorizontalPanel userProfilePanel = new HorizontalPanel();
	        userProfilePanel.add(inputTable);
	        userProfilePanel.add(routeTable);
	        userProfilePanel.add(frequentTable);
	        userProfilePanel.add(distanceTable);
	        initWidget(userProfilePanel);
	        
	    }
	    
	    
	    // setting up required fields for user information
		private void initComponents(UserInformation userinfo) {
	        inputTable = new FlexTable();
	        routeTable = new FlexTable();
	        frequentTable = new FlexTable();
	        distanceTable = new FlexTable();
	        frequentTable.setStyleName("frequentTable");
	        distanceTable.setStyleName("distanceTable");
	        submit = new Button("Submit");
	        changePasswordButton = new Button("Change Your Password and Profile Picture Here");
	        cancel = new Label("Cancel");
	        cancel.setStyleName("footer_feedback_widget");
	        cancel.addStyleName("font-75em");
	        cancel.addStyleName("display-inline");

	        firstName = createTextBox("205px");
	        firstName.setText(info.getFirstName());
	        lastName = createTextBox("205px");
	        lastName.setText(info.getLastName());
	        description = createTextArea("250px", "100px");
	        description.setText(info.getDescription());
	        populateDistanceTable(userinfo);
	        populateRouteTable();
	        populateFrequentTable(userinfo);
	    }
		
		public static void populateDistanceTable(UserInformation user){
			//info = userInformation;
			distanceTable.setText(0,1, "Current Distance");
			
			int distanceVal = user.getDistance();
			
			distanceTable.setText(2, 1, ""+distanceVal);
			
		}
		
		
		public static void populateFrequentTable(UserInformation user) {
			  //info = userInformation;
			   frequentTable.setText(0,1, "Frequently Used Route");
			   
			   userInfoService.getFreqbr(user.getListDestination(), new AsyncCallback<Long>(){

			    @Override
			    public void onFailure(Throwable caught) {
			     Window.alert("We didn't get the most frequent integer");
			     
			    }

			    @Override
			    public void onSuccess(Long result) {
			    	if (result == 0)
			    	{
				         frequentTable.setText(1, 1, "You gotta");
				         frequentTable.setText(1, 2, "choose a point!");
			    	}
			    	else{
			     final Long freqid = result;
			     Window.alert("result for success call of fbr"+Long.toString(result));
			     
			     ArrayList<PointOfInterest> lopoi = new ArrayList<PointOfInterest>();
			     poiService.getPoints(new AsyncCallback<ArrayList<PointOfInterest>>(){

			      @Override
			      public void onFailure(Throwable caught) {
			       Window.alert("we didn't get the points needed for the frequency table");
			       
			      }
			      
			      
			      @Override
			      public void onSuccess(ArrayList<PointOfInterest> result) {
			    	  Window.alert("point.getkey() result"+Long.toString(result.get(0).getKey()));
			       for (PointOfInterest point: result){
			        if(point.getKey() == freqid)
			        {
			         frequentTable.setText(1, 1, point.getStNum());
			         frequentTable.setText(1, 2, point.getStName());
			        }
			        
			       }			       
			       
			      }
			      
			     });
			    }
			    }
			    
			   });

			   }
			  
		
		
		public void populateRouteTable() {
			
			info = userInformation;
			routeTable.setText(0, 1, "Recently Added Routes");
			
			routeTable.clear();
			for (int i = 0; i < info.currentLat.size(); i++) {
	        	
	        	routeTable.setWidget(i, 2, new Label (info.currentLatLng.get(i)));
	        	routeTable.setWidget(i, 3, new Label (info.destinationLatLng.get(i)));
	        	final int index = i;
	        	
	        	Button deleteButton = new Button("Delete Route");
	        	Button showButton = new Button ("Show Route on Map");
	        	deleteButton.addClickHandler(new ClickHandler() {

		            @Override
		            public void onClick(ClickEvent event) {
		            	
		            	UserInfoServiceAsync userInfoService = GWT.create(UserInfoService.class);
		            	
		            	userInformation.deleteRoute(index);
		            	populateRouteTable();

		            	userInfoService.updateSavedRoutes(userInformation, new AsyncCallback<Void>() {
		            		@Override
		            		public void onFailure (Throwable caught) {

		            		}

		            		@Override
		            		public void onSuccess(Void result) {
		            			

		            		}
		            	});
		            	
		            }
		        });
	        	
	        	showButton.addClickHandler(new ClickHandler() {

		            @Override
		            public void onClick(ClickEvent event) {
		            	Window.alert("PRINTING ROUTES FROM SAVE");
		            	tabContainer.setVisible(true);
		            	tabContainer.selectTab(0);
		            	
		            	Window.alert("the tab container should have been changed");
		            	Window.alert("this is the destination address" + info.destinationLatLng.get(index));
		            	
		            	router(map, LatLng.create(info.destinationLat.get(index), info.destinationLong.get(index)), 
		            			LatLng.create(info.currentLat.get(index), info.currentLong.get(index)), 
		            		info.destinationLatLng.get(index));
		            	System.out.println("AFTER PRINTING ROUTES FROM SAVE");
		            	
		            }
		        });
		        
	        	routeTable.setWidget(i, 4, deleteButton);
	        	routeTable.setWidget(i, 5, showButton);
	        }
		}
	    
		// Create input area for personal description
	    private TextArea createTextArea(String width, String height) {
			// TODO Auto-generated method stub
			final TextArea area = new TextArea();
			area.setStyleName("input_area");
			area.setWidth(width);
			area.setHeight(height);
			return area;
		}
	    
	    // Create input box for, firstname and lastname
		private TextBox createTextBox(String width) {
			// TODO Auto-generated method stub
			final TextBox box = new TextBox();
			box.setStyleName("input_box");
			box.setWidth(width);
			return box;
		}
		
		public UserInformation getInfo() {
			info.setFirstName(this.firstName.getText());
			info.setLastName(this.lastName.getText());
			info.setDescription(this.description.getText());
			return info;
			
			
		}
		
		private Widget createInputTable() {

	        //input box for first name
	        inputTable
	                .setHTML(0,0,
	                        "<span class=\"font-80em\" style=\"white-space:nowrap\">Given name <span " +
	                                "class=\"required\">*</span></span>");
	        inputTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasAlignment.ALIGN_TOP);
	        inputTable.getFlexCellFormatter().setWidth(0, 0, "200px");
	        inputTable.setWidget(0, 1, firstName);

	        //input box for last name
	        inputTable
	                .setHTML(1,0,
	                        "<span class=\"font-80em\" style=\"white-space:nowrap\">Family name <span " +
	                                "class=\"required\">*</span></span>");
	        inputTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasAlignment.ALIGN_TOP);
	        inputTable.getFlexCellFormatter().setWidth(1, 0, "200px");
	        inputTable.setWidget(1, 1, lastName);


	        //input area for personal description
	        inputTable.setHTML(2, 0,
	                           "<span class=\"font-80em\" style=\"white-space:nowrap\">About yourself</span>");
	        inputTable.getFlexCellFormatter().setVerticalAlignment(2, 0, HasAlignment.ALIGN_TOP);
	        inputTable.getFlexCellFormatter().setWidth(2, 0, "200px");
	        inputTable.setWidget(2, 1, description);
	        
	        HTMLPanel butPanel = new HTMLPanel(
	        		"<span id=\"changePW_button\"></span>");
	        butPanel.add(changePasswordButton, "changePW_button");
	        changePasswordButton.setWidth("300px");

	        HTMLPanel buttonPanel = new HTMLPanel(
	                "<span id=\"submit_button\"></span>");
	        buttonPanel.add(submit, "submit_button");
	        
	        changePasswordButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Window.open("https://accounts.google.com/b/0/ManageAccount","_blank", "");
				}
			});
	        
	        inputTable.setText (6, 0, "--------------------------------------------");
	        inputTable.setText (7, 0, "Change your Background Color");
        	
       	 	Button redButton = new Button("Red");
	        Button blueButton = new Button("Blue");
	        Button greyButton = new Button ("Grey");
	        Button whiteButton = new Button ("White");
	        
	        inputTable.setWidget(8, 0, redButton);
	        inputTable.setWidget(9, 0, blueButton);
	        inputTable.setWidget(10, 0, greyButton);
	        inputTable.setWidget(11, 0, whiteButton);
	        
			
			redButton.addClickHandler(new ClickHandler() {

	            @Override
	            public void onClick(ClickEvent event) {
	            	
	            	RootPanel.getBodyElement().getStyle().setBackgroundColor("#FF0000");
	            	
	            }
	        });
			
			blueButton.addClickHandler(new ClickHandler() {

	            @Override
	            public void onClick(ClickEvent event) {
	            	
	            	RootPanel.getBodyElement().getStyle().setBackgroundColor("#00FFFF");
	            	
	            }
	        });
			
			greyButton.addClickHandler(new ClickHandler() {

	            @Override
	            public void onClick(ClickEvent event) {
	            	
	            	RootPanel.getBodyElement().getStyle().setBackgroundColor("#CCCCCC");
	            	
	            }
	        });
			
			whiteButton.addClickHandler(new ClickHandler() {

	            @Override
	            public void onClick(ClickEvent event) {
	            	
	            	RootPanel.getBodyElement().getStyle().setBackgroundColor("#FFFFFF");
	            	
	            }
	        });
	        
	        
	        // inputTable.setText(7, 0, "Change Your Background Color");
	        
	        
	        /*
	        Button redButton = new Button("Red");
	        Button blueButton = new Button("Blue");
	        Button greyButton = new Button ("Grey");
	        Button whiteButton = new Button ("White");
	        */
	        
	        

	        inputTable.getFlexCellFormatter().setColSpan(3, 0, 2);
	        inputTable.setWidget(3, 0, buttonPanel);
	        inputTable.getCellFormatter().setHorizontalAlignment(3, 0, HasAlignment.ALIGN_LEFT);
	        
	        inputTable.getFlexCellFormatter().setColSpan(4, 0, 2);
	        inputTable.setWidget(4, 0, butPanel);
	        inputTable.getCellFormatter().setHorizontalAlignment(4, 0, HasAlignment.ALIGN_LEFT);

	        

	        return inputTable;
	    }
		
	    public void addSubmitClickHandler(final ClickHandler handler) {
	        if (submitRegistration != null)
	            submitRegistration.removeHandler();
	        submitRegistration = submit.addClickHandler(new ClickHandler() {

	            @Override
	            public void onClick(ClickEvent event) {
	                if (!valid())
	                    return;

	                handler.onClick(event);
	            }
	        });
	    }

	    // check if the fields are filled in or not
	    // description box can be empty, others cannot
	    protected boolean valid() {
			// TODO Auto-generated method stub
			boolean valid = true;
			if (firstName.getText().isEmpty()) {
				firstName.setStyleName("Input Box Empty");
				valid = false;
			} else {
				firstName.setStyleName("Input Box");
			}
			
			if (lastName.getText().isEmpty()) {
				lastName.setStyleName("Input Box Empty");
				valid = false;
			} else {
				lastName.setStyleName("Input Box");
			}
			
			
			return valid;
	    }

	}

	private static String currentLocation = null;
	private static LatLng currentLatLng = null;
	private static String destinationLocation = null;
	private static LatLng destinationLatLng = null;
	static EditUserProfile editProfile = null;
	public static TabPanel tabContainer = new TabPanel();
	public static UserInformation userInformation = null;
	
	
	public void onModuleLoad() {
		  
		  
		  //Import all the data here
		  
		  drinkService.getDrinkpoints(new AsyncCallback<ArrayList<DrinkingFountain>>(){
		   
		   @Override
		   public void onFailure(Throwable caught)
		   {
		    Window.alert("We didn't get the fountainlist");
		   }
		   //plots the ALL the fountains
		   @Override
		   public void onSuccess(ArrayList<DrinkingFountain> result)
		   {
		    Window.alert("Welcome fountains are ready for you!");
		    lodf = result;
		   }
		  
		  });   
		  LoginServiceAsync loginService = GWT.create(LoginService.class);
		     loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
		       public void onFailure(Throwable error) {
		       }
		       public void onSuccess(LoginInfo result) {
		           loginInfo = result;
		           if(loginInfo.isLoggedIn()) {
		            
		            
		            
		            userInfoService.checkUserExists(loginInfo.getEmailAddress(), new AsyncCallback<Boolean>() {
		             @Override
		              public void onFailure(Throwable error) {
		              Window.alert("we didn't check if the user exists");
		              }
		             @Override
		              public void onSuccess(Boolean result) {
		              Window.alert("this works");
		              if (result){
		                   if((loginInfo.getEmailAddress() == admin1) || 
		                     (loginInfo.getEmailAddress() == admin2) || 
		                     (loginInfo.getEmailAddress() == admin3)){
		                   loadAdmin(); 
		                 } 
		                 loadPop();
		               }
		              else{
		               UserInformation newuser = new UserInformation();
		               newuser.setEmailAddress(loginInfo.getEmailAddress());
		               newuser.setDescription("fill this out");
		               newuser.setFirstName("First name");
		               newuser.setLastName("Last Name");
		               newuser.setListDestination();
		               newuser.initializeDistance();
		               userInformation = newuser;
		               
		               userInfoService.createUser(newuser, new AsyncCallback<Void>(){

		        @Override
		        public void onFailure(Throwable caught) {
		         Window.alert("create user didn't work");
		         
		        }

		        @Override
		        public void onSuccess(Void result) {
		         Window.alert("Welcome! you are a new user!");
		         
		        }

		               });
		               
		               
		               
		                    if((loginInfo.getEmailAddress() == admin1) || 
		                      (loginInfo.getEmailAddress() == admin2) || 
		                      (loginInfo.getEmailAddress() == admin3)){
		                    loadAdmin(); 
		                  } 
		                  loadPop();
		              }

		                  }
		                });
		        
		          }  
		           
		           else { 
		               loadLogin();
		           }
		           }
		         });
		       }
	

	private void loadLogin() {
	    signInLink.setHref(loginInfo.getLoginUrl());
	    signInLink2.setHref(loginInfo.getLoginUrl());
	    tab1.setHeight("10px"); 
	    tab2.setHeight("100px");
	    adminPanel.setSize("100px", "100px"); 
	    userPanel.setSize("100px", "100px"); 
	    userPanel.add(signInLink); 
	    adminPanel.add(signInLink2); 
	    adminDec.add(adminPanel); 	    
	    userDec.add(userPanel); 
	    signmein.add(userDec, tabone);
	    signmein.add(adminDec, tabtwo); 
	    signmein.selectTab(0); 
	    signmein.setWidth("1100px"); 
	    signmein.setHeight("950px"); 
	    RootPanel.get("mainPanel").add(signmein); 
	  }
	
	public void loadPop() {
		 AjaxLoaderOptions options = AjaxLoaderOptions.newInstance();
		    options.setOtherParms("sensor=false");
		    Runnable callback = new Runnable() {
		      public void run() {
		        getUserInfo();
		      }
		    };
		    AjaxLoader.loadApi("maps", "3", callback, options);
		}
	
	public void getUserInfo() {
	     
	     UserInfoServiceAsync userInfoService = GWT.create(UserInfoService.class);
	     Window.alert("we are here get user info");
	     System.out.println("STARTED GETTING USER INFO");
	     
	     userInfoService.getUserInformation(loginInfo.getEmailAddress(), new AsyncCallback<UserInformation>() {
	      @Override
	      public void onFailure (Throwable caught) {
	       
	       Window.alert("didn't get the user information correctly");

	      }

	      @Override
	      public void onSuccess(UserInformation result) {
	       userInformation = result;
	       //Austin's method
	       // Retrieves the data from datastore so we can use it locally
	       poiService.getPoints(new AsyncCallback<ArrayList<PointOfInterest>>(){

	        @Override
	        public void onFailure(Throwable caught) {
	         // TODO Auto-generated method stub
	         Window.alert("nothing retrieved"); 
	        }
	        // next try putting it in a button...
	        @Override
	        public void onSuccess(ArrayList<PointOfInterest> result) {
	         // TODO Auto-generated method stub
	         Window.alert("Welcome user! bike points are ready to go!");
	         //This is the right place for rendermap();
	         
	         lopoi = result;
	         lopoi2 = result;
	         
	         renderMap();
	         }
	       });
	       
	       System.out.println("SUCCESS GET USER INFO");

	      }
	     });
	     
	  
	 }

	public void loadAdmin(){ 
        
		/*
		Button adminButton = new Button("Admin");
        Button adminList = new Button ("Current Administrators"); 
        adminList.setWidth("320px");
        adminList.setHeight("50px");
        adminButton.setWidth("1000px");
        adminButton.setHeight("50px");
    	HorizontalPanel adminPan = new HorizontalPanel(); 
    	adminPan.add(adminButton); 
    	adminPan.add(adminList); 
    	RootPanel.get("admin").add(adminPan);
    	
    	//put all Admin functionality directly in this method  
    	adminButton.addClickHandler(new ClickHandler() {
	         @Override
	         public void onClick(ClickEvent event) {
	        	 Window.alert("You are the Admin!"); 
	        }
	      });
    
    
    	adminList.addClickHandler(new ClickHandler() {
	         @Override
	         public void onClick(ClickEvent event) {
	        	Window.alert("Currently the Admins are: Austin Liu, " + "Alvin Chan, " + "Christopher Pang, " + "Imran Habib & "+ "Aubrey Graham");
	         
	        }
	      });
    
    */
    }


    public static void renderMap() {

    	// get all the points for the bike racks...

    	userInfoService.getUserInformation(loginInfo.getEmailAddress(), new AsyncCallback<UserInformation>() {
    		@Override
    		public void onFailure (Throwable caught) {
    			Window.alert("we didn't get the user information");

    		}

    		@Override
    		public void onSuccess(UserInformation result) {
    			userInformation = result;
    			Window.alert("on success get user information");
    		}
    	});
    	
		RootPanel.get("mainPanel").add(tabContainer);
		
		VerticalPanel mapPan = new VerticalPanel(); 
		// RootPanel.get("map_canvas").add(mapPan); 
		HTML html = new HTML("<div id = 'map_canvas'></div>");
		mapPan.add(html);
		
		
		//Panels
		VerticalPanel tabPan = new VerticalPanel();
		MainPage mainPage = new MainPage(tabPan);
		
		//Tab Containers
		tabContainer.add(mainPage, "Home");
		editProfile = new EditUserProfile(userInformation);
		tabContainer.add(editProfile, "Edit Profile");
		tabContainer.selectTab(0);
		//View the database panel
		DatabaseViewer dbView = new DatabaseViewer(lopoi, lodf);
		tabContainer.add(editProfile, "Edit Profile");
		if ((loginInfo.getEmailAddress() == admin1) || (loginInfo.getEmailAddress() == admin2) || (loginInfo.getEmailAddress() == admin3)){
			tabContainer.add(dbView, "View the database");
		}
		
		Label mainTabLabel1 = new Label("Select a Tab to learn more about the functionality of PopCycle");
		mainTabLabel1.setHeight("300");
		Label mainTabLabel2 = new Label("To begin your search for bike racks, select the 'Plot Racks' button. Once pressed, select from the options provided.");
		mainTabLabel2.setHeight("300");
		Label mainTabLabel3 = new Label("To begin your search for bike routes, select the 'Plot Routes' button. Once pressed, choose your destination, and choose from the 'customize rack options' button on what you want to see on your route.");
		mainTabLabel3.setHeight("300");
		Label mainTabLabel4 = new Label("To find water fountains along your route, use the plot fountains button"); 
		mainTabLabel4.setHeight("300");
        Label mainTabLabel5 = new Label ("To create a user profile, select the 'Create Profile' tab. This will automatically save your PopCycle usage"); 
        Label mainTabLabel6 = new Label ("To reset the map, use the reset map button. It will result in a fresh map!"); 
        Label mainTabLabel7 = new Label ("To share activity on social media, log-in with Facebook, or share activity through the Twitter link"); 


       mainTabLabel1.addStyleName("mainPage1"); 
        mainTabLabel2.addStyleName("mainPage2"); 
        mainTabLabel3.addStyleName("mainPage3"); 
        mainTabLabel4.addStyleName("mainPage4"); 
        mainTabLabel5.addStyleName("mainPage5"); 
        mainTabLabel6.addStyleName("mainPage6"); 
        mainTabLabel7.addStyleName("mainPage7"); 

		//Removed these unecessary buttons
		/**
		Button newButton = new Button ("Start Popcycle"); 
		Button impButton = new Button ("Import the poi data");
		Button drinkButton = new Button("import drink dat");
		Button drinkdelButton = new Button("delete drink data");
		Button impButtonp2 = new Button ("import the data p2");
		Button impButtonp3 = new Button ("import the data p3");
		Button impButtonp4 = new Button ("import the data p4");
		Button delPointsButton = new Button("delete POI data");
		**/
		Button clearMapButton = new Button("Remove Points");
		clearMapButton.setTitle("Click to remove all plots");
		
	
				Label settingsLabel = new Label("Rack Settings"); 
		Label rackLabel = new Label ("How do you want to view bike racks?"); 
		rackLabel.setTitle("Select from the options below"); 
		rackLabel.addStyleName("style2"); 
		rackLabel.setHeight("200"); 
		Label fountainLabel = new Label ("How do you want to view fountains?"); 
		fountainLabel.addStyleName("founteen"); 
		fountainLabel.setTitle("Click to view settings for choosing fountains");
		settingsLabel.addStyleName("style");
		
		settingsLabel.setTitle("customize your bike-rack finder with the options below");
		
		Label settingsLabel2 = new Label ("Fountain Settings");
		settingsLabel2.addStyleName("style3"); 
		settingsLabel2.setHeight("500");
		settingsLabel2.setTitle("Customize your fountain finder with the options below"); 
		
		
		Button settingsButton = new Button ("Settings");
		settingsButton.setTitle("Click to customize rack and fountain settings");
		
		mainTab.add(mainTabLabel1, mainTab1); 
		mainTab.add(mainTabLabel2, mainTab2); 
		mainTab.add(mainTabLabel3, mainTab3); 
		mainTab.add(mainTabLabel4, mainTab4); 
		mainTab.add(mainTabLabel5, mainTab5); 
		mainTab.add(mainTabLabel6, mainTab6); 
		mainTab.add(mainTabLabel7, mainTab7); 
		


		mainTab.selectTab(0);
		mainTab.setWidth("1000"); 

	
		LatLng myLatLng = LatLng.create(49.2611, -123.2531);
		MapOptions myOptions = MapOptions.create();
		myOptions.setZoom(12);
		myOptions.setCenter(myLatLng);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);
		// GoogleMap.crea
		ZoomControlOptions zoomControls = ZoomControlOptions.create();  
		zoomControls.setStyle(ZoomControlStyle.SMALL);  
		MapTypeControlOptions mapTypeOptions = MapTypeControlOptions.create();  
		mapTypeOptions.setStyle(MapTypeControlStyle.DEFAULT);    
		
			final Button routeButton = new Button("Find Routes!");
		final Button rackButton = new Button("Find Racks!"); 
		final Button fountainButton = new Button("Find Fountains!");
		final Button profileButton = new Button("Go to Profile!");
		//COMMENT IMRAN: these are the add/remove buttons 
		Button addFlex2 = new Button ("Add to Table"); 
	   	Button removeFlex2 = new Button ("Remove from Table"); 
        Button addFlex3 = new Button ("Add to Table"); 
        Button removeFlex3 = new Button ("Remove from Table");
        // END OF COMMENT IMRAN 
		
     
       // final Button resetButton = new Button("Reset Map"); 
		final PushButton settingsPush = new PushButton ("Customize Rack Settings"); 
		final Button navigraytion = new Button ("Sign-Out");
		final Button navigraytion2 = new Button ("Learn about Popcycle");
	    final Button helpButton = new Button ("Help");
	    final Button fountainSettings = new Button ("Customize Fountain Settings");
		helpButton.setWidth("100px"); 
		helpButton.addStyleName("helpTag");
		helpButton.setWidth("100px"); 
	    navigraytion.setWidth("100px"); 
	    navigraytion.addStyleName("navigraytion");
		navigraytion2.setWidth("100px");
		fountainSettings.setWidth("100px");
		fountainSettings.addStyleName("fountainDOE");

		routeButton.setWidth("220px");
		rackButton.setWidth("220px");
		fountainButton.setWidth("220px"); 
		profileButton.setWidth("220px"); 
		helpButton.setWidth("220px");
		//resetButton.setWidth("220px"); 

routeButton.setTitle("Click to begin choosing a route");
		rackButton.setTitle("Click to begin finding racks");
		fountainButton.setTitle("Click to find fountains along your route"); 
		profileButton.setTitle("Click to access your Gmail Account"); 
		//resetButton.setTitle("Click to reset the map"); 
		
		routeButton.addStyleName("routeButton");
		rackButton.addStyleName("rackButton");
		fountainButton.addStyleName("fountainButton"); 
		profileButton.addStyleName("profileButton");
		//resetButton.addStyleName("resetButton"); 
		//extraButton.addStyleName("extraButton"); 
		
		//Extra buttons stuff

		
addFlex2.addStyleName("flextable2add");
	    removeFlex2.addStyleName("flextable2remove");
	    addFlex3.addStyleName("flextable3add");
	    removeFlex3.addStyleName("flextable3remove");
	    flextable2.addStyleName("flextable2"); 
	    flextable3.addStyleName("flextable3"); 
	  //	END OF COMMENT IMRAN
	    
		settingsButton.addStyleName("settings");
		settingsPush.addStyleName("pushSettings"); 
		settingsButton.setWidth("220px");
		settingsButton.setHeight("30px"); 
		settingsPush.setWidth("220px"); 






/*


   newButton.setWidth("220px"); 
		newButton.addStyleName("newer");
		impButton.addStyleName("newer");
		drinkButton.addStyleName("newer");
		drinkdelButton.addStyleName("newer");
		impButtonp2.addStyleName("newer");
		impButtonp3.addStyleName("newer");
		impButtonp4.addStyleName("newer");
		delPointsButton.addStyleName("newer");
		**/
		clearMapButton.addStyleName("profileButton");
		clearMapButton.setWidth("220px");
		


       flextable2Formatter = flextable2.getFlexCellFormatter();
	    flextable2.setCellSpacing(10); 
	    flextable2.setCellPadding(10);
	    flextable2Formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
	    flextable2.setHTML(0, 0, " Add description of the funtionality of the flextable here ");
        flextable2Formatter.setColSpan(0, 0, 2);
        
        
        flextable3Formatter = flextable3.getFlexCellFormatter();
	    flextable3.setCellSpacing(10); 
	    flextable3.setCellPadding(10);
	    flextable3Formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
	    flextable3.setHTML(0, 0, " Add description of the funtionality of the flextable here ");
        flextable3Formatter.setColSpan(0, 0, 2);
      
        

        
        
      //COMMENT IMRAN: this section creates the panels and button handlers, the remove buttons works 
        VerticalPanel flextable2Panel = new VerticalPanel();
 	    flextable2Panel.addStyleName("buttonFlexPanel");
 	   VerticalPanel flextable3Panel = new VerticalPanel();
	   flextable3Panel.addStyleName("buttonFlexPanel");

    
	   flextable2Panel.add(addFlex2); 
	   flextable2Panel.add(removeFlex2); 
	   flextable2.setWidget(0, 1, flextable2Panel);
	   flextable2Formatter.setVerticalAlignment(0, 1, 
			      HasVerticalAlignment.ALIGN_TOP);
	    
	   flextable3Panel.add(addFlex3); 
	   flextable3Panel.add(removeFlex3); 
	   flextable3.setWidget(0, 1, flextable3Panel);
	   flextable3Formatter.setVerticalAlignment(0, 1, 
			      HasVerticalAlignment.ALIGN_TOP);
	    
	  
	 
	   
	   addFlex2.addClickHandler(new ClickHandler() {
           @Override
           public void onClick(ClickEvent event) {
              addToFlex1(flextable2);
           }

			private void addToFlex1(final FlexTable flextable2) {
			  poiService.sortMostRidden(lopoi, new AsyncCallback<ArrayList<PointOfInterest>>(){
				   
				   @Override
				   public void onFailure(Throwable caught)
				   {
				    Window.alert("we didn't sort the list");
				   }
				   //plots the ALL the fountains
				   @Override
				   public void onSuccess(ArrayList<PointOfInterest> result)
				   {
					 flextable2.clear();
				    Window.alert("we got the sorted list");
				    sortedFreqList = result;
				    flextable2.setText(0, 0, "Most Frequently used Routes");
				    flextable2.setText(1, 0, ""+result.get(0).getStNum()+"");
				  flextable2.setText(1, 1, ""+result.get(0).getStName()+"");
				    flextable2.setText(2, 0, ""+result.get(1).getStNum()+"");
				  flextable2.setText(2, 1, ""+result.get(1).getStName()+"");
				  
				    flextable2.setText(3, 0, ""+result.get(2).getStNum()+"");
				  flextable2.setText(3, 1, ""+result.get(2).getStName()+"");
				    flextable2.setText(4, 0, ""+result.get(3).getStNum()+"");
				  flextable2.setText(4, 1, ""+result.get(3).getStName()+"");
				  
				    flextable2.setText(5, 0, ""+result.get(4).getStNum()+"");
				  flextable2.setText(5, 1, ""+result.get(4).getStName()+"");
				 // flextable2.setText(0, 1, "2");

				   }
				  
				  }); 

				
			}
        });

        removeFlex2.addClickHandler(new ClickHandler() {
           @Override
           public void onClick(ClickEvent event) {
              removeFromFlex(flextable2);
           }

			private void removeFromFlex(FlexTable flextable2) {
				 int numRows = flextable2.getRowCount();
			      if (numRows > 1) {
			         flextable2.removeRow(numRows - 1);
			         flextable2.getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
			      }
			}
        });
	   
 	   addFlex3.addClickHandler(new ClickHandler() {
           @Override
           public void onClick(ClickEvent event) {
              addToFlex2(flextable3);
           }

           private void addToFlex2(final FlexTable flextable3) {
 				
 				userInfoService.getAllUsers(new AsyncCallback<ArrayList<UserInformation>>(){
	  				   
	  				   @Override
	  				   public void onFailure(Throwable caught)
	  				   {
	  				    Window.alert("we didn't get allthe users");
	  				   }
	  				   //plots the ALL the fountains
	  				   @Override
	  				   public void onSuccess(ArrayList<UserInformation> result)
	  				   {
	  					 userInfoService.sortMostDistance(result, new AsyncCallback<ArrayList<UserInformation>>(){
			  				   
			  				   @Override
			  				   public void onFailure(Throwable caught)
			  				   {
			  				    Window.alert("we didn't sort the list");
			  				   }
			  				   //plots the ALL the fountains
			  				   @Override
			  				   public void onSuccess(ArrayList<UserInformation> result)
			  				   {
			  					 flextable3.clear();
			  				    Window.alert("we got the sorted list");
			  				    sortedDistList = result;
			  				    flextable3.setText(0, 0, "TOP 5 DISTANCES");
			  				    flextable3.setText(1, 0, ""+result.get(0).getFirstName()+"");
			  				  flextable3.setText(1, 1, ""+result.get(0).getLastName()+"");
			  				  flextable3.setText(1, 2, ""+result.get(0).getDistance()+"");
			  				  
			  				  if (result.get(0).getFirstName().equals(result.get(1).getFirstName())){
			  					 flextable3.setText(2, 0, "");
				  				  flextable3.setText(2, 1, "");
				  				  flextable3.setText(2, 2, ""+result.get(1).getDistance()+"");
				  				  
				  				  
				  				    flextable3.setText(3, 0, "");
				  				  flextable3.setText(3, 1, "");
				  				  flextable3.setText(3, 2, "");
				  				  
				  				    flextable3.setText(4, 0, "");
				  				  flextable3.setText(4, 1, "");
				  				  flextable3.setText(4, 2, "");
			  				  }
			  				  
			  				 /* 
			  				  if (result.get(3).getFirstName().equals(result.get(4).getFirstName())){
				  				  
				  				    flextable3.setText(4, 0, "");
				  				  flextable3.setText(4, 1, "");
				  				  flextable3.setText(4, 2, "");
			  				  }
			  				  */
			  				    flextable3.setText(2, 0, ""+result.get(1).getFirstName()+"");
			  				  flextable3.setText(2, 1, ""+result.get(1).getLastName()+"");
			  				  flextable3.setText(2, 2, ""+result.get(1).getDistance()+"");
			  				  
			  				  
			  				    flextable3.setText(3, 0, ""+result.get(2).getFirstName()+"");
			  				  flextable3.setText(3, 1, ""+result.get(2).getLastName()+"");
			  				  flextable3.setText(3, 2, ""+result.get(2).getDistance()+"");
			  				  
			  				    flextable3.setText(4, 0, ""+result.get(3).getFirstName()+"");
			  				  flextable3.setText(4, 1, ""+result.get(3).getLastName()+"");
			  				  flextable3.setText(4, 2, ""+result.get(3).getDistance()+"");
			  				  

			  				 // flextable2.setText(0, 1, "2");
			
			  				   }
			  				  
			  				  }); 
	
	  				   }
	  				  
	  				  });
 			   

 				
 			}
         });

        removeFlex3.addClickHandler(new ClickHandler() {
           @Override
           public void onClick(ClickEvent event) {
              removeFromFlex(flextable3);
           }

			private void removeFromFlex(FlexTable flextable3) {
				 int numRows = flextable3.getRowCount();
			      if (numRows > 1) {
			         flextable3.removeRow(numRows - 1);
			         flextable3.getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
			      }
			}
        });
	   
      //END OF COMMENT IMRAN  
	   
         







        clearMapButton.addStyleName("newer");

		 HorizontalPanel horz = new HorizontalPanel(); 
		//Unecessary buttons now in admin tab
		 /**       
		horz.add(newButton);
		horz.add(impButton);
	    horz.add(impButtonp2);
	    horz.add(impButtonp3);
	    horz.add(impButtonp4);
	    horz.add(delPointsButton);
	    horz.add(drinkButton);
	    horz.add(drinkdelButton);
	    **/
	    //horz.add(clearMapButton);
		 horz.add(clearMapButton);
		mainPanel.add(routeButton); 
		mainPanel.add(rackButton); 
		mainPanel.add(fountainButton); 
		mainPanel.add(profileButton);
		mainPanel.add(clearMapButton); 
		mainPanel.add(settingsButton); 
		
		tabPan.add(horz); 

		 // Make a new check box, and select it by default.
		cb1 = new CheckBox("Bike-Racks Along Route");
	    cb1.setValue(true);
	    cb1.setTitle("Select to view all bike racks along your route"); 
	    cb2 = new CheckBox("Bike-Racks At Destination");
	    cb2.setValue(true);
	    cb2.setTitle("Select to view all bike racks at your destination"); 
	    cb3 = new CheckBox("Bike-Racks At Current Location");
	    cb3.setValue(true);
	    cb3.setTitle("Select to view all bike racks at your current location"); 
	    cb9 = new CheckBox("Closest Bike-Rack At Destination");
	    cb9.setValue(true);
	    cb9.setTitle("Select to view the closest bike rack at your destination");
	    cb10 = new CheckBox("Closest Bike-Rack At Current Location");
	    cb10.setValue(true);
	    cb10.setTitle("Select to view the closest bike rack at your current location");	    
final VerticalPanel panel1 = new VerticalPanel();
 panel1.setSpacing(10);
	    panel1.add(settingsLabel); 
	    panel1.add(cb1);
	    panel1.add(cb2);
	    panel1.add(cb3);
	    panel1.add(cb10);
	    panel1.add(cb9);
	    cb4 = new CheckBox("Water-Fountains Along Route");
	    cb4.setTitle("Select to view water-fountains along your route"); 
	    cb4.setValue(true);
	    cb5 = new CheckBox("Water-Fountains At Destination");
	    cb5.setTitle("Select to view water fountains at your destination"); 
	    cb5.setValue(true);
	    cb6 = new CheckBox("Water-Fountains At Current Location");
	    cb6.setTitle("Select to view water fountains at your current location");
	    cb6.setValue(true);
	    
	    cb7 = new CheckBox("Closest Water-Fountain At Destination");
	    cb7.setTitle("Select to view the closet water fountain at your destination"); 
	    cb8 = new CheckBox("Closest Water-Fountain At Current Location");
	    cb8.setTitle("Select to view the closest water fountain at your current location"); 
	    cb7.setValue(true);
	    cb8.setValue(true);
	    final VerticalPanel panel2 = new VerticalPanel();
	    panel2.setSpacing(10);
	    panel2.add(settingsLabel2);
	    panel2.add(cb4);
	    panel2.add(cb5);
	    panel2.add(cb6);
	    panel2.add(cb7);
	    panel2.add(cb8);  
	    panel1.setVisible(false);
	    panel2.setVisible(false);	       

  	defaultSettings.setTitle("Use the Customize button to change default settings"); 
	    defaultSettings.setValue(true);
		defaultSettings.setEnabled(false);
	    final HorizontalPanel settingsPanel = new HorizontalPanel();
	    settingsPanel.add(settingsPush); 
	    settingsPanel.add(defaultSettings);
	    settingsPanel.setVisible(false);

final RadioButton radioButton1 = new RadioButton("radioGroup", "Bike Racks at Destination");
	       final RadioButton radioButton2 = new RadioButton("radioGroup", "Bike Racks at Current Location");
	       final RadioButton radioButton4 = new RadioButton("radioGroup", "Closest Bike Rack at Destination");
	       final RadioButton radioButton5 = new RadioButton("radioGroup", "Closest Bike Rack at Current Location");

radioButton1.setTitle("Select to show bike racks close to your destination");  
	       radioButton2.setTitle("Select to show bike racks close to your current location"); 
	       radioButton4.setTitle("Select to show the closest bike rack at your destination");
	       radioButton5.setTitle("Select to show the closest bike rack to your current location"); 
	
	       radioButton1.setValue(true);
	     
	    HorizontalPanel panel = new HorizontalPanel();
	    panel.setSpacing(50);            
	    panel.add(radioButton1);
	    panel.add(radioButton2);
	    panel.add(radioButton4);
	    panel.add(radioButton5);
           HorizontalPanel panel3 = new HorizontalPanel();	    



	    tabPan.add(panel);
         tabPan.add(panel3);
	    
	    
	    
	      final VerticalPanel radioButs = new VerticalPanel(); 
	    radioButs.add(rackLabel); 
	    radioButs.add(radioButton1); 
	    radioButs.add(radioButton2); 
	    radioButs.add(radioButton4); 
	    radioButs.add(radioButton5); 
	    radioButs.setVisible(false);
	 // the fountain radio buttons..
	    final RadioButton radioButton10 = new RadioButton("fountainGroup", "Fountains at destination");
	       final RadioButton radioButton11 = new RadioButton("fountainGroup", "Fountains at current location");
	       final RadioButton radioButton12 = new RadioButton("fountainGroup", "Closest fountains at destination");
	       final RadioButton radioButton13 = new RadioButton("fountainGroup", "CLosest fountains at Current Location");
	
	       radioButton1.setValue(true);
	       radioButton10.setValue(true);
	       radioButton10.setTitle("Select to view all fountains at destination");
	       radioButton11.setTitle("Select to view all fountains at current location");
	       radioButton12.setTitle("Select to view Closest fountain at destination");
	       radioButton13.setTitle("Select to view Closest fountain at Curent Location");
	     
	    final VerticalPanel panel10 = new VerticalPanel();
	    panel.setSpacing(50);            
	   panel10.add(fountainLabel);
	    panel10.add(radioButton11);
	    panel10.add(radioButton12);
	    //panel.add(radioButton3);
	    panel10.add(radioButton13);
	    panel10.add(radioButton10);
	    panel10.setVisible(false);
	   
	   
	    //radioButton10.setVisible(true);
	    //radioButton12.setVisible(true);
	    //radioButton11.setVisible(true);
	    //radioButton13.setVisible(true);

	    
		tabPan.add(mainPanel);
		  
      tabPan.add(settingsPanel);
		
		
		final TextBox radiusBar = new TextBox();
		radiusBar.setVisible(false);
		radiusBar.setHeight("30px");
		radiusBar.setWidth("100px");
		radiusBar.setStyleName("suggestionz");
		radiusBar.setText("Search Radius");
		final TextBox searchBar = new TextBox();
		searchBar.setVisible(false);
		searchBar.setHeight("30px");
		searchBar.setWidth("1000px");
		searchBar.setStyleName("suggestionz");
		searchBar.setText("Choose Your Destination!");
		boxHelper.add(radiusBar);
		boxHelper.add(searchBar);
		boxHelper.add(flextable);
		//boxHelper.add(log);
		
	
		tabPan.add(boxHelper);
		tabPan.add(boxHelper);
		tabPan.add(log);
		tabPan.add(panel10);
	    tabPan.add(panel1);
	    tabPan.add(panel2);
		tabPan.add(radioButs); 
		tabPan.add(mapPan);
	
	    
	    
	   // mapPan.getElement()
	    
	    map = GoogleMap.create(Document.get().getElementById("map_canvas"), myOptions);
		
	    HorizontalPanel horPanel = new HorizontalPanel();
	    
	    horPanel.add(mainTab);
	    RootPanel.get("infoTab").add(horPanel);
        tabPan.add(flextable2);
	    tabPan.add(flextable3);
	    
	    

   
	     
	     RootPanel.get("navigraytion").add(navigraytion);
		 RootPanel.get("helpTag").add(helpButton); 

		currlocationplots(map, 500);
		// ROUTE BUTTON
		routeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if(racksButtonpressed == true && routeButtonpressed == false && fountainsButtonpressed == false){
					rackButton.setStyleName("notpressed", true);
					rackButton.setStyleName("pressed", false);
					routeButton.setStyleName("notpressed",false);
					routeButton.setStyleName("pressed", true);
					fountainButton.setStyleName("notpressed",true);
					fountainButton.setStyleName("pressed", false);
					radiusBar.setVisible(true);
					settingsPanel.setVisible(false);
					panel1.setVisible(true);
					panel2.setVisible(true);
					flextable.clear(); 
					racksButtonpressed = false; 
	        		 fountainsButtonpressed = false;
	        		 routeButtonpressed = true;
	        		 searchBar.setVisible(true);
	        		 flextable.setVisible(true);
	        		 radioButs.setVisible(false);
	        		 //radioButton1.setVisible(false);
	        		 //radioButton2.setVisible(false);
	        		 //radioButton4.setVisible(false);
	        		 //radioButton5.setVisible(false);
	        		 return;
				}
				
				if (racksButtonpressed == false && routeButtonpressed == true && fountainsButtonpressed == false){
					routeButton.setStyleName("pressed", false);
					rackButton.setStyleName("notpressed", true);
					routeButton.setStyleName("notpressed", true);
					panel1.setVisible(false);
					panel2.setVisible(false);
					settingsPanel.setVisible(false);
					radiusBar.setVisible(false);
					flextable.setVisible(false);
	        		 searchBar.setVisible(false);
	        		 racksButtonpressed = false;
	        		 fountainsButtonpressed = false;
	        		 routeButtonpressed = false;
	        		 return;
	        	 }
				if(routeButtonpressed == false && racksButtonpressed == false && fountainsButtonpressed == false){
	        		searchBar.setVisible(true);
	        		radiusBar.setVisible(true);
	        		 routeButtonpressed = true;
	        		 racksButtonpressed = false;
	        		 fountainsButtonpressed = false;
	        		 settingsPanel.setVisible(false);
	        		 flextable.setVisible(true);
	        		 panel1.setVisible(true);
						panel2.setVisible(true);
	        		 System.out.println("route button pressed 1");
	        		routeButton.setStyleName("notpressed",false);
	        		 routeButton.setStyleName("pressed", true);
		        	rackButton.setStyleName("notpressed",true);
		        	rackButton.setStyleName("pressed", false);
		        	fountainButton.setStyleName("notpressed",true);
		        	fountainButton.setStyleName("pressed", false);
	        		 return;
	        	 }
				if (routeButtonpressed == false && racksButtonpressed == false && fountainsButtonpressed == true){
	        		searchBar.setVisible(true);
	        		radiusBar.setVisible(true);
	        		 routeButtonpressed = true;
	        		 racksButtonpressed = false;
	        		 fountainsButtonpressed = false;
	        		 flextable.setVisible(true);
	        		 panel1.setVisible(true);
					panel2.setVisible(true);
	        		 System.out.println("route button pressed 1");
	        		routeButton.setStyleName("notpressed",false);
	        		 routeButton.setStyleName("pressed", true);
		        	rackButton.setStyleName("notpressed",true);
		        	rackButton.setStyleName("pressed", false);
		        	fountainButton.setStyleName("notpressed",true);
		        	fountainButton.setStyleName("pressed", false);
		        	panel10.setVisible(false);
	        		 return;
				}
			}
		});
		
		// RACK BUTTON
		rackButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//plotBikeRacks(map, lopoi);
				
				 if (routeButtonpressed == true && racksButtonpressed == false && fountainsButtonpressed == false){
					 routeButton.setStyleName("notpressed",true);
	        		 routeButton.setStyleName("pressed", false);
		        	rackButton.setStyleName("notpressed",false);
		        	rackButton.setStyleName("pressed", true);
		        	fountainButton.setStyleName("notpressed", true);
		        	fountainButton.setStyleName("pressed", false);
		        	panel1.setVisible(false);
					panel2.setVisible(false);
					settingsPanel.setVisible(false);
					 flextable.clear();
	        		 routeButtonpressed = false; 
	        		 fountainsButtonpressed = false; 
	        		 racksButtonpressed = true;
	        		 radiusBar.setVisible(true);
	        		 searchBar.setVisible(true);
	        		 radioButs.setVisible(true);
	        		 //radioButton1.setVisible(true);
	        		 //radioButton2.setVisible(true);
	        		 //radioButton4.setVisible(true);
	        		 //radioButton5.setVisible(true);
	        		 return;
	        	 }
				 if(routeButtonpressed == false && racksButtonpressed == true && fountainsButtonpressed == false){
	        		 routeButton.setStyleName("notpressed",true);
	        		 routeButton.setStyleName("pressed", false);
		        	rackButton.setStyleName("notpressed",true);
		        	rackButton.setStyleName("pressed", false);
		        	fountainButton.setStyleName("notpressed", true);
		        	fountainButton.setStyleName("pressed", false);
	        		 racksButtonpressed = false;
	        		 routeButtonpressed = false;
	        		 fountainsButtonpressed = false;
	        		 searchBar.setVisible(false);
	        		 radiusBar.setVisible(false);
	        		 flextable.setVisible(false);
	        		radioButs.setVisible(false);
	        		 // radioButton1.setVisible(false);
	        		// radioButton2.setVisible(false);
	        		 //radioButton4.setVisible(false);
	        		// radioButton5.setVisible(false);
	        		 return;
	        	}
				 if(routeButtonpressed == false && racksButtonpressed == false && fountainsButtonpressed == false){
	        		 routeButton.setStyleName("notpressed",true);
	        		 routeButton.setStyleName("pressed", false);
		        	rackButton.setStyleName("notpressed",false);
		        	rackButton.setStyleName("pressed", true);
		        	fountainButton.setStyleName("notpressed", true);
		        	fountainButton.setStyleName("pressed", false);
	        		 racksButtonpressed = true;
	        		 routeButtonpressed = false; 
	        		 fountainsButtonpressed = false;
	        		 searchBar.setVisible(true);
	        		 radiusBar.setVisible(true);
	        		 flextable.setVisible(true);
	        		 radioButs.setVisible(true);
	        		 //radioButton1.setVisible(true);
	        		 //radioButton2.setVisible(true);
	        		 //radioButton5.setVisible(true);
	        		 //radioButton4.setVisible(true);
	        		 return;
	        	 }
	        	 if(routeButtonpressed == false && racksButtonpressed == false && fountainsButtonpressed == true){
	        		 routeButton.setStyleName("notpressed",true);
	        		 routeButton.setStyleName("pressed", false);
		        	rackButton.setStyleName("notpressed",false);
		        	rackButton.setStyleName("pressed", true);
		        	fountainButton.setStyleName("notpressed", true);
		        	fountainButton.setStyleName("pressed", false);
	        		 racksButtonpressed = true;
	        		 routeButtonpressed = false; 
	        		 fountainsButtonpressed = false;
	        		 searchBar.setVisible(true);
	        		 radiusBar.setVisible(true);
	        		 flextable.setVisible(true);
	        		 radioButs.setVisible(true) ;
	        		 //radioButton1.setVisible(true);
	        		 //radioButton2.setVisible(true);
	        		 //radioButton5.setVisible(true);
	        		 //radioButton4.setVisible(true);
	        		 panel10.setVisible(false);
	        		 return;
	        	 
	        	 }
			}
	         
		});
		
		
		
		
		//FOUNTAIN BUTTON
		fountainButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("the fountain button has been pressed");
				if(routeButtonpressed == false && racksButtonpressed == false && fountainsButtonpressed == false){
					searchBar.setVisible(true);
					radiusBar.setVisible(true);
					flextable.setVisible(true); 
					fountainsButtonpressed = true;
					routeButtonpressed = false;
					racksButtonpressed = false;
					fountainButton.setStyleName("pressed", true);
					fountainButton.setStyleName("notpressed", false);
					routeButton.setStyleName("notpressed", true);
					routeButton.setStyleName("pressed", false);
					rackButton.setStyleName("notpressed", true);
					rackButton.setStyleName("pressed", false);
					panel10.setVisible(true);
					return;
				}
				if(routeButtonpressed == true && racksButtonpressed == false && fountainsButtonpressed == false){
					searchBar.setVisible(true);
					radiusBar.setVisible(true);
					flextable.setVisible(true);
					routeButtonpressed = false;
					racksButtonpressed = false;
					fountainsButtonpressed = true;
					// have to hide all the checkboxes...
					panel1.setVisible(false);
					panel2.setVisible(false);
					fountainButton.setStyleName("pressed", true);
					fountainButton.setStyleName("notpressed", false);
					routeButton.setStyleName("notpressed", true);
					routeButton.setStyleName("pressed", false);
					rackButton.setStyleName("notpressed", true);
					rackButton.setStyleName("pressed", false);
					panel10.setVisible(true);
					
					return;
				}
				if(routeButtonpressed == false && racksButtonpressed == true && fountainsButtonpressed == false){
					searchBar.setVisible(true);
					radiusBar.setVisible(true);
					flextable.setVisible(true);
					racksButtonpressed = false;
					routeButtonpressed = false;
					fountainsButtonpressed = true;
					fountainButton.setStyleName("pressed", true);
					fountainButton.setStyleName("notpressed", false);
					routeButton.setStyleName("notpressed", true);
					routeButton.setStyleName("pressed", false);
					rackButton.setStyleName("notpressed", true);
					rackButton.setStyleName("pressed", false);
					radioButs.setVisible(false); 
					//radioButton1.setVisible(false);
	        		 //radioButton2.setVisible(false);
	        		 //radioButton5.setVisible(false);
	        		 //radioButton4.setVisible(false);
					panel10.setVisible(true);
					return;
				}
				if(routeButtonpressed == false && racksButtonpressed == false && fountainsButtonpressed == true){
					searchBar.setVisible(false);
					radiusBar.setVisible(false);
					flextable.setVisible(false);
					routeButtonpressed = false;
					racksButtonpressed = false;
					fountainsButtonpressed = false;
					fountainButton.setStyleName("pressed", false);
					fountainButton.setStyleName("notpressed", true);
					routeButton.setStyleName("notpressed", true);
					routeButton.setStyleName("pressed", false);
					rackButton.setStyleName("notpressed", true);
					rackButton.setStyleName("pressed", false);
					panel10.setVisible(false);
					return;
				}
			}
		});

		profileButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open("https://plus.google.com/","_blank", "");
			}
		});


		navigraytion.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				signOutLink.setHref(loginInfo.getLogoutUrl());
				Window.open(signOutLink.getHref(), "_self", "");
				Window.confirm("Let's be real, do you really want to sign out!?");
				Window.alert("Thanks for using PopCycle!");

			}
		});



	helpButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert("Scroll over any button for its functionality. Refer to the help section for detailed instructions");
				

			}
		});
		
		


		
				clearMapButton.addClickHandler(new ClickHandler(){
				
					@Override
					public void onClick(ClickEvent event) {
						System.out.println("remove points is being clicked");
						cleanMap(map);
						}
						
					});
		
	
		radiusBar.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				radiusBar.setFocus(true);
			}
			
		});
	radiusBar.addKeyDownHandler(new KeyDownHandler(){

		@Override
		public void onKeyDown(KeyDownEvent event) {
			 
		String radiusString = radiusBar.getText();
		try {
			Radius = Integer.parseInt(radiusString);
				System.out.println("the inputted radius");
				System.out.println(Radius);
			}
		catch(NumberFormatException e) {
		}
		}
		
	});




	settingsButton.addClickHandler(new ClickHandler () {
	    	   @Override
	    	   public void onClick(ClickEvent event) { 
	    		 if (settingsButtonPressed == true){ 
	    		   panel1.setVisible(false);
				   panel2.setVisible(false);
				   defaultSettings.setValue(true);
		    	   defaultSettings.setEnabled(false);
		    	 
	    		   settingsButtonPressed = false; 
	    		 } else { 
	    			 panel1.setVisible(true); 
	    			 panel2.setVisible(true); 
	    			 panel10.setVisible(false);
	    			 defaultSettings.setValue(false); 
		    	     defaultSettings.setEnabled(false);
	    			 radioButs.setVisible(false); 
		    	     settingsButtonPressed = true; 
	    		 }
	    			
		
	    			   
	    		    
	    		  
	    	   }
	       });
		
		settingsPush.addClickHandler (new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event){ 
				 if (settingsPushButton == true){ 
		    		   panel1.setVisible(false);
		    		defaultSettings.setValue(true);
		    		defaultSettings.setEnabled(false);
					   settingsPushButton = false; 
		    		 } else { 
		    			 panel1.setVisible(true); 
		    			 
		    			defaultSettings.setValue(false); 
		    			defaultSettings.setEnabled(false);
		    			panel10.setVisible(false);
		    			 settingsPushButton = true; 
		    		 }
		    			
			}
		});






		
		searchBar.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				searchBar.setFocus(true);
			}
		});

		searchBar.addKeyDownHandler(new KeyDownHandler(){

		@Override
		public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					if (radiusBar.getText().toUpperCase().equals("SEARCH RADIUS")||
							Radius <= 0){
						Window.alert("Invalid Search Radius");
						// might want to out something in the flextable saying that its not going to work
						// unless you fix the radius
					}
					getCorrections();
				}	
		}
				private void getCorrections() {
					flextable.clear();
					flextable.clear(true);
					Window.alert("get corrections is running");
					final String symbol = searchBar.getText().toUpperCase().trim();
					Geocoder geocode = Geocoder.create();

					GeocoderRequest request = GeocoderRequest.create();
					request.setRegion("Canada");
					request.setAddress(symbol);
					LatLng latlng = null;
					if (racksButtonpressed && radioButton2.isChecked()){
							Button currlocationbutton = new Button("get bike racks at current location!");
							flextable.setWidget(0, 3, currlocationbutton);
							System.out.println("before adding the clickhandler");
							currlocationbutton.addClickHandler(new ClickHandler(){
								@Override
								public void onClick(ClickEvent event) {
									currlocationplots(map, Radius);
								}
							});
							return;
							}
					
					if(racksButtonpressed && radioButton5.isChecked()){
						Button closestcurrlocation = new Button("Closest Bike Rack at Current Location");
						flextable.setWidget(0,3, closestcurrlocation);
						closestcurrlocation.addClickHandler(new ClickHandler(){
							@Override
							public void onClick(ClickEvent event) {
								plotclosestitem(map, position, lopoi);
							}
						});
						return;	
					}
					// getting plots around certain radius
					if(fountainsButtonpressed && radioButton11.isChecked()){
						Button closestcurrlocation = new Button("get current location water fountains");
						flextable.setWidget(0,3,closestcurrlocation);
						closestcurrlocation.addClickHandler(new ClickHandler(){
							@Override
							public void onClick(ClickEvent event) {
								currlocationplots(map, Radius);
							}
						});
						return;	
					}// getting the closest plot near location
					if(fountainsButtonpressed && radioButton13.isChecked()){
						Button closestcurrlocation = new Button("get closest fountain");
						flextable.setWidget(0,3, closestcurrlocation);
						closestcurrlocation.addClickHandler(new ClickHandler(){
							@Override
							public void onClick(ClickEvent event) {
								plotclosestitem1(map, position, lodf);
							}
						});
						return;	
					}
					geocode.geocode(request, new Geocoder.Callback(){	
						public void handle(JsArray<GeocoderResult> a, GeocoderStatus b) {
							if(b == GeocoderStatus.OK){
								System.out.println("everytime i press enter this happens");

								System.out.println(a.length());
								int listlength = a.length();

								String[] loAddresses = new String[listlength];
								final LatLng[] loLatLngs = new LatLng[listlength];

								// gather the street names as well as the latlngs...
								for (int i = 0; i < listlength; i++){
									GeocoderResult result = a.get(i);
									String address = result.getFormattedAddress();
									loAddresses[i] = address;
									LatLng location = result.getGeometry().getLocation();
									loLatLngs[i] = location;
								}
								
								if (loAddresses.length > 0) {
									destinationLocation = loAddresses[0];
									destinationLatLng = loLatLngs[0];
									
								} else {
									System.out.println("ERROR!! couldn't find destination");
								}

								for (int i = 0; i < listlength; i++){

									String address = loAddresses[i];
									LatLng location = loLatLngs[i];
									System.out.println(address);
		
									if (routeButtonpressed)									
										addAddressesforroute(address, location, i);

									if(racksButtonpressed)
										addAddressesforrack(address, location, i);

									if (fountainsButtonpressed)
										addAddressforfountain(address,location,i);

								}
							}
						}
					// closest fountain at destination
					private void addAddressforfountain(String address,
							final LatLng location, int i) {
						int row = flextable.getRowCount();
						if (fountainsButtonpressed && radioButton12.isChecked()){
							flextable.setText(i, 0, address);
							Button racksalongroutebutton = new Button("get closest water fountain at destination");
							flextable.setWidget(0,3, racksalongroutebutton);
							racksalongroutebutton.addClickHandler(new ClickHandler(){
								@Override
								public void onClick(ClickEvent event) {
									plotclosestitem1(map, location, lodf);
								}
							});
							return;
						}
						// plots fountains at destination
						if (fountainsButtonpressed && radioButton10.isChecked()){
							System.out.println("adding addresses for fountains.");
							flextable.setText(i, 0, address);
							Button fountainDestinationButton = new Button("get destination fountains");
							flextable.setWidget(row,3, fountainDestinationButton);
							fountainDestinationButton.addClickHandler(new ClickHandler(){

								@Override
								public void onClick(ClickEvent event) {
									System.out.println("the size of the lodf" + lodf.size());
									Fountains(map, position, location, lodf, Radius);
								}
							});
						}
					}

						private void addAddressesforrack(String address,
								final LatLng location, int i) {

							int row = flextable.getRowCount();
							if (racksButtonpressed && radioButton4.isChecked()){
								flextable.setText(i, 0, address);
								Button racksalongroutebutton = new Button("get closest bike rack");
								flextable.setWidget(0,3, racksalongroutebutton);
								racksalongroutebutton.addClickHandler(new ClickHandler(){
									@Override
									public void onClick(ClickEvent event) {
										plotclosestitem(map, location, lopoi);
									}
								});
								return;
							}
							
							if(radioButton1.isChecked()){
								flextable.setText(i, 0, address);
								Button destinationbutton = new Button("get bike racks at destination!");
								flextable.setWidget(row, 3, destinationbutton);
								destinationbutton.addClickHandler(new ClickHandler(){
								public void onClick(ClickEvent event) {
									Racks(map, position, location, lopoi, Radius);
								}
							});
							}
						}

						private void addAddressesforroute(final String address, final LatLng location, int i) {
							double estimate0 = distance(position, location);
							//double estimate = round(estimate0, 2);
							estimate0 = (float) estimate0;
							int estimate = (int) Math.round(estimate0);
							String estimatestring = Double.toString(estimate);
							String string1 = "The estimated distance: ";
							String append = string1.concat(estimatestring);
							String append0 = append.concat(" meters to ");
							String append1 = append0.concat(address);
							address1 = address;
							flextable.setText(i, 0, append1);
							int row = flextable.getRowCount();
							Button destinationbutton = new Button("route to destination");
							flextable.setWidget(row, 3, destinationbutton);
							
							//addresses.add(address);
							// we can just pass the address down as a parameter...
							destinationbutton.addClickHandler(new ClickHandler(){

								public void onClick(ClickEvent event) {
									flextable.removeAllRows();
									System.out.println("the routes button has been pressed");	
									routefromcurrlocationwithRacks(map, location, bikerackwithlatlngs, address);
								}
							});
						}
					});
				}
			});	
    }
	
    
    private static void cleanMap(final GoogleMap map)
	{
		System.out.println(lomarkers2.length());
		for(int i = 0; i < lomarkers2.length(); i++)
		{
			System.out.println("cleaning up the markers");
			Marker mark = lomarkers2.get(i);
			mark.setMap((GoogleMap) null);
			mark = null;
		
	}
		
	}
 
 // plot the closest destination given the fountains or bikeracks
 	public static double plotclosestitem(final GoogleMap map,
 		final LatLng destination1, ArrayList<PointOfInterest> loRacks) {
 	
 		double curlat = destination1.lat();
 		double curlng = destination1.lng();
 		// holds the currently closest relative to destination
 		PointOfInterest closestPoint = null;
 		double distanceClosest = 999999999;
 	
 		System.out.println(loRacks.size());
 		
 		double distance = 0;
 		for (int i = 0; i < loRacks.size(); i++){
 			
 			LatLng latlng = poiConvertor(loRacks.get(i));
 			
 			 double earthRadius = 3958.75;
 			double lat = latlng.lat();
 			double lng = latlng.lng();
 			
 			double dLat = Math.toRadians(curlat-lat);
 			double dLng = Math.toRadians(curlng-lng);
 			
 			double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
 		               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
 		               Math.sin(dLng/2) * Math.sin(dLng/2);
 		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
 		    double dist = earthRadius * c;
 		    
 		    int meterConversion = 1609;
 		    distance = dist * meterConversion;
 		    System.out.println("the is the distance");
 		    System.out.println(distance);
 		    if (distance <= distanceClosest ){
 		    	distanceClosest = distance;
 		    	closestPoint = loRacks.get(i);
 		    	System.out.println("the closest point so far");	
 		    }
 		  
 		    }	
 		System.out.println("blah blah blah");
 		String iconName = "http://www2.psd100.com/ppp/2013/11/2701/Map-location-marker-1127205319.png";
 		 MarkerOptions markerOptions = MarkerOptions.create();
 		 markerOptions.setOptimized(false);
 		 markerOptions.setDraggable(true);
 		 MarkerImage image = MarkerImage.create(iconName);
 		 image.getScaledSize();
 		 Size size = Size.create(50, 50);
 		 image.setScaledSize(size);
 		 markerOptions.setIcon(image);
 		 Marker marker = Marker.create(markerOptions);
 		 marker.setPosition(poiConvertor(closestPoint));
 		 marker.setMap(map);
 		lomarkers2.push(marker);
 		markerHandler(marker, closestPoint);	
 		return distance;
 	}	
				
 	public static double plotclosestitem1(GoogleMap map, LatLng destination,			
			ArrayList<DrinkingFountain> lofd) {
		
					double curlat = destination.lat();
					double curlng = destination.lng();
					// holds the currently closest relative to destination
					DrinkingFountain closestPoint = null;
					double distanceClosest = 999999999;
				
					System.out.println(lofd.size());
					
					double distance = 0;
					for (int i = 0; i < lofd.size(); i++){
						
						LatLng latlng = dfConvertor(lofd.get(i));
						 double earthRadius = 3958.75;
						double lat = latlng.lat();
						double lng = latlng.lng();
						
						double dLat = Math.toRadians(curlat-lat);
						double dLng = Math.toRadians(curlng-lng);
						
						double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
					               Math.sin(dLng/2) * Math.sin(dLng/2);
					    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
					    double dist = earthRadius * c;
					    
					    int meterConversion = 1609;
					    distance = dist * meterConversion;
					    System.out.println("the is the distance");
					    System.out.println(distance);
					    if (distance <= distanceClosest ){
					    	distanceClosest = distance;
					    	closestPoint = lofd.get(i);
					    	System.out.println("the closest point so far");	
					    }
					    }	
					System.out.println("blah blah blah");
					String iconName = "http://www2.psd100.com/ppp/2013/11/2701/Map-location-marker-1127205319.png";
					 MarkerOptions markerOptions = MarkerOptions.create();
					 markerOptions.setOptimized(false);
					 markerOptions.setDraggable(true);
					 MarkerImage image = MarkerImage.create(iconName);
					 image.getScaledSize();
					 Size size = Size.create(50, 50);
					 image.setScaledSize(size);
					 markerOptions.setIcon(image);
					 Marker marker = Marker.create(markerOptions);
					 marker.setPosition(dfConvertor(closestPoint));
					 marker.setMap(map);
					lomarkers2.push(marker);
					markerHandler2(marker, closestPoint);	
					return distance;
			}

			
	// dont really need this method... can just call router on its own because the current location has been set at the start of method...
	private static void routefromcurrlocationwithRacks(final GoogleMap map, final LatLng destination, final JsArray<LatLng> bikerackwithlatlngs2, final String address){
		Geolocation geolocation = Geolocation.getIfSupported();
		if (geolocation == null)
		{
			Window.alert("Your old browser is stuck in the past");
		}
		geolocation.getCurrentPosition(new Callback<Position, PositionError>(){
			public void onSuccess(Position result){
				router(map, destination, position, address);
			}
			public void onFailure(PositionError reason)
			{
				Window.alert(reason.getMessage());
			}
				});
	} 
	
	
	// should be able to see the closest racks as well...
	private static void currlocationplots(final GoogleMap map, int radius){
		Geolocation geolocation = Geolocation.getIfSupported(); 
		if (geolocation == null)
		{
			Window.alert("Your old browser is stuck in the past");
		}
		geolocation.getCurrentPosition(new Callback<Position, PositionError>()
				{
			public void onSuccess(Position result)
			{
				lat =  result.getCoordinates().getLatitude();
				lng = result.getCoordinates().getLongitude();  
				position = LatLng.create(lat,lng);
				
				// get the address string from the latlng
				GeocoderRequest request = GeocoderRequest.create();
				request.setLocation(position);
				request.setRegion("Canada");
				Geocoder geocoder = Geocoder.create();
				geocoder.geocode(request, new Geocoder.Callback() {
					String[] loAddresses;
					
					@Override
					public void handle(JsArray<GeocoderResult> a, GeocoderStatus b) {
						// get the name of the string...
						if(b == GeocoderStatus.OK){
							System.out.println("inside the current location geocoder handler");
							System.out.println(a.length());
							int listlength = a.length();
							loAddresses = new String[listlength];
							final LatLng[] loLatLngs = new LatLng[listlength];
							// gather the street names as well as the latlngs...
							for (int i = 0; i < listlength; i++){
								GeocoderResult result = a.get(i);
								String address = result.getFormattedAddress();
								loAddresses[i] = address;
								System.out.println(address);
							}
						
							
							Marker marker = Marker.create();
							MarkerOptions options = MarkerOptions.create();
							currentLocation = loAddresses[0];
							String string2 = "Your current position:    ";
							
							String string3 = string2.concat(currentLocation);
							options.setTitle(string3);
							
							flextable.setText(0, 0, string3);
							marker.getVisible();
							marker.setPosition(position);
							marker.setMap(map); 
							
							// plots depending on which mode its in.
							if (racksButtonpressed )
							Racks(map, position, null, lopoi, Radius);
							if (fountainsButtonpressed)
							Fountains(map,position, null, lodf, Radius);
						}
					}
				});
			}
			public void onFailure(PositionError reason)
			{
				Window.alert(reason.getMessage());
			}
				});
	}
	
	
	
	public static void router(final GoogleMap map, final LatLng destination, final LatLng origin, final String address){
		//cleanMap(map);
		Window.alert("the router is starting");
		DirectionsRequest request = DirectionsRequest.create();
	    request.setDestination(destination);
	    request.setOrigin(origin);
	    request.setTravelMode(TravelMode.BICYCLING);
	    // renders the results and puts it onto map
	    final DirectionsRenderer directionsrenderer = DirectionsRenderer.create();
	    log.setVisible(true);
	    routerRun = true;
	    directionsrenderer.setMap(map);
	    DirectionsService directionsService = DirectionsService.create();
	    directionsService.route(request, new DirectionsService.Callback(){
	    	double totalDistance = 0;
	    	double totalTime = 0;
			@SuppressWarnings("deprecation")
			@Override
			
			public void handle(DirectionsResult a, DirectionsStatus b) {
				System.out.println("here");
				if(b == DirectionsStatus.OK){
					directionsrenderer.setDirections(a);
					    JsArray<DirectionsRoute> listofdirections = a.getRoutes();
					    System.out.print(listofdirections.length());
					    System.out.println("just before the loop");
					    
					    for (int i = 0; i < listofdirections.length(); i++){
					    	DirectionsRoute route = listofdirections.get(i);
					    	JsArray<LatLng> overview = route.getOverviewPath();
					    	
					    	JsArray<DirectionsLeg> leg = route.getLegs();
					    	for(int j = 0; j < leg.length(); j++){
					    		flextable.setText(0, 0, "routing.");
					    		DirectionsLeg currleg = leg.get(j);
					    		Distance distance = currleg.getDistance();
					    		Duration time = currleg.getDuration();
					    		double doubleTime= time.getValue();
					    		double doubleDistance = distance.getValue();
					    		totalDistance += doubleDistance;
					    		totalTime += doubleTime;
					    		flextable.setText(0, 0, "routing..");
					    	}
					    	// plot fountains along route
					    	if (cb4.isChecked()){
					    		for(int p = 0; p < overview.length(); p++){
					    			flextable.setText(0,0,"routing...");
					    			LatLng locations = overview.get(p);
					    			Fountains(map, position, locations, lodf, Radius);
					    		}
					    	}// plot racks along route
					    Window.alert("done plotting the fountains");
					    	if(cb1.isChecked()){
					    	for(int g = 0; g < overview.length(); g++){
					    		System.out.println("going through the markers");
					    		flextable.setText(0, 0, "routing...");
					    		LatLng locations = overview.get(g);
					    		Racks(map, position, locations, lopoi, Radius);
					    		flextable.setText(0, 0, "routing....");
					    	}
					    }
					    }
					    Window.alert("done finding all the bike racks and fountains along the route");
					    // bike racks at destination
					    if(cb2.isChecked()){
					    	Window.alert("bike racks at destination has been chosen");
				    		Racks(map, position, destination, lopoi, Radius);
				    	}
					    // bike racks at current location
				    	if (cb3.isChecked()){
				    		Window.alert("bike racks at current location has been chosen");
				    		currlocationplots(map, Radius);
				    	}
				    	// Fountains at destination
				    	if (cb5.isChecked()){
				    		Window.alert("Fountains at destination has been chosen");
				    		Fountains(map, position, destination, lodf, Radius);
				    	}
				    	// Fountains at currlocation
						if (cb6.isChecked()){
							Window.alert("Fountains at curlocation has been chosen");
							currlocationplots(map, Radius);
							}
						// closest fountain at destination
						if (cb7.isChecked()){
							Window.alert("closest fountain at desitination");
							distance =plotclosestitem1(map, destination, lodf);
						}
						// closest fountain at current location
						if(cb8.isChecked()){
							Window.alert("closest fountain at current location");
							distance = plotclosestitem1(map, origin, lodf);
						}
						
						if (cb9.isChecked()){
							Window.alert("closest item of bike rack at destination");
							distance = plotclosestitem(map, destination, lopoi);
						}
						if (cb10.isChecked()){
							Window.alert("plotclosestitem is running at curlocation for bikeracks");
							distance = plotclosestitem(map, origin, lopoi);
						}
						flextable.setText(0,0,"done everything else");
						routerRun = false;
				    	String stringDistance = Double.toString(totalDistance);
				    	String stringTime = Double.toString(totalTime);
				    	Window.alert(stringDistance);
				    	Window.alert(stringTime);
				    	System.out.println("total inside the handler");
				    	System.out.println(stringTime);
				    	System.out.println(stringDistance);
				    	
				    	// convert the time into hours, min, sec.
				    	converter(totalTime);
				    	Window.alert("done converting all the time");
				    	String hourstr = Double.toString(hours);
				    	String minstr = Double.toString(minutes);
				    	
				    	String string0 = "Time:  ";
				    	String str1 = "hours: ";
				    	String str2 = " minutes: ";
				    	
				    	String TimeString = string0.concat(str1).concat(hourstr).concat(str2).concat(minstr);//.concat(str3).concat(secondstr);
				    	
				    	String string1 = ", Distance:  ";
				    	String string5 = string1.concat(stringDistance);
				    	String string11 = string5.concat(" meters ");
				    	String string4 = TimeString.concat(string11);
				    	String string6 = "Destination: ";
				    	String string10 = address.concat("--->");
				    	String string7 = string6.concat(string10);
				    	String string8 = string7.concat(string4);
				    	String str5 = new Double(distance).toString();
				      	String str6 = str5.substring(0,str5.indexOf('.'));
				      	distance = Double.valueOf(str6);
				    	String lastpart = "---> closest destination bike-rack is ";
				    	String stringD = Double.toString(distance);
				    	String string9 = lastpart.concat(stringD);
				    	string9 = string9.concat(" meters ");
				    	string8 = string8.concat(string9);
				    	final String symbol = string8;
				    	log.setText(z,0, string8);
				    	final Button button = new Button("DELETE ROUTE");
				    	
				    	HorizontalPanel buttons = new HorizontalPanel();
					    
				    	//Button deleteButton = new Button ("Delete Route");
				    	Button saveButton = new Button ("Save This Route!");
				    	//buttons.add(deleteButton);
				    	buttons.add(saveButton);
				    	
				    	
				    	
				    	log.setWidget(z, 2, button);
				    	log.setWidget(z, 3, saveButton);
				    	flextable.setText(0,0, "DONE!!");
				    	Window.alert("this is the string for the route:    " + string8);
				    	Window.alert("save button done");
				    	
				    	
						saveButton.addClickHandler(new ClickHandler(){
				        	@Override
				        	public void onClick(ClickEvent event) {
				        		Window.alert("SAVE BUTTON ON CLICK START");
				        		
				        		userInformation.addRoute(currentLocation, address, position.lat(),
				        				position.lng(), destination.lat(), destination.lng());
				        		
				        		editProfile.populateRouteTable();
				        		
				        		
				        		UserInfoServiceAsync userInfoService = GWT.create(UserInfoService.class);

				            	userInfoService.updateSavedRoutes(userInformation, new AsyncCallback<Void>() {
				            		@Override
				            		public void onFailure (Throwable caught) {
				            			Window.alert("This route wasn't saved");

				            		}

				            		@Override
				            		public void onSuccess(Void result) {
				            			Window.alert("This Route has been Successfully Saved!");
				            		}
				            	});
				        	}
						});
				    	
				    	
				    	
				    	button.addClickHandler(new ClickHandler(){
				    		
							@Override
							public void onClick(ClickEvent event) {
								directionsrenderer.setMap(null);
								remove(symbol);
								
							}

							private void remove(String symbol) {
								for (int i = 0; i < z; i++){
									// holds the current string
									String string = log.getText(i, 0);
									System.out.println(string);
									System.out.println(symbol);
									if(string.equals(symbol)){
										System.out.println("does this get executed??");
										log.removeRow(i);
										z--;
									}
								}
							}
				    	});
					z++;
				}else{
					System.out.println("Error");
				}
			}
	    });
	    
	}



	

	
	public static void converter(double totalTime){
		
		Window.alert("inside convertor");
		System.out.println(totalTime);
		minutes = totalTime/60;
		hours = minutes/60;
		
		String numberD = String.valueOf(hours);
		Window.alert("number of hours: " + numberD);
	    numberD = numberD.substring ( numberD.indexOf ( "." ) );
	    System.out.println(numberD);
	    Double numberDouble = (Double.parseDouble(numberD));
	    System.out.println(numberDouble);
		System.out.println(numberDouble);
	  	minutes = numberDouble*60;  
	  	String numberSec = String.valueOf(minutes);
	  	
	  	Window.alert("the amount of minutes: " + numberSec);
	  	String str1 = new Double(hours).toString();
	  	String str2 = str1.substring(0,str1.indexOf('.'));
	  	hours = Double.valueOf(str2);
	  	String str3 = new Double(minutes).toString();
	  	String str4 = str3.substring(0,str3.indexOf('.'));
	  	minutes = Double.valueOf(str4);
	  	Window.alert("end of convertor");
		}

	public static double distance(LatLng latlng1, LatLng latlng2){
		// this method will get the distance between two latlng points...
		// this will be used for estimating the distance from one suggestion bike rack to another...
		
			double curlat = latlng2.lat();
			double curlng = latlng2.lng();
			// holds the currently closest relative to destination
			LatLng closestPoint;
			//double distanceClosest = 999999999;
			//for (int i = 0; i < loRacks.length(); i++){
				
				 double earthRadius = 3958.75;
				double lat = latlng1.lat();
				double lng = latlng1.lng();
				
				double dLat = Math.toRadians(curlat-lat);
				double dLng = Math.toRadians(curlng-lng);
				
				double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
			               Math.sin(dLng/2) * Math.sin(dLng/2);
			    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			    double dist = earthRadius * c;

			    int meterConversion = 1609;
			    // check if it is within radius and if it is then add to the list of latlngs... 
			    
			    double distance = dist * meterConversion;
			    System.out.println("this is the distance");
			    System.out.println(distance);
			    //if (distance <= distanceClosest ){
			    //	distanceClosest = distance;
			    //	closestPoint = loRacks.get(i);
			    //	System.out.println("the closest point so far");
			    //}		
			    return distance;
	
	}
	
	// Austin's method
		// Plots all water fountain markers with unique water fountain image
		private static void plotFountainPoints(final GoogleMap map, ArrayList<DrinkingFountain> alodf)
		{
			for(DrinkingFountain df: alodf)
			{
				String lattit = df.getDlat();
				String longit = df.getDlon();
				double v1 = Double.parseDouble(lattit);
				double v2 = Double.parseDouble(longit);
				position = LatLng.create(v1, v2);
				String iconURL = "http://www.stopsignsandmore.com/images/Product/medium/1573.gif";
				 MarkerOptions markerOptions = MarkerOptions.create();
				 markerOptions.setOptimized(false);
				 MarkerImage image = MarkerImage.create(iconURL);
				 image.getScaledSize();
				 Size size = Size.create(20, 20);
				 image.setScaledSize(size);
				 markerOptions.setIcon(image);
				Marker marker = Marker.create(markerOptions);
				marker.getVisible();
				/**
				marker.addMouseOverListener(new MouseOverHandler(){

					@Override
					public void handle(MouseEvent event) {
						
					}
					
				});
				**/
				marker.setPosition(position);
				marker.setMap(map);
				alom.add(marker);
			}
		}
		
		 //Plots all bike racks markers with unique bike rack image
		 private static void plotBikeRacks(final GoogleMap map, ArrayList<PointOfInterest> alopoi)
		 {

		  for (final PointOfInterest poi: alopoi)
		  {
		   final LatLng latlng = poiConvertor(poi);
		   String number = poi.getStNum() + ", ";
		   String name = poi.getStName() + ", ";
		   String van = "Vancouver, British Columbia, Canada";
		   final String address = number + name + van;
		   String content = "Bike Rack"+ poi.getStNum()+" "+poi.getStName();
		   String lattit = poi.getLat();
		   String longit = poi.getLng();
		   if(lattit == null && longit == null)
		   {
		    //UBC latlongs
		    lattit = "49.2611";
		    longit = "123.2531";
		   }
		   double v1 = Double.parseDouble(lattit);
		   double v2 = Double.parseDouble(longit);
		   position = LatLng.create(v1, v2);
		   String iconURL = "http://gridchicago.com/metra/images/icon_markers_116x116_black.png";
		    MarkerOptions markerOptions = MarkerOptions.create();
		    markerOptions.setOptimized(false);
		    MarkerImage image = MarkerImage.create(iconURL);
		    image.getScaledSize();
		    Size size = Size.create(30, 30);
		    image.setScaledSize(size);
		    markerOptions.setIcon(image);
		   final Marker marker = Marker.create(markerOptions);
		   marker.getVisible();
		   marker.setPosition(position);
		   marker.setMap(map);
		   final InfoWindow infoWindow = InfoWindow.create();
		   infoWindow.setContent(content);
		   marker.addMouseOverListener(new MouseOverHandler(){

		    @Override
		    public void handle(MouseEvent event) {
		     // TODO Auto-generated method stub
		     infoWindow.open(map, marker);
		    }
		   });
		   marker.addMouseOutListener(new MouseOutHandler(){

		    @Override
		    public void handle(MouseEvent event) {
		     // TODO Auto-generated method stub
		     infoWindow.close();
		    }
		    
		   });
		   marker.addDblClickListener(new DblClickHandler(){
		    @Override
		    public void handle(MouseEvent event) {
		     userInfoService.updateTravelledDestination(poi, loginInfo.getEmailAddress(), new AsyncCallback<Void>() {

		      @Override
		      public void onFailure(Throwable caught) {
		       Window.alert("didn't save this properly");
		       
		      }

		      @Override
		      public void onSuccess(Void result) {
		       
		       Window.alert("You just saved this destination!");
		       
		      }
		     });
		     
		     LatLng poiLatLng = poiConvertor(poi);
		     int currUserDist = userInformation.getDistance();
		     int distanceTrav = (int) distance(position, poiLatLng);
		     userInformation.setDistance(currUserDist + distanceTrav);
		    Window.alert(""+distanceTrav);
	    

        	userInfoService.addDistance(loginInfo.getEmailAddress(), distanceTrav,  new AsyncCallback<Void>() {
        		@Override
        		public void onFailure (Throwable caught) {

        		}

        		@Override
        		public void onSuccess(Void result) {
        			Window.alert("distance was added correctly");
        			

        		}
        	});
        	poiService.addFreq(poi.getKey(), new AsyncCallback<Void>() {
        		@Override
        		public void onFailure (Throwable caught) {

        		}

        		@Override
        		public void onSuccess(Void result) {
        			Window.alert("frequency incremented");
        			

        		}
        	});
        	
		     Long valLong = poi.getKey();
		     String window = valLong.toString();
		     Window.alert(window);
		     System.out.println("marker has been double clicked");
		    // this will take in the poi latlng and the poi address
		     router(map, latlng, position, address);
		    }
		    });
		   
		   alom.add(marker);
		  }
		 }
	
		 public static void Fountains(final GoogleMap map, final LatLng curlocation, final LatLng destination, final ArrayList<DrinkingFountain>loFountains, int radius){
				
				ArrayList<DrinkingFountain> sorted = new ArrayList();
				if (destination == null){
					sorted = sortFountains(map, curlocation, destination, loFountains, radius, "curlocation");
				}else{
				 sorted = sortFountains(map, curlocation, destination, loFountains, radius, "destination");
				}
				
				
				if(sorted.size() == 0){
					System.out.println("There are no fountains in your area");
					//if(!routerRun)
					//Window.alert("there are no fountains in your area");
				}
			for(int i = 0; i < sorted.size(); i++){
					System.out.println("plotting fountain image");
					String content = "Fountain location: " +sorted.get(i).getAdd();
					 String iconURL = "http://www.stopsignsandmore.com/images/Product/medium/1573.gif";
					 MarkerOptions markerOptions = MarkerOptions.create();
					 markerOptions.setOptimized(false);
					 MarkerImage image = MarkerImage.create(iconURL);
					 image.getScaledSize();
					 Size size = Size.create(20, 20);
					 image.setScaledSize(size);
					markerOptions.setIcon(image);
					final Marker marker1 = Marker.create(markerOptions);
					final InfoWindow infoWindow = InfoWindow.create();
					infoWindow.setContent(content);
					marker1.addMouseOverListener(new MouseOverHandler(){

						@Override
						public void handle(MouseEvent event) {
							// TODO Auto-generated method stub
							infoWindow.open(map, marker1);
						}
					});
					marker1.addMouseOutListener(new MouseOutHandler(){

						@Override
						public void handle(MouseEvent event) {
							// TODO Auto-generated method stub
							infoWindow.close();
						}
						
					});
					marker1.getVisible();
					LatLng coordinates = dfConvertor(sorted.get(i));
					marker1.setPosition(coordinates);
					marker1.setMap(map);
					lomarkers2.push(marker1);		
					 markerHandler2(marker1, sorted.get(i));
				 }
			}

		 public static void Racks(final GoogleMap map, final LatLng curlocation, final LatLng destination, final ArrayList<PointOfInterest> loRacks, int radius){
				System.out.println("racks is running");
				ArrayList <PointOfInterest> sortedRacks = new ArrayList();
				if (destination == null){
					System.out.println("destination is null");
					sortedRacks = sortRacks(map, curlocation, destination, loRacks, radius, "curlocation");
				}else{
					System.out.println("destination is not null");
				 sortedRacks = sortRacks(map, curlocation, destination, loRacks, radius, "destination");
			}
				if(sortedRacks.size() == 0){
					System.out.println("There are no racks in your area");
					// this is a notify message if there are no racks in a certain area..
					// gets called too much when routing...
					//if(!routerRun)
					//Window.alert("there are no bike racks in your area");
				}
				for(int i = 0; i < sortedRacks.size(); i++){ 
					System.out.println("plotting racks image");
					String content = "BikeRack: " +sortedRacks.get(i).getStNum()+" "+sortedRacks.get(i).getStName();
					 String iconName = "http://gridchicago.com/metra/images/icon_markers_116x116_black.png";
					 MarkerOptions markerOptions = MarkerOptions.create();
					 markerOptions.setOptimized(false);
					 MarkerImage image = MarkerImage.create(iconName);
					 image.getScaledSize();
					 Size size = Size.create(20, 20);
					 image.setScaledSize(size);
					 markerOptions.setIcon(image);
					final Marker marker1 = Marker.create(markerOptions);
					final InfoWindow infoWindow = InfoWindow.create();
					infoWindow.setContent(content);
					marker1.addMouseOverListener(new MouseOverHandler(){

						@Override
						public void handle(MouseEvent event) {
							infoWindow.open(map, marker1);
						}
					});
					marker1.addMouseOutListener(new MouseOutHandler(){

						@Override
						public void handle(MouseEvent event) {
							infoWindow.close();
						}
						
					});
					 LatLng coordinates = poiConvertor(sortedRacks.get(i));
					 marker1.setPosition(coordinates);
					 marker1.setMap(map);
					 lomarkers2.push(marker1);		
					markerHandler(marker1, sortedRacks.get(i));
					 }
		}

	// makes a markerHandler for bike racks...
		private static void markerHandler(final Marker marker1, final PointOfInterest pointOfInterest){
			// we now have the point of interest which can then be used to get the points needed...
			if(pointOfInterest == null){
				System.out.println("there is no closest point");
				return;
			}
			
			final LatLng latlng = poiConvertor(pointOfInterest);
			String number = pointOfInterest.getStNum() + ", ";
			String name = pointOfInterest.getStName() + ", ";
			String van = "Vancouver, British Columbia, Canada";
			final String address = number + name + van;
			   marker1.addDblClickListener(new DblClickHandler(){
				    @Override
				    public void handle(MouseEvent event) {
				     userInfoService.updateTravelledDestination(pointOfInterest, loginInfo.getEmailAddress(), new AsyncCallback<Void>() {

				      @Override
				      public void onFailure(Throwable caught) {
				       Window.alert("didn't save this properly");
				       
				      }

				      @Override
				      public void onSuccess(Void result) {
				       
				       Window.alert("You just saved this destination!");
				       
				      }
				     });
				     
				     LatLng poiLatLng = poiConvertor(pointOfInterest);
				     int currUserDist = userInformation.getDistance();
				     int distanceTrav = (int) distance(position, poiLatLng);
				     userInformation.setDistance(currUserDist + distanceTrav);
				    Window.alert(""+distanceTrav);
			    

		        	userInfoService.addDistance(loginInfo.getEmailAddress(), distanceTrav,  new AsyncCallback<Void>() {
		        		@Override
		        		public void onFailure (Throwable caught) {

		        		}

		        		@Override
		        		public void onSuccess(Void result) {
		        			Window.alert("distance was added correctly");
		        			

		        		}
		        	});
		        	poiService.addFreq(pointOfInterest.getKey(), new AsyncCallback<Void>() {
		        		@Override
		        		public void onFailure (Throwable caught) {

		        		}

		        		@Override
		        		public void onSuccess(Void result) {
		        			Window.alert("frequency incremented");
		        			

		        		}
		        	});
		        	
			        
			        Long valLong = pointOfInterest.getKey();
			        String window = valLong.toString();
			        Window.alert(window);
							System.out.println("marker has been double clicked");
							// this will take in the poi latlng and the poi address
								router(map, latlng, position, address);
							}
						 });					
		}
		// markerhandler for fountains...
		private static void markerHandler2(final Marker marker1, final DrinkingFountain drinkingFountain){
			if(drinkingFountain == null){
				System.out.println("there is no closest point");
				return;
			}
			
			final LatLng latlng = dfConvertor(drinkingFountain);
			final String address = drinkingFountain.getAdd();
			marker1.addDblClickListener(new DblClickHandler(){
				 
				@Override
				public void handle(MouseEvent event) {
				System.out.println("marker has been double clicked");
				// this will take in the poi latlng and the poi address
					router(map, latlng, position, address);
				}
			 });		
		}

		// accepts DrinkingFountain and outputs the latlng...
				private static LatLng dfConvertor(DrinkingFountain df){
					if(df == null){
						// plot in russia somewhere...
						LatLng latlng = LatLng.create(49, 51);
						return latlng;
					}
					String lattit = df.getDlat();
					String longit = df.getDlon();
					if (lattit == null){
						System.out.println("there is a null value so plot in russia");
						LatLng latlng = LatLng.create(49, 51);
						return latlng;
					}
					double v1 = Double.parseDouble(lattit);
					double v2 = Double.parseDouble(longit);
					LatLng latlng = LatLng.create(v1,v2);
					return latlng;
				}
				// this method returns a latlng from point of interest object
					private static LatLng poiConvertor(PointOfInterest poi){
						if(poi == null){
							// plot in russia somewhere...
							LatLng latlng = LatLng.create(49, 51);
							return latlng;
						}
							String lattit = poi.getLat();
							String longit = poi.getLng();
							if (lattit == null){
								System.out.println(lattit);
								System.out.println("there is a null value so plot in russia");
								LatLng latlng = LatLng.create(49, 51);
								return latlng;
							}
							double v1 = Double.parseDouble(lattit);
							double v2 = Double.parseDouble(longit);
							 LatLng latlng = LatLng.create(v1, v2);
							return latlng;
						
					}

					// accepts loFountains and spits out the ones that are within a radius of a inputted point
					private static ArrayList<DrinkingFountain> sortFountains(GoogleMap map,
							LatLng curlocation, LatLng destination,
							ArrayList<DrinkingFountain> loFountains, int radius, String command) {
						
						ArrayList<DrinkingFountain> sortedFountains = new ArrayList<DrinkingFountain>();
						
						if (command == "curlocation"){
							 double earthRadius = 3958.75;
								
								double curlat = curlocation.lat();
								double curlng = curlocation.lng();
								
								System.out.println("just before sorting the forloop" + ".... the size of the loFountains" + loFountains.size());
								
								for (int i = 0; i < loFountains.size(); i++){
									System.out.println("the fountains are being sorted");
									LatLng latlng = dfConvertor(loFountains.get(i));
									double lat = latlng.lat();
									double lng = latlng.lng();
									
									double dLat = Math.toRadians(curlat-lat);
									double dLng = Math.toRadians(curlng-lng);
									
									double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
								               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
								               Math.sin(dLng/2) * Math.sin(dLng/2);
								    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
								    double dist = earthRadius * c;

								    int meterConversion = 1609; 
								    
								    double distance = dist * meterConversion;
								    
								    if (distance <= radius){
								    	sortedFountains.add(loFountains.get(i));
								    }
								    }
								}
						
						if (command == "destination"){
							 double earthRadius = 3958.75;
								
								double curlat = destination.lat();
								double curlng = destination.lng();
								//int d = 0;
								System.out.println("just before sorting the forloop" + ".... the size of the loFountains" + loFountains.size());
								
								for (int i = 0; i < loFountains.size(); i++){
									System.out.println("the fountains are being sorted");
									LatLng latlng = dfConvertor(loFountains.get(i));
									double lat = latlng.lat();
									double lng = latlng.lng();
									
									double dLat = Math.toRadians(curlat-lat);
									double dLng = Math.toRadians(curlng-lng);
									
									double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
								               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
								               Math.sin(dLng/2) * Math.sin(dLng/2);
								    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
								    double dist = earthRadius * c;

								    int meterConversion = 1609; 
								    
								    double distance = dist * meterConversion;
								    
								    if (distance <= radius){
								    	sortedFountains.add(loFountains.get(i));
								    }
								    }
								}
						System.out.println("the size of the sorted Fountains" + sortedFountains.size());
						return sortedFountains;
						
					}

	
					private static ArrayList<PointOfInterest> sortRacks(GoogleMap map, LatLng curlocation, LatLng destination, ArrayList<PointOfInterest> loRacks, int radius, String command){
						
						
						ArrayList<PointOfInterest> sortedRacks = new ArrayList<PointOfInterest>();
						if(command == "curlocation"){
						 double earthRadius = 3958.75;
						 double curlat = curlocation.lat();
						 double curlng = curlocation.lng();
						System.out.println("this line is working");
						int d = 0;
						for (int i = 0; i < loRacks.size(); i++){  // need to fix this... chnage parameters to jsArray.. or arraylist
							System.out.println("inside the loop is running as well");
							
							LatLng latlng = poiConvertor(loRacks.get(i));
							
							double lat = latlng.lat();
							double lng = latlng.lng();
							
							double dLat = Math.toRadians(curlat-lat);
							double dLng = Math.toRadians(curlng-lng);
							
							double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
						               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
						               Math.sin(dLng/2) * Math.sin(dLng/2);
						    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
						    double dist = earthRadius * c;

						    int meterConversion = 1609; 
						    double distance = dist * meterConversion;
						    if (distance <= radius){
						    	sortedRacks.add(loRacks.get(i));
						    	System.out.println("adding to sorted Racks" + lat + lng);
						    }
						}
						}
					
					if (command == "destination"){
						double earthRadius = 3958.75;
						double curlat = destination.lat();
							double curlng = destination.lng();
							int d = 0;
							for (int i = 0; i < loRacks.size(); i++){
								// get there latlngs...
								LatLng latlng = poiConvertor(loRacks.get(i));
								double lat = latlng.lat();
								double lng = latlng.lng();
								
								double dLat = Math.toRadians(curlat-lat);
								double dLng = Math.toRadians(curlng-lng);
								
								double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
							               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(curlat)) *
							               Math.sin(dLng/2) * Math.sin(dLng/2);
							    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
							    double dist = earthRadius * c;

							    int meterConversion = 1609; 
							    // distance of the two...
							    double distance = dist * meterConversion;
							    
							    if (distance <= radius){
							    	sortedRacks.add(loRacks.get(i));
							    	System.out.println("adding to sorted Racks" + lat + lng);
							    }
							    }
							}
					return sortedRacks;
					
					}
	}

/*private static void latlngMaker (ArrayList<PointOfInterest> lopoi){
	// turn all these points into latlngs...
	 
	for (PointOfInterest poi: lopoi){
		System.out.println("adding things to the JSARRAY");
		String lattit = poi.getLat();
		String longit = poi.getLng();
		double v1 = Double.parseDouble(lattit);
		double v2 = Double.parseDouble(longit);
		LatLng latlng = LatLng.create(v1,v2);
		// push the latlng into this variable...
		bikerackwithlatlngs.push(latlng);
	}	
	System.out.println("the length of the latlngs" + bikerackwithlatlngs.length());
}*/

