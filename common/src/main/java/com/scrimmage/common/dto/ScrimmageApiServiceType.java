package com.scrimmage.common.dto;

public enum ScrimmageApiServiceType {

  API("api"),
  P2E("p2e"),
  FED("fed"),
  NBC("nbc");

  private final String value;

  ScrimmageApiServiceType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  public static String getUrl(String url,
      ScrimmageApiServiceType scrimmageApiServiceType, String subroute) {
    return url + "/" + scrimmageApiServiceType.toString() + (subroute == null || subroute.isBlank()
        ? "" : subroute);
  }
}
