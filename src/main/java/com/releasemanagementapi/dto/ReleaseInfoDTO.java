package com.releasemanagementapi.dto;

import lombok.Data;

@Data
public class ReleaseInfoDTO {

	private Long id;
	private String project;
	private Integer buildId;
	private String status;
	private String description;

}