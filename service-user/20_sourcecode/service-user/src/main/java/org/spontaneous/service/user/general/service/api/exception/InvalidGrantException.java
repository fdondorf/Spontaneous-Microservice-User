package org.spontaneous.service.user.general.service.api.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Exception for requests with an grant
 *
 * @author hahmad
 */
public class InvalidGrantException extends OAuth2Exception {

  private static final long serialVersionUID = -454266469953764370L;

  /**
   * Instantiates a new Invalid grant exception.
   *
   * @param msg the msg
   */
  public InvalidGrantException(String msg) {
    super(msg);
  }

  @Override
  public String getOAuth2ErrorCode() {
    return "invalid_grant";
  }

  @Override
  public int getHttpErrorCode() {
    return 401;
  }

}
