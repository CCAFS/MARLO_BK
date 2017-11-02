package org.cgiar.ccafs.marlo.data.model;
// Generated Apr 11, 2017 9:08:14 AM by Hibernate Tools 4.3.1.Final


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.HashSet;
import java.util.Set;

/**
 * Phases generated by hbm2java
 */
public class Phase implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = -1603080182029677862L;


  private Long id;

  private GlobalUnit crp;


  private String description;


  private int year;
  private Set<ProjectPhase> projectPhases = new HashSet<ProjectPhase>(0);

  public Phase() {
  }

  public Phase(GlobalUnit crp, String description, int year) {
    this.crp = crp;
    this.description = description;
    this.year = year;
  }

  public Phase(GlobalUnit crp, String description, int year, Set<ProjectPhase> projectPhases) {
    this.crp = crp;
    this.description = description;
    this.year = year;
    this.projectPhases = projectPhases;
  }

  public GlobalUnit getCrp() {
    return crp;
  }

  public String getDescription() {
    return this.description;
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

  public Set<ProjectPhase> getProjectPhases() {
    return this.projectPhases;
  }

  public int getYear() {
    return this.year;
  }

  @Override
  public boolean isActive() {

    return true;
  }

  public void setCrp(GlobalUnit crp) {
    this.crp = crp;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public void setId(Long id) {
    this.id = id;
  }

  public void setProjectPhases(Set<ProjectPhase> projectPhases) {
    this.projectPhases = projectPhases;
  }

  public void setYear(int year) {
    this.year = year;
  }
}

