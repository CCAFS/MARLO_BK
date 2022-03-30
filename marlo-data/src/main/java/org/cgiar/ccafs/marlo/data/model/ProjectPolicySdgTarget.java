package org.cgiar.ccafs.marlo.data.model;
// Generated Jan 31, 2019 9:21:00 AM by Hibernate Tools 5.3.6.Final

import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;


public class ProjectPolicySdgTarget extends MarloBaseEntity implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = 8026523460750859950L;

  @Expose
  private ProjectPolicy projectPolicy;

  @Expose
  private SdgTargets sdgTarget;
  @Expose
  private Phase phase;

  public ProjectPolicySdgTarget() {
  }

  public void copyFields(ProjectPolicySdgTarget other) {
    this.setPhase(other.getPhase());
    this.setProjectPolicy(other.getProjectPolicy());
    this.setSdgTarget(other.getSdgTarget());
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
    ProjectPolicySdgTarget other = (ProjectPolicySdgTarget) obj;
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

  public Phase getPhase() {
    return phase;
  }

  public ProjectPolicy getProjectPolicy() {
    return projectPolicy;
  }

  public SdgTargets getSdgTarget() {
    return sdgTarget;
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

  public void setPhase(Phase phase) {
    this.phase = phase;
  }

  public void setProjectPolicy(ProjectPolicy projectPolicy) {
    this.projectPolicy = projectPolicy;
  }

  public void setSdgTarget(SdgTargets sdgTarget) {
    this.sdgTarget = sdgTarget;
  }
}

