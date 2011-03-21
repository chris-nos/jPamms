import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableHeaderCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;


public class MensaScraper  {

	private ArrayList<MensaDay> menues = new ArrayList<MensaDay>();
	
	public static String MENSA = "http://www.studentenwerk-pb.de/gastronomie/speiseplaene/bistro-palmengarten.html";
	public static String PALMENGARTEN = "http://www.studentenwerk-pb.de/gastronomie/speiseplaene/mensa.html?no_cache=1";
	public static String PUB = "http://www.studentenwerk-pb.de/gastronomie/speiseplaene/gownsmens-pub.html";
	
	private String location = MENSA;
	
	 
	public ArrayList<MensaDay> getMenues() {
		return menues;
	}

	public void setMenues(ArrayList<MensaDay> menues) {
		this.menues = menues;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation()
	{
		return this.location;
	}

	public void scrap() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", 
		"fatal"); 
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog"); 
		WebClient browser = new WebClient();
		//browser.setIncorrectnessListener();
		HtmlPage mensaPage = browser.getPage(location);
		List weekContentDivision = mensaPage.getByXPath("/html/body/div[2]/div/div[2]/div[1]/div/div[2]/table");
		menues = extractWeekMenues(weekContentDivision);
		browser.closeAllWindows();
		
	}
	
	private ArrayList<MensaDay> extractWeekMenues(List dayTables)
	{
		ArrayList result = new ArrayList();
		for(int i=0; i<dayTables.size() ;i++)
		{
			if(dayTables.get(i) instanceof HtmlTable)
			{
				HtmlTable dayData = (HtmlTable)dayTables.get(i);
				result.add(extractDayData(dayData));
			}
		}
		return result;
	}
	
	private MensaDay extractDayData(HtmlTable dayTable)
	{
		MensaDay day = new MensaDay();
		
		//get description
		ArrayList<Menu> menues = new ArrayList<Menu>();
		day.setDescription(dayTable.getAttribute("summary"));
		

		//get date
		Date date = new Date();
		Object tableHead = dayTable.getFirstByXPath("./thead/tr[1]/th");
		if(tableHead instanceof HtmlTableHeaderCell)
		{
			String dateData = ((HtmlTableHeaderCell) tableHead).asText();
			dateData = dateData.substring(dateData.length()-10);
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			try {
				date = format.parse(dateData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			day.setDate(new GregorianCalendar(date.getYear()+1900, date.getMonth(),date.getDate()));
			
		
		}
		
		//get menues 
		List menuTableRows = dayTable.getByXPath("./tbody/tr");
		for(int i = 0;i < menuTableRows.size(); i++)
		{
			Object menuRow = menuTableRows.get(i);
			if(menuRow instanceof HtmlTableRow )
				menues.add(extractMenueData((HtmlTableRow)menuRow));
		}
		
		day.setMenues(menues);
		System.out.println(day);
		return day;
	}
	
	private Menu extractMenueData(HtmlTableRow menuRow)
	{
		Menu menu = new Menu();
		//name
		menu.setName(((HtmlDivision)menuRow.getFirstByXPath("./td/div")).asText());
		menu.setMainDish(((HtmlDivision)menuRow.getByXPath("./td[2]/div[1]").get(0)).asText());
		menu.setSideDish(((HtmlDivision)menuRow.getByXPath("./td[2]/div[2]").get(0)).asText());
		return menu;
	}

}