package pti.JobAdvertisement.response;

public class AppKey {
	private Integer key;
	private String massege;

	public AppKey() {
		super();
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getMassege() {
		return massege;
	}

	public void setMassege(String massege) {
		this.massege = massege;
	}

	@Override
	public String toString() {
		return "AppKey [key=" + key + ", massege=" + massege + "]";
	}

}
