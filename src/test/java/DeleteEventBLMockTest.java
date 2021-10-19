import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;

class DeleteEventBLMockTest {
	DataAccess dataAccess = Mockito.mock(DataAccess.class);



	BLFacade sut = new BLFacadeImplementation(dataAccess);


	@Test
	@DisplayName("caso 3: Evento nulo")
	void test3() {
		Mockito.when(dataAccess.deleteEvent(Mockito.isNull(Event.class))).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class,()->{
			sut.deleteEvent(null);
		});
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		Mockito.verify(dataAccess,Mockito.times(1)).deleteEvent(eventCaptor.capture());

	}


	@Test
	@DisplayName("Caso 1,2: evento eliminado correctamente")
	void test1() {
		java.util.Date d = UtilDate.newDate(2019, 10, 17);

		Event ev1 = new Event(3, "Atletico-Madrid", d);
		boolean expected=true;
		Mockito.doReturn(true).when(dataAccess).deleteEvent(Mockito.any(Event.class));
		boolean actual=sut.deleteEvent(ev1);
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		Mockito.verify(dataAccess,Mockito.times(1)).deleteEvent(eventCaptor.capture());
		assertEquals(expected,actual);
	}
	
	@Test
	@DisplayName("Caso 4: Evento no se encuentra en la base de datos")
	void test4() {
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1 = new Event(3, "Atletico-Madrid", d);
		boolean expected=false;
		Mockito.doReturn(false).when(dataAccess).deleteEvent(Mockito.any(Event.class));
		boolean actual=sut.deleteEvent(ev1);
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		Mockito.verify(dataAccess,Mockito.times(1)).deleteEvent(eventCaptor.capture());
		assertEquals(expected,actual);
	}
}
