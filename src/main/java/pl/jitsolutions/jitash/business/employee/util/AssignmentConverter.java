package pl.jitsolutions.jitash.business.employee.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import pl.jitsolutions.jitash.business.employee.boundry.AssignmentsProvider;
import pl.jitsolutions.jitash.business.employee.entity.Assignment;

@Named
@FacesConverter("assignmentConverter")
public class AssignmentConverter implements Converter {
	@Inject
	private AssignmentsProvider assignmentsProvider;

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {
		Assignment assignment= assignmentsProvider.getAssignmentById(Long.parseLong(value));
		return assignment;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		Assignment assignment = (Assignment) o;
		return assignment.getAssignment_id() != null ? String.valueOf(assignment.getAssignment_id()) : null;
	}

}
