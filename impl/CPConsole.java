package impl;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import base.*;

/**
 * Impementation of ICPConsole
 */
public class CPConsole implements ICPConsole {
    /**
     * CPConsole constructor
     * @param in 
     * @param out
     * @param interactive flag telling if info messages will be displayed when data is read 
     * (usually false when reading from a file)
     */
    public CPConsole(InputStream in, PrintStream out, boolean interactive){
        this.in = in;
        this.out = out;
        this.interactive = interactive;
        console = new Scanner(in);
    }

    public void setController(ICPController controller){
        this.controller = controller;
    }

    void print(String string){
        out.print(string);
    }

    void print(String string, boolean intearctiveModeOnly){
        if(interactive || !intearctiveModeOnly){
            out.print(string);
        }
    }

    void println(String string){
        out.println(string);
    }

    void println(String string, boolean intearctiveModeOnly){
        if(interactive || !intearctiveModeOnly){
            out.println(string);
        }
    }

    String readString(){
        String s = "";
        while(s.length() == 0){
            s = console.nextLine();
            s = s.trim();
        }
        
        return s;
    }

    int readInt(){
        int n = console.nextInt();
        console.nextLine();

        return n;
    }

    void cleanup(){
        console.nextLine();
    }

    @Override
    public void showOptions(List<String> options) {
        if(interactive){
            println("Select option");

            int i = 0;
            for(String option: options){
                println("" + i + ". " + option);
                i++;
            }
            print("Your option: ");
        }
        
        try{
            int o = readInt();
            controller.onOptionSelected(o);
        }
        catch(InputMismatchException ex){
            cleanup();
            controller.onInputError(ex.getMessage());    
        }
        catch(NoSuchElementException ex){
            controller.onInputEnd();
        }
    }

    @Override
    public void showMessage(String message){
        println(message); 
    }

    @Override
    public void showInteractiveInfo(String message){
        if(interactive){
            println(message);
            println("");
        } 
    }
    

    @Override
    public void readPerson(boolean update) {
        //if updating, employeeID will be ignored
        int employeeID = 0;

        try{
            if(!update){
                print("EmployeeID = ", true);
                employeeID = readInt();
            }

            if(update){
                print("New name = ", true);
            }
            else{
                print("Name = ", true);
            }
            
            String name = readString();

            print("Age = ", true);
            int age = readInt();

            print("Address = ", true);
            String address = readString();

            print("Reports = ", true);
            String reports = readString();

            print("BossId (enter " + Person.NOPERSON_ID + " if the current Person is a CEO)= ", true);
            int bossID = readInt();

            controller.onPersonDataRead(employeeID, name, age, address, reports, bossID);
        }
        catch(InputMismatchException ex){
            cleanup();
            controller.onInputError(ex.getMessage());    
        }
        catch(NoSuchElementException ex){
            controller.onInputEnd();
        }
    }

    @Override
    public void getString(String message) {
        if(interactive){
            println(message);
        }
        try{
            controller.onStringSelected(readString());
        }
        catch(InputMismatchException ex){
            cleanup();
            controller.onInputError(ex.getMessage());    
        }
    }

    InputStream in;
    PrintStream out;
    boolean interactive;
    Scanner console;
    ICPController controller;
}