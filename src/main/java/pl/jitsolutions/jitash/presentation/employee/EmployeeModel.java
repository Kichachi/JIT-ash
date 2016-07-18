package pl.jitsolutions.jitash.presentation.employee;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Named
@ViewScoped
public class EmployeeModel implements Serializable{

	private Employee employee = new Employee();

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
