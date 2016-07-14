package pl.jitsolutions.jitash.tests.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.model.SortOrder;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeeTestDataProvider;
import pl.jitsolutions.jitash.business.employee.entity.Employee;
import pl.jitsolutions.jitash.business.employee.entity.LazyEmployeeDataModel;
import pl.jitsolutions.jitash.business.employee.entity.Status;

/**
 * Created by JIT on 14.07.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LazyEmployeeDataModelTest {

	@InjectMocks
	private EmployeeTestDataProvider mockEmployeeTestDataProvider;
	@InjectMocks
	private LazyEmployeeDataModel mockLazyEmployeeDataModel;

	@Test
	public void loadTest() throws Exception {
		//given
		List<Employee> employees = mockEmployeeTestDataProvider.createEmployees(200);
		employees.get(0).setName("Piotr");
		mockLazyEmployeeDataModel.setDatasource(employees);
		Map<String, Object> filters = new HashMap<>();
		filters.put("name", "pio");
		Map<String, Object> filters2 = new HashMap<>();
		filters2.put("active", Status.ALL.getValue());
		//when
		List<Employee> employeesAfterLoadNormalCase = mockLazyEmployeeDataModel
				.load(0, 20, null, SortOrder.ASCENDING, new HashMap<>());
		List<Employee> employeesAfterLoadWhenEndOfList = mockLazyEmployeeDataModel
				.load(180, 30, "name", SortOrder.ASCENDING, filters2);
		List<Employee> employeesAfterLoadWithNameFilter = mockLazyEmployeeDataModel
				.load(0, 10, "surname", SortOrder.ASCENDING, filters);

		//then
		Assert.assertEquals("Size of the list should be only 20", 20, employeesAfterLoadNormalCase.size());
		Assert.assertEquals("Size of the list when the end of page is coming should be only 20", 20,
				employeesAfterLoadWhenEndOfList.size());
		for (Employee employee : employeesAfterLoadWithNameFilter) {
			Assert.assertTrue("Name should start with PIO", employee.getName().toUpperCase().startsWith("PIO"));
		}
	}

	@Test
	public void getRowDataTest() {
		//given
		List<Employee> employees = mockEmployeeTestDataProvider.createEmployees(10);
		mockLazyEmployeeDataModel.setDatasource(employees);
		for(int i = 0 ; i <employees.size() ; i++){
			employees.get(i).setId(Long.valueOf(i+1));
		}
		Long idExpected = employees.get(4).getId();
		//when
		Long idActual = mockLazyEmployeeDataModel.getRowData("5").getId();
		//then
		Assert.assertEquals("IDs are not identical",idExpected,idActual);
	}


}