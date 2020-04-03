package org.spontaneous.service.user.general.service.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for requests with a invalid api version.
 *
 * @author Horst Jilg
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidApiVersionException extends TechnicalException {

  private static final long serialVersionUID = -325031916930690763L;

  /**
   * Instantiates a new Invalid api version exception.
   *
   * @param message the message
   */
  public InvalidApiVersionException(String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "invalid_api_version";
  }

}
