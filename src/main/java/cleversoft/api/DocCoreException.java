package cleversoft.api;

public abstract class DocCoreException {

    private int code;
    private String message;

    abstract protected void setCode(int errorCode);
    abstract protected void setMessage(String errorMessage);
}
