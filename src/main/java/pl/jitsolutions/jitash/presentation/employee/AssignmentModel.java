package pl.jitsolutions.jitash.presentation.employee;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pl.jitsolutions.jitash.business.employee.entity.Assignment;

@Named
@ViewScoped
public class AssignmentModel implements Serializable {

	private Assignment assignment = new Assignment();

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
}
