package base;


/**
 * Utility class containing methods to generate string representations of a Person
 * and the table headers for Person lists 
 */
public class Utils {
    
    public static String getBriefHeader(){
        return "ID" + ",\t" + "name" + ",\t" + "bossID";
    }
    
    public static String getPersonBrief(Person person){
        if(person == null){
            return "-\tNo Person";
        }
        return person.employeeID + ",\t" + person.name + ",\t" + person.bossID;
    }

    public static String getVerboseHeader(){
        return "ID" + ",\t" + "name" + ",\t" + "age" + ",\t" + "address" + 
            ",\t" + "reports" + ",\t" + "bossID";
    }
    
    public static String getPersonVerbose(Person person){
        if(person == null){
            return "-,\tNo Person";
        }
        return person.employeeID + ",\t" + person.name + ",\t" + person.age + ",\t" + person.address + 
            ",\t" + person.reports + ",\t" + person.bossID;
    }
    

}
