package org.cgiar.ccafs.marlo.data.model;
// Generated Mar 2, 2017 3:32:46 PM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * CenterProjectCrosscutingTheme generated by hbm2java
 */
public class CenterProjectCrosscutingTheme implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -742468038373623053L;


  @Expose
  private Long id;


  @Expose
  private User modifiedBy;


  private CenterProject project;


  @Expose
  private User createdBy;


  @Expose
  private Boolean climateChange;


  @Expose
  private Boolean gender;


  @Expose
  private Boolean youth;


  @Expose
  private Boolean policiesInstitutions;


  @Expose
  private Boolean capacityDevelopment;

  @Expose
  private Boolean bigData;

  @Expose
  private Boolean na;

  @Expose
  private boolean active;

  @Expose
  private Date activeSince;

  @Expose
  private Boolean impactAssessment;

  @Expose
  private String modificationJustification;

  public CenterProjectCrosscutingTheme() {
  }

  public CenterProjectCrosscutingTheme(CenterProject project, boolean active) {
    this.project = project;
    this.active = active;
  }

  public CenterProjectCrosscutingTheme(User createdBy, CenterProject project, User modifiedBy, Boolean climateChange,
    Boolean gender, Boolean youth, Boolean policiesInstitutions, Boolean capacityDevelopment, Boolean bigData,
    boolean active, Date activeSince, String modificationJustification) {
    this.modifiedBy = modifiedBy;
    this.project = project;
    this.createdBy = createdBy;
    this.climateChange = climateChange;
    this.gender = gender;
    this.youth = youth;
    this.policiesInstitutions = policiesInstitutions;
    this.capacityDevelopment = capacityDevelopment;
    this.bigData = bigData;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }

  public Date getActiveSince() {
    return activeSince;
  }

  public Boolean getBigData() {
    return bigData;
  }

  public Boolean getCapacityDevelopment() {
    return capacityDevelopment;
  }

  public Boolean getClimateChange() {
    return climateChange;
  }

  public User getCreatedBy() {
    return createdBy;
  }


  public Boolean getGender() {
    return gender;
  }


  @Override
  public Long getId() {
    return id;
  }


  public Boolean getImpactAssessment() {
    return impactAssessment;
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


  public Boolean getNa() {
    return na;
  }


  public Boolean getPoliciesInstitutions() {
    return policiesInstitutions;
  }


  public CenterProject getProject() {
    return project;
  }


  public Boolean getYouth() {
    return youth;
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

  public void setBigData(Boolean bigData) {
    this.bigData = bigData;
  }

  public void setCapacityDevelopment(Boolean capacityDevelopment) {
    this.capacityDevelopment = capacityDevelopment;
  }

  public void setClimateChange(Boolean climateChange) {
    this.climateChange = climateChange;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setGender(Boolean gender) {
    this.gender = gender;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setImpactAssessment(Boolean impactAssessment) {
    this.impactAssessment = impactAssessment;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setNa(Boolean na) {
    this.na = na;
  }


  public void setPoliciesInstitutions(Boolean policiesInstitutions) {
    this.policiesInstitutions = policiesInstitutions;
  }

  public void setProject(CenterProject project) {
    this.project = project;
  }


  public void setYouth(Boolean youth) {
    this.youth = youth;
  }


}

