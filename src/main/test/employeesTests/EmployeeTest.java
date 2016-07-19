package employeesTests;

import org.junit.Before;
import org.junit.Test;

import pl.jitsolutions.jitash.business.employee.entity.Employee;
import pl.jitsolutions.jitash.business.employee.entity.Status;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EmployeeTest {

	private Employee employee;
	private Employee.EmployeeBuilder builder;

	@Before
	public void setUp(){
		builder = new Employee.EmployeeBuilder();

		employee = new Employee();
		employee.setEmployee_id(10L);
		employee.setName("Jan");
		employee.setSurname("Kowalski");
		employee.setPESEL("95091192384");
		employee.setTelephone("123123123");
		employee.setEmail("email@domain");
	}

	@Test
	public void idShouldBeNotNull(){
		assertNotNull(employee.getEmployee_id());
	}

	@Test
	public void nameShouldBeString(){
		assertTrue(employee.getName() instanceof String);
	}

	@Test
	public void nameShouldNotBeNull(){
		assertNotNull(employee.getName());
	}

	@Test
	public void nameShouldBeBetween2And20(){

		boolean isNameBetween2And20 = false;

		if(employee.getName().length() >= 2 && employee.getName().length() <= 20){
			isNameBetween2And20 = true;
		}

		assertTrue(isNameBetween2And20);
	}

	@Test
	public void surnameShouldBeNotNull(){
		assertNotNull(employee.getSurname());
	}

	@Test
	public void peselShouldHaveLengthof11(){

		boolean isPeselLength11 = false;

		if(employee.getPESEL().length() == 11){
			isPeselLength11 = true;
		}

		assertTrue(isPeselLength11);
	}

	@Test
	public void phoneNumberShouldHaveLengthOf9(){

		boolean isPhoneNumberLength11 = false;

		if(employee.getTelephone().length() == 9){
			isPhoneNumberLength11 = true;
		}

		assertTrue(isPhoneNumberLength11);
	}

	@Test
	public void phoneNumberShouldNotBeNull(){
		assertNotNull(employee.getTelephone());
	}

	@Test
	public void emailShouldNotBeNull(){
		assertNotNull(employee.getEmail());
	}

	@Test
	public void activityShouldBeTypeOfStatus(){
		assertTrue(employee.getActive() instanceof Status);
	}

	@Test
	public void employeeBuilderMethodsShouldReturnBuilderReferrece(){

		assertTrue(builder.withActive(employee.getActive()) instanceof Employee.EmployeeBuilder &&
				builder.withEmail(employee.getEmail()) instanceof  Employee.EmployeeBuilder &&
				builder.withName(employee.getName()) instanceof Employee.EmployeeBuilder &&
				builder.withSurname(employee.getSurname()) instanceof Employee.EmployeeBuilder &&
				builder.withPESEL(employee.getPESEL()) instanceof  Employee.EmployeeBuilder &&
				builder.withTelephone(employee.getTelephone()) instanceof Employee.EmployeeBuilder);
	}

	@Test
	public void builderShouldGiveCompleteEmployeeInstance(){
		assertTrue(builder.build() instanceof Employee);
	}

}