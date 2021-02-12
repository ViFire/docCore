package cleversoft.api;

import java.util.ArrayList;
import java.util.List;


public class ResponseHelper<T> {

    private List<T> errors = new ArrayList<>();
    private List<T> warnings = new ArrayList<>();
    private T payload = null;

    public List<T> getErrors() {
        return errors;
    }

    public void setErrors(T error) {
        errors.add(error);
    }

    public List<T> getWarnings() {
        return warnings;
    }

    public void setWarnings(T warning) {
        warnings.add(warning);
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
