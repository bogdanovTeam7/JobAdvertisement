package pti.JobAdvertisement.response;

import java.util.ArrayList;
import java.util.List;

public class URLs {
	private List<String> urlList;
	private String massege;

	public URLs() {
		super();
		urlList = new ArrayList<>();
	}

	public String getMassege() {
		return massege;
	}

	public void setMassege(String massege) {
		this.massege = massege;
	}

	public void addURL(String URL) {
		urlList.add(URL);
	}

	public List<String> getUrlList() {
		return urlList;
	}

	@Override
	public String toString() {
		return "URLs [urlList=" + urlList + ", massege=" + massege + "]";
	}

}
