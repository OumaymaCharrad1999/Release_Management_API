package com.releasemanagementapi.service;

import org.springframework.http.ResponseEntity;

import com.releasemanagementapi.dto.PlanReleaseDTO;

public interface PlanReleaseService {

	ResponseEntity<?> addNewPlanRelease(PlanReleaseDTO request);
	public ResponseEntity<?> prepareNextBuildRelease(String project);
	
}