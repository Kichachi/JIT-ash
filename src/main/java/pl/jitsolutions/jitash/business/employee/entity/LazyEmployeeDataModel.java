package pl.jitsolutions.jitash.business.employee.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.util.EmployeeLazySorter;

public class LazyEmployeeDataModel extends LazyDataModel<Employee> {
	@EJB
	private EmployeesProvider employeesProvider;
	private boolean firstTime=true;
	private List<Employee>datasource;

	public LazyEmployeeDataModel(int first,int pageSize) {
		//TODO pobranie z bazy tylko wartosci od first do first+pageSize
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
		System.out.println("jestem w load " + " oto filtry: "  + filters);
		List<Employee> data = new ArrayList<Employee>();
		if(firstTime ) {
			if(filters.isEmpty()) {
				filters.put("active", true);
			} else {
				firstTime = false;
			}
		}
		//filter
		for(Employee employee : datasource) {
			boolean match = true;

			if (filters != null) {
				for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						String fieldValue = String.valueOf(employee.getClass().getField(filterProperty).get(employee));

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
