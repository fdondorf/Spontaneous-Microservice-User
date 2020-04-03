package org.spontaneous.service.user.general.service.api.exception;

/**
 * Common business exception.
 * <p>
 * Every business exception has an defined error code. Nevertheless the getErrorCode() method can be overridden.
 *
 * @author Florian Dondorf
 */
public class BusinessException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  private final String errorCode;

  /**
   * Creates a BusinessException object
   *
   * @param errorCode Error code of this exception
   * @param message Detailed message
   * @param cause Cause for buisness exception
   */
  public BusinessException(String errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  /**
   * Creates a BusinessException object
   *
   * @param errorCode Error code of this exception
   * @param message Detailed message
   */
  public BusinessException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  @Override
  public String getErrorCode() {
    return errorCode;
  }

}
