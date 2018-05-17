package additional;

public class ConnException extends Exception {
    private static String messages []= {"You entered wrong password.","Account is not existing.",
            "This mail is occupied in database.", "Unknown error.", "Don't try to be hacker!"};
    public enum ErrorTypes{ wrongPass,notExitsting, existing, err, hacker };
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
