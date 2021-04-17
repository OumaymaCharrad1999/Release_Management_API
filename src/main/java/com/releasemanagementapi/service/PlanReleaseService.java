package com.releasemanagementapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.releasemanagementapi.dto.PlanReleaseDTO;

public interface PlanReleaseService {

	ResponseEntity<?> save(PlanReleaseDTO request);
	public String prepareNextBuildRelease(String project);
	List<String> getSuccessfulVersions(String project);
	String getSuccessfulVersions2(String project);
}