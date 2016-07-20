package pl.jitsolutions.jitash.business.employee.boundry;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import pl.jitsolutions.jitash.business.employee.entity.Assignment;
import pl.jitsolutions.jitash.business.employee.entity.Employee;
import pl.jitsolutions.jitash.business.employee.entity.Status;

@Stateless
public class EmployeesProvider {
	@PersistenceContext
	private EntityManager entityManager;
	@Inject
	private AssignmentsProvider assignmentsProvider;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Employee> getEmployees() {
		System.out.println("getEmployees");
		TypedQuery<Employee> query = entityManager.createNamedQuery(Employee.GET_EMPLOYEES, Employee.class);
		return query.getResultList();
	}

	private Predicate getFilterCondition(CriteriaBuilder criteriaBuilder, Root<Employee> myObj,
			Map<String, Object> filters) {
		Predicate filterCondition = criteriaBuilder.conjunction();
		String wildCard = "%";
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			if (!filter.getValue().equals("")) {
				switch (filter.getKey()) {
				case "assignment":
					Path<Assignment> assignmentPath = myObj.get(filter.getKey());
					Assignment assignment = assignmentsProvider.getAssignmentByValue(filter.getValue().toString());
					filterCondition = criteriaBuilder
							.and(filterCondition, criteriaBuilder.equal(assignmentPath, assignment));
					break;
				case "employee_id":
					Path<Long> pathEmployee = myObj.get(filter.getKey());
					filterCondition = criteriaBuilder.and(filterCondition,
							criteriaBuilder.equal(pathEmployee, Long.valueOf(filter.getValue().toString())));
					break;
				case "active":
					Path<Status> pathActive = myObj.get(filter.getKey());
					filterCondition = criteriaBuilder
							.and(filterCondition, criteriaBuilder.equal(pathActive, filter.getValue()));
					break;
				default:
					String value = wildCard + filter.getValue() + wildCard;
					Path<String> path = myObj.get(filter.getKey());
					filterCondition = criteriaBuilder.and(filterCondition, criteriaBuilder.like(path, value));
					break;
				}
			}
		}
		return filterCondition;
	}

	public int count(Map<String, Object> filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Employee> myObj = criteriaQuery.from(Employee.class);
		criteriaQuery.where(getFilterCondition(criteriaBuilder, myObj, filters));
		criteriaQuery.select(criteriaBuilder.count(myObj));
		return entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
	}

	public Employee getEmployee(Long id) {
		TypedQuery<Employee> query = entityManager.createNamedQuery(Employee.GET_EMPLOYEE, Employee.class);
		query.setParameter("id", id);
		return query.getResultList().get(0);
	}

	public List<Employee> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		if (sortField == null) {
			sortField = "employee_id";
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
		Root<Employee> myObj = criteriaQuery.from(Employee.class);
		criteriaQuery.where(getFilterCondition(criteriaBuilder, myObj, filters));
		if (sortOrder == SortOrder.ASCENDING) {
			criteriaQuery.orderBy(criteriaBuilder.asc(myObj.get(sortField)));
		} else if (sortOrder == SortOrder.DESCENDING) {
			criteriaQuery.orderBy(criteriaBuilder.desc(myObj.get(sortField)));
		}
		return entityManager.createQuery(criteriaQuery).setFirstResult(first).setMaxResults(pageSize).getResultList();
	}

}
