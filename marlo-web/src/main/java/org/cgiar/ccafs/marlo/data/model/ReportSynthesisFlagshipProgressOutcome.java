package org.cgiar.ccafs.marlo.data.model;
// Generated Jun 6, 2018 11:11:29 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * ReportSynthesisFlagshipProgressMilestone generated by hbm2java
 */
public class ReportSynthesisFlagshipProgressOutcome extends MarloBaseEntity implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = 2456861488209435207L;

  @Expose
  private CrpProgramOutcome crpProgramOutcome;

  @Expose
  private ReportSynthesisFlagshipProgress reportSynthesisFlagshipProgress;

  @Expose
  private String summary;

  private Set<ReportSynthesisFlagshipProgressOutcomeMilestone> reportSynthesisFlagshipProgressOutcomeMilestones =
    new HashSet<ReportSynthesisFlagshipProgressOutcomeMilestone>(0);


  private List<ReportSynthesisFlagshipProgressOutcomeMilestone> milestones;


  public ReportSynthesisFlagshipProgressOutcome() {
  }


  public CrpProgramOutcome getCrpProgramOutcome() {
    return crpProgramOutcome;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  public List<ReportSynthesisFlagshipProgressOutcomeMilestone> getMilestones() {
    return milestones;
  }

  @Override
  public String getModificationJustification() {
    return "";
  }


  @Override
  public User getModifiedBy() {
    User u = new User();
    u.setId(new Long(3));
    return u;
  }


  public ReportSynthesisFlagshipProgress getReportSynthesisFlagshipProgress() {
    return reportSynthesisFlagshipProgress;
  }


  public Set<ReportSynthesisFlagshipProgressOutcomeMilestone> getReportSynthesisFlagshipProgressOutcomeMilestones() {
    return reportSynthesisFlagshipProgressOutcomeMilestones;
  }


  public String getSummary() {
    return summary;
  }


  @Override
  public boolean isActive() {
    return true;
  }


  public void setCrpProgramOutcome(CrpProgramOutcome crpProgramOutcome) {
    this.crpProgramOutcome = crpProgramOutcome;
  }


  public void setMilestones(List<ReportSynthesisFlagshipProgressOutcomeMilestone> milestones) {
    this.milestones = milestones;
  }


  @Override
  public void setModifiedBy(User modifiedBy) {

  }


  public void setReportSynthesisFlagshipProgress(ReportSynthesisFlagshipProgress reportSynthesisFlagshipProgress) {
    this.reportSynthesisFlagshipProgress = reportSynthesisFlagshipProgress;
  }


  public void setReportSynthesisFlagshipProgressOutcomeMilestones(
    Set<ReportSynthesisFlagshipProgressOutcomeMilestone> reportSynthesisFlagshipProgressOutcomeMilestones) {
    this.reportSynthesisFlagshipProgressOutcomeMilestones = reportSynthesisFlagshipProgressOutcomeMilestones;
  }


  public void setSummary(String summary) {
    this.summary = summary;
  }

  @Override
  public String toString() {
    return "Id=" + this.getId() + "ReportSynthesisFlagshipProgressOutcome [reportSynthesisFlagshipProgress="
      + reportSynthesisFlagshipProgress + "]";
  }


}

