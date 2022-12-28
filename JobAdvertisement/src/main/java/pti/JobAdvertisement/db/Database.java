package pti.JobAdvertisement.db;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import jakarta.persistence.Query;
import pti.JobAdvertisement.model.Client;
import pti.JobAdvertisement.model.Position;

public class Database {

	private SessionFactory sessionFactory;

	public Database() {

		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings from
																							// hibernate.cfg.xml
				.build();

		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}

	public void close() {
		sessionFactory.close();
	}

	public List<Client> getAllClients() {
		List<Client> clients = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("SELECT c FROM Client c", Client.class);
		clients = query.getResultList();
		transaction.commit();
		session.close();
		return clients;
	}

	public boolean isEmailUnique(String email) {
		List<Client> clients = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("SELECT c FROM Client c WHERE c.email=?1", Client.class);
		query.setParameter(1, email);
		clients = query.getResultList();
		transaction.commit();
		session.close();
		return clients.isEmpty();
	}

	public void insertClient(Client client) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.persist(client);
		transaction.commit();
		session.close();
	}

	public boolean isKeyValid(int key) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Client client = session.get(Client.class, key);
		transaction.commit();
		session.close();
		return client != null;
	}

	public void insertPosition(Position position) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.persist(position);
		transaction.commit();
		session.close();
	}

	public Position getPositionById(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Position position = session.get(Position.class, id);
		transaction.commit();
		session.close();
		return position;
	}

	public List<Position> getPositionsBySubtitle(String subtitle) {
		List<Position> positions = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		subtitle = "%" + subtitle + "%";
		Query query = session.createQuery("SELECT p FROM Position p WHERE UPPER(p.title) LIKE UPPER(?1)",
				Position.class);
		query.setParameter(1, subtitle);

		positions = query.getResultList();
		transaction.commit();
		session.close();
		return positions;
	}

}
