package com.scrimmage.core;

import com.scrimmage.common.constant.LogLevel;
import com.scrimmage.common.service.ILoggerService;
import java.util.Arrays;

public class ScrimmageLoggerService implements ILoggerService {

  private final ScrimmageConfig scrimmageConfig;

  public ScrimmageLoggerService(ScrimmageConfig scrimmageConfig) {
    this.scrimmageConfig = scrimmageConfig;
  }

  @Override
  public void log(Object... args) {
    if (scrimmageConfig.getLogLevel().getOrdinal()
        <= LogLevel.LOG.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }

  @Override
  public void warn(Object... args) {
    if (scrimmageConfig.getLogLevel().getOrdinal()
        <= LogLevel.WARN.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }

  @Override
  public void debug(Object... args) {
    if (scrimmageConfig.getLogLevel().getOrdinal()
        <= LogLevel.DEBUG.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }

  @Override
  public void info(Object... args) {
    if (scrimmageConfig.getLogLevel().getOrdinal()
        <= LogLevel.INFO.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }

  @Override
  public void error(Object... args) {
    if (scrimmageConfig.getLogLevel().getOrdinal()
        <= LogLevel.ERROR.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }
}
