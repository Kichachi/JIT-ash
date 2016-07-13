package pl.jitsolutions.jitash.business.employee.boundry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Stateless
public class EmployeeCreator {
	@PersistenceContext
	private EntityManager entityManager;

	public String save(Employee employee) {
		try{
			entityManager.persist(employee);
			entityManager.flush();
			return "Dodano pracownika";
		} catch (PersistenceException e){
			e.printStackTrace();
			return "Nie udalo sie dodac pracownika";
		}
	}
}
