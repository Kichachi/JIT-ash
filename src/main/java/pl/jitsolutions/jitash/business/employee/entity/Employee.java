package pl.jitsolutions.jitash.business.employee.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name= Employee.GET_EMPLOYEES, query = "SELECT Employee FROM Employee employee") })
public class Employee implements Serializable {

	private static final String PREFIX = "jitash.business.employee.entity";

	public static final String GET_EMPLOYEES = "getEmployees";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//@Size(min=2,max=20)
	//@NotNull
	private String name;
	//@Size(min=2,max=20)
//	@NotNull
	private String surname;
	//@Size(min=11,max=11)
//	@NotNull
	private String PESEL;
//	@Size(min=9,max=9)
//	@NotNull
	private String telephone;
	//@NotNull
	private String email;
	private boolean active = true;


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

	public boolean isActive() { return active;	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Employee() {}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
		final Employee other = (Employee) obj;
		if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public static final class EmployeeBuilder {
		private String name;
		private String surname;
		private String PESEL;
		private String telephone;
		private String email;
		private boolean active;

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

		public EmployeeBuilder withActive(boolean active) {
			this.active = active;
			return this;
		}

		public Employee build() {
			Employee employee = new Employee();
			employee.setName(name);
			employee.setSurname(surname);
			employee.setPESEL(PESEL);
			employee.setTelephone(telephone);
			employee.setEmail(email);
			employee.setActive(active);
			return employee;
		}
	}
}
