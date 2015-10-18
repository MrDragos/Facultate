package ro.pub.cs.aipi.lab07.general;

public interface Constants {

    final public static String 		APPLICATION_NAME 				= "HealthcareSystem";

    final public static String 		DATABASE_CONNECTION 			= "jdbc:mysql://localhost:3306/grupa342c4_savadragos";
    final public static String 		DATABASE_USERNAME 				= "root";
    //final public static String 		DATABASE_PASSWORD 				= "StudentAipi2014";
    final public static String 		DATABASE_PASSWORD 				= "19381932";
    final public static String 		DATABASE_NAME 					= "grupa342c4_savadragos";

    final public static boolean 	DEBUG 							= false;
    
    final public static String		ADMINISTRATOR_SERVLET_PAGE_NAME	= "Administration Page";
    final public static String		CLIENT_SERVLET_PAGE_NAME		= "Book Catalog";

    final public static String      SIGNIN                      	= "Sign in";
    final public static String      MEDICAL_TEAM                    = "Medical Team";
    final public static String      MEDICAL_SERVICE                 = "Medical Service";
    final public static String      LOCATION                      	= "Location";
    final public static String      SIGNOUT                     	= "Sign out";  
    final public static String      WELCOME_MESSAGE             	= "Welcome, ";
    final public static String      IDENTIFIER                  	= "identifier"; 
    final public static String 		ERROR_USERNAME_PASSWORD 		= "Either username or password are incorrect!";

    final public static String      USERS_TABLE                 	= "user";    
    final public static String      USER_ROLE                   	= "role";
    final public static String      ADMINISTRATOR_ROLE          	= "administrator";
    final public static String      CLIENT_ROLE                 	= "client";
    final public static String      USER_NAME                  	 	= "username";
    final public static String      USER_PASSWORD               	= "password";
    final public static String      USER_PERSONAL_IDENTIFIER        = "personal_identifier";    
    
    final public static int         USER_NONE                   	= 0;
    final public static int         USER_ADMINISTRATOR          	= 1;
    final public static int         USER_CLIENT                 	= 2;    
    
    final public static int         OPERATION_NONE              	= 0;
    final public static int         OPERATION_INSERT            	= 1;
    final public static int         OPERATION_UPDATE_PHASE1     	= 2;
    final public static int         OPERATION_UPDATE_PHASE2     	= 3;    
    final public static int         OPERATION_DELETE            	= 4;
    final public static int         OPERATION_LOGOUT            	= 5;
    final public static int         OPERATION_COMPLETE           		= 6;
    final public static int         OPERATION_CANCEL           	= 7;
    
    final public static String      ADD_BUTTON_NAME             	= "Add";
    final public static String      UPDATE_BUTTON_NAME          	= "Update";
    final public static String      DELETE_BUTTON_NAME          	= "Delete";
    
    final public static String		OPERATION_TABLE_HEADER			= "operation";
    
    final public static String      INVALID_VALUE               	= "invalid";
    
    final public static String      SELECTED_TABLE              	= "selectedTable";
    
    final public static String      COPIES                      	= "Copies";
    final public static String      SHOPPING_CART               	= "Shopping Cart";
    final public static String      ADD_TO_CART                 	= "Add to Cart";
    final public static String      COMPLETE_COMMAND            	= "Complete Command";
    final public static String      CANCEL_COMMAND              	= "Cancel Command";
    final public static String      ORDER_TOTAL                  	= "Order Total:";    
    final public static String      EMPTY_CART                  	= "The shopping cart is empty!";
    
    final public static String      BOOKS_TABLE                 	= "book";
    final public static String		AUTHORS							= "author(s): ";
    final public static String      TITLE                       	= "title";
    final public static String      STOCK                       	= "stockpile";
    final public static String      PRICE                       	= "price";
    
    final public static String      INVOICE_TABLE                 	= "invoice";
    final public static String      STATE_ISSUED                	= "issued";
    final public static String      INVOICE_DETAIL_TABLE          	= "invoice_detail";
    
    final public static String      COLLECTIONS_TABLE           	= "series";
    final public static String      SPECIALITIES_TABLE           	= "speciality";
    final public static String      COLLECTION                  	= "Collection: ";
    final public static String		SELECTED_COLLECTION				= "selectedCollection";
    final public static String		SELECTED_SPECIALITY				= "selectedSpeciality";
    final public static String      DOMAINS_TABLE               	= "genre";
    final public static String      DOMAIN                      	= "Domain: ";
    final public static String		SELECTED_DOMAIN					= "selectedDomain";
    
    final public static String      ALL                         	= "all";
    
    final public static String      INVALID_COMMAND_ERROR1      	= "the order cannot be meet for the book id ";
    final public static String      INVALID_COMMAND_ERROR2      	= " (insufficient stock)";
    
    final public static String[]    SYSTEM_FUNCTION             	= {"CURDATE()"};
}