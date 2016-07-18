package pl.jitsolutions.jitash.presentation.employee;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Named
@ViewScoped
public class EmployeeDetailsView implements Serializable {
	private Employee employee;

	@Inject
	private EmployeesProvider employeesProvider;


	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
