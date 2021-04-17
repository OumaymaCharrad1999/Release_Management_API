package com.releasemanagementapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.releasemanagementapi.model.ReleaseInfo;

import javassist.NotFoundException;

public interface ReleaseInfoService {

	ResponseEntity<?>  getByProject(String project) throws NotFoundException;
	ResponseEntity<?> updateStatus(String project, String status) throws Exception;
	
}