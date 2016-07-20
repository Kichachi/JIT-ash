package pl.jitsolutions.jitash.presentation.employee;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pl.jitsolutions.jitash.business.employee.boundry.AssignmentCreator;
import pl.jitsolutions.jitash.business.employee.boundry.AssignmentsProvider;
import pl.jitsolutions.jitash.business.employee.entity.Assignment;

@Named
@ViewScoped
public class AddAssignmentView implements Serializable {

	@Inject
	private AssignmentsProvider assignmentsProvider;

	@Inject
	private AssignmentCreator assignmentCreator;

	@Inject
	private AssignmentModel assignmentModel;


	private final String successMessage = "Przydział został dodany do bazy";
	private final String existsMessage = "Przydział o wprowadzonych danych istnieje już w bazie.";
	private final String failMessage = "Dodanie przydziału się nie powiodło.";
	private String assignmentMessage;

	public String saveAssignment(){
		Assignment assignment = assignmentModel.getAssignment();
		Map<String, Object> filters = new HashMap<>();
		filters.put("value", assignment.getValue());
		int count = assignmentsProvider.count(filters);
		if (count > 0) {
			assignmentMessage = existsMessage;
		} else {
			if (assignmentCreator.save(assignment)) {
				assignmentMessage = successMessage;
			} else {
				assignmentMessage = failMessage;
			}
		}
		return assignmentMessage;
	}

	public void refreshIfSuccess() throws IOException {
		if (assignmentMessage.equals(successMessage)) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		}
	}

	public String getAssignmentMessage() {
		return assignmentMessage;
	}

	public void setAssignmentMessage(String assignmentMessage) {
		this.assignmentMessage = assignmentMessage;
	}
}
