package org.cgiar.ccafs.marlo.data.model;
// Generated Jun 6, 2018 11:11:29 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ReportSynthesisFlagshipProgressOutcomeMilestoneLink generated by hbm2java
 */
public class ReportSynthesisFlagshipProgressOutcomeMilestoneLink extends MarloAuditableEntity
  implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = 2932541945652137791L;

  @Expose
  private ReportSynthesisFlagshipProgressOutcomeMilestone reportSynthesisFlagshipProgressOutcomeMilestone;

  @Expose
  private String link;

  public ReportSynthesisFlagshipProgressOutcomeMilestoneLink() {
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }

    ReportSynthesisFlagshipProgressOutcomeMilestoneLink other =
      (ReportSynthesisFlagshipProgressOutcomeMilestoneLink) obj;

    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }

    return true;
  }

  public String getLink() {
    return link;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  public ReportSynthesisFlagshipProgressOutcomeMilestone getReportSynthesisFlagshipProgressOutcomeMilestone() {
    return reportSynthesisFlagshipProgressOutcomeMilestone;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public void setReportSynthesisFlagshipProgressOutcomeMilestone(
    ReportSynthesisFlagshipProgressOutcomeMilestone reportSynthesisFlagshipProgressOutcomeMilestone) {
    this.reportSynthesisFlagshipProgressOutcomeMilestone = reportSynthesisFlagshipProgressOutcomeMilestone;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("link", this.link)
      .append("reportSynthesisFlagshipProgressOutcomeMilestone", this.reportSynthesisFlagshipProgressOutcomeMilestone)
      .toString();
  }
}

