package pl.jitsolutions.jitash.business.employee.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQueries({ @NamedQuery(name = Assignment.GET_ASSIGNMENTS, query = "SELECT Assignment FROM Assignment assignment"),
					  @NamedQuery(name = Assignment.GET_ASSIGNMENTID,
								  query = "SELECT Assignment FROM Assignment assignment WHERE assignment"
										  + ".assignment_id = :id"), @NamedQuery(name = Assignment.GET_ASSIGNMENTVALUE,
																				 query = "SELECT Assignment FROM Assignment "
																						 + "assignment WHERE assignment.value = :value") })
@Entity
public class Assignment implements Serializable {

	private static final String PREFIX = "jitash.business.employee.entity";

	public static final String GET_ASSIGNMENTS = PREFIX + "getAssignments";
	public static final String GET_ASSIGNMENTID = PREFIX + "getAssignmentById";
	public static final String GET_ASSIGNMENTVALUE = PREFIX + "getAssignmentByValue";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assignment_id;

	@Column(unique = true)
	@NotNull(message = "Nazwa przydziału nie może być pusta")
	@Size(min = 1, max = 40, message = "Przydzial musi mieć od 1 do 40 znaków")
	private String value;

	public Assignment() {
	}

	public Assignment(String value) {
		this.value = value;
	}

	public Long getAssignment_id() {
		return assignment_id;
	}

	public void setAssignment_id(Long id) {
		this.assignment_id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + (assignment_id != null ? assignment_id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Assignment other = (Assignment) obj;
		if ((this.assignment_id == null) ?
				(other.assignment_id != null) :
				!this.assignment_id.equals(other.assignment_id)) {
			return false;
		}
		return true;
	}

}
