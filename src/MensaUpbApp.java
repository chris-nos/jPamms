import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;



public class MensaUpbApp {
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		new MensaScraper().scrap();
	}
}
