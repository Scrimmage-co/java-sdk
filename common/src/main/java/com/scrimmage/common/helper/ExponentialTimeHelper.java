package com.scrimmage.common.helper;

public class ExponentialTimeHelper {

  public static int getExponentialTime(int timeToRetry) {
    return 1000 * 2 ^ timeToRetry;
  }

}
