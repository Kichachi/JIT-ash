package pl.jitsolutions.jitash.business.employee.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
	private boolean firstTime=true;
	private List<Employee>datasource;

	@PostConstruct
	public void init() {
		System.out.println("Inicjuje");
		datasource = employeesProvider.getEmployees();
	}
	@Override
	public Employee getRowData(String rowKey) {
		for(Employee employee : datasource) {
			if(employee.getId().equals(rowKey))
				return employee;
		}
		return null;
	}

	@Override
	public Object getRowKey(Employee employee) {
		return employee.getId();
	}

	@Override
	public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		List<Employee> data = new ArrayList<Employee>();
		if(firstTime ) {
			if(filters.isEmpty()) {
				filters.put("active", true);
			} else {
				firstTime = false;
			}
		}
		System.out.println("jestem w load " + " oto filtry: "  + filters);
		//filter
		for(Employee employee : datasource) {
			boolean match = true;

			if (filters != null) {
				for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						Field field = employee.getClass().getDeclaredField(filterProperty);
						field.setAccessible(true);
						String fieldValue = String.valueOf(field.get(employee));

						if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
							match = true;
						}
						else {
							match = false;
							break;
						}
					} catch(Exception e) {
						match = false;
					}
				}
			}

			if(match) {
				data.add(employee);
			}
		}

		//sort
		if(sortField != null) {
			Collections.sort(data, new EmployeeLazySorter(sortField, sortOrder));
		}

		//rowCount
		int dataSize = data.size();
		this.setRowCount(dataSize);

		//paginate
		if(dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			}
			catch(IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		}
		else {
			return data;
		}
	}


}
