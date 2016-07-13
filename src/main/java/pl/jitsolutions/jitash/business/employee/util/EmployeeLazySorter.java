package pl.jitsolutions.jitash.business.employee.util;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import pl.jitsolutions.jitash.business.employee.entity.Employee;

public class EmployeeLazySorter implements Comparator<Employee> {
	private String sortField;
	private SortOrder sortOrder;

	public EmployeeLazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}


	public int compare(Employee employee1, Employee employee2) {
		try {
			Object value1 = Employee.class.getField(this.sortField).get(employee1);
			Object value2 = Employee.class.getField(this.sortField).get(employee2);

			int value = ((Comparable)value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		}
		catch(Exception e) {
			throw new RuntimeException();
		}
	}
}
