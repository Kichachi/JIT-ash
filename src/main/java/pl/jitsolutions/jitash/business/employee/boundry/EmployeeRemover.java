package pl.jitsolutions.jitash.business.employee.boundry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import pl.jitsolutions.jitash.business.employee.entity.Status;

@Stateless
public class EmployeeRemover {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void remove(Long id) {
		Query query = entityManager.createQuery("UPDATE Employee e SET e.active = :active WHERE e.employee_id = :i ");
		query.setParameter("active", Status.INACTIVE);
		query.setParameter("i", id);
		query.executeUpdate();
	}

}
