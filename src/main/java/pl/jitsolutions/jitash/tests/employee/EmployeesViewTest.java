package pl.jitsolutions.jitash.tests.employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.primefaces.model.LazyDataModel;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeeTestDataProvider;
import pl.jitsolutions.jitash.presentation.employee.EmployeesView;

import static org.mockito.Mockito.mock;

public class EmployeesViewTest {

	private EmployeesView view;

	@InjectMocks
	private EmployeeTestDataProvider provider;

	@Before
	public void setUp(){

		this.view = new EmployeesView();
		this.view.setEmployeesModel(mock(LazyDataModel.class));
	}

	@Test
	public void employeesModelShouldNotBeNull(){
		Assert.assertNotNull(view.getEmployeesModel());
	}

	@Test
	public void employeesModelShouldBeLazyDataModelList(){
		Assert.assertTrue(view.getEmployeesModel() instanceof LazyDataModel);
	}

	@Test
	public void employeesModelShouldHaveZeroPageSizeByDefault(){
		Assert.assertEquals(view.getEmployeesModel().getPageSize(), 0);
	}

}