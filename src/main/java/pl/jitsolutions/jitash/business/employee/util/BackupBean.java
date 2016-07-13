package pl.jitsolutions.jitash.business.employee.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class BackupBean implements Serializable {

	private Boolean status;

	@PostConstruct
	public void init() {
		status = true;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}

