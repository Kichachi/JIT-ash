package pl.jitsolutions.jitash.tests.employee;

import org.junit.Assert;
import org.primefaces.model.SortOrder;

import pl.jitsolutions.jitash.business.employee.entity.Employee;
import pl.jitsolutions.jitash.business.employee.entity.Status;
import pl.jitsolutions.jitash.business.employee.util.EmployeeLazySorter;

/**
 * Created by JIT on 14.07.2016.
 */
public class EmployeeLazySorterTest {
	@org.junit.Test
	public void compareShouldBeRightWithLocale(){
		// given
		Employee employee1 = new Employee.EmployeeBuilder().withName("Andrzej").withSurname("Bernardynowski")
				.withEmail("andrzejbernardynowski@email.com").withPESEL("90909090901").withTelephone("890890890")
				.build();
		Employee employee2 = new Employee.EmployeeBuilder().withName("Łukasz").withSurname("Bernardynowski")
				.withEmail("lukaszsliwka@email.com").withPESEL("12345678910").withTelephone("123456789").withActive
						(Status.INACTIVE).build();
		EmployeeLazySorter employeeLazySorterName = new EmployeeLazySorter("name",SortOrder.ASCENDING);
		EmployeeLazySorter employeeLazySorterSurname = new EmployeeLazySorter("surname",SortOrder.ASCENDING);
		EmployeeLazySorter employeeLazySorterEmail = new EmployeeLazySorter("email",SortOrder.ASCENDING);
		EmployeeLazySorter employeeLazySorterPESEL = new EmployeeLazySorter("PESEL",SortOrder.ASCENDING);
		EmployeeLazySorter employeeLazySorterTelephone = new EmployeeLazySorter("telephone",SortOrder.ASCENDING);
		//when
		int valueName = employeeLazySorterName.compare(employee1,employee2);
		int valueSurname = employeeLazySorterSurname.compare(employee1,employee2);
		int valueEmail = employeeLazySorterEmail.compare(employee1,employee2);
		int valuePESEL = employeeLazySorterPESEL.compare(employee1,employee2);
		int valueTelephone = employeeLazySorterTelephone.compare(employee1,employee2);
		//then
		Assert.assertTrue("First name is before the second",valueName<0);
		Assert.assertTrue("Both surnames are equal",valueSurname==0);
		Assert.assertTrue("First email is before the second",valueEmail<0);
		Assert.assertTrue("First PESEL is after the second",valuePESEL>0);
		Assert.assertTrue("First telephone is after the second",valueTelephone>0);
	}

}