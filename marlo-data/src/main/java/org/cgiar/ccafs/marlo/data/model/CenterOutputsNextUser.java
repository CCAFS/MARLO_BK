package org.cgiar.ccafs.marlo.data.model;
// Generated Nov 30, 2016 9:52:32 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * CenterOutputsNextUser generated by hbm2java
 */
public class CenterOutputsNextUser implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = 4282051161983708196L;


  @Expose
  private Long id;


  @Expose
  private CenterOutput researchOutput;

  @Expose
  private CenterNextuserType nextuserType;

  @Expose
  private User modifiedBy;

  @Expose
  private User createdBy;

  @Expose
  private Date activeSince;

  @Expose
  private boolean active;

  @Expose
  private String modificationJustification;

  public CenterOutputsNextUser() {
  }

  public CenterOutputsNextUser(CenterOutput researchOutput, CenterNextuserType nextuserType) {
    this.researchOutput = researchOutput;
    this.nextuserType = nextuserType;
  }


  public CenterOutputsNextUser(CenterOutput researchOutput, CenterNextuserType nextuserType, User modifiedBy,
    User createdBy, Date activeSince, boolean active, String modificationJustification) {
    this.researchOutput = researchOutput;
    this.nextuserType = nextuserType;
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.activeSince = activeSince;
    this.active = active;
    this.modificationJustification = modificationJustification;
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
    CenterOutputsNextUser other = (CenterOutputsNextUser) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }


  public Date getActiveSince() {
    return activeSince;
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


  public CenterNextuserType getNextuserType() {
    return nextuserType;
  }

  public CenterOutput getResearchOutput() {
    return researchOutput;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
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

  public void setNextuserType(CenterNextuserType nextuserType) {
    this.nextuserType = nextuserType;
  }


  public void setResearchOutput(CenterOutput researchOutput) {
    this.researchOutput = researchOutput;
  }

}

