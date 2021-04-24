package com.releasemanagementapi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.releasemanagementapi.model.ReleaseInfo;
import com.releasemanagementapi.persistence.ReleaseInfoRepository;
import com.releasemanagementapi.service.ReleaseInfoService;

/**
 * 
 * @author this service used for to manage releases
 */

@Service
public class ReleaseInfoServiceImpl implements ReleaseInfoService {

	@Autowired
	ReleaseInfoRepository repo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<?> getByProject(String project) {
		
		ReleaseInfo releaseInfo = repo.findTop1ByProjectOrderByBuildIdDesc(project);
		List<ReleaseInfo> releases = repo.findByProject(project);
		String getVersions = "";
		
		if (releaseInfo == null) {
			return new ResponseEntity("Projet n'existe pas", HttpStatus.NOT_FOUND);
		}
		
		for (ReleaseInfo releaseInfo1 : releases) {
			getVersions += releaseInfo1.getPlanRelease().getMajorVersion() + "-" + releaseInfo1.getPlanRelease().getMinorVersion() + "-" + releaseInfo1.getPlanRelease().getIntegration() + "-" + releaseInfo1.getBuildId() + "     " + releaseInfo1.getStatus() + "\n";
		}
		return new ResponseEntity(getVersions, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<?> getStatus(String project) {
		
		ReleaseInfo releaseInfo = repo.findTop1ByProjectOrderByBuildIdDesc(project);

		if (releaseInfo == null) {
			return new ResponseEntity("Projet n'existe pas", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity(releaseInfo.getStatus(), HttpStatus.OK);
		
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<?> updateStatus(String project, String status) {

		ReleaseInfo releaseInfo = repo.findTop1ByProjectOrderByBuildIdDesc(project);

		if (releaseInfo == null) {
			return new ResponseEntity("Projet n'existe pas", HttpStatus.NOT_FOUND);
		}

		releaseInfo.setStatus(status);
		ReleaseInfo result = repo.save(releaseInfo);
		return new ResponseEntity(result, HttpStatus.OK);

	}

}