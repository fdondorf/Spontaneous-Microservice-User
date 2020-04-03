package org.spontaneous.service.user.general.service.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for requests with a invalid app version.
 *
 * @author Horst Jilg
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAppVersionException extends TechnicalException {

  private static final long serialVersionUID = -1785339490834616631L;

  /**
   * Instantiates a new Invalid app version exception.
   *
   * @param message the message
   */
  public InvalidAppVersionException(String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "invalid_app_version";
  }

}
