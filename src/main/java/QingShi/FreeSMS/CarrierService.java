package QingShi.FreeSMS;

import JSON.CarrierLookUpResults;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Carrier service responsible for creating the dedicated email address for a certain phone number.
 * 
 * @author shiqing
 */
public class CarrierService {
	/**
	 * Retrieve the carrier information form returned JSON object.
	 */
	public String retrieveCarrire(String result) {
        Gson gson = new GsonBuilder().create();
        CarrierLookUpResults results = gson.fromJson(result, CarrierLookUpResults.class);
        return results.getLookupResults().get(0).getNetwork();
	}
	
	/**
	 * Generate the dedicate email address for this phone number.
	 * If not found return null.
	 * @param phoneNumber
	 * @param carrierName
	 * @return
	 */
	public String generateEmailAddress(String phoneNumber, String carrierName) {
		for (Carrier carrier : Carrier.values()) {
			if (carrierName.equals(carrier.getCarrierName())) {
				return phoneNumber + carrier.getEmailSuffix();
			}
		}
		return null;
	}
}
