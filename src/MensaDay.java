import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

public class MensaDay {
	private String description  = "";
	private GregorianCalendar date = new GregorianCalendar();
	private ArrayList<Menu> menues = new ArrayList();
	
	public Calendar getDate() {
		return date;
	}
	public void setDate(GregorianCalendar date) {
		this.date = date;

	}
	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String day) {
		this.description = day;
	}
	
	public ArrayList getMenues() {
		return menues;
	}
	
	public void setMenues(ArrayList menues) {
		this.menues = menues;
	}
	
	public String getDayName()
	{
		return Util.indexToDay(date.get(Calendar.DAY_OF_WEEK));
	}

	public String toString()
	{
		String day= getDayName()+"\n---------------------\n";
		for(int i = 0; i< menues.size();i++)
			day+= menues.get(i)+"\n";
		return day+"----------------------\n";
	}
	

	
	
}
