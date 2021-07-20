package apps.gam.web.backend.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Status {
	@Id	
	private String id;
	
	private String description;

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

}
