package com.scrimmage.common.dto;

import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenOptions {

  private Set<String> tags;
  private Map<String, Object> properties;

}
