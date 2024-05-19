package com.scrimmage.common.constant;

public enum LogLevel {
  LOG("log", 2),
  WARN("warn", 3),
  DEBUG("debug", 0),
  INFO("info", 1),
  ERROR("error", 4);

  private final String value;
  private final int level;

  LogLevel(String value, int level) {
    this.value = value;
    this.level = level;
  }

  public int getOrdinal() {
    return this.level;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
