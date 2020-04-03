package org.spontaneous.service.user.general.service.api.exception;

import java.util.UUID;

/**
 *
 * Common technical exception.
 * <p>
 * Generates automatically an unique log id. If instantiated with an cause and the cause exception has already an unique log id, then this id will be
 * used.
 * <p>
 * Subclasses are allowed to provide their own unique log id, if they have one.
 *
 * @author Horst Jilg
 */
public class TechnicalException extends ApplicationException {

  private static final long serialVersionUID = 1L;
  private String uuid;

  /**
   * Creates a TechnicalException object
   *
   * @param message Message of the excetion
   */
  public TechnicalException(String message) {
    super(message);
    uuid = UUID.randomUUID().toString();
  }

  /**
   * Creates a TechnicalException object
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method).
   * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is
   *          nonexistent or unknown.)
   */
  public TechnicalException(String message, Throwable cause) {
    super(message, cause);
    if (cause != null && cause instanceof TechnicalException) {
      uuid = ((TechnicalException) cause).uuid;
    } else {
      uuid = UUID.randomUUID().toString();
    }
  }

  /**
   * Creates a TechnicalException object
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method).
   * @param uuid Uuid to identify this exception
   */
  protected TechnicalException(String message, String uuid) {
    super(message);
    this.uuid = uuid;
  }

  /**
   * This method can be overridden in subclasses to deliver their own error code.
   *
   * @return the error code
   */
  @Override
  public String getErrorCode() {
    return "internal_server_error";
  }

  /**
   *
   * Retruns a unique error log id that can be used to identify this error
   *
   * @return an unique log id
   */
  public String getErrorLogId() {
    return uuid.toString();
  }

}
