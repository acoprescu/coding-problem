package impl;

/**
 * Used by HRManager to pass detail about the failed operations
 */

public interface CreatePersonErrorHandler{
    /**
     * Called when a Person can not be created/updated
     * @param errorMessage
     */
    void onCreateError(String errorMessage);
}
