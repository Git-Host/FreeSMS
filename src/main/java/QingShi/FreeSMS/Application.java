package QingShi.FreeSMS;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Entry point to start the FreeSMS application.
 * 
 * @author shiqing
 *
 */
public class Application extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Application.class);
	
	public Application() {
		// Log initialization
		ConsoleAppender appender = new ConsoleAppender(new PatternLayout());
		logger.setLevel(Level.INFO);
		logger.addAppender(appender);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Load static index.html page 
		String text = new Scanner( new File("/Users/shiqing/Documents/workspace_jee2/FreeSMS/src/resource/index.html") ).useDelimiter("\\A").next();
		resp.getWriter().write(text);
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Change to getEnv
		Server server = new Server(5000);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		// Add servlets
		context.addServlet(new ServletHolder(new Application()), "/");
		context.addServlet(new ServletHolder(new MessageService()), "/sendMessage");
		
		server.start();
		logger.info("Server starts at 5000");
		server.join();
	}

}
