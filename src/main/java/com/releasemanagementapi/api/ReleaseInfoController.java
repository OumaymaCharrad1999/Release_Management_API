package com.releasemanagementapi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.releasemanagementapi.model.ReleaseInfo;
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
	public List<ReleaseInfo> getByProject(@PathVariable String project) {

		return releaseInfoService.getByProject(project);

	}
	
	@PatchMapping("/{project}/{status}")
	public ReleaseInfo updateStatus(@PathVariable String project, @PathVariable String status) {

		try {
			return releaseInfoService.updateStatus(project, status);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

}
