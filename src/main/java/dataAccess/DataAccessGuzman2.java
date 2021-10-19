package dataAccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Event;
import domain.Question;

public class DataAccessGuzman2 {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;
	static String adm = "admin";

	ConfigXML c = ConfigXML.getInstance();

	public DataAccessGuzman2(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
		+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessGuzman2() {
		new DataAccess(false);
	}
	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
		+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}
	
	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}
	
	public boolean deleteEvent(Event event) {
		if (event == null)
			throw new NullPointerException("The event is null");

		if (db.find(Event.class, event.getEventNumber()) == null) {
			System.out.println("El evento no se encuentra en la base de datos");
			return false;
		}

		String deleteEventSql = "DELETE FROM Event e WHERE e.getEventNumber()=?1";
		String selectAllQuestionSql = "SELECT qu FROM Question qu";

		Query query = db.createQuery(deleteEventSql, Question.class);

		query.setParameter(1, event.getEventNumber());

		TypedQuery<Question> query2 = db.createQuery(selectAllQuestionSql, Question.class);
		try {
			List<Question> allDbQuestions = query2.getResultList();
			db.getTransaction().begin();
			for (Question question : allDbQuestions) {
				if (question.getEvent().equals(event)) {
					db.remove(question);
					System.out.println("La pregunta:"+question.getQuestion()+" ha sido ELIMINADA");
				} else {
					System.out.println("La pregunta "+question.getQuestion()+" No ha sido ELIMINADA");
				}
			}
			query.executeUpdate();
			db.getTransaction().commit();
			System.out.println("Eveno Eliminado: " + event); 

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}


}
