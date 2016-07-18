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

import org.primefaces.context.RequestContext;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeeCreator;
import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;

@Named
@ViewScoped
public class AddEmployeeView implements Serializable {

	@Inject
	private EmployeeCreator employeeCreator;

	@Inject
	private EmployeesProvider employeesProvider;

	@Inject
	private EmployeeModel employeeModel;

	private String employeeMessage = "";

	private String successMessage = "Pracownik został dodany do bazy";
			private String failMessage = "Pracownik o wprowadzonych danych istnieje już w bazie.";

	public String save() {
		Map<String,Object> filters = new HashMap<>();
		filters.put("PESEL",employeeModel.getEmployee().getPESEL());
		int count = employeesProvider.count(filters);
		if(count>0) {
			employeeMessage = failMessage;
		} else {
			employeeMessage = successMessage;
			employeeCreator.save(employeeModel.getEmployee());
		}
		RequestContext.getCurrentInstance().update("addEmployeeViewMessage");
		return employeeMessage;
	}

	public void refreshIfSuccess() throws IOException {
		if(employeeMessage.equals(successMessage)){
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		}
	}

	public String getEmployeeMessage() {
		return employeeMessage;
	}
}
