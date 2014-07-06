package QingShi.FreeSMS;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

//import com.telapi.api.TelapiConnector;
//import com.telapi.api.configuration.BasicTelapiConfiguration;
//import com.telapi.api.domain.CarrierLookup;
//import com.telapi.api.domain.list.CarrierLookupList;
//import com.telapi.api.exceptions.TelapiException;

/**
 * Message service responsible to resolve the phone number carrier
 * Generate the required information for email service to use.
 * 
 * Use {@code DefaultHttpClient} to do the REST api call to TelApi.
 * Right now comment all the TelApi java jar, cause it will throw the Jackson mapping exception.
 * 
 * @author shiqing
 *
 */
public class MessageService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final EmailService EMAIL_SERVICE = new EmailService();
	private static final CarrierService CARRIER_SERVICE = new CarrierService();
	private static Logger logger = Logger.getLogger(MessageService.class);
	
	private final String USERNAME = "USERNAME";
	private final String PASSWORD = "PASSWORD";
	private final String CARRIRE_LOOKUP_URL = "https://api.telapi.com/v1/Accounts/" + USERNAME + "/Lookups/Carrier.json?PhoneNumber=";
	
	public MessageService() {
		ConsoleAppender appender = new ConsoleAppender(new PatternLayout());
		logger.setLevel(Level.INFO);
		logger.addAppender(appender);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String replyEmail = req.getParameter("replyEmail");
		final String phoneNumber = req.getParameter("phoneNumber");
		final String subject = req.getParameter("subject");
		final String body = req.getParameter("body");
		
		// TelApi gives the json mapping exceptio, change to use apache http client to do the 
		// REST call directly
		/*
		BasicTelapiConfiguration conf = new BasicTelapiConfiguration();
        conf.setSid("AC6c8890849f742e2d95b14286b6bc96eb");
        conf.setAuthToken("2fab4c9b87174b439a5b83e901e60745");
        TelapiConnector conn = new TelapiConnector(conf);

        try {
            CarrierLookupList carrierLookups = conn.carrierLookup(phoneNumber);
            for (CarrierLookup carrierLookup : carrierLookups) {
                System.out.println(carrierLookup.getSid());
            }
        } catch (TelapiException e) {
            e.printStackTrace();
        }
        */
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
            httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME + ":" + PASSWORD));
            HttpPost httpPost = new HttpPost(CARRIRE_LOOKUP_URL + phoneNumber);

            logger.info("executing request : " + httpPost.getRequestLine());
            HttpResponse dresponse = httpclient.execute(httpPost);

            logger.info("Response status : " + dresponse.getStatusLine());
            String result = EntityUtils.toString(dresponse.getEntity());
            
            String toEmail = CARRIER_SERVICE.generateEmailAddress(phoneNumber, CARRIER_SERVICE.retrieveCarrire(result));
            if (toEmail == null) {
            	logger.warn("No carrier information about this number found, status : " + dresponse.getStatusLine());
            	throw new Exception("No carrier information about this number found");
            }
            EMAIL_SERVICE.sendEmail(toEmail, replyEmail, subject, body);
            
            logger.info("Message sending successfully.");
        } catch (Exception e) {
        	logger.error("Message sending fail due to " + e.getStackTrace());
            // TODO Add error static page
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
		
		// Show success page
		String text = new Scanner( new File("/Users/shiqing/Documents/workspace_jee2/FreeSMS/src/resource/success.html") ).useDelimiter("\\A").next();
		resp.getWriter().write(text);
	}
}
