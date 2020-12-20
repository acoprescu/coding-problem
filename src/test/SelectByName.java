package test;

import base.Person;
import impl.HRManager;

/**
 * Checks that select by name works correctly,
 */
public class SelectByName extends BaseTest {
    public SelectByName(HRManager hrManager){
        this.hrManager = hrManager;
    }

    HRManager hrManager;

    void checkGetByName(String name, int count, String message){
        var list = hrManager.getEmployeesByName(name);
            assertNotNull(list, 
                message + " HRManager::getEmployeesByName should return not null");
            assertTrue(list.size() == count, 
                message + " HRManager::getEmployeesByName should return a list with " + 
                    count + " elements");
    }

    @Override
    void testCall() {
            checkGetByName("Some Name", 0, "Initial Test");
            
            hrManager.addEmployee(10, "John Doe", 40, "Address 1", "Reports 1", Person.NOPERSON_ID);
            hrManager.addEmployee(20, "John Doe", 40, "Address 1", "Reports 1", 10);
            checkGetByName("John Doe", 2, "Two employees added");
            
            Person person = hrManager.getEmployeesByName("John Doe").get(1);
            hrManager.editEmployee(person.getEmployeeID(), "John Doe 1", person.getAge(), 
                    person.getAddress(), person.getReports(), person.getBossID());
            checkGetByName("John Doe", 1, "Person renamed");
            checkGetByName("John Doe 1", 1, "Person renamed");

            person = hrManager.getEmployeesByName("John Doe").get(0);
            hrManager.editEmployee(person.getEmployeeID(), "John Doe 2", person.getAge(), 
                    person.getAddress(), person.getReports(), person.getBossID());
            checkGetByName("John Doe", 0, "Another Person renamed");

        }
    
}
