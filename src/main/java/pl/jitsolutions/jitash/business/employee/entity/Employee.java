package pl.jitsolutions.jitash.business.employee.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@NamedQueries({ @NamedQuery(name= Employee.GET_EMPLOYEES, query = "SELECT Employee FROM Employee employee") })
public class Employee implements Serializable {

	private static final String PREFIX = "jitash.business.employee.entity";

	public static final String GET_EMPLOYEES = PREFIX + "getEmployees";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min=2, max=40, message="Imię powinno zawierać od 2 do 40 znaków.")
	@NotNull(message="Imię nie może być puste")
	private String name;
	@Size(min=2,max=40, message="Nazwisko powinno zawierać od 2 do 40 znaków.")
	@NotNull(message="Nazwisko nie może być puste")
	private String surname;
	@Size(min=11,max=11, message="PESEL powinien zawierać dokładnie 11 znaków.")
	@NotNull(message="PESEL nie może być pusty")
	@Column(unique=true)
	private String PESEL;
	@Size(min=9,max=9, message="Telefon powinien zawierać dokładnie 9 znaków.")
	@NotNull(message = "Telefon nie może być pusty")
	private String telephone;
	@Email
	@Size(min=5,max=70, message="Email powinien zawierać od 5 do 70.")
	@NotNull
	private String email;
	@Enumerated(EnumType.STRING)
	private Status active = Status.ACTIVE;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Status getActive() { return active;	}

	public void setActive(Status active) {
		this.active = active;
	}

	public Employee() {}

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
		return name+" "+surname+" with id: "+id;
	}



	public static final class EmployeeBuilder {
		private Long id;
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
			this.active=active;
			return this;
		}

		public Employee build() {
			Employee employee = new Employee(name,surname,PESEL,telephone,email,active);
			return employee;
		}
	}
}
