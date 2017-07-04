package org.cgiar.ccafs.marlo.data.model;
// Generated Dec 2, 2016 11:02:00 AM by Hibernate Tools 3.4.0.CR1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CenterCycle generated by hbm2java
 */
public class CenterCycle implements java.io.Serializable {


  private static final long serialVersionUID = -8464775969513955058L;


  private Long id;

  private User modifiedBy;


  private User createdBy;


  private String name;


  private String acronym;


  private boolean active;


  private String modificationJustification;


  private Date activeSince;


  private Set<CenterSubmission> submissions = new HashSet<CenterSubmission>(0);


  public CenterCycle() {
  }


  public CenterCycle(boolean active) {
    this.active = active;
  }


  public CenterCycle(User modifiedBy, User createdBy, String name, String acronym, boolean active,
    String modificationJustification, Date activeSince, Set<CenterSubmission> submissions) {
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.name = name;
    this.acronym = acronym;
    this.active = active;
    this.modificationJustification = modificationJustification;
    this.activeSince = activeSince;
    this.submissions = submissions;
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
    CenterCycle other = (CenterCycle) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }


  public String getAcronym() {
    return acronym;
  }


  public Date getActiveSince() {
    return activeSince;
  }


  public User getCreatedBy() {
    return createdBy;
  }


  public Long getId() {
    return id;
  }


  public String getModificationJustification() {
    return modificationJustification;
  }


  public User getModifiedBy() {
    return modifiedBy;
  }


  public String getName() {
    return name;
  }

  public Set<CenterSubmission> getSubmissions() {
    return submissions;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public boolean isActive() {
    return active;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
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


  public void setName(String name) {
    this.name = name;
  }

  public void setSubmissions(Set<CenterSubmission> submissions) {
    this.submissions = submissions;
  }

}

