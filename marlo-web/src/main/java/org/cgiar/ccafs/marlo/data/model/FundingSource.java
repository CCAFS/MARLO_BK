package org.cgiar.ccafs.marlo.data.model;
// Generated Oct 27, 2016 3:39:29 PM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;

/**
 * FundingSource generated by hbm2java
 */
public class FundingSource implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -3854119067580692258L;


  @Expose
  private boolean active;

  @Expose
  private Date activeSince;


  private List<FundingSourceBudget> budgets;

  private List<FundingSourceInstitution> institutions;


  @Expose
  private BudgetType budgetType;


  @Expose
  private Integer centerType;


  @Expose
  private String contactPersonEmail;


  @Expose
  private String contactPersonName;

  @Expose
  private User createdBy;


  @Expose
  private Crp crp;


  @Expose
  private String description;


  @Expose
  private Date endDate;


  @Expose
  private FileDB file;

  @Expose
  private String financeCode;

  private Set<FundingSourceBudget> fundingSourceBudgets = new HashSet<FundingSourceBudget>(0);

  private Set<FundingSource> fundingSources = new HashSet<FundingSource>(0);


  private Set<FundingSourceInstitution> fundingSourceInstitutions = new HashSet<FundingSourceInstitution>(0);

  @Expose
  private Long id;


  @Expose
  private Institution institution;
  @Expose
  private Institution leader;


  @Expose
  private String modificationJustification;


  @Expose
  private User modifiedBy;


  private Set<ProjectBudget> projectBudgets = new HashSet<ProjectBudget>(0);

  private List<ProjectBudget> projectBudgetsList;

  @Expose
  private Date startDate;

  @Expose
  private Integer status;

  @Expose
  private String title;

  public FundingSource() {
  }

  public FundingSource(User modifiedBy, boolean active, Date activeSince, String modificationJustification) {
    this.modifiedBy = modifiedBy;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }

  public FundingSource(User modifiedBy, User createdBy, Institution institution, String description, Date startDate,
    Date endDate, String financeCode, String contactPersonName, String contactPersonEmail, Integer centerType,
    BudgetType type, boolean active, Date activeSince, String modificationJustification,
    Set<FundingSourceBudget> fundingSourceBudgets, Set<ProjectBudget> projectBudgets, Crp crp) {
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.institution = institution;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.financeCode = financeCode;
    this.contactPersonName = contactPersonName;
    this.contactPersonEmail = contactPersonEmail;
    this.centerType = centerType;
    this.budgetType = type;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
    this.fundingSourceBudgets = fundingSourceBudgets;
    this.projectBudgets = projectBudgets;
    this.crp = crp;
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
    FundingSource other = (FundingSource) obj;
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

  public List<FundingSourceBudget> getBudgets() {
    return budgets;
  }

  public BudgetType getBudgetType() {
    return budgetType;
  }

  public Integer getCenterType() {
    return centerType;
  }

  public String getComposedName() {
    try {
      return "<b> (F" + this.id + ") " + this.getBudgetType().getName() + "</b> - " + this.title;
    } catch (Exception e) {
      return "<b> (F" + this.id + ") </b> - " + this.title;
    }
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

  public String getDescription() {
    return description;
  }

  public Date getEndDate() {
    return endDate;
  }


  public FileDB getFile() {
    return file;
  }


  public String getFinanceCode() {
    return financeCode;
  }


  public Set<FundingSourceBudget> getFundingSourceBudgets() {
    return fundingSourceBudgets;
  }


  public Set<FundingSourceInstitution> getFundingSourceInstitutions() {
    return fundingSourceInstitutions;
  }


  public Set<FundingSource> getFundingSources() {
    return fundingSources;
  }

  @Override
  public Long getId() {
    return id;
  }

  public Institution getInstitution() {
    return institution;
  }

  public List<FundingSourceInstitution> getInstitutions() {
    return institutions;
  }

  public Institution getLeader() {
    return leader;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  @Override
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


  public List<ProjectBudget> getProjectBudgetsList() {
    return projectBudgetsList;
  }


  public double getRemaining(int year) {
    double used = 0;
    double total = 0;
    for (FundingSourceBudget fundingSourceBudget : this.getFundingSourceBudgets().stream()
      .filter(c -> c.isActive() && c.getYear() != null && c.getYear().intValue() == year)
      .collect(Collectors.toList())) {
      if (fundingSourceBudget.getBudget() != null) {
        total = total + fundingSourceBudget.getBudget().doubleValue();
      }

    }
    for (ProjectBudget projectBudget : this.getProjectBudgets().stream()
      .filter(c -> c.isActive() && c.getYear() == year).collect(Collectors.toList())) {
      used = used + projectBudget.getAmount().doubleValue();
    }
    return total - used;

  }


  public Date getStartDate() {
    return startDate;
  }


  public Integer getStatus() {
    return status;
  }


  public String getStatusName() {
    if (status != null && status.intValue() != -1) {
      AgreementStatusEnum statusEnum = AgreementStatusEnum.getValue(status.intValue());
      if (statusEnum != null) {
        return statusEnum.getStatus();
      }
    }
    return "";

  }


  public String getTitle() {
    return title;
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


  public void setBudgets(List<FundingSourceBudget> budgets) {
    this.budgets = budgets;
  }


  public void setBudgetType(BudgetType budgetType) {
    this.budgetType = budgetType;
  }


  public void setCenterType(Integer centerType) {
    this.centerType = centerType;
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

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setFile(FileDB file) {
    this.file = file;
  }

  public void setFinanceCode(String financeCode) {
    this.financeCode = financeCode;
  }

  public void setFundingSourceBudgets(Set<FundingSourceBudget> fundingSourceBudgets) {
    this.fundingSourceBudgets = fundingSourceBudgets;
  }

  public void setFundingSourceInstitutions(Set<FundingSourceInstitution> fundingSourceInstitutions) {
    this.fundingSourceInstitutions = fundingSourceInstitutions;
  }

  public void setFundingSources(Set<FundingSource> fundingSources) {
    this.fundingSources = fundingSources;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setInstitutions(List<FundingSourceInstitution> institutions) {
    this.institutions = institutions;
  }

  public void setLeader(Institution leader) {
    this.leader = leader;
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

  public void setProjectBudgetsList(List<ProjectBudget> projectBudgetsList) {
    this.projectBudgetsList = projectBudgetsList;
  }


  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setTitle(String title) {
    this.title = title;
  }


}

