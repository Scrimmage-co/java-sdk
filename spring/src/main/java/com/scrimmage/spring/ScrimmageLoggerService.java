package com.scrimmage.spring;

import com.scrimmage.common.constant.LogLevel;
import com.scrimmage.common.service.ILoggerService;
import java.util.Arrays;

public class ScrimmageLoggerService implements ILoggerService {

  private ScrimmageConfigService scrimmageConfigService;

  public ScrimmageLoggerService(ScrimmageConfigService scrimmageConfigService) {
    this.scrimmageConfigService = scrimmageConfigService;
  }

  @Override
  public void log(Object... args) {
    if (LogLevel.valueOf(scrimmageConfigService.logLevel).getOrdinal()
        >= LogLevel.LOG.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }

  @Override
  public void warn(Object... args) {
    if (LogLevel.valueOf(scrimmageConfigService.logLevel).getOrdinal()
        >= LogLevel.WARN.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }

  @Override
  public void debug(Object... args) {
    if (LogLevel.valueOf(scrimmageConfigService.logLevel).getOrdinal()
        >= LogLevel.DEBUG.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }

  @Override
  public void info(Object... args) {
    if (LogLevel.valueOf(scrimmageConfigService.logLevel).getOrdinal()
        >= LogLevel.INFO.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }

  @Override
  public void error(Object... args) {
    if (LogLevel.valueOf(scrimmageConfigService.logLevel).getOrdinal()
        >= LogLevel.ERROR.getOrdinal()) {
      System.out.println(Arrays.toString(args));
    }
  }
}
