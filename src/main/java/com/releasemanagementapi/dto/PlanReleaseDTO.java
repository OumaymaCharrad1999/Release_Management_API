package com.releasemanagementapi.dto;

import lombok.Data;

@Data
public class PlanReleaseDTO {

	 private Long planReleaseId;
	 private Integer majorVersion;
	 private Integer minorVersion;
	 private Integer integration;

}
