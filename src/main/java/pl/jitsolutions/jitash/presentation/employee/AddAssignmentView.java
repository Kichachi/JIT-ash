package pl.jitsolutions.jitash.presentation.employee;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.jitsolutions.jitash.business.employee.boundry.AssignmentCreator;
import pl.jitsolutions.jitash.business.employee.boundry.AssignmentsProvider;

@Named
@ViewScoped
public class AddAssignmentView implements Serializable {

	@Inject
	private AssignmentsProvider assignmentsProvider;

	@Inject
	private AssignmentCreator assignmentCreator;

	@Inject
	private AssignmentModel assignmentModel;

	private String assignmentMessage = "Dodano nowy przydział";

	public String saveAssignment(){

		System.out.println("Dodajemy przydzial");
		assignmentCreator.save(assignmentModel.getAssignment());

		return null;
	}

	public String getAssignmentMessage() {
		return assignmentMessage;
	}

	public void setAssignmentMessage(String assignmentMessage) {
		this.assignmentMessage = assignmentMessage;
	}
}
