package base;

/**
 * Data class for representing a Person
 */
public class Person{

    /**
     * Used to specify the boss ID for the CEO  
     */
    public static final int NOPERSON_ID = 0;

    public Person(int employeeID, String name, int age, String address, String reports, int bossID){
        this.employeeID = employeeID;
        this.age = age;
        this.bossID = bossID;    
        this.address = address;
        this.name = name;
        this.reports = reports;
    }

    public void update(String name, int age, String address, String reports, int bossID){
        this.age = age;
        this.bossID = bossID;    
        this.address = address;
        this.name = name;
        this.reports = reports;
    }

    int employeeID;
    int bossID;
    int age;
    String name;
    String address;
    String reports;

    public int getEmployeeID() {
        return employeeID;
    }

    public int getBossID() {
        return bossID;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getReports() {
        return reports;
    }

}