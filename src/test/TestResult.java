package test;
 
/**
 * Object returned buy each test
 */
public class TestResult {

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }


    public static TestResult createSuccesful(){
        return new TestResult(true, null);
    }

    public static TestResult createFailed(String message){
        return new TestResult(false, message);
    }

    private TestResult(boolean isSuccessful, String message){
        this.isSuccessful = isSuccessful;
        this.message = message == null ? "": message;
    }

    boolean isSuccessful;
    String message;

}
