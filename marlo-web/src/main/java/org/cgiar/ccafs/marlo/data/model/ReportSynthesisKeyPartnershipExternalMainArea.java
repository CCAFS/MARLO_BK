package org.cgiar.ccafs.marlo.data.model;
// Generated Mar 7, 2019 7:42:16 AM by Hibernate Tools 5.3.6.Final

import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;

/**
 * ReportSynthesisKeyPartnershipExternalMainAreas generated by hbm2java
 */
public class ReportSynthesisKeyPartnershipExternalMainArea extends MarloBaseEntity
  implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = -6108083528060500202L;
  @Expose
  private RepIndPartnershipMainArea partnerArea;
  @Expose
  private ReportSynthesisKeyPartnershipExternal reportSynthesisKeyPartnershipExternal;


  public ReportSynthesisKeyPartnershipExternalMainArea() {
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    ReportSynthesisKeyPartnershipExternalMainArea other = (ReportSynthesisKeyPartnershipExternalMainArea) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
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


  public RepIndPartnershipMainArea getPartnerArea() {
    return partnerArea;
  }

  public ReportSynthesisKeyPartnershipExternal getReportSynthesisKeyPartnershipExternal() {
    return reportSynthesisKeyPartnershipExternal;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }

  @Override
  public boolean isActive() {
    return true;
  }


  @Override
  public void setModifiedBy(User modifiedBy) {

  }


  public void setPartnerArea(RepIndPartnershipMainArea partnerArea) {
    this.partnerArea = partnerArea;
  }


  public void setReportSynthesisKeyPartnershipExternal(
    ReportSynthesisKeyPartnershipExternal reportSynthesisKeyPartnershipExternal) {
    this.reportSynthesisKeyPartnershipExternal = reportSynthesisKeyPartnershipExternal;
  }


}

