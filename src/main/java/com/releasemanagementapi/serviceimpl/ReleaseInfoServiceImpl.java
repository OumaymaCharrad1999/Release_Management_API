package com.releasemanagementapi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.releasemanagementapi.model.ReleaseInfo;
import com.releasemanagementapi.persistence.ReleaseInfoRepository;
import com.releasemanagementapi.service.ReleaseInfoService;

import javassist.NotFoundException;

/**
 * 
 * @author this service used for to manage releases
 */

@Service
public class ReleaseInfoServiceImpl implements ReleaseInfoService {

	@Autowired
	ReleaseInfoRepository repo;

	@Override
	public List<ReleaseInfo> getByProject(String project) {

		return repo.findByProject(project);

	}

	@Override
	public ReleaseInfo updateStatus(String project, String status) throws Exception {

		ReleaseInfo releaseInfo = repo.findTop1ByProjectOrderByBuildIdDesc(project);
		
		if (releaseInfo == null) {
			throw new NotFoundException("Projet n'existe pas");
		}
		
		releaseInfo.setStatus(status);
		ReleaseInfo result = repo.save(releaseInfo);
		return result;

	}

}