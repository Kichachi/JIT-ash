package pl.jitsolutions.jitash.presentation.employee;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.entity.Employee;
import pl.jitsolutions.jitash.business.employee.entity.LazyEmployeeDataModel;

@Named
@RequestScoped
public class EmployeesView implements Serializable{
	@EJB
	private EmployeesProvider employeesProvider;

	public LazyDataModel<Employee> getEmployeesModel() {
		return employeesModel;
	}

	private LazyDataModel<Employee> employeesModel;

	@PostConstruct
	public void init() {
		//TODO zmiana na przyjmowanie wartosci z ogólnego Beana zamiast na sztywno
 		employeesModel = new LazyEmployeeDataModel(0,10);
	}

}
