package impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.*;

/**
 * Implementation of IHRManager
 * Before adding/updating an Employee, the HRManager performs various validity checks:
 * <ul>
 * <li>A persons boss id can be either 0 (for a CEO) or the ID of an existing employee</li>
 * <li>The EmployeeID needs to be unique</li>
 * <li>A person can not be their own boss</li>
 * <li>(when updating) In order to avoid hierarchical loops (cycling), a person can not become the boss of their boss    
 * </li>
 * </ul> 
 */
public class HRManager implements IHRManager {
        public HRManager(Map<Integer, Team> catalog, CreatePersonErrorHandler errorHandler){
            this.catalog = catalog;
            this.errorHandler = errorHandler;

            //initializing the hierachical structure
            catalog.put(Person.NOPERSON_ID, new Team(null));
        }
        
        Map<Integer, Team> catalog;
        CreatePersonErrorHandler errorHandler;
   
        private boolean verify(boolean update, int employeeID, String name, int age, String address, String reports, int bossID){
            if(bossID == employeeID){
                errorHandler.onCreateError("bossID and employeeID must be different");
            }
            
            if(!catalog.containsKey(bossID)){
                errorHandler.onCreateError("boss ID not found");
                return false;
            }            

            if(update){
                if(!catalog.containsKey(employeeID)){
                    errorHandler.onCreateError("employee ID not found");

                    return false;

                }

                if(isManager(employeeID, bossID)){
                    errorHandler.onCreateError("can not move " + employeeID + " under " + bossID);

                    return false;
                }

            }   
            else{
                if(catalog.containsKey(employeeID)){
                    errorHandler.onCreateError("employee ID already in use");
                    return false;
                }
            } 

            if(name == null || name.length() == 0){
                errorHandler.onCreateError("name is missing");
                return false;
            }

            if(address == null || address.length() == 0){
                errorHandler.onCreateError("address is missing");                      
                return false;              
            }

            if(employeeID == Person.NOPERSON_ID){
                errorHandler.onCreateError("invalid employee ID");
                return false;
            }

            return true;
        }
     
        public boolean addEmployee(int employeeID, String name, int age, String address, String reports, int bossID){
            if(!verify(false, employeeID, name, age, address, reports, bossID)){
                return false;
            } 

            Person newPerson = new Person(employeeID, name, age, address, reports, bossID);    
            addPersonToCatalog(newPerson);
            return true;
        }

        public boolean editEmployee(int employeeID, String name, int age, String address,String reports, int bossID){
            if(!verify(true, employeeID, name, age, address, reports, bossID)){
                return false;
            }

            Person employee = getEmployeeByID(employeeID);
            if(employee.getBossID() != bossID) {
                var oldTeam = catalog.get(employee.getBossID());
                oldTeam.getEmployees().remove(employee);

                var newTeam = catalog.get(bossID);
                newTeam.getEmployees().add(employee);
            }

            employee.update(name, age, address, reports, bossID);


            return true;
        }


        private boolean addPersonToCatalog(Person person){
            var team = catalog.get(person.getBossID());
            if(team == null){
                return false;
            }    
            team.addEmployee(person);

            catalog.put(person.getEmployeeID(), new Team(person));

            return true;
        }

        public Person getEmployeeByID(int id){
            var team = catalog.get(id);
            return team == null? null:team.getLead();
        }

        public List<Person> getEmployeesByName(String name){
            ArrayList<Person> list = new ArrayList<>();
            for(var entry: catalog.values()){
                if(entry.getLead() != null && entry.getLead().getName().equals(name)){
                   list.add(entry.getLead()); 
                }
            }
            return list;
        }

        public List<Person> getAllEmployees(){
            ArrayList<Person> list = new ArrayList<>();
            for(var entry: catalog.values()){
                if(entry.getLead() != null){
                    list.add(entry.getLead());
                }
            }
            return list;
        }

        private boolean isManager(int managerID, int employeeID){
            if(employeeID == 0){
                return false;
            }

            if(managerID == 0){
                return true;
            }

            while(true){
                int bossID = getEmployeeByID(employeeID).getBossID();
                if(bossID == managerID){
                    return true;
                }
                if(bossID == 0){
                    return false;
                }
                employeeID = bossID;
            }
        }
    

}
