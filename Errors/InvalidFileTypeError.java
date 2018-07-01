package Errors;

public class InvalidFileTypeError extends Error {

    public InvalidFileTypeError(String cause) {
        super(cause);
    }

    @Override
    public String toString() {
        if (this.getCause().charAt(0) == 'd')
            return this.getCause() + " nie jest tekstowy";
        else
            return this.getCause() + " nie jest XML";
    }
}
