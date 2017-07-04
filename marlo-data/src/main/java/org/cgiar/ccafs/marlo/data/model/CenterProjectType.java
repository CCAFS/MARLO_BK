package org.cgiar.ccafs.marlo.data.model;
// Generated May 23, 2017 1:16:41 PM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * CenterProjectType generated by hbm2java
 */
public class CenterProjectType implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -6666681595172502767L;


  @Expose
  private Long id;

  @Expose
  private User modifiedBy;

  @Expose
  private User createdBy;

  @Expose
  private String name;

  @Expose
  private boolean active;

  @Expose
  private Date activeSice;

  @Expose
  private String modificationJustification;

  public CenterProjectType() {
  }

  public CenterProjectType(Long id, User modifiedBy, User createdBy, String name, boolean active, Date activeSice,
    String modificationJustification) {
    super();
    this.id = id;
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.name = name;
    this.active = active;
    this.activeSice = activeSice;
    this.modificationJustification = modificationJustification;
  }


  public Date getActiveSice() {
    return activeSice;
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

  @Override
  public boolean isActive() {
    return active;
  }


  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSice(Date activeSice) {
    this.activeSice = activeSice;
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


}

