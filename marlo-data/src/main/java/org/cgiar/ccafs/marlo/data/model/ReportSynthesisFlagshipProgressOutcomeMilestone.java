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
public class ReportSynthesisFlagshipProgressOutcomeMilestone extends MarloAuditableEntity
  implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = 2932541945652137791L;

  @Expose
  private CrpMilestone crpMilestone;

  @Expose
  private ReportSynthesisFlagshipProgressOutcome reportSynthesisFlagshipProgressOutcome;

  @Expose
  private GeneralStatus milestonesStatus;

  @Expose
  private String evidence;

  private Set<ReportSynthesisFlagshipProgressCrossCuttingMarker> reportSynthesisFlagshipProgressCrossCuttingMarkers =
    new HashSet<ReportSynthesisFlagshipProgressCrossCuttingMarker>(0);

  List<ReportSynthesisFlagshipProgressCrossCuttingMarker> markers;

  @Expose
  private RepIndMilestoneReason reason;

  @Expose
  private String otherReason;


  public ReportSynthesisFlagshipProgressOutcomeMilestone() {
  }


  public CrpMilestone getCrpMilestone() {
    return this.crpMilestone;
  }


  public String getEvidence() {
    return this.evidence;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }


  public List<ReportSynthesisFlagshipProgressCrossCuttingMarker> getMarkers() {
    return this.markers;
  }


  public GeneralStatus getMilestonesStatus() {
    return milestonesStatus;
  }

  public String getOtherReason() {
    return this.otherReason;
  }


  public RepIndMilestoneReason getReason() {
    return this.reason;
  }

  public Set<ReportSynthesisFlagshipProgressCrossCuttingMarker>
    getReportSynthesisFlagshipProgressCrossCuttingMarkers() {
    return this.reportSynthesisFlagshipProgressCrossCuttingMarkers;
  }


  public ReportSynthesisFlagshipProgressOutcome getReportSynthesisFlagshipProgressOutcome() {
    return this.reportSynthesisFlagshipProgressOutcome;
  }


  public String getStatusName() {

    if (this.getMilestonesStatus() == null) {
      return "";
    } else {
      switch (this.getMilestonesStatus().getId().intValue()) {
        case 3:
          return "Complete";

        case 4:
          return "Extended";

        case 5:
          return "Cancelled";

        case 6:
          return "Changed";
      }
    }
    return "";
  }


  public void setCrpMilestone(CrpMilestone crpMilestone) {
    this.crpMilestone = crpMilestone;
  }


  public void setEvidence(String evidence) {
    this.evidence = evidence;
  }


  public void setMarkers(List<ReportSynthesisFlagshipProgressCrossCuttingMarker> markers) {
    this.markers = markers;
  }


  public void setMilestonesStatus(GeneralStatus milestonesStatus) {
    this.milestonesStatus = milestonesStatus;
  }


  public void setOtherReason(String otherReason) {
    this.otherReason = otherReason;
  }


  public void setReason(RepIndMilestoneReason reason) {
    this.reason = reason;
  }


  public void setReportSynthesisFlagshipProgressCrossCuttingMarkers(
    Set<ReportSynthesisFlagshipProgressCrossCuttingMarker> reportSynthesisFlagshipProgressCrossCuttingMarkers) {
    this.reportSynthesisFlagshipProgressCrossCuttingMarkers = reportSynthesisFlagshipProgressCrossCuttingMarkers;
  }


  public void setReportSynthesisFlagshipProgressOutcome(
    ReportSynthesisFlagshipProgressOutcome reportSynthesisFlagshipProgressOutcome) {
    this.reportSynthesisFlagshipProgressOutcome = reportSynthesisFlagshipProgressOutcome;
  }


}

