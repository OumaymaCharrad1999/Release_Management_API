package com.releasemanagementapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity // This tells Hibernate to make a table out of this class
@Data
public class ReleaseInfo {
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Long id;
	
	  @Column(name = "PROJECT")
	  private String project;
	  
	  @Column(name = "BUILD_ID")
	  private Integer buildId;
	  
	  @Column(name = "STATUS")
	  private String status;
	  
	  @Column(name = "DESCRIPTION")
	  private String description;
	 
	  @ManyToOne
	  @JoinColumn(name = "PLAN_RELEASE_ID")
	  private PlanRelease planRelease;

}