package com.releasemanagementapi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity  // This tells Hibernate to make a table out of this class
@Data
public class PlanRelease {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long planReleaseId;

	@Column(name = "MAJOR_VERSION")
	private Integer majorVersion;

	@Column(name = "MINOR_VERSION")
	private Integer minorVersion;

	@Column(name = "INTEGRATION")
	private Integer integration;
	
	@JsonIgnore
	@OneToMany(mappedBy = "planRelease")
	private List<ReleaseInfo> releaseInfos;

}