package com.scrimmage.common.service;

public interface ILoggerService {
  void log (Object... args);
  void warn (Object... args);
  void debug (Object... args);
  void info (Object... args);
  void error (Object... args);
}
