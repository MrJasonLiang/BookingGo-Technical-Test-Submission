package main;

public class Ride implements Comparable<Ride> {
	private String supplierID;
	private String carType;
	private int price;
	
	public Ride(String supplierID, String carType, int price) {
		this.supplierID = supplierID;
		this.carType = carType;
		this.price = price;
	}
	
	public String getSupplierID() {
		return supplierID;
	}
	
	public String getCarType() {
		return carType;
	}
	
	public int getPrice() {
		return price;
	}
	
	@Override
	public int compareTo(Ride other) {
		return Integer.compare(price, other.price);
	}
}
