package test;

/**
 * Base class for all the tests 
 * The implementations should override testCall
 */
public abstract class BaseTest {
    void assertNotNull(Object obj, String message){
        if(obj == null){
            throw new AssertionError(message);
        }
    }

    void assertTrue(boolean exp, String message){
        if(!exp){
            throw new AssertionError(message);
        }
    }
    
    /**
     * The actuall content of the test 
     */
    abstract void testCall();

    /**
     * Method to be called for each test
     * The default implementation calls testCall() and checks that assertions have passed
     * @return
     */
    public TestResult runTest(){
        try{
            testCall();
        }
        catch(AssertionError err){
            return TestResult.createFailed("Assert failed: " + err.getMessage());
        }
        catch(Exception e){
            return TestResult.createFailed("Exception " + e.getMessage());
        }

        return TestResult.createSuccesful();

    }

}
