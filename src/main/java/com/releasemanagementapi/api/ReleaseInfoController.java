package com.releasemanagementapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.releasemanagementapi.service.ReleaseInfoService;

@RestController
@RequestMapping(path = "/release-management-api") // This means URL's start with /release-management (after Application
												// path)

public class ReleaseInfoController {
	@Autowired // This means to get the bean called releaseRepository which is auto-generated
				// by Spring
				// We will use it to handle the data
	private ReleaseInfoService releaseInfoService;

	@GetMapping("/{project}")
	public ResponseEntity<?> getByProject(@PathVariable String project) {
			
		return releaseInfoService.getByProject(project);

	}
	
	@PatchMapping("/{project}/{status}")
	public ResponseEntity<?> updateStatus(@PathVariable String project, @PathVariable String status) {

		return releaseInfoService.updateStatus(project, status);

	}

}