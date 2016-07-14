package pl.jitsolutions.jitash.business.employee.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.util.EmployeeLazySorter;

@Stateless
public class LazyEmployeeDataModel extends LazyDataModel<Employee> {
	@Inject
	private EmployeesProvider employeesProvider;
	private boolean allFlag = false;

	private List<Employee> datasource;

	@PostConstruct
	public void init() {
		datasource = employeesProvider.getEmployees();
	}

	@Override
	public Employee getRowData(String rowKey) {
		Long rowKeyLong = Long.parseLong(rowKey);
		for (Employee employee : datasource) {
			if (employee.getId().equals(rowKeyLong))
				return employee;
		}
		return null;
	}

	@Override
	public Object getRowKey(Employee employee) {
		return employee.getId();
	}

	@Override
	public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		System.out.println(first + " " + pageSize + " " + sortField + " " + sortOrder + " " + filters);
		List<Employee> data = new ArrayList<>();
		System.out.println("jestem w load" + " oto filtry: " + filters + " flaga " + allFlag);
		if (filters.isEmpty()) {
			filters.put("active", Status.ACTIVE);
		}
		if (filters.containsKey("active")) {
			if (filters.get("active").equals(Status.ALL.getValue())) {
				allFlag = true;
				filters.remove("active");
			}
		}
		System.out.println("jestem w load 2" + " oto filtry: " + filters );
		//filter
		for (Employee employee : datasource) {
			boolean match = true;

			if (filters != null) {
				for (String filterProperty : filters.keySet()) {
					try {
						Object filterValue = filters.get(filterProperty);
						Field field = employee.getClass().getDeclaredField(filterProperty);
						field.setAccessible(true);
						String fieldValue = String.valueOf(field.get(employee)).toUpperCase(new Locale("pl", "PL"));

						if (filterValue == null || fieldValue
								.startsWith(filterValue.toString().toUpperCase(new Locale("pl", "PL")))) {
							match = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
					}
				}
			}

			if (match) {
				data.add(employee);
			}
		}
		//sort
		if (sortField != null) {
			Collections.sort(data, new EmployeeLazySorter(sortField, sortOrder));
		}

		//rowCount
		int dataSize = data.size();
		setRowCount(dataSize);


		if(allFlag) {
			allFlag=false;
			filters.put("active",Status.ALL.getValue());
		}

		System.out.println(data.size());
		//paginate
		if (dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}

	public void setDatasource(List<Employee> datasource) {
		this.datasource = datasource;
	}

}
