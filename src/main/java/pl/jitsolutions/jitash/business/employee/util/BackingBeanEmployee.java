package pl.jitsolutions.jitash.business.employee.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import pl.jitsolutions.jitash.business.employee.entity.Status;

import static pl.jitsolutions.jitash.business.employee.entity.Status.ACTIVE;

@Named
@RequestScoped
public class BackingBeanEmployee implements Serializable {

	private String status;

	@PostConstruct
	public void init() {
		status = ACTIVE.getValue() ;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Status[] getStatuses() {
		return Status.values();
	}
}

