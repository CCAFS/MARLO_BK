package org.cgiar.ccafs.marlo.data.model;
// Generated Aug 22, 2016 10:31:13 AM by Hibernate Tools 4.3.1.Final


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * ProjectCommunication generated by hbm2java
 */
public class ProjectCommunication implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = -8633549958796027690L;
  @Expose
  private Long id;
  @Expose
  private FileDB file;
  @Expose
  private ProjectOutcome projectOutcome;
  @Expose
  private User modifiedBy;
  @Expose
  private User createdBy;
  @Expose
  private String communication;
  @Expose
  private boolean active;
  @Expose
  private Date activeSince;
  @Expose
  private String modificationJustification;
  @Expose
  private int year;


  public ProjectCommunication() {
  }


  public ProjectCommunication(FileDB file, ProjectOutcome projectOutcome, User usersByModifiedBy, User usersByCreatedBy,
    String communication, boolean isActive, Date activeSince, String modificationJustification) {
    this.file = file;
    this.projectOutcome = projectOutcome;
    this.modifiedBy = usersByModifiedBy;
    this.createdBy = usersByCreatedBy;
    this.communication = communication;
    this.active = isActive;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }


  public ProjectCommunication(ProjectOutcome projectOutcome, User usersByModifiedBy, User usersByCreatedBy,
    String communication, boolean isActive, Date activeSince, String modificationJustification) {
    this.projectOutcome = projectOutcome;
    this.modifiedBy = usersByModifiedBy;
    this.createdBy = usersByCreatedBy;
    this.communication = communication;
    this.active = isActive;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }


  public Date getActiveSince() {
    return this.activeSince;
  }

  public String getCommunication() {
    return this.communication;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public FileDB getFile() {
    return file;
  }


  @Override
  public Long getId() {
    return this.id;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();

    sb.append("Id : ").append(this.getId());


    return sb.toString();
  }


  public String getModificationJustification() {
    return this.modificationJustification;
  }


  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }


  public ProjectOutcome getProjectOutcome() {
    return projectOutcome;
  }


  public int getYear() {
    return year;
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


  public void setCommunication(String communication) {
    this.communication = communication;
  }


  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setFile(FileDB file) {
    this.file = file;
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


  public void setProjectOutcome(ProjectOutcome projectOutcome) {
    this.projectOutcome = projectOutcome;
  }


  public void setYear(int year) {
    this.year = year;
  }


}

