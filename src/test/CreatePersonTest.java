package test;

import base.Person;

/**
 * Tests the creation and updating of a Person (without adding to the hierarchy)
 */
public class CreatePersonTest extends BaseTest {

    Person testCreate(int employeeID, String name, int age, String address, String reports, int bossID){
        Person person = new Person(employeeID, name, age, address, reports, bossID);
        
        boolean compare = 
            person.getEmployeeID() == employeeID &&
            person.getName().equals(name) &&
            person.getAge() == age &&
            person.getAddress().equals(address) &&
            person.getReports().equals(reports) && 
            person.getBossID() == bossID;

            assert compare : 
                "Error on creating Person " + list(employeeID, name, age, address, reports, bossID);
    
            return person;
    }

    void testUpdate(Person person, String name, int age, String address, String reports, int bossID){
        int employeeID = person.getEmployeeID();
        person.update(name, age, address, reports, bossID);

        assertTrue(person.getEmployeeID() == employeeID ,
            "Error on updating Person - employeeID should remain unchanged");

        boolean compare = 
            person.getName().equals(name) &&
            person.getAge() == age &&
            person.getAddress().equals(address) &&
            person.getReports().equals(reports) && 
            person.getBossID() == bossID;
            
            assertTrue(compare,
                "Error on updating Person " + list(employeeID, name, age, address, reports, bossID));


    }

    String list(int employeeID, String name, int age, String address, String reports, int bossID){
        return ": " + employeeID + " " + name + " " + 
                                                age + " " + address + " "  +reports +  " " + bossID;

    }

    @Override
    void testCall() {
            int employeeID = 10;
            String name = "John Doe";
            int age = 25;
            String address = "Somewhere 1";
            String reports = "Report 1";
            int bossID = Person.NOPERSON_ID;

            var person = testCreate(employeeID, name, age, address, reports, bossID);
            
            name = "John Doe1";
            age = 35;
            address = "Somewhere 2";
            reports = "Report 2";
            bossID = 20;

            testUpdate(person, name, age, address, reports, bossID);
    }
    
}
