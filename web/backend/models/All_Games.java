package apps.gam.web.backend.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Immutable // This is a view, not a table. So, no setters
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class All_Games {

	@Id
	private int id;

	private String title;
	private String platform;
	private String rate;
	private String status;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getPlatform() {
		return platform;
	}

	public String getRate() {
		return rate;
	}

	public String getStatus() {
		return status;
	}

}
