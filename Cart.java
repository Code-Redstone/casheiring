public class Cart {
	private double subtotal;
	private int qty;
	private Product p;
	
	public Cart(Product p, int qty) {
		this.qty = qty;
		this.p = p;
		this.subtotal = p.getPrice() * qty;
	}
	
	public int getQty() {
		return qty;
	}
	
	public double getTotal() {
		return subtotal;
	}
	
	public Product getProduct() {
		return p;
	}
}