package org.cgiar.ccafs.marlo.data.model;
// default package
// Generated Jun 9, 2017 10:44:23 AM by Hibernate Tools 3.4.0.CR1


import java.util.Date;

/**
 * CapdevDiscipline generated by hbm2java
 */
public class CapdevDiscipline implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Long id;
  private User usersByModifiedBy;
  private User usersByCreatedBy;
  private CapacityDevelopment capacityDevelopment;
  private Discipline disciplines;
  private boolean active;
  private Date activeSince;
  private String modificationJustification;

  public CapdevDiscipline() {
  }


  public CapdevDiscipline(boolean active, Date activeSince) {
    this.active = active;
    this.activeSince = activeSince;
  }

  public CapdevDiscipline(User usersByModifiedBy, User usersByCreatedBy, CapacityDevelopment capacityDevelopment,
    Discipline disciplines, boolean active, Date activeSince, String modificationJustification) {
    this.usersByModifiedBy = usersByModifiedBy;
    this.usersByCreatedBy = usersByCreatedBy;
    this.capacityDevelopment = capacityDevelopment;
    this.disciplines = disciplines;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }

  public Date getActiveSince() {
    return this.activeSince;
  }

  public CapacityDevelopment getCapacityDevelopment() {
    return this.capacityDevelopment;
  }

  public Discipline getDisciplines() {
    return this.disciplines;
  }


  public Long getId() {
    return this.id;
  }

  public String getModificationJustification() {
    return this.modificationJustification;
  }


  public User getUsersByCreatedBy() {
    return this.usersByCreatedBy;
  }

  public User getUsersByModifiedBy() {
    return this.usersByModifiedBy;
  }

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setCapacityDevelopment(CapacityDevelopment capacityDevelopment) {
    this.capacityDevelopment = capacityDevelopment;
  }

  public void setDisciplines(Discipline disciplines) {
    this.disciplines = disciplines;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


  public void setUsersByCreatedBy(User usersByCreatedBy) {
    this.usersByCreatedBy = usersByCreatedBy;
  }


  public void setUsersByModifiedBy(User usersByModifiedBy) {
    this.usersByModifiedBy = usersByModifiedBy;
  }


}

