package org.spontaneous.service.user.general.service.api.exception;

/**
 * An abstract super class for all technical and business exceptions.
 * <p>
 * Subclasses have to define the getErrorCode() method.
 *
 * @author Florian Dondorf
 */
public abstract class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a ApplicationException object
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method).
   * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is
   *          nonexistent or unknown.)
   */
  public ApplicationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a ApplicationException object
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method).
   */
  public ApplicationException(String message) {
    super(message);
  }

  /**
   * Template method that returns a error code
   *
   * @return Returns a error code
   */
  public abstract String getErrorCode();

  /**
   * This method delivers by default the message of the exception.
   *
   * @return the error description
   */
  public String getErrorDescription() {
    return getMessage();
  }

}
