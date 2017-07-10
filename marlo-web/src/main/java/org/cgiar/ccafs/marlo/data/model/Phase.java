package org.cgiar.ccafs.marlo.data.model;
// Generated Apr 11, 2017 9:08:14 AM by Hibernate Tools 4.3.1.Final


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * Phases generated by hbm2java
 */
public class Phase implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = -1603080182029677862L;
  @Expose
  private Long id;
  private Crp crp;
  @Expose
  private String description;
  @Expose
  private int year;
  @Expose
  private Boolean editable;
  @Expose
  private Boolean visible;
  @Expose
  private Phase next;


  private Set<ProjectPhase> projectPhases = new HashSet<ProjectPhase>(0);
  private Set<ProjectInfo> projectInfos = new HashSet<ProjectInfo>(0);
  private Set<ProjectFocus> projectFocuses = new HashSet<ProjectFocus>(0);
  private Set<ProjectClusterActivity> projectClusters = new HashSet<ProjectClusterActivity>(0);
  private Set<ProjectPartner> partners = new HashSet<ProjectPartner>(0);


  public Phase() {
  }


  public Phase(Crp crp, String description, int year) {
    this.crp = crp;
    this.description = description;
    this.year = year;
  }


  public Phase(Crp crp, String description, int year, Set<ProjectPhase> projectPhases) {
    this.crp = crp;
    this.description = description;
    this.year = year;
    this.projectPhases = projectPhases;
  }


  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }

    Phase other = (Phase) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }


  public String getComposedName() {
    return this.description + " - " + year;
  }


  public Crp getCrp() {
    return this.crp;
  }


  public String getDescription() {
    return this.description;
  }


  public Boolean getEditable() {
    return editable;
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


  public Phase getNext() {
    return next;
  }

  public Set<ProjectPartner> getPartners() {
    return partners;
  }


  public Set<ProjectClusterActivity> getProjectClusters() {
    return projectClusters;
  }

  public Set<ProjectFocus> getProjectFocuses() {
    return projectFocuses;
  }

  public Set<ProjectInfo> getProjectInfos() {
    return projectInfos;
  }


  public Set<ProjectPhase> getProjectPhases() {
    return this.projectPhases;
  }

  public Boolean getVisible() {
    return visible;
  }

  public int getYear() {
    return this.year;
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

    return true;
  }

  public void setCrp(Crp crp) {
    this.crp = crp;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEditable(Boolean editable) {
    this.editable = editable;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setNext(Phase next) {
    this.next = next;
  }

  public void setPartners(Set<ProjectPartner> partners) {
    this.partners = partners;
  }

  public void setProjectClusters(Set<ProjectClusterActivity> projectClusters) {
    this.projectClusters = projectClusters;
  }

  public void setProjectFocuses(Set<ProjectFocus> projectFocuses) {
    this.projectFocuses = projectFocuses;
  }

  public void setProjectInfos(Set<ProjectInfo> projectInfos) {
    this.projectInfos = projectInfos;
  }

  public void setProjectPhases(Set<ProjectPhase> projectPhases) {
    this.projectPhases = projectPhases;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public void setYear(int year) {
    this.year = year;
  }
}

