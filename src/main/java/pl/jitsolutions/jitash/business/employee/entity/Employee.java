package pl.jitsolutions.jitash.business.employee.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


@Entity
@NamedQueries({ @NamedQuery(name = Employee.GET_EMPLOYEES, query = "SELECT Employee FROM Employee employee"),
					  @NamedQuery(name = Employee.GET_EMPLOYEE,
								  query = "SELECT Employee FROM Employee employee WHERE employee.employee_id = :id") })

public class Employee implements Serializable {

	private static final String PREFIX = "jitash.business.employee.entity";

	public static final String GET_EMPLOYEES = PREFIX + "getEmployees";
	public static final String GET_EMPLOYEE = PREFIX + "getEmployee";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employee_id;

	@Size(min = 2, max = 40, message = "Imię powinno zawierać od 2 do 40 znaków.")
	@NotNull(message = "Imię nie może być puste")
	private String name;

	@Size(min = 2, max = 40, message = "Nazwisko powinno zawierać od 2 do 40 znaków.")
	@NotNull(message = "Nazwisko nie może być puste")
	private String surname;

	@Pattern(regexp="^([0-9]{11})$", message = "PESEL powinien zawierać dokładnie 11 znaków.")
	@NotNull(message = "PESEL nie może być pusty")
	@Column(unique = true)
	private String PESEL;

	@Pattern(regexp="^([0-9]{9})$", message = "Telefon powinien zawierać dokładnie 9 znaków.")
	@NotNull(message = "Telefon nie może być pusty")
	private String telephone;

	@Email
	@Size(min = 5, max = 70, message = "Email powinien zawierać od 5 do 70 znaków.")
	@NotNull(message = "EMAIL nie może być pusty")
	private String email;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="assignment_id")
	private Assignment assignment;

	@Lob
	@Size(max=1024)
	@Column(length=1024)
	private String comment;

	@Enumerated(EnumType.STRING)
	private Status active = Status.ACTIVE;

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long id) {
		this.employee_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPESEL() {
		return PESEL;
	}

	public void setPESEL(String PESEL) {
		this.PESEL = PESEL;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getActive() {
		return active;
	}

	public void setActive(Status active) {
		this.active = active;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Employee() {
	}

	public Employee(String name, String surname, String PESEL, String telephone, String email, Status active) {
		this.name = name;
		this.surname = surname;
		this.PESEL = PESEL;
		this.telephone = telephone;
		this.email = email;
		this.active = active;
	}

	@Override
	public String toString() {
		return name + " " + surname + " with employee_id: " + employee_id;
	}

	public static final class EmployeeBuilder {
		private Long employee_id;
		private String name;
		private String surname;
		private String PESEL;
		private String telephone;
		private String email;
		private Status active = Status.ACTIVE;

		public static EmployeeBuilder anEmployee() {
			return new EmployeeBuilder();
		}

		public EmployeeBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public EmployeeBuilder withSurname(String surname) {
			this.surname = surname;
			return this;
		}

		public EmployeeBuilder withPESEL(String PESEL) {
			this.PESEL = PESEL;
			return this;
		}

		public EmployeeBuilder withTelephone(String telephone) {
			this.telephone = telephone;
			return this;
		}

		public EmployeeBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		public EmployeeBuilder withActive(Status active) {
			this.active = active;
			return this;
		}

		public Employee build() {
			Employee employee = new Employee(name, surname, PESEL, telephone, email, active);
			return employee;
		}
	}
}
