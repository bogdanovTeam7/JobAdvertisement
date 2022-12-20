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


}
