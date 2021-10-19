import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccessGuzman2;
import domain.Event;
import utility.TestUtilityDataAccess;

class DeleteEventDA2Test {

	static DataAccessGuzman2 sut = new DataAccessGuzman2(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();

	/*
	 * No se como comprobar si los outputs son los correctos con un assert pero son los correctos. 
	 */
	@Test
	@DisplayName("Caso 1: Eliminar evento cuando hay otros eventos con preguntas en la BD")
	@Order(1)
	void testDeleteEventA() {
		//define parameters
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1= new Event(1,"Atletico-Atletic", d);
		Event ev2= new Event(2, "Madrid-Atletic", d);
		Event ev3= new Event(3, "Atletico-Barcelona", d);
		Event ev4= new Event(4,"Atletico-Atletic", d);
		testDA.open();
		testDA.addEvent(ev1);
		testDA.addEvent(ev2);
		testDA.addEvent(ev3);
		testDA.addEvent(ev4);

		try {
			testDA.createQuestion(ev3, "Quien ganara", 0.3f);
			testDA.createQuestion(ev4, "Empate", 0.5f);
			testDA.close();
			boolean expected = true;
			System.out.println("Output: ");
			boolean actual = sut.deleteEvent(ev1);
			System.out.println("/FIN");
			assertEquals(expected, actual);
			System.out.println("Devolviendo datos a la base de datos");
			testDA.open();
			testDA.removeEvent(ev2);
			testDA.removeEvent(ev3);
			testDA.removeEvent(ev4);
			testDA.close();
		} catch (Exception e) {
			// TODO: handle exception
			testDA.close();
			fail("no se ha podido realizar el test correctamente");
		}
	}
	@Test
	@DisplayName("Caso 2: Eliminar Evento con 1 pregunta")
	@Order(2)
	void testDeleteEventB() {
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1= new Event(1,"Atletico-Atletic", d);
		Event ev2= new Event(2, "Madrid-Atletic", d);
		Event ev3= new Event(3, "Atletico-Barcelona", d);
		Event ev4= new Event(4,"Atletico-Atletic", d);
		testDA.open();
		testDA.addEvent(ev1);
		testDA.addEvent(ev2);
		testDA.addEvent(ev3);
		testDA.addEvent(ev4);

		try {
			testDA.createQuestion(ev3, "Quien ganara", 0.3f);
			testDA.createQuestion(ev4, "Empate", 0.5f);
			testDA.close();
			boolean expected = true;
			System.out.println("Output: ");
			boolean actual = sut.deleteEvent(ev4);
			System.out.println("/FIN");
			assertEquals(expected, actual);
			System.out.println("Devolviendo datos a la base de datos");
			testDA.open();
			testDA.removeEvent(ev1);
			testDA.removeEvent(ev2);
			testDA.removeEvent(ev3);
			testDA.close();
		} catch (Exception e) {
			// TODO: handle exception
			testDA.close();
			fail("no se ha podido realizar el test correctamente");
		}
	}
	
	

	@Test
	@DisplayName("Caso 3: evento nulo")
	@Order(3)
	void testDeleteEvent4() throws NullPointerException {
		Event ev1 = null;
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			sut.deleteEvent(ev1);
		});
		String actual = exception.getMessage();
		String expected = "The event is null";
		assertEquals(expected, actual);
		testDA.close();
	}
	
	

	@Test
	@DisplayName("Caso 4: El evento no se encuentra en la base de datos")
	@Order(4)
	void testDeleteEvent3() {
		//define parameters
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1 = new Event(3, "Atletico-Madrid", d);
		boolean expected = sut.deleteEvent(ev1);
		//fdsajkfdsla
		boolean obtained = false;
		assertEquals(expected, obtained);
	}

	@Test
	@DisplayName("Caso 6: Eliminar evento sin Pregunta")
	@Order(5)
	void testDeleteEvent7() {
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1 = new Event(3, "Atletico-Madrid", d);
		testDA.open();
		testDA.addEvent(ev1);
		testDA.close();
		boolean expected = sut.deleteEvent(ev1);
		boolean obtained = true;
		assertEquals(expected, obtained);
	}

}
