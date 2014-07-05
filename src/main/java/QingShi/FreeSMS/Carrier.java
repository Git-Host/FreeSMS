package QingShi.FreeSMS;

/**
 * Enum for map carrier to their email suffix
 * Refer to {@link http://www.emailtextmessages.com/}
 * 
 * If you want to add new carrier for any mobile phone number, 
 * just update here, and you are done.
 * 
 * @author shiqing
 */
public enum Carrier {
	ATT("AT&T Wireless","@txt.att.net"),
	SPRINT("Sprint Spectrum, L.P.","@messaging.sprintpcs.com"),
	TMOBILE("T-Mobile USA, Inc.","@tmomail.net "),
	VERIZON("Verizon Wireless","@vtext.com");
	
	private String carrierName;
	private String emailSuffix;
	
	private Carrier(String carrierName, String emailSuffix) {
		this.carrierName = carrierName;
		this.emailSuffix = emailSuffix;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public String getEmailSuffix() {
		return emailSuffix;
	}
}
