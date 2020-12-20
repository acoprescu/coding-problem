import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

import impl.*;


public class CodingProblem {

    static void start(InputStream in, PrintStream out, boolean interactive){
        final var console = new CPConsole(in, out, interactive);
        var hrManager  = new HRManager(new HashMap<>(), s -> console.showMessage(s));
        var controller = CPController.createController(hrManager, console);

        controller.start();
    }

    public static void main(String[] args){
        boolean customIO = (args.length >= 2); 
        
        InputStream in = null;
        PrintStream out = null;
        
        if(customIO){
            try{
                in = new FileInputStream(args[0]);
                out = new PrintStream(args[1]);
            }
            catch(FileNotFoundException ex){
                if(in != null){
                    try{
                        in.close();
                    }
                    catch(IOException e){
                    }
                }            
                System.out.println("Initialization Error");
                return;
            }
        }
        else{
            in = System.in;
            out = System.out;
        }        

        start(in, out, !customIO);

        if(customIO){
            try{
                in.close();
            }
            catch(IOException e){
            }
            out.close();
        }
    }


}
