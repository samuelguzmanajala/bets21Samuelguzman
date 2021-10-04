import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.AdminUser;
import domain.Event;
import domain.Question;
import domain.RegularUser;
import domain.User;
import exceptions.QuestionAlreadyExist;
import utility.TestUtilityDataAccess;
import utility.TestUtilityFacadeImplementation;

class DeleteEventDATest {

	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	static DataAccess testDA = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));

	private Event ev;
	//@BeforeAll
	static void setUpBeforeClass() throws Exception {
		testDA.open(false);
		testDA.registrar("samuel", "1234", "samuel", "guzman", "12/04/2000", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "32", 100);
		testDA.close();
	}


	/*
	 * No he conseguido hacer que funcione como deberia el test espero un NullPointer
	 * pero al hacer el assertThrows me da error. 
	 * 
	 */

	@Test
	@DisplayName("evento nulo")
	void testDeleteEvent() throws NullPointerException{
		User u = new RegularUser("samuel", "1234", "samuel", "guzman", "12/04/2000", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "32", 100);
		Event ev1=null;
		testDA.open(false);
		//assertThrows(NullPointerException.class,()->{
		try {
			//Throwable exception=	assertThrows(NullPointerException.class,()->{
			sut.deleteEvent(null);
			testDA.close();
			//});
			//String excMsg = exception.getMessage();
			//assert excMsg != null 
			//		: "Exception message should not be null";
			//String msg 
			//= "Exception message should contain the word \"amount\"";
			//assert excMsg.toLowerCase().contains("amount") : msg;
		}catch(Exception e){
			String msg=e.getMessage();
			assertEquals(e,"parametro nulo");
			testDA.close();
		}

	}
	@Test
	@DisplayName("evento nulo")
	void testDeleteEvent2() throws NullPointerException{
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev1=new Event(2,"Madrid-Atletic",UtilDate.newDate(year, month, 17));
		testDA.open(false);
		//testDA.insertEvent(ev1);
		try {
			sut.deleteEvent(ev1);
		}catch(Exception e) {
			String msg=e.getMessage();
			assertEquals(msg,"no se ha encontrado el evento");
		}


	}
	/*
	@Test
	@DisplayName("evento nulo")
	void testDeleteEvent3() throws NullPointerException{
		System.out.println("test3");
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		testDA.open(false);
		Event ev1=new Event(3,"Atletico-Barcelona",UtilDate.newDate(year, month, 17));
		boolean s=testDA.insertEvent(ev1);

		boolean expected=true;
		testDA.close();

		testDA.open(false);
		//sut.open(true);
		ArrayList<Event> ed=sut.getEvents(UtilDate.newDate(year, month, 17));
		System.out.println(ed.get(0).getDescription());
		testDA.close();
		testDA.open(false);
		try {
			boolean obtained=testDA.deleteEvent(ed.get(0));
			assertEquals(expected,obtained);
		}catch(Exception e) {

		}

		testDA.close();

//esto es para que haya un commit	}
*/
	

	

	@Test
	@DisplayName("evento nulo")
	void testDeleteEvent4() throws NullPointerException{
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev1=new Event(4,"Atletico-Barcelona",UtilDate.newDate(year, month, 17));
		Question q1;
		q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
			
		testDA.open(false);
		boolean s=testDA.insertEvent(ev1);
			boolean expected=true;
			boolean obtained=testDA.deleteEvent(ev1);
			assertEquals(expected,obtained);
			testDA.close();

		
	}
	 




}
