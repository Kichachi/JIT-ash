package pl.jitsolutions.jitash.business.employee.util;

import java.lang.reflect.Field;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

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
			Field field = Employee.class.getDeclaredField(sortField);
			field.setAccessible(true);
			Object value1 = field.get(employee1);
			Object value2 = field.get(employee2);
			int value;
			if(value1.getClass().isAssignableFrom(String.class)){
				Collator col = Collator.getInstance(new Locale("pl","PL"));
				value = col.compare(value1,value2);
			} else {
				value = ((Comparable)value1).compareTo(value2);
			}

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		}
		catch(Exception e) {
			throw new RuntimeException();
		}
	}
}
