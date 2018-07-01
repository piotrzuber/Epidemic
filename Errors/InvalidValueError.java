package Errors;

public class InvalidValueError extends Error {
    private String key;

    public InvalidValueError(String cause, String key) {
        super(cause);
        this.key = key;
    }

    @Override
    public String toString() {
        return "Niedozwolona wartość " + this.getCause() + " dla klucza " + this.key;
    }
}
