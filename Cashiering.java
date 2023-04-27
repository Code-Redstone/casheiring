import java.util.* ;
import java.time.* ; 
import java.time.format.DateTimeFormatter;

public class Cashiering {
    static HashMap<String, Product> products = new HashMap<String, Product>();
    static HashMap<String, Cart> cart = new HashMap<String, Cart>();
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
    	products.put("0001", new Product(200.00, "Spam"));
    	products.put("0002", new Product(24.00, "Century Tuna"));
    	products.put("0003", new Product(28.00, "Corned Beef"));
    	products.put("0004", new Product(23.00, "Beef loaf"));
    	products.put("0005", new Product(20.00, "Carne norte"));
    	products.put("0006", new Product(18.00, "Youngs Town"));
    	products.put("0007", new Product(22.00, "Fresca Tuna " ));
    	clear();
		header();
		productList();
    	while (true) {
    		//header          
    		line();
    		space();
    		System.out.print("+  Type Product code e.g.(1) to scan product    +\n");
    		System.out.print("+  Type '1' to finish transaction               +\n");
    		System.out.print("+  Type '2' to remove last added product        +\n");
    		System.out.print("+  Type '3' to Cancel transaction               +\n");
    		space();
    		line();
    		//Enter Product Code to Proceed
	        System.out.print("\nType here: "); 
	        String p_key = scanner.nextLine();
        	char firstChar = p_key.charAt(0);
    		boolean space = Character.isWhitespace(firstChar);
    		if (space == true) {
    			
    		}
	        else if (p_key.equalsIgnoreCase("3")) {
	        	System.out.print("Transaction Terminated \nSystem turning off \nThank you have a good day"); 
	        	break;
	        }
	        else if (p_key.equalsIgnoreCase("2")) {
	        	System.out.print("Enter product code to remove: "); 
	        	String r_key = scanner.nextLine();
	        	if (cart.containsKey(r_key)) {
					cart.remove(r_key);
					String leftAlignFormat = "| %-15s | %-12.2f |  %-10d  |%n";
					clear();
					productList();
					headerCart();
		            for (Map.Entry<String, Cart> c : cart.entrySet()) {
		                Cart kart = c.getValue();
		                Product p = kart.getProduct();
		        		System.out.format(leftAlignFormat, p.getName(), p.totalPrice(kart.getQty()), kart.getQty());   
		            }
		            line();
	        	}	
	        	else {
	        		clear();
	        		productList();
	        		line();
	        		System.out.println("          PRODUCT IS NOT IN THE CART       "); 
	        		line();
	        	}
	        }
	        if (products.containsKey(p_key)) {
	        	while (true) {
			        System.out.print("Enter quantity:"); 
			        String quantity = scanner.nextLine();
			        try {
			        	int qty=Integer.parseInt(quantity);
			        	
				        if (qty < 0) {            
				        	System.out.println("\n*************************************************\n"
				        						+"*               Enter Valid Number              *\n"
				        						+"*************************************************\n");  
				        }
				        else {
				            Product item = products.get(p_key);
				            cart.put(p_key, new Cart(item, qty));
				            clear();
				            productList();
				            headerCart();
				            String leftAlignFormat = "| %-15s | %-12.2f |  %-10d  |%n";
				            for (Map.Entry<String, Cart> c : cart.entrySet()) {
				                Cart kart = c.getValue();
				                Product p = kart.getProduct();
				        		System.out.format(leftAlignFormat, p.getName(), p.getPrice(), kart.getQty());   
				            }
				            line();
				            break;
				        }
			          } catch (Exception e) {
			            System.out.println("\nSomething went wrong. \nPlease enter a number\n");
			          }
	        	}
	        } else if (p_key.equalsIgnoreCase("1")) {
	        	if (cart.containsKey("0001") || cart.containsKey("0002") || cart.containsKey("0003") || cart.containsKey("0004")|| cart.containsKey("0005") || cart.containsKey("0006")|| cart.containsKey("0007")){
		        	clear();
		        	headerReceipt();
		            double total = 0;
		            double subtotal = 0;
		            double tax = 0;
		            String leftAlignFormat = "+      %-13s %-9d %-17.2f+%n";
		            for (Map.Entry<String, Cart> c : cart.entrySet()) {
		                Cart kart = c.getValue();
		                Product p = kart.getProduct();
		        		System.out.format(leftAlignFormat, p.getName(),kart.getQty(), p.totalPrice(kart.getQty()) );
		        		subtotal = subtotal + p.totalPrice(kart.getQty());
		            }
	        		tax = 0.10 * subtotal;
			        total = subtotal + tax;
			        space();
					System.out.println("+     *************************************     +");
					space();
			        System.out.format("+    %-24s  %-16.2f +%n", "  Sub Total", subtotal);
			        System.out.format("+    %-24s  %-16.2f +%n", "  Sales Tax  " , tax);
		            System.out.format("+    %-24s  %-16.2f +%n", "  TOTAL AMOUNT (PHP)", total);
		            space();
		    		line();
		    		space();
		    		System.out.print("+   Type 'Cancel' to Cancel transaction         +\n");
		    		space();
		            line();
		            reciept();
	        	}
	        	else { 
	        		clear();
	        		productList();
	        		System.out.println("\n*************************************************\n"
    									+"*              No product rendered              *\n"
    									+"*************************************************\n"); 
	        	}
	        	
	        } else {
	        	if(!p_key.equalsIgnoreCase("2")) {
	        		System.out.println("\n \n \n \n \n");
	        		line();
	        		System.out.println("      !!! Enter valid product CODE !!!");
	        		line();
	        		System.out.println("\n \n \n \n \n");
	        	}
	        	header();
	        }

    	}
    }
    static void productList() {
		System.out.format("+-----------------------------------------------+%n");
		System.out.format("+ NAME            | PRICE        | Code         +%n");
		System.out.format("+-----------------------------------------------+%n");
        for (Map.Entry<String, Product> p : products.entrySet()) {
            Product product = p.getValue();
    		System.out.format("+ %-17s %-14.2f %-12s +%n", product.getName(),product.getPrice(),p.getKey());
        }
        line();
    }
    
    static void headerCart() {
		System.out.format("+-----------------------------------------------+%n");
		System.out.format("+ NAME            | PRICE        | QTY          +%n");
		System.out.format("+-----------------------------------------------+%n");
    }
    static void headerReceipt() {
    	line();
    	space();
		System.out.println("+               Corny Corner Store              +");
		System.out.println("+                Max Suniel Street              +");
		System.out.println("+           Carmen, Cagayan de Oro City,        +");
		System.out.println("+             9000, Misamis Oriental            +");
		space();
		System.out.println("+     *************************************     +");
		System.out.println("+                                               +");
		System.out.println("+      Product       Qty       Price            +");
		System.out.println("+                                               +");
    }
    static void header() {
		line();
		System.out.println("+               Canned Goods Store              +");
		line();
    }
    static void dateTime() {
    	LocalDateTime timeNow = LocalDateTime.now();
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    	String formattedTime = timeNow.format(timeFormat);
    	space();
    	System.out.println("+      Date: "+LocalDate.now()+"                         +");
    	System.out.println("+      Time: "+formattedTime+"                           +");
    	space();
    	System.out.println("+           Thank You For Supporting            +");
    	System.out.println("+            	Local Business!                 +");
    	space();
    }
    static void line() {
    	System.out.format("+-----------------------------------------------+%n");
    }
    static void space() {
    	System.out.println("+                                               +");
    }
    static void clear() {
        String test = "\n ";
        System.out.println(test.repeat(100));
    }
    static void reciept() {
        double total = 0;
        double subtotal = 0;
        double tax = 0;
        double change = 0;
    	while (true) {
            System.out.print("Customer Cash:"); 
            String r_cash = scanner.nextLine();
            if (r_cash.equalsIgnoreCase("cancel")){
            	System.out.print("Transaction Cancelled \nThank you have a good day"); 
            	System.exit(0);
            }
            try {
            	int rendered_c=Integer.parseInt(r_cash);
            	
    	        if (rendered_c < 0) {            
    	        	System.out.println("\n*************************************************\n"
    	        						+"*             Enter positive number             *\n"
    	        						+"*************************************************\n");  
    	        }
    	        else {
    	        	clear();
    	        	headerReceipt();
    	            for (Map.Entry<String, Cart> c : cart.entrySet()) {
    	                Cart kart = c.getValue();
    	                Product p = kart.getProduct();
    	        		System.out.format("+      %-13s %-9d %-17.2f+%n", p.getName(),kart.getQty(), p.totalPrice(kart.getQty()) );
    	        		subtotal = subtotal + p.totalPrice(kart.getQty());
    	            }
            		tax = 0.10 * subtotal;
    		        total = subtotal + tax;
    		        change = rendered_c - total;
					System.out.println("+     *************************************     +");
					space();
			        System.out.format("+    %-24s  %-16.2f +%n", "  Sub Total", subtotal);
			        System.out.format("+    %-24s  %-16.2f +%n", "  Sales Tax  " , tax);
		            System.out.format("+    %-24s  %-16.2f +%n", "  TOTAL AMOUNT (PHP)", total);
		            space();
		            System.out.println("+     *************************************     +");

    		        if (rendered_c < total ) {
    		        	System.out.println("\n*************************************************\n"
        									+"*        Insufficient amount try again          *\n"
        									+"*************************************************\n");  
    		        	reciept();
    		        }
    		        else {
    			        space();
        	        	clear();
        	        	headerReceipt();
        	            for (Map.Entry<String, Cart> c : cart.entrySet()) {
        	                Cart kart = c.getValue();
        	                Product p = kart.getProduct();
        	        		System.out.format("+      %-13s %-9d %-17.2f+%n", p.getName(),kart.getQty(), p.totalPrice(kart.getQty()) );
        	            }
        	            space();
    					System.out.println("+     *************************************     +");
    					space();
    			        System.out.format("+    %-24s  %-16.2f +%n", "  Sub Total", subtotal);
    			        System.out.format("+    %-24s  %-16.2f +%n", "  Sales Tax  " , tax);
    		            System.out.format("+    %-24s  %-16.2f +%n", "  TOTAL AMOUNT (PHP)", total);
    		            space();
    		            System.out.println("+     *************************************     +");
    		            space();
    			        System.out.format("+    %-24s  %-16d +%n", "  Cash Rendered ", rendered_c);
    			        System.out.format("+    %-24s  %-16.2f +%n", "  Change  " , change);
    			        space();
    		            System.out.println("+     *************************************     +");
    		            space();
    		            dateTime();
    		            line();
    		            System.exit(0);
    		        }
    	        }
              } catch (Exception e) {
                System.out.println("\nSomething went wrong. \nPlease enter a number\n");
              }
    	}
    }
}