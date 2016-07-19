package pl.jitsolutions.jitash.presentation.employee;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeeRemover;
import pl.jitsolutions.jitash.business.employee.entity.Status;

@Named
@ViewScoped
public class RemoveEmployeeView implements Serializable {

	@Inject
	private EmployeeDetailsView employeeDetail;

	@Inject
	private EmployeeRemover remover;

	private String destinationRedirectFile = "../employees.xhtml";

	public void remove() throws IOException{

		if(isSelectedEmployeeActive()){
			remover.remove(employeeDetail.getEmployee().getEmployee_id());
			System.out.println("Remove");
		}

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(destinationRedirectFile);

	}

	public boolean isEmployeeActive(){

		if(isSelectedEmployeeActive()) return true;
		else return false;
	}

	public boolean isSelectedEmployeeActive(){
		return employeeDetail.getEmployee().getActive() == Status.ACTIVE;
	}

}
