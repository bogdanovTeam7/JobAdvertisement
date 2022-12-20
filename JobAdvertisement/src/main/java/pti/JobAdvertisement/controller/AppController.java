package pti.JobAdvertisement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pti.JobAdvertisement.db.Database;
import pti.JobAdvertisement.model.Client;
import pti.JobAdvertisement.response.AppKey;

@RestController
public class AppController {
	@GetMapping("/")
	public List<Client> showUsers() {

		Database db = new Database();
		List<Client> clients = db.getAllClients();
		db.close();

		return clients;
	}

	@PostMapping("/clients")
	public AppKey registrate(@RequestParam(name = "name") String name, @RequestParam(name = "email") String email) {
		AppKey key = new AppKey();
		if (!isNameValid(name)) {
			key.setMassege("Hibás név");
		} else if (!isEmailValid(email)) {
			key.setMassege("Hibás email");
		} else if (!isEmailUnique(email)) {
			key.setMassege("Olayn email már létezik");
		} else {
			Database db = new Database();
			Client client = new Client(name, email);
			db.insertClient(client);
			key.setKey(client.getId());
			key.setMassege("Sikeres regisztráció");
			db.close();
		}

		return key;
	}

	private boolean isEmailUnique(String email) {
		Database db = new Database();
		boolean isUnique = db.isEmailUnique(email);
		db.close();
		return isUnique;
	}

	private boolean isEmailValid(String email) {
		String regex = "^(.+)@(.+)$";
		return email.matches(regex);
	}

	private boolean isNameValid(String name) {
		if (name == null) {
			return false;
		}
		name = name.trim();
		return name.length() > 0 && name.length() <= 100;
	}
}
