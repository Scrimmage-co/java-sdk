package com.scrimmage.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrimmageServiceUnavailable extends RuntimeException {

  private int status = 503;

  public ScrimmageServiceUnavailable(String message) {
    super(message);
  }
}
