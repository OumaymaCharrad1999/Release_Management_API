package com.releasemanagementapi.serviceimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.releasemanagementapi.dto.PlanReleaseDTO;
import com.releasemanagementapi.model.PlanRelease;
import com.releasemanagementapi.model.ReleaseInfo;
import com.releasemanagementapi.persistence.PlanReleaseRepository;
import com.releasemanagementapi.persistence.ReleaseInfoRepository;
import com.releasemanagementapi.service.PlanReleaseService;

@Service
public class PlanReleaseServiceImpl implements PlanReleaseService {

	@Autowired
	private ReleaseInfoRepository releaseInfoRepository;
	
	@Autowired
	PlanReleaseRepository repo;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public PlanRelease save(PlanReleaseDTO request) {
	
		ModelMapper modelMapper = new ModelMapper();
		PlanRelease planRelease = modelMapper.map(request, PlanRelease.class);
		PlanRelease result = repo.save(planRelease);
		return result;
		
	}

	@Override
	public String prepareNextBuildRelease(String project) {

		ReleaseInfo releaseInfo = releaseInfoRepository.findTop1ByProjectOrderByBuildIdDesc(project);
		String nextBuild = "";

			if (releaseInfo.getStatus().equalsIgnoreCase("Installed")) {
				entityManager.detach(releaseInfo);
				releaseInfo.setId(null);
				releaseInfo.setStatus("Null");
				releaseInfo.setBuildId(releaseInfo.getBuildId() + 1);
				releaseInfoRepository.save(releaseInfo);
				nextBuild = releaseInfo.getPlanRelease().getMajorVersion() + "-" + releaseInfo.getPlanRelease().getMinorVersion() + "-" + releaseInfo.getPlanRelease().getIntegration() + "-" + (releaseInfo.getBuildId());
			} 
			else {
				nextBuild = releaseInfo.getPlanRelease().getMajorVersion() + "-" + releaseInfo.getPlanRelease().getMinorVersion() + "-" + releaseInfo.getPlanRelease().getIntegration() + "-" + (releaseInfo.getBuildId());
			}
	
		return nextBuild;
		
	}

	@Override
	public List<ReleaseInfo> getSuccessfulVersions(String project) {
		
		return releaseInfoRepository.findByProjectAndStatus(project, "Checked");
		
	}
	
}
