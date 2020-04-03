package org.spontaneous.service.user.general.service.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for requests with an invalid app system string.
 *
 * @author Horst Jilg
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAppSystemException extends TechnicalException {

  private static final long serialVersionUID = 2620746995699471649L;

  /**
   * Instantiates a new Invalid app system exception.
   *
   * @param message the message
   */
  public InvalidAppSystemException(String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "invalid_app_system";
  }

}
