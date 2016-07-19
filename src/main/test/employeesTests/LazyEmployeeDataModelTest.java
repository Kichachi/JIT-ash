package employeesTests;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeeCreator;
import pl.jitsolutions.jitash.business.employee.boundry.EmployeeTestDataProvider;
import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.entity.Employee;
import pl.jitsolutions.jitash.business.employee.entity.LazyEmployeeDataModel;

/**
 * Created by JIT on 14.07.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LazyEmployeeDataModelTest {

	@InjectMocks
	private EmployeeTestDataProvider mockEmployeeTestDataProvider;
	@InjectMocks
	private LazyEmployeeDataModel mockLazyEmployeeDataModel;
	@InjectMocks
	private EmployeeCreator employeeCreator;
	@Mock
	private EmployeesProvider employeesProvider;
	@Mock
	private EntityManager entityManager;


	@Before
	public void init() {
		List<Employee> employees = mockEmployeeTestDataProvider.createEmployees(200);
		employees.get(0).setName("Piotr");
		for(Employee employee : employees){
			employeeCreator.save(employee);
		}
	}
//	@Test
//	public void givenEmployeesWhenGettingListOf20ThenEquals20() throws Exception {
//		//given
//
//		//when
//		List<Employee> employeesAfterLoadNormalCase = mockLazyEmployeeDataModel
//				.load(0, 20, null, SortOrder.ASCENDING, new HashMap<>());
//		//then
//		Assert.assertEquals("Size of the list should be only 20", 20, employeesAfterLoadNormalCase.size());
//	}
//
//	@Test
//	public void givenEmployeesAndStatusFilterWhenGettingListOf20AtEndOfListThenEquals20() throws Exception {
//		//given
//		Map<String, Object> filters = new HashMap<>();
//		filters.put("active", Status.ALL);
//		//when
//		List<Employee> employeesAfterLoadWhenEndOfList = mockLazyEmployeeDataModel
//				.load(180, 30, "name", SortOrder.ASCENDING, filters);
//		//then
//		Assert.assertEquals("Size of the list when the end of page is coming should be only 20", 20,
//				employeesAfterLoadWhenEndOfList.size());
//	}
//
//	@Test
//	public void givenEmployeesAndNameFilterWhenGettingListOf10ThenEveryonesLikePio() throws Exception {
//		//given
//		Map<String, Object> filters = new HashMap<>();
//		filters.put("name", "pio");
//		//when
//		List<Employee> employeesAfterLoadWithNameFilter = mockLazyEmployeeDataModel
//				.load(0, 10, "surname", SortOrder.ASCENDING, filters);
//		//then
//		Assert.assertFalse("List shouldnt be empty",employeesAfterLoadWithNameFilter.isEmpty());
//		for (Employee employee : employeesAfterLoadWithNameFilter) {
//			Assert.assertTrue("Name should start with PIO", employee.getName().toLowerCase().startsWith("pio"));
//		}
//	}
//
	@Test
	public void givenEmployeesExpectedIdWhenRowData5ThenIDsAreEqual() {
		//given
		List<Employee> employees = mockEmployeeTestDataProvider.createEmployees(10);
		mockLazyEmployeeDataModel.setDatasource(employees);
		for(int i = 0 ; i <employees.size() ; i++) {
			employees.get(i).setEmployee_id(Long.valueOf(i+1));
		}
		Long idExpected = employees.get(4).getEmployee_id();
		//when
		Long idActual = mockLazyEmployeeDataModel.getRowData("5").getEmployee_id();
		//then
		Assert.assertEquals("IDs are not identical",idExpected,idActual);
	}


}