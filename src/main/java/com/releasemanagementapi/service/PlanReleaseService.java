package com.releasemanagementapi.service;

import org.springframework.http.ResponseEntity;

import com.releasemanagementapi.dto.PlanReleaseDTO;

public interface PlanReleaseService {

	ResponseEntity<?> addNewPlanRelease(PlanReleaseDTO request);
	public ResponseEntity<?> prepareNextBuildRelease(String project);
	ResponseEntity<?> getSuccessfulVersions(String project);
	ResponseEntity<?> getSuccessfulVersions2(String project);
	
}