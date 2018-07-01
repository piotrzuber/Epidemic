package Errors;

public class NoSuchFileError extends Error {

    public NoSuchFileError(String cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "Brak pliku " + this.getCause();
    }
}
