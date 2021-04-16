package com.releasemanagementapi.service;

import java.util.List;

import com.releasemanagementapi.model.ReleaseInfo;

public interface ReleaseInfoService {

	List<ReleaseInfo> getByProject(String project);
	ReleaseInfo updateStatus(String project, String status) throws Exception;
	
}
