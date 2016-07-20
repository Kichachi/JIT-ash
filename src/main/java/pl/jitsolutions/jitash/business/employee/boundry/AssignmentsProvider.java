package pl.jitsolutions.jitash.business.employee.boundry;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pl.jitsolutions.jitash.business.employee.entity.Assignment;

@Stateless
public class AssignmentsProvider {
	@PersistenceContext
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Assignment> getAssignments() {
		TypedQuery<Assignment> query = entityManager.createNamedQuery(Assignment.GET_ASSIGNMENTS, Assignment.class);
		return query.getResultList();
	}

	public Assignment getAssignmentById(Long id) {
		TypedQuery<Assignment> query = entityManager.createNamedQuery(Assignment.GET_ASSIGNMENTID, Assignment.class);
		query.setParameter("id", id);
		return query.getResultList().get(0);
	}

	public Assignment getAssignmentByValue(String value) {
		TypedQuery<Assignment> query = entityManager.createNamedQuery(Assignment.GET_ASSIGNMENTVALUE, Assignment.class);
		query.setParameter("value", value);
		return query.getResultList().get(0);
	}

	private Predicate getFilterCondition(CriteriaBuilder criteriaBuilder, Root<Assignment> myObj,
			Map<String, Object> filters) {
		Predicate filterCondition = criteriaBuilder.conjunction();
		String wildCard = "%";
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			if (!filter.getValue().equals("")) {
				String value = wildCard + filter.getValue() + wildCard;
				Path<String> path = myObj.get(filter.getKey());
				filterCondition = criteriaBuilder.and(filterCondition, criteriaBuilder.like(path, value));
			}
		}
		return filterCondition;
	}

	public int count(Map<String, Object> filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Assignment> myObj = criteriaQuery.from(Assignment.class);
		criteriaQuery.where(getFilterCondition(criteriaBuilder, myObj, filters));
		criteriaQuery.select(criteriaBuilder.count(myObj));
		return entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
	}

}
