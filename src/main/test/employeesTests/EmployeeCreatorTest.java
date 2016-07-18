package employeesTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.jitsolutions.jitash.business.employee.boundry.EmployeeCreator;
import pl.jitsolutions.jitash.business.employee.entity.Employee;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EmployeeCreatorTest {

	private EmployeeCreator creator;

	@PersistenceContext
	private EntityManager entityManager;

	private Employee employee, employee1;

	@Before
	public void setUp(){

		this.creator = new EmployeeCreator();
		this.creator.setEntityManager(mock(EntityManager.class));

		employee = new Employee();

		employee.setEmail("mail");
	}

	@Test
	public void creatorOutputMessageShouldBeString(){
		Assert.assertTrue(creator.save(employee) instanceof String);
	}

	@Test
	public void outputMessageAfterAddingEmployeeShouldBePositive(){

		String expected = "Dodano pracownika";

		assertEquals(expected, creator.save(employee));
	}

}