package org.cgiar.ccafs.marlo.data.model;
// Generated Nov 24, 2016 11:37:14 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * CenterBeneficiary generated by hbm2java
 */
public class CenterBeneficiary implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = 6215788474686902391L;


  @Expose
  private Long id;


  @Expose
  private User modifiedBy;

  @Expose
  private User createdBy;

  @Expose
  private CenterBeneficiaryType beneficiaryType;

  @Expose
  private String name;

  @Expose
  private boolean active;

  @Expose
  private Date activeSince;

  @Expose
  private String modificationJustification;

  private Set<CenterImpactBeneficiary> researchImpactBeneficiaries = new HashSet<CenterImpactBeneficiary>(0);

  public CenterBeneficiary() {
  }


  public CenterBeneficiary(CenterBeneficiaryType beneficiaryType, String name, boolean active) {
    this.beneficiaryType = beneficiaryType;
    this.name = name;
    this.active = active;
  }


  public CenterBeneficiary(User modifiedBy, User createdBy, CenterBeneficiaryType beneficiaryType, String name, boolean isActive,
    Date activeSince, String modificationJustification, Set<CenterImpactBeneficiary> researchImpactBeneficiaries) {
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.beneficiaryType = beneficiaryType;
    this.name = name;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
    this.researchImpactBeneficiaries = researchImpactBeneficiaries;
  }


  public Date getActiveSince() {
    return activeSince;
  }


  public CenterBeneficiaryType getBeneficiaryType() {
    return beneficiaryType;
  }


  public User getCreatedBy() {
    return createdBy;
  }


  @Override
  public Long getId() {
    return id;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  public String getModificationJustification() {
    return modificationJustification;
  }


  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public String getName() {
    return name;
  }

  public Set<CenterImpactBeneficiary> getResearchImpactBeneficiaries() {
    return researchImpactBeneficiaries;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setBeneficiaryType(CenterBeneficiaryType beneficiaryType) {
    this.beneficiaryType = beneficiaryType;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setName(String name) {
    this.name = name;
  }


  public void setResearchImpactBeneficiaries(Set<CenterImpactBeneficiary> researchImpactBeneficiaries) {
    this.researchImpactBeneficiaries = researchImpactBeneficiaries;
  }


}

