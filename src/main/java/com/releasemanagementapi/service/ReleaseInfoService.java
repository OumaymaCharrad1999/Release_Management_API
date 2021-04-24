package com.releasemanagementapi.service;

import org.springframework.http.ResponseEntity;

public interface ReleaseInfoService {

	ResponseEntity<?> getByProject(String project);
	ResponseEntity<?> getStatus(String project);
	ResponseEntity<?> updateStatus(String project, String status);
	
}