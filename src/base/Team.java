package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Data class used to pair an employee with the team (possible an empty one) they lead
 * 
 */
public class Team {
    public Team(Person lead, List<Person> employees){
        this.lead = lead;
        this.employees = (employees == null? new ArrayList<Person>() : employees);
    }
    
    public Team(Person lead){
        this(lead, new ArrayList<>());
    }

    public Team(Person lead, Person employee){
        this(lead, new ArrayList<>(Arrays.asList(employee)));
    }
    
    public void addEmployee(Person newEmployee){
        employees.add(newEmployee);
    }

    Person lead;
    List<Person> employees;

    public Person getLead() {
        return lead;
    }

    public List<Person> getEmployees() {
        return employees;
    }
}
