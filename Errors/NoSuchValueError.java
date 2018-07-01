package Errors;

public class NoSuchValueError extends Error {

    public NoSuchValueError(String cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "Brak wartości dla klucza " + this.getCause();
    }
}
