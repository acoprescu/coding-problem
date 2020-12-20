package base;

/**
 * An ICPController handles the logic of application, 
 * receiving events from a ICPConsole and handling the model through an IHRManager 
 */

public interface ICPController {
    /**
     * Called when the user inputs data for creating/updating a user
     * @param employeeID Person's ID. If this is an update, employeeID is ignored
     * @param name Person's name
     * @param age Person's name
     * @param address Person's address
     * @param reports Person's reports
     * @param bossID Person's bossID
     */
    void onPersonDataRead(int employeeID, String name, int age, String address, String reports, int bossID);
                            
    /**
     * Called when the user selects an option from a list
     */
    void onOptionSelected(int option);

    /**
     * Called when the user imputs a string
     * @param string
     */
    void onStringSelected(String string);

    /**
     * Called to handle an input error
     * @param error
     */
    void onInputError(String error);

    /**
     * Handles the end of the input file (no more data available) 
     */
    void onInputEnd();
}
