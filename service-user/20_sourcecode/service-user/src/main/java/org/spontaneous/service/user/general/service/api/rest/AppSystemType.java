package org.spontaneous.service.user.general.service.api.rest;

/**
 * A datatype for valid app systems.
 *
 * @author Florian Dondorf
 */
public enum AppSystemType {
  ANDROID("android"),
  IOS("ios");

  private final String name;

  private AppSystemType(String name) {
    this.name = name;
  }

  /**
   * Checks if a given string is one of the enum types
   *
   * @param name - name of the app system
   * @return true if appSystemString is from an enum type
   */
  public static boolean isValid(String name) {
    for (AppSystemType appSystem : AppSystemType.values()) {
      if (appSystem.name.equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get a valid AppSystemType for the given name
   *
   * @param name - name of the app system
   * @return a valid AppSystemType otherwise an IllegalArgumentException
   */
  public static AppSystemType fromName(String name) {
    for (AppSystemType appSystem : AppSystemType.values()) {
      if (appSystem.name.equals(name)) {
        return appSystem;
      }
    }
    throw new IllegalArgumentException("Not a AppSystemType: " + name);
  }

}
