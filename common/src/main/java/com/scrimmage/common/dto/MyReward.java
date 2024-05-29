package com.scrimmage.common.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyReward {

  int id;
  String namespace;
  String key;
  String name;
  Date createdAt;
}
