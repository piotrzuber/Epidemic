package Errors;

public abstract class Error {
    private String cause;

    public Error(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return this.cause;
    }

    public void throwError() {
        System.out.println(this.toString());
        System.exit(1);
    }
}
