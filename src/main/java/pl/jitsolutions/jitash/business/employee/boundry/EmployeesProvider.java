package pl.jitsolutions.jitash.business.employee.boundry;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Stateless
public class EmployeesProvider {
	@PersistenceContext
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Employee>getEmployees() {
		System.out.println("getEmployees");
		TypedQuery<Employee> query = entityManager.createNamedQuery(Employee.GET_EMPLOYEES, Employee.class);
		return query.getResultList();
	}
}
