import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

import impl.HRManager;
import test.*;

class TestRunnerImpl {

    /**
     * List of tests to run
     */
    BaseTest[] tests = {
        new CreatePersonTest(),
        new AddToTreeTest(new HRManager(new HashMap<>(), s->{})),
        new SelectByName(new HRManager(new HashMap<>(), s->{})),
    };


    private TestRunnerImpl(String path) {
        this.path = path;
    }

    static TestRunnerImpl create(String path){
        var tr = new TestRunnerImpl(path);
        if(path == null){
            tr.out = System.out;
        }
        else{    
            try{
                tr.out = new PrintStream(path);  
            }
            catch(FileNotFoundException ex){
                return null;
            }
        }

        return tr;
    }

    void close(){
        if(path != null){
            out.close();
        }
    }

    PrintStream out;
    String path;
    
    

}

public class TestRunner{
    /**
     * Creates a TestRunnerImpl object and runs the tests
     * @param args File to write the results into 
     * (uses default output if no arg was passed)
     */
    public static void main(String[] args){
        TestRunnerImpl runner = TestRunnerImpl.create(args.length > 0? args[0] : null); 
        if(runner == null){
            System.out.println("Error on test initialization");
            return;
        }

        int successful = 0;
        int failed = 0;
        

        for(var test: runner.tests){
            var testResult = test.runTest();
            if(testResult.isSuccessful()){
                runner.out.println(test.getClass().getSimpleName() + ": success") ;
                successful++;
            }
            else{
                runner.out.println(test.getClass().getSimpleName() + ": failed");
                runner.out.println(testResult.getMessage());
                failed++;
            }
        }

        runner.out.println("Total Tests: " + (successful + failed) + " " + 
            "Successful: " + successful + " " +
            "Failed: " + failed
             );

        runner.close();
    }
}