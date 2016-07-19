package pl.jitsolutions.jitash.presentation.employee;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Named
@RequestScoped
public class EmployeesView implements Serializable {
//	@Inject
//	private EmployeesProvider employeesProvider;
//	@Inject
//	private AssignmentsProvider assignmentsProvider;
	@Inject
	private LazyDataModel<Employee> employeesModel;

	private Employee selectedEmployee;

	public EmployeesView() {}

	public LazyDataModel<Employee> getEmployeesModel() {
		return employeesModel;
	}

	public void setEmployeesModel(LazyDataModel<Employee> employeesModel) {
		this.employeesModel = employeesModel;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Employee Selected", ((Employee) event.getObject()).getEmployee_id().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void showEmployeeDetails() {	}

}
