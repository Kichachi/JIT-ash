package pl.jitsolutions.jitash.presentation.employee;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pl.jitsolutions.jitash.business.employee.boundry.AssignmentsProvider;
import pl.jitsolutions.jitash.business.employee.boundry.EmployeeCreator;
import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.entity.Assignment;
import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Named
@ViewScoped
public class AddEmployeeView implements Serializable {

	@Inject
	private EmployeeCreator employeeCreator;

	@Inject
	private EmployeesProvider employeesProvider;

	@Inject
	private AssignmentsProvider assignmentsProvider;

	@Inject
	private EmployeeModel employeeModel;

	private String employeeMessage = "";

	private final String successMessage = "Pracownik został dodany do bazy";
	private final String existsMessage = "Pracownik o wprowadzonych danych istnieje już w bazie.";
	private final String failMessage = "Dodanie pracownika się nie powiodło.";
	private List<Assignment> assignments;

	@PostConstruct
	public void init() {
		assignments = assignmentsProvider.getAssignments();
	}

	public String save() {
		Map<String, Object> filters = new HashMap<>();
		Employee employee = employeeModel.getEmployee();
		filters.put("PESEL", employee.getPESEL());
		int count = employeesProvider.count(filters);
		if (count > 0) {
			employeeMessage = existsMessage;
		} else {
			employee.setAssignment(assignmentsProvider.getAssignmentById(employee.getAssignment().getAssignment_id()));
			if (employeeCreator.save(employee)) {
				employeeMessage = successMessage;
			} else {
				employeeMessage = failMessage;
			}
		}
		return employeeMessage;
	}

	public void refreshIfSuccess() throws IOException {
		if (employeeMessage.equals(successMessage)) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		}
	}

	public String getEmployeeMessage() {
		return employeeMessage;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
}
