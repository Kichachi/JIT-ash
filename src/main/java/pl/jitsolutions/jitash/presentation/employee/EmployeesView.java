package pl.jitsolutions.jitash.presentation.employee;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Named
@ViewScoped
public class EmployeesView implements Serializable {

	@Inject
	private EmployeesProvider employeesProvider;
	@Inject
	private LazyDataModel<Employee> employeesModel;

	public EmployeesView() { System.out.println("KONSTRUKTOR");	}

	public LazyDataModel<Employee> getEmployeesModel() {
		return employeesModel;
	}

}
