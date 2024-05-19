package com.scrimmage.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountNotLinkedException extends RuntimeException {

  private int status = 400;

  public AccountNotLinkedException(String message) {
    super(message);
  }

}
