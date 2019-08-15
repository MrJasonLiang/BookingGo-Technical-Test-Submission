import java.util.Collections;
import java.util.List;

public class SupplierApiResponse {
	private String supplier_id;
	private String pickup;
	private String dropoff;
	private List<Option> options;
	
	public String getSupplier_id() {
		return supplier_id;
	}
	
	public String getPickup() {
		return pickup;
	}
	
	public String getDropoff() {
		return dropoff;
	}
	
	public List<Option> getOptions() {
		return Collections.unmodifiableList(options);
	}
	
	public void printOptionsDesc() {
		Collections.sort(options, Collections.reverseOrder());
		
		for (Option option : options) {
			System.out.println(option.getCarType() + " - " + option.getPrice());
		}
	}
}
