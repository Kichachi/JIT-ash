package pl.jitsolutions.jitash.business.employee.boundry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.jitsolutions.jitash.business.employee.entity.Employee;

@Singleton
@Startup
public class EmployeeTestDataProvider {

	private final static String[] names;
	private final static String[] surnames;
	private final static String[] emails;

	static {
		names = new String[]{"Michał","Mikołaj","Mateusz","Piotr","Łukasz","Krzysztof","Marta","Agata","Monika",
				"Małgorzata","Paweł"};
		surnames = new String[]{"Garbarczyk","Stobiński","Modrzejewski","Raszkowski","Ziółkowski","Laptop","Cymerys",
				"Wicikowski","Pelzner","Milewicz","Lys"};
		emails = new String[]{"@wp.pl","@gmail.com","@jitsolutions.pl","@najlepszyserwisswiata.world"};

	}

	public List<Employee> createEmployees(int size){
		List<Employee> employeeList = new ArrayList<Employee>();
		for (int i = 0;i<size;i++) {
			employeeList.add(generateEmployee());
		}
		return employeeList;
	}

	public Employee generateEmployee(){
		String name = getRandomName();
		String surname = getRandomSurname();
		return new Employee.EmployeeBuilder().withEmail(getRandomEmail(name,surname)).withName
				(name).withSurname(surname).withPESEL(getRandomPESEL()).withTelephone
				(getRandomTelephone()).withActive(getRandomActive()).build();
	}

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	private void init() {
		for (int i =0 ; i<200 ; i++) {
			Employee employee = generateEmployee();
			entityManager.persist(employee);
		}
		entityManager.flush();
	}
	private String getRandomName() { return names[(int) (Math.random()*names.length)]; }
	private String getRandomSurname() { return surnames[(int) (Math.random()*surnames.length)]; }
	private String getRandomEmail(String name,String surname) { return name + surname + names[(int) (Math.random()*names
			.length)]; }
	private String getRandomTelephone() {
		Random rand = new Random();
		int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
		int num2 = rand.nextInt(743);
		int num3 = rand.nextInt(10000);

		DecimalFormat format = new DecimalFormat("000");
		return format.format(num1) + format.format(num2) + format.format(num3);
	}
	private String getRandomPESEL() {
		DecimalFormat format = new DecimalFormat("00000000000");
		return format.format(Math.random()*100000000000.0);
	}
	private boolean getRandomActive() { return (Math.random() >0.5) ? true : false;}
}
