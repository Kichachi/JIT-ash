package pl.jitsolutions.jitash.business.employee.entity;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import pl.jitsolutions.jitash.business.employee.boundry.AssignmentsProvider;
import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;

@Stateless
public class LazyEmployeeDataModel extends LazyDataModel<Employee> {
	@Inject
	private EmployeesProvider employeesProvider;
	@Inject
	private AssignmentsProvider assignmentsProvider;

	private boolean allFlag = false;

	private List<Employee> datasource;

	private List<Assignment> assignments;

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	@Override
	public Employee getRowData(String rowKey) {
		Long rowKeyLong = Long.parseLong(rowKey);
		for (Employee employee : datasource) {
			if (employee.getEmployee_id().equals(rowKeyLong))
				return employee;
		}
		return null;
	}

	@Override
	public Object getRowKey(Employee employee) {
		return employee.getEmployee_id();
	}

	@Override
	public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		assignments = assignmentsProvider.getAssignments();
		List<Employee> data;
		if (filters.isEmpty()) {
			filters.put("active", Status.ACTIVE);
		}
		if (filters.containsKey("active")) {
			if (filters.get("active").equals(Status.ALL)) {
				allFlag = true;
				filters.remove("active");
			}
		}
		setRowCount(employeesProvider.count(filters));
		datasource = employeesProvider.getResultList(first, pageSize, sortField, sortOrder, filters);
		System.out.println("Mam " + datasource.size() + "rekordow w liscie");
		if (allFlag) {
			allFlag = false;
			filters.put("active", Status.ALL);
		}
		return datasource;
	}

	public void setDatasource(List<Employee> datasource) {
		this.datasource = datasource;
	}

}
