package com.releasemanagementapi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.releasemanagementapi.dto.PlanReleaseDTO;
import com.releasemanagementapi.model.PlanRelease;
import com.releasemanagementapi.model.ReleaseInfo;
import com.releasemanagementapi.service.PlanReleaseService;

@RestController
@RequestMapping("/plan-release-api")
public class PlanReleaseController {
	
	@Autowired
	private PlanReleaseService planReleaseService;
	
	@PostMapping // Map ONLY POST Requests
	public ResponseEntity<?> addNewPlanRelease(@RequestBody PlanReleaseDTO planReleaseDTO) {

		return planReleaseService.save(planReleaseDTO);

	}
	
	@GetMapping("/{project}")
	public String prepareNextBuild(@PathVariable String project) {
		
		return planReleaseService.prepareNextBuildRelease(project);
		
	}
	
	@GetMapping("/get-successful-versions/{project}")
	public List<String> getSuccessfulVersions(@PathVariable String project) {

		return planReleaseService.getSuccessfulVersions(project);

	}
	@GetMapping("/get-successful-versions2/{project}")
	public String getSuccessfulVersions2(@PathVariable String project) {

		return planReleaseService.getSuccessfulVersions2(project);

	}
	
}