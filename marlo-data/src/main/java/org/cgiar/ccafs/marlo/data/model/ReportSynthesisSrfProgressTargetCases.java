package org.cgiar.ccafs.marlo.data.model;
// Generated Feb 27, 2019 2:52:09 PM by Hibernate Tools 5.3.6.Final


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * ReportSynthesisSrfProgressTargets generated by hbm2java
 */
public class ReportSynthesisSrfProgressTargetCases extends MarloAuditableEntity
  implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = 1867695696627239380L;

  @Expose
  private ReportSynthesisSrfProgress reportSynthesisSrfProgress;

  @Expose
  private SrfSloIndicatorTarget srfSloIndicatorTarget;


  @Expose
  private String briefSummary;


  @Expose
  private String additionalContribution;


  @Expose
  private Boolean isContributing;

  private Set<ProgressTargetCaseGeographicScope> progressTargetCaseGeographicScopes =
    new HashSet<ProgressTargetCaseGeographicScope>(0);
  private List<ProgressTargetCaseGeographicScope> geographicScopes;
  private Set<ProgressTargetCaseGeographicRegion> progressTargetCaseGeographicRegions =
    new HashSet<ProgressTargetCaseGeographicRegion>(0);
  private List<ProgressTargetCaseGeographicRegion> geographicRegions;

  public ReportSynthesisSrfProgressTargetCases() {
  }


  public String getAdditionalContribution() {
    return additionalContribution;
  }


  public String getBriefSummary() {
    return briefSummary;
  }


  public List<ProgressTargetCaseGeographicRegion> getGeographicRegions() {
    return geographicRegions;
  }


  public List<ProgressTargetCaseGeographicScope> getGeographicScopes() {
    return geographicScopes;
  }


  public Boolean getIsContributing() {
    return isContributing;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }


  public Set<ProgressTargetCaseGeographicRegion> getProgressTargetCaseGeographicRegions() {
    return progressTargetCaseGeographicRegions;
  }

  public Set<ProgressTargetCaseGeographicScope> getProgressTargetCaseGeographicScopes() {
    return progressTargetCaseGeographicScopes;
  }

  public ReportSynthesisSrfProgress getReportSynthesisSrfProgress() {
    return reportSynthesisSrfProgress;
  }

  public SrfSloIndicatorTarget getSrfSloIndicatorTarget() {
    return srfSloIndicatorTarget;
  }

  public void setAdditionalContribution(String additionalContribution) {
    this.additionalContribution = additionalContribution;
  }

  public void setBriefSummary(String briefSummary) {
    this.briefSummary = briefSummary;
  }

  public void setGeographicRegions(List<ProgressTargetCaseGeographicRegion> geographicRegions) {
    this.geographicRegions = geographicRegions;
  }

  public void setGeographicScopes(List<ProgressTargetCaseGeographicScope> geographicScopes) {
    this.geographicScopes = geographicScopes;
  }

  public void setIsContributing(Boolean isContributing) {
    this.isContributing = isContributing;
  }

  public void setProgressTargetCaseGeographicRegions(
    Set<ProgressTargetCaseGeographicRegion> progressTargetCaseGeographicRegions) {
    this.progressTargetCaseGeographicRegions = progressTargetCaseGeographicRegions;
  }

  public void
    setProgressTargetCaseGeographicScopes(Set<ProgressTargetCaseGeographicScope> progressTargetCaseGeographicScopes) {
    this.progressTargetCaseGeographicScopes = progressTargetCaseGeographicScopes;
  }

  public void setReportSynthesisSrfProgress(ReportSynthesisSrfProgress reportSynthesisSrfProgress) {
    this.reportSynthesisSrfProgress = reportSynthesisSrfProgress;
  }

  public void setSrfSloIndicatorTarget(SrfSloIndicatorTarget srfSloIndicatorTarget) {
    this.srfSloIndicatorTarget = srfSloIndicatorTarget;
  }

}

