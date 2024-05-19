package com.scrimmage.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardableEventDTO {

  private String namespace;

  private String userId;

  private String dataType;

  private String eventId;

  private Rewardable body;
}
