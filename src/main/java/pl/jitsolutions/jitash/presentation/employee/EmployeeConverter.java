package pl.jitsolutions.jitash.presentation.employee;

import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Named
@RequestScoped
public class EmployeeConverter implements Converter {

	@Inject
	private EmployeesProvider employeesProvider;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		try {
			Long id = Long.valueOf(value);
			return employeesProvider.getEmployee(id);
		} catch (NumberFormatException e) {
			throw new ConverterException("To nie jest prawidłowe ID pracownika: " + value, e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof Employee) {
			Long id = ((Employee) value).getId();
			return (id != null) ? String.valueOf(id) : null;
		} else {
			throw new ConverterException("To nie jest prawidlowe ID pracownika " + value);
		}
	}

}
