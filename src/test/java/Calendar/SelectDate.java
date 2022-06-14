package Calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.annotations.Test;

public class SelectDate {

	@Test
	public void selectDate() throws Exception {
		Date d = new Date();
		System.out.println(d.toString());
		String date = "15-04-1997";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date d1 = sdf.parse(date);
		System.out.println(d1.toString());
						
		sdf = new SimpleDateFormat("dd");
		String day = sdf.format(d1);
		System.out.println("day--------"+day);
		
		sdf = new SimpleDateFormat("MMMM");
		String month = sdf.format(d1);
		System.out.println("Month------------"+month);
		
		sdf= new SimpleDateFormat("yyyy");
		String year = sdf.format(d1);
		System.out.println("Year--------"+year);

	}
	@Test
	public void selectDateFromCalendar(String date) {
		
		try {
			Date sdf = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			String day = new SimpleDateFormat("dd").format(sdf);
			String month = new SimpleDateFormat("MMMM").format(sdf);
			String year = new SimpleDateFormat("yyyy").format(sdf);
			String monthyear = month+" "+year;
			System.out.println(monthyear   +"     "+ day );
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
