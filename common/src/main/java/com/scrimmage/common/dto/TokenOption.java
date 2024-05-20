package com.scrimmage.common.dto;

import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenOption {

  private String id;
  private Set<String> tags;
  private Map<String, Object> properties;
}
