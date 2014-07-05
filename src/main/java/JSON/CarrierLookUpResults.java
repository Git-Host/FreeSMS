package JSON;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Java POJO to hold the json result from TelApi REST api call for carrier look up.
 * 
 * Example response : 
 * 
 * {
 *  "first_page_uri": "/v1/Accounts/../Lookups/Carrier.json?Page=0&amp;PageSize=50", 
 *  "carrier_lookups": [{"phone_number": "+11234567890", 
 *                       "date_updated": "Wed, 02 Jul 2014 16:17:38 -0000", 
 *                       "price": "0.01", 
 *                       "mcc": 310, 
 *                       "carrier_id": 6140, 
 *                       "country_code": "US", 
 *                       "mnc": 150, 
 *                       "network": "AT&T Wireless", 
 *                       "mobile": "true", 
 *                       "uri": "/v2/Accounts/....", 
 *                       "account_sid": "...", 
 *                       "sid": "...", "
 *                       date_created": "Wed, 02 Jul 2014 16:17:37 -0000", 
 *                       "api_version": "v2"}], 
 *  "end": 0, 
 *  "total": 1, 
 *  "previous_page_uri": "", 
 *  "num_pages": 1, 
 *  "uri": "/v1/Accounts/.../Lookups/Carrier.json", 
 *  "page_size": 50, 
 *  "start": 0, 
 *  "next_page_uri": "", 
 *  "last_page_uri": "/v1/Accounts/.../Lookups/Carrier.json?Page=0&PageSize=50", 
 *  "page": 0
 *  }
 * 
 * @author shiqing
 *
 */
public class CarrierLookUpResults {
	@SerializedName("carrier_lookups")
	private List<CarrireLookUpResult> lookupResults;
	
	public List<CarrireLookUpResult> getLookupResults() {
		return lookupResults;
	}

	public void setLookupResults(List<CarrireLookUpResult> lookupResults) {
		this.lookupResults = lookupResults;
	}

	public static class CarrireLookUpResult {
		@SerializedName("phone_number")
		private String phoneNumber;
		@SerializedName("network")
		private String network;
		@SerializedName("carrier_id")
		private String carrierId;
		
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getNetwork() {
			return network;
		}
		public void setNetwork(String network) {
			this.network = network;
		}
		public String getCarrierId() {
			return carrierId;
		}
		public void setCarrierId(String carrierId) {
			this.carrierId = carrierId;
		}
	}
}
