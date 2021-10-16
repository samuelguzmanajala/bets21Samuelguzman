import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import utility.TestUtilityDataAccess;

class DeleteEventDATest {

	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();

	/**
	 * *It's goig to allow test the output
	 */
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	/*
	 * No he conseguido hacer que funcione como deberia el test espero un
	 * NullPointer pero al hacer el assertThrows me da error.
	 *
	 */

	@Test
	@DisplayName("Caso 2: evento nulo")
	void testDeleteEvent2() throws NullPointerException {
		Event ev1 = null;
		NullPointerException exception = assertThrows(NullPointerException.class, () -> {
			sut.deleteEvent2(ev1);
		});
		String actual = exception.getMessage();
		String expected = "The event is null";
		assertEquals(expected, actual);
		testDA.close();
	}

	@Test
	@DisplayName("Caso 3: El evento no se encuentra en la base de datos")
	void testDeleteEvent3() {
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1 = new Event(3, "Atletico-Madrid", d);
		testDA.open();
		testDA.addEventWithQuestion("desc", d, "quien ganara?", 0.3f);
		testDA.close();
		boolean expected = sut.deleteEvent2(ev1);
		boolean obtained = false;
		assertEquals(expected, obtained);
	}

	@Test
	@DisplayName("Caso : Eliminar evento sin Pregunta")
	void testDeleteEvent7() {
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1 = new Event(3, "Atletico-Madrid", d);
		testDA.open();
		testDA.addEvent(ev1);
		testDA.close();
		boolean expected = sut.deleteEvent2(ev1);
		boolean obtained = true;
		assertEquals(expected, obtained);
	}

	@Test
	@DisplayName("Caso : Eliminar evento cuando solo hay una pregunta en la BD y esta pertenece al evento")
	void testDeleteEventWithQuestionOnlyEvent() {
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1 = new Event(3, "Atletico-Madrid", d);
		testDA.open();
		testDA.addEvent(ev1);
		try {
			testDA.createQuestion(ev1, "Quien ganara", 0.3f);
			testDA.createQuestion(ev1, "Empate", 0.5f);
			testDA.close();
			boolean expected = true;
			boolean actual = sut.deleteEvent2(ev1);
			assertEquals(expected, actual);
		} catch (Exception e) {
			// TODO: handle exception
			testDA.close();
			fail("no se ha podido realizar el test correctamente");
		}

	}
/*
	@Test
	@DisplayName("Caso : Eliminar evento con pregunta cuando hay mas preguntas en otros eventos")
	void testDeleteEventWithQuestion() {
		boolean expected = true;
		java.util.Date d = UtilDate.newDate(2019, 10, 17);
		Event ev1 = new Event(1, "Atletico-Atletic", d);
		Event ev2 = new Event(2, "Madrid-Atletic", d);
		testDA.open();
		testDA.addEvent(ev1);
		testDA.addEvent(ev2);
		try {
			testDA.createQuestion(ev1, "Atletico gana", 0.8f);
			testDA.createQuestion(ev2, "Empate", 0.15f);
			testDA.close();
			boolean actual = sut.deleteEvent2(ev1);
			assertEquals(expected, actual);
		} catch (Exception e) {
			testDA.close();
			// TODO: handle exception
		}
	}
	*/
	
	
	
	

	
	
	
/*
	@Test
	@DisplayName("evento nulo")
	void testDeleteEvent5() throws NullPointerException {
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev1 = new Event(2, "Madrid-Atletic", UtilDate.newDate(year, month, 17));
		testDA.open();
		// testDA.insertEvent(ev1);
		try {
			sut.deleteEvent(ev1);
		} catch (Exception e) {
			String msg = e.getMessage();
			assertEquals(msg, "no se ha encontrado el evento");
		}

	}

	@Test
	@DisplayName("evento nulo")
	void testDeleteEvent4() throws NullPointerException {
		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		Event ev1 = new Event(4, "Atletico-Barcelona", UtilDate.newDate(year, month, 17));
		Question q1;
		q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);

		testDA.open();
		// boolean s=testDA.addEventWithQuestion();
		boolean expected = true;
		boolean obtained = true;
		// boolean obtained=testDA.deleteEvent(ev1);
		assertEquals(expected, obtained);
		testDA.close();

	}
*/

}
