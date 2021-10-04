import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.QuestionAlreadyExist;

class DeleteEventBLMockTest {
	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Question mockedQuestion = Mockito.mock(Question.class);
	Event mockedEvent = Mockito.mock(Event.class);
	

	BLFacade sut = new BLFacadeImplementation(dataAccess);

	@SuppressWarnings("unchecked")
	@Test
	void test() {
		Event ev1 = new Event();
		Mockito.when(dataAccess.deleteEvent(Mockito.isNull(Event.class))).thenThrow(new NullPointerException());
		assertThrows(NullPointerException.class,()->{
			sut.deleteEvent(null);
		});
	}
	
	
	@Test
	void test2() {
		Event ev1 = new Event();
		Mockito.doReturn(null).when(mockedEvent).getEventDate();
		Mockito.when(dataAccess.deleteEvent(ev1)).thenThrow(new NullPointerException());
		assertThrows(NullPointerException.class,()->{
			sut.deleteEvent(ev1);
		});
	}
	

	@Test
	void test3() {
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
		Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));

		Question q1;
		Question q2;
		Question q3;
		
		q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
		q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
		q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
		List<Question> preguntasDB=new ArrayList<Question>();
		preguntasDB.add(q1);
		preguntasDB.add(q2);
		preguntasDB.add(q3);
		
		
		
		Mockito.doReturn(UtilDate.newDate(year, month, 17)).when(mockedEvent).getEventDate();
		Mockito.doReturn(1).when(mockedEvent).getEventNumber();
		Mockito.doReturn(ev1).when(mockedQuestion).getEvent();
		Mockito.doReturn(true).when(dataAccess).deleteEvent(ev1);
		boolean expected=true;
		boolean obtained=sut.deleteEvent(ev1);
		assertEquals(expected,obtained);
		/*
		Mockito.when(dataAccess.deleteEvent(ev1)).thenThrow(new NullPointerException());
		assertThrows(NullPointerException.class,()->{
			sut.deleteEvent(ev1);
		});
		*/
	}
	public void show(boolean isquestion) {
		if(isquestion) {
			System.out.println("se ha eliminado la pregunta");

		}else {
			System.out.println("no se ha eliminado la pregunta");
		}
	}
	@Test
	void test4() {
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
		Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));

		Question q1;
		Question q2;
		Question q3;
		
		q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
		q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
		q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
		List<Question> preguntasDB=new ArrayList<Question>();
		preguntasDB.add(q1);
		preguntasDB.add(q2);
		preguntasDB.add(q3);
		
		
		
		Mockito.doReturn(UtilDate.newDate(year, month, 17)).when(mockedEvent).getEventDate();
		Mockito.doReturn(1).when(mockedEvent).getEventNumber();
		Mockito.doReturn(ev1).when(mockedQuestion).getEvent();
		Mockito.doReturn(false).when(dataAccess).deleteEvent(ev1);
		boolean expected=false;
		boolean obtained=sut.deleteEvent(ev1);
		assertEquals(expected,obtained);
		/*
		Mockito.when(dataAccess.deleteEvent(ev1)).thenThrow(new NullPointerException());
		assertThrows(NullPointerException.class,()->{
			sut.deleteEvent(ev1);
		});
		*/
	}
	
	
	

}
