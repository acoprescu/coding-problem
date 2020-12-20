package test;

import base.Person;
import impl.HRManager;

/**
 * Checks that 
 * new Employees are added correctly,
 * the HRManager prevents adding two employees with the same id,
 * an employee can not be added if boss id is invalid or equal to the empoyee id   
 */
public class AddToTreeTest extends BaseTest {
    public AddToTreeTest(HRManager hrManager){
        this.hrManager = hrManager;
    }

    HRManager hrManager;

    void checkAllEmp(int count, String message){
        var list = hrManager.getAllEmployees();
            assertNotNull(list, 
                    message + " HRManager::getAllEmployees should return not null");
            assertTrue(list.size() == count, 
                    message + " HRManager::getAllEmployees should return a list with " + 
                        count + " elements");
    }

    @Override
    void testCall() {
            checkAllEmp(0, "Initial Test");
            
            hrManager.addEmployee(10, "Manager", 40, "Address 1", "Reports 1", Person.NOPERSON_ID);
            checkAllEmp(1, "Manager Added");
            
            boolean b = true;
            b &= hrManager.addEmployee(20, "Employee 1", 40, "Address 2", "Reports 2", 10);
            b &= hrManager.addEmployee(30, "Employee 2", 30, "Address 3", "Reports 3", 10);
            b &= hrManager.addEmployee(40, "Employee 2", 30, "Address 3", "Reports 3", 10);
            assertTrue(b, "add Employee returned false");
            checkAllEmp(4, "Three employees added");

            b = hrManager.addEmployee(20, "Employee 01", 40, "Address 02", "Reports 2", 10);
            assertTrue(!b, "add Employee returned true");
            checkAllEmp(4, "Add Employee with the same ID");

            b = hrManager.addEmployee(100, "Employee I", 40, "Address 1", "Reports 1", 100);
            assertTrue(!b, "add Employee returned true");
            checkAllEmp(4, "Add Employee with the ID == bossID");

            b = hrManager.addEmployee(100, "Employee I", 40, "Address 1", "Reports 1", 1000);
            assertTrue(!b, "add Employee returned true");
            checkAllEmp(4, "Add Employee with the non existing bossID");
    }
    
}
