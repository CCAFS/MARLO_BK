package org.cgiar.ccafs.marlo.data.model;
// Generated Sep 6, 2016 2:19:19 PM by Hibernate Tools 4.3.1.Final


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * ProjectBilateralCofinancing generated by hbm2java
 */
public class ProjectBilateralCofinancing implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = -5725787908570331052L;


  @Expose
  private Long id;


  @Expose
  private Institution institution;


  @Expose
  private LiaisonInstitution liaisonInstitution;


  @Expose
  private User createdBy;


  @Expose
  private User modifiedBy;


  @Expose
  private String title;


  @Expose
  private Date startDate;


  @Expose
  private Date endDate;
  @Expose
  private Integer agreement;
  @Expose
  private boolean active;
  @Expose
  private Date activeSince;
  @Expose
  private String modificationJustification;
  @Expose
  private Long budget;
  @Expose
  private String fincanceCode;
  @Expose
  private String contactPersonName;
  @Expose
  private String contactPersonEmail;
  private Crp crp;
  private Set<ProjectBudget> projectBudgets = new HashSet<ProjectBudget>(0);

  public Date getActiveSince() {
    return activeSince;
  }

  public Integer getAgreement() {
    return agreement;
  }

  public Long getBudget() {
    return budget;
  }

  public String getContactPersonEmail() {
    return contactPersonEmail;
  }

  public String getContactPersonName() {
    return contactPersonName;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public Crp getCrp() {
    return crp;
  }

  public Date getEndDate() {
    return endDate;
  }


  public String getFincanceCode() {
    return fincanceCode;
  }


  @Override
  public Long getId() {
    return id;
  }


  public Institution getInstitution() {
    return institution;
  }


  public LiaisonInstitution getLiaisonInstitution() {
    return liaisonInstitution;
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

  public Set<ProjectBudget> getProjectBudgets() {
    return projectBudgets;
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getTitle() {
    return title;
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

  public void setAgreement(Integer agreement) {
    this.agreement = agreement;
  }

  public void setBudget(Long budget) {
    this.budget = budget;
  }

  public void setContactPersonEmail(String contactPersonEmail) {
    this.contactPersonEmail = contactPersonEmail;
  }

  public void setContactPersonName(String contactPersonName) {
    this.contactPersonName = contactPersonName;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setCrp(Crp crp) {
    this.crp = crp;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setFincanceCode(String fincanceCode) {
    this.fincanceCode = fincanceCode;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setLiaisonInstitution(LiaisonInstitution liaisonInstitution) {
    this.liaisonInstitution = liaisonInstitution;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setProjectBudgets(Set<ProjectBudget> projectBudgets) {
    this.projectBudgets = projectBudgets;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }


  public void setTitle(String title) {
    this.title = title;
  }

}

