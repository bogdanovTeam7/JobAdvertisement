package pti.JobAdvertisement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "positions")
public class Position {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "location")
	private String location;
	@Column(name = "client_id")
	private int clientId;

	public Position() {
		super();
	}

	public Position(int id, String title, String location, int clientId) {
		super();
		this.id = id;
		this.title = title;
		this.location = location;
		this.clientId = clientId;
	}

	public Position(String title, String location, int clientId) {
		super();
		this.id = 0;
		this.title = title;
		this.location = location;
		this.clientId = clientId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", title=" + title + ", location=" + location + ", clientId=" + clientId + "]";
	}

}
