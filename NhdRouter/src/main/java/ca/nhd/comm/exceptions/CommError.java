package ca.nhd.comm.exceptions;

public class CommError extends RuntimeException {
    public CommError(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
