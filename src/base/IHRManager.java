package base;

import java.util.List;

/**
 * A IHRManager handles the memory representation of the company hierarchical structure (model)
 */
public interface IHRManager {
    boolean addEmployee(int employeeID, String name, int age, String address, String reports, int bossID);
    boolean editEmployee(int employeeID, String name, int age, String address, String reports, int bossID);
    Person getEmployeeByID(int id);
    List<Person> getEmployeesByName(String name);
    List<Person> getAllEmployees();
}