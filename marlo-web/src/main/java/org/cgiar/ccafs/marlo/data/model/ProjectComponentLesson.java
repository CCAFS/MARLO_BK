package org.cgiar.ccafs.marlo.data.model;
// Generated Aug 4, 2016 11:18:23 AM by Hibernate Tools 4.3.1.Final


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * ProjectComponentLesson generated by hbm2java
 */
public class ProjectComponentLesson implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = -2716216544705498218L;


  @Expose
  private Long id;

  @Expose
  private Project project;
  @Expose
  private User modifiedBy;
  @Expose
  private User createdBy;
  @Expose
  private String componentName;
  @Expose
  private String lessons;
  @Expose
  private int year;
  @Expose
  private boolean active;
  @Expose
  private Date activeSince;
  @Expose
  private IpProgram ipProgram;
  @Expose
  private String modificationJustification;
  @Expose
  private String cycle;

  @Expose
  private ProjectOutcome projectOutcome;

  public ProjectComponentLesson() {
  }


  public ProjectComponentLesson(Project project, User usersByModifiedBy, User usersByCreatedBy, String componentName,
    String lessons, int year, boolean isActive, Date activeSince, String modificationJustification, String cycle) {
    this.project = project;
    this.modifiedBy = usersByModifiedBy;
    this.createdBy = usersByCreatedBy;
    this.componentName = componentName;
    this.lessons = lessons;
    this.year = year;
    this.active = isActive;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
    this.cycle = cycle;
  }


  public ProjectComponentLesson(User usersByModifiedBy, User usersByCreatedBy, String componentName, int year,
    boolean isActive, Date activeSince, String modificationJustification) {
    this.modifiedBy = usersByModifiedBy;
    this.createdBy = usersByCreatedBy;
    this.componentName = componentName;
    this.year = year;
    this.active = isActive;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }

  public Date getActiveSince() {
    return this.activeSince;
  }

  public String getComponentName() {
    return this.componentName;
  }

  public User getCreatedBy() {
    return this.createdBy;
  }

  public String getCycle() {
    return this.cycle;
  }

  @Override
  public Long getId() {
    return this.id;
  }

  public IpProgram getIpProgram() {
    return ipProgram;
  }


  public String getLessons() {
    return this.lessons;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();

    sb.append("Id : ").append(this.getId());


    return sb.toString();
  }

  @Override
  public String getModificationJustification() {
    return this.modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return this.modifiedBy;
  }

  public Project getProject() {
    return project;
  }

  public ProjectOutcome getProjectOutcome() {
    return projectOutcome;
  }

  public int getYear() {
    return this.year;
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


  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }

  public void setCreatedBy(User usersByCreatedBy) {
    this.createdBy = usersByCreatedBy;
  }

  public void setCycle(String cycle) {
    this.cycle = cycle;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setIpProgram(IpProgram ipProgram) {
    this.ipProgram = ipProgram;
  }

  public void setLessons(String lessons) {
    this.lessons = lessons;
  }


  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User usersByModifiedBy) {
    this.modifiedBy = usersByModifiedBy;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectOutcome(ProjectOutcome projectOutcome) {
    this.projectOutcome = projectOutcome;
  }


  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return id.toString();
  }


}

