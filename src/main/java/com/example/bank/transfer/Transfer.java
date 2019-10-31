package com.example.bank.transfer;

import lombok.AllArgsConstructor;
import lombok.Value;
import spark.utils.StringUtils;

@Value
@AllArgsConstructor
public class Transfer {

  private String userFrom;
  private String userTo;
  private int amount;
  private String description;

  public boolean isValid() {
    return StringUtils.isNotBlank(userFrom) && StringUtils.isNotBlank(userTo) && amount >= 0;
  }
}
