package QingShi.FreeSMS;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import JSON.CarrierLookUpResults;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Carrier service responsible for creating the dedicated email address for a certain phone number.
 * 
 * @author shiqing
 */
public class CarrierService {
	private static Logger logger = Logger.getLogger(CarrierService.class);

	public CarrierService() {
		ConsoleAppender appender = new ConsoleAppender(new PatternLayout());
		logger.setLevel(Level.INFO);
		logger.addAppender(appender);
	}
	
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
		String email = null;
		for (Carrier carrier : Carrier.values()) {
			if (carrierName.equals(carrier.getCarrierName())) {
				email = phoneNumber + carrier.getEmailSuffix();
			}
		}
		logger.info("Generating email address : " + email);
		return email;
	}
}
