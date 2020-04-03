package org.spontaneous.service.user.general.service.api.rest;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The header data for all tracking service requests.
 * <p>
 * JSON example: <code>
 * {
 * 	"apiVersion": "1.0",
 * 	"appVersion": "1.0",
 * 	"appKey": "asdjk49ksdfa490sfdaasjfl32",
 * 	"appSystem": "android"
 *     ...
 * }
 * </code>
 *
 * @author fdondorf
 */
public class Header {

  @NotBlank
  @Size(max = 255)
  private String apiVersion;

  @NotBlank
  @Size(max = 255)
  private String appVersion;

  @NotBlank
  @Size(max = 255)
  private String appSystem;

  @NotBlank
  @Size(max = 255)
  private String appKey;

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getAppSystem() {
    return appSystem;
  }

  public void setAppSystem(String appSystem) {
    this.appSystem = appSystem;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  @Override
  public String toString() {
    return "Header [apiVersion=" + apiVersion + ", appVersion=" + appVersion + ", appSystem=" + appSystem + "]";
  }

}
