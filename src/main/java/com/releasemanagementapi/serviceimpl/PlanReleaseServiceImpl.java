package com.releasemanagementapi.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<?> addNewPlanRelease(PlanReleaseDTO request) {

		ModelMapper modelMapper = new ModelMapper();
		PlanRelease planRelease = modelMapper.map(request, PlanRelease.class);
		
		boolean exist = repo.existsByMajorVersionAndMinorVersionAndIntegration(planRelease.getMajorVersion(),planRelease.getMinorVersion(),planRelease.getIntegration());
		
		if (exist) {
			return new ResponseEntity("Plan release existe déjà", HttpStatus.FOUND);
		}
		
		PlanRelease result = repo.save(planRelease);
		return new ResponseEntity(result, HttpStatus.CREATED);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<?> prepareNextBuildRelease(String project) {

		ReleaseInfo releaseInfo = releaseInfoRepository.findTop1ByProjectOrderByBuildIdDesc(project);
		String nextBuild = "";

		if (releaseInfo == null) {
			return new ResponseEntity("Projet n'existe pas", HttpStatus.NOT_FOUND);
		}
		
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
		return new ResponseEntity(nextBuild, HttpStatus.OK);
	
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<?> getSuccessfulVersions(String project) {
		
		List<ReleaseInfo> releases = releaseInfoRepository.findByProjectAndStatus(project, "Checked");
		ReleaseInfo releaseInfo2 = releaseInfoRepository.findTop1ByProjectOrderByBuildIdDesc(project);
		List<String> list = new ArrayList<>();
		
		if (releaseInfo2 == null) {
			return new ResponseEntity("Projet n'existe pas", HttpStatus.NOT_FOUND);
		}
		
		for (ReleaseInfo releaseInfo : releases) {
			String successfulVersions = releaseInfo.getPlanRelease().getMajorVersion() + "-" + releaseInfo.getPlanRelease().getMinorVersion() + "-" + releaseInfo.getPlanRelease().getIntegration() + "-" + (releaseInfo.getBuildId());
			list.add(successfulVersions);
		}
		return new ResponseEntity(list, HttpStatus.OK);
	
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<?> getSuccessfulVersions2(String project) {
		
		List<ReleaseInfo> releases = releaseInfoRepository.findByProjectAndStatus(project, "Checked");
		ReleaseInfo releaseInfo2 = releaseInfoRepository.findTop1ByProjectOrderByBuildIdDesc(project);
		String successfulVersions ="";
		
		if (releaseInfo2 == null) {
			return new ResponseEntity("Projet n'existe pas", HttpStatus.NOT_FOUND);
		}
		
		for (ReleaseInfo releaseInfo : releases) {
			successfulVersions += releaseInfo.getPlanRelease().getMajorVersion() + "-" + releaseInfo.getPlanRelease().getMinorVersion() + "-" + releaseInfo.getPlanRelease().getIntegration() + "-" + (releaseInfo.getBuildId())+"\n";
		}
		return new ResponseEntity(successfulVersions, HttpStatus.OK);
		
	}

}