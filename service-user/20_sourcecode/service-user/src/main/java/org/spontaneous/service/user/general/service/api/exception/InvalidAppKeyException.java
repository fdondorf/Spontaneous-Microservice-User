package org.spontaneous.service.user.general.service.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception for requests with a invalid app key.
 *
 * @author Horst Jilg
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAppKeyException extends TechnicalException {

  private static final long serialVersionUID = 820350169930201011L;

  /**
   * Instantiates a new Invalid app key exception.
   *
   * @param message the message
   */
  public InvalidAppKeyException(String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "invalid_app_key";
  }

}
