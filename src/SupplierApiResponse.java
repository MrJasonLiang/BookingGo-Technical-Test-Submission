import java.util.Collections;
import java.util.List;

public class SupplierApiResponse {
	private String supplier_id;
	private String pickup;
	private String dropoff;
	private List<Option> options;
	
	public String getSupplierID() {
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
}
