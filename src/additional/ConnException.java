package additional;

public class ConnException extends Exception {
    private static String messages []= {"You entered wrong password.","Account is not existing", "Unknown error."};
    public enum ErrorTypes{ wrongPass,notExitsting, err };
    private ErrorTypes errorType;
    public ConnException(ErrorTypes typeOfError){
        errorType = typeOfError;
    }
    public String getErrorMessage(){
        return messages[errorType.ordinal()];
    }
    public ErrorTypes getErrorType(){
        return errorType;
    }
}