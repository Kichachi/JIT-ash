package pl.jitsolutions.jitash.business.employee.boundry;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.jitsolutions.jitash.business.employee.entity.Assignment;

@Stateless
public class AssignmentsProvider {
	@PersistenceContext
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Assignment>getAssignments() {
		TypedQuery<Assignment> query = entityManager.createNamedQuery(Assignment.GET_ASSIGNMENTS, Assignment.class);
		return query.getResultList();
	}


	public Assignment getAssignmentById(Long id) {
		TypedQuery<Assignment> query = entityManager.createNamedQuery(Assignment.GET_ASSIGNMENTID, Assignment.class);
		query.setParameter("id",id);
		return query.getResultList().get(0);
	}
	public Assignment getAssignmentByValue(String value) {
		TypedQuery<Assignment> query = entityManager.createNamedQuery(Assignment.GET_ASSIGNMENTVALUE, Assignment.class);
		query.setParameter("value",value);
		return query.getResultList().get(0);
	}

}
