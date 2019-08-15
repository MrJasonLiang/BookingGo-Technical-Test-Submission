
public class Option implements Comparable<Option> {
	private String car_type;
	private int price;
	
	public String getCarType() {
		return car_type;
	}
	
	public int getPrice() {
		return price;
	}

	@Override
	public int compareTo(Option other) {
		return Integer.compare(price, other.price);
	}
}
