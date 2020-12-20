package impl;

import java.util.ArrayList;
import java.util.List;

import base.*;

/**
 * Implementation of ICPController
 */
public class CPController implements ICPController {
    /**
     * Factory method creating a controller and associationg it with a Console
     * The controller does nothing until start() is called
     * 
     * @param hrManager
     * @param console
     * @return
     */
    public static CPController createController(IHRManager hrManager, ICPConsole console){
        var controller = new CPController(hrManager, console);
        console.setController(controller);

        return controller;
    }
    
    CPController(IHRManager hrManager, ICPConsole console){
        this.hrManager = hrManager;
        this.console = console;
    }


    public void start(){
        setState(State.STATE_RUNNING);
        run();
    }

    //runs in a loop and update the status of the console according to the current state
    void run(){
        while(true){
            if(state == State.STATE_EXIT){
                break;
            }
            update();
        }
    }

    IHRManager hrManager;
    ICPConsole console;
    
    State state;
    int currentSellID = 0;
    List<Person> currentList;

    enum State{
        STATE_NONE,
        STATE_RUNNING, 
        STATE_INPUT_NAME,
        STATE_SELECT_FROM_NAME, 
        STATE_CREATE,
        STATE_EDIT, 
        STATE_EXIT
    }

    //commands corresponding to the console main menu
    final Command[] mainOptionCommands = {
        new Command("Add Employee", this::addEmployee),
        new Command("Edit Employees (by Name)", this::selectByName),
        new Command("List All", this::listAll),
        new Command("Exit", this::exit),
    };

    void addEmployee(){
        setState(State.STATE_CREATE);
    }

    void selectByName(){
        setState(State.STATE_INPUT_NAME);
    }

    void listAll(){
        console.showMessage(Utils.getVerboseHeader());
        for(var str: hrManager.getAllEmployees()){
            console.showMessage(Utils.getPersonVerbose(str));
        }

        setState(state);
    }

    void exit(){
        setState(State.STATE_EXIT);
    }

    void setState(State state){
        this.state = state; 
    }

    void update(){
        ArrayList<String> output = new ArrayList<>();

        switch(state){
            case STATE_RUNNING:
                for(var command: mainOptionCommands){
                    output.add(command.title);
                }
                console.showOptions(output);
            break;

            case STATE_INPUT_NAME:
                console.getString("Name: ");
            break;

            case STATE_SELECT_FROM_NAME:
                for(var person: currentList){
                    output.add(Utils.getPersonBrief(person));
                }
                console.showOptions(output);
            break;    

            case STATE_CREATE:
                console.readPerson(false);
            break;
            
            case STATE_EDIT:
                console.readPerson(true);
            break;

            default:

            break;
        }
    }

    @Override
    public void onPersonDataRead(int employeeID, String name, int age, 
                                    String address, String reports, int bossID) {

        if(state == State.STATE_CREATE){
            console.showInteractiveInfo("Adding Employee..");
            hrManager.addEmployee(employeeID, name, age, address, reports, bossID);
        }
        else
        if(state == State.STATE_EDIT){
            console.showInteractiveInfo("Updating Employee..");
            hrManager.editEmployee(currentSellID, name, age, address, reports, bossID);
        }
        setState(State.STATE_RUNNING);
    }


    @Override
    public void onOptionSelected(int option) {
        switch(state){
            case STATE_RUNNING:
                if(option < 0 || option >= mainOptionCommands.length){
                    console.showMessage("Wrong option");
                    console.showMessage("");
                    setState(State.STATE_RUNNING);
                    return;
                }
                mainOptionCommands[option].method.apply();    
            break; 

            case STATE_SELECT_FROM_NAME:
                if(option < 0 || option >= currentList.size()){
                    console.showMessage("Wrong option");
                    console.showMessage("");
                    setState(State.STATE_RUNNING);
                    return;     
                }
                currentSellID = currentList.get(option).getEmployeeID();
                setState(State.STATE_EDIT);
            break; 
            default:
            break;
        }

    }

    @Override
    public void onStringSelected(String string) {
        if(state == State.STATE_INPUT_NAME){
            currentList = hrManager.getEmployeesByName(string);
            if(currentList == null || currentList.isEmpty()){
                console.showMessage("No result");
                console.showMessage("");
                setState(State.STATE_RUNNING);
            }
            else
            if(currentList.size() == 1){
                currentSellID = currentList.get(0).getEmployeeID();
                setState(State.STATE_EDIT);
            }
            else{
                setState(State.STATE_SELECT_FROM_NAME);
            }
        }

    }

    @Override
    public void onInputError(String error) {
        console.showMessage("InputError " + error);
        console.showMessage("");
        setState(State.STATE_RUNNING);
    }

    @Override
    public void onInputEnd() {
        console.showInteractiveInfo("No more data");
        setState(State.STATE_EXIT);
    }
    
    
}


interface VoidMethod{
    abstract void apply();
}

class Command{
    Command(String title, VoidMethod method){
        this.title = title;
        this.method = method;
    }

    String title;
    VoidMethod method;
}