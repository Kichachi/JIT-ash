package pl.jitsolutions.jitash.business.employee.boundry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import pl.jitsolutions.jitash.business.employee.entity.Assignment;

@Stateless
public class AssignmentCreator {
	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Transactional
	public String save(Assignment assignment) {
		try{
			entityManager.persist(assignment);
			entityManager.flush();
			return "Dodano przydzial";
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "Nie udalo sie dodac przydzialu";
		}
	}
}
