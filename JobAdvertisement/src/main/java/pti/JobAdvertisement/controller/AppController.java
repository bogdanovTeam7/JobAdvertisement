package pti.JobAdvertisement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pti.JobAdvertisement.db.Database;
import pti.JobAdvertisement.model.Client;
import pti.JobAdvertisement.model.Position;
import pti.JobAdvertisement.response.AppKey;
import pti.JobAdvertisement.response.URLs;

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
		if (!isTextValid(name, 100)) {
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

	private boolean isTextValid(String name, int length) {
		if (name == null) {
			return false;
		}
		name = name.trim();
		return name.length() > 0 && name.length() <= length;
	}

	@PostMapping("/positions")
	public URLs insertPosition(@RequestParam(name = "key") int key, @RequestParam(name = "title") String title,
			@RequestParam(name = "location") String location) {
		URLs urls = new URLs();
		if (!isTextValid(title, 50)) {
			urls.setMassege("hibás az állás megnevezése");
		} else if (!isTextValid(location, 50)) {
			urls.setMassege("hibás földrajzi hely");
		} else {
			Database db = new Database();
			if (!db.isKeyValid(key)) {
				urls.setMassege("hibás kulcs");
			} else {
				Position position = new Position(title, location, key);
				db.insertPosition(position);
				String url = createPositionURL(position.getId());
				urls.addURL(url);
				urls.setMassege("sikeres mentés");
				System.out.println(urls);
			}

			db.close();
		}

		return urls;
	}

	private String createPositionURL(int id) {
		return "/position/" + id;
	}

	@PostMapping("/position/{id}")
	public Position getPosition(@PathVariable int id) {
		Database db = new Database();
		Position position = db.getPositionById(id);
		db.close();
		return position;
	}

	@GetMapping("/search")
	public URLs searchURLs(@RequestParam(name = "key") int key, @RequestParam(name = "subtitle") String subtitle) {
		URLs urls = new URLs();
		Database db = new Database();
		if (!isTextValid(subtitle, 50)) {
			urls.setMassege("hibás az állás megnevezése");
		} else if (!db.isKeyValid(key)) {
			urls.setMassege("hibás kulcs");
		} else {
			List<Position> positions = db.getPositionsBySubtitle(subtitle);
			for (Position position : positions) {
				String url = createPositionURL(position.getId());
				urls.addURL(url);
			}
			urls.setMassege("sikeres eredmény lekérdezése");
		}

		db.close();
		return urls;
	}

}
