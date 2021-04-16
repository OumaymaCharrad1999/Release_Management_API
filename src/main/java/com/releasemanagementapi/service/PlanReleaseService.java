package com.releasemanagementapi.service;

import java.util.List;

import com.releasemanagementapi.dto.PlanReleaseDTO;
import com.releasemanagementapi.model.PlanRelease;
import com.releasemanagementapi.model.ReleaseInfo;

public interface PlanReleaseService {

	PlanRelease save(PlanReleaseDTO request);
	public String prepareNextBuildRelease(String project);
	List<ReleaseInfo> getSuccessfulVersions(String project);

}
