package pl.jitsolutions.jitash.business.employee.boundry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import pl.jitsolutions.jitash.business.employee.entity.Assignment;
import pl.jitsolutions.jitash.business.employee.entity.Employee;
import pl.jitsolutions.jitash.business.employee.entity.Status;

@Singleton
@Startup
public class EmployeeTestDataProvider {
	private final static String[] names;
	private final static String[] surnames;
	private final static String[] emails;
	private final static String[] assignments;
	@Inject
	private EmployeeCreator employeeCreator;
	@Inject
	private AssignmentCreator assignmentCreator;
	@Inject
	private AssignmentsProvider assignmentsProvider;

	static {
		names = new String[] { "Michal", "Mikolaj", "Mateusz", "Piotr", "Lukasz", "Krzysztof", "Marta", "Agata",
				"Monika", "Malgorzata", "Pawel" };
		surnames = new String[] { "Garbarczyk", "Stobinski", "Modrzejewski", "Raszkowski", "Ziolkowski", "Laptop",
				"Cymerys", "Wicikowska", "Pelzner", "Milewicz", "Lys" };
		emails = new String[] { "@wp.pl", "@gmail.com", "@jitsolutions.pl", "@najlepszyserwisswiata.world" };
		assignments = new String[] { "JIT Solutions", "LPP", "Nordea" };
	}

	public List<Employee> createEmployees(int size) {
		List<Employee> employeeList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			employeeList.add(generateEmployee());
		}
		return employeeList;
	}

	public Employee generateEmployee() {
		String name = getRandomName();
		String surname = getRandomSurname();
		return new Employee.EmployeeBuilder().withEmail(getRandomEmail(name, surname)).withName(name)
				.withSurname(surname).withPESEL(getRandomPESEL()).withTelephone(getRandomTelephone())
				.withActive(getRandomActive()).build();
	}

	@PostConstruct
	private void init() {
		for (String assignment : assignments){
			assignmentCreator.save(new Assignment(assignment));
		}
		for (int i = 0; i < 200; i++) {
			Employee employee = generateEmployee();
			Assignment assignment = assignmentsProvider.getAssignmentById((long)Math.floor(Math.random()*3)+1);
			employee.setAssignment(assignment);
			employeeCreator.save(employee);
		}
	}

	private String getRandomName() {
		return names[(int) (Math.random() * names.length)];
	}

	private String getRandomSurname() {
		return surnames[(int) (Math.random() * surnames.length)];
	}

	private String getRandomEmail(String name, String surname) {
		return name + surname + emails[(int) (Math.random() * emails.length)];
	}

	private String getRandomTelephone() {
		Random rand = new Random();
		int num1 = rand.nextInt(800) + 100;
		int num2 = rand.nextInt(999);
		int num3 = rand.nextInt(999);

		DecimalFormat format = new DecimalFormat("000");
		return format.format(num1) + format.format(num2) + format.format(num3);
	}

	private String getRandomPESEL() {
		DecimalFormat format = new DecimalFormat("00000000000");
		return format.format(Math.random() * 100000000000.0);
	}

	private Status getRandomActive() {
		return (Math.random() > 0.5) ? Status.ACTIVE : Status.INACTIVE;
	}

	private Assignment getRandomAssignment() {
		return new Assignment (assignments[(int) (Math.random() * assignments.length)]);
	}
}
