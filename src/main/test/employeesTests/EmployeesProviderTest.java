package employeesTests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.SortOrder;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeesProvider;
import pl.jitsolutions.jitash.business.employee.entity.Employee;

@RunWith(Arquillian.class)
public class EmployeesProviderTest {
//	@Deployment
//	public static Archive<?> createDeployment() {
//		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(EmployeesProvider.class.getPackage())
//				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
//				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//	}

	@PersistenceContext
	EntityManager entityManager;

	@Inject
	EmployeesProvider employeesProvider;

	@Ignore
	@Test
	@UsingDataSet("datasets/employeesdataset.xml")
	@ShouldMatchDataSet("datasets/employees4andrzej.xml")
	public void andrzejProviderTest() {
		//given
		Map<String, Object> filters = new HashMap<>();
		filters.put("name", "andrzej");
		//when
		int countEmployeesAndrzej = employeesProvider.count(filters);
		//then
		Assert.assertEquals("There should be 10 Andrzej in database", 10, countEmployeesAndrzej);
	}

	@Ignore
	@Test
	@UsingDataSet("datasets/employeesdataset.xml")
	public void garbarczykSurnameTest() {
		//given
		Map<String, Object> filters = new HashMap<>();
		filters.put("surname", "garbarczyk");
		//when
		List<Employee> employeesAndrzej = employeesProvider.getResultList(0, 10, null, SortOrder.ASCENDING, filters);
		//then
		for (Employee employee : employeesAndrzej) {
			Assert.assertTrue("the only surnames should be Garbarczyk",
					"garbarczyk".equals(employee.getName().toLowerCase()));
		}
	}

}