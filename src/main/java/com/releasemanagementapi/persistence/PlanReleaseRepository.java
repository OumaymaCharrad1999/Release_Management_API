package com.releasemanagementapi.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.releasemanagementapi.model.PlanRelease;

public interface PlanReleaseRepository extends JpaRepository<PlanRelease, Long> {

}