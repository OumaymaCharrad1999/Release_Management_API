package com.releasemanagementapi.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.releasemanagementapi.model.ReleaseInfo;

public interface ReleaseInfoRepository extends JpaRepository<ReleaseInfo, Long> {
	
	public List<ReleaseInfo> findByProject(String project);
	public ReleaseInfo findTop1ByProjectOrderByBuildIdDesc(String project);
	public List<ReleaseInfo> findByProjectAndStatus(String project, String status);


}