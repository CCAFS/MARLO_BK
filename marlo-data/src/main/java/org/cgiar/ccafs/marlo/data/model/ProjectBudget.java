/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.marlo.data.model;


import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;

/**
 * @author hjimenez
 */
public class ProjectBudget extends MarloAuditableEntity implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = -6117852720583204865L;

  @Expose
  private Institution institution;


  private Project project;

  @Expose
  private Double amount;
  @Expose
  private BudgetType budgetType;
  @Expose
  private int year;
  @Expose
  private Double genderPercentage;
  @Expose
  private Double genderValue;
  @Expose
  private FundingSource fundingSource;
  @Expose
  private String rationale;
  @Expose
  private Phase phase;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }

    ProjectBudget other = (ProjectBudget) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }

  public Double getAmount() {
    return amount;
  }

  public BudgetType getBudgetType() {
    return budgetType;
  }

  public FundingSource getFundingSource() {
    return fundingSource;
  }

  public Double getGenderPercentage() {
    return genderPercentage;
  }

  public Double getGenderValue() {
    return genderValue;
  }

  public Institution getInstitution() {
    return institution;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  public Phase getPhase() {
    return phase;
  }

  public Project getProject() {
    return project;
  }

  public String getRationale() {
    return rationale;
  }


  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }


  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public void setBudgetType(BudgetType budgetType) {
    this.budgetType = budgetType;
  }

  public void setFundingSource(FundingSource fundingSource) {
    this.fundingSource = fundingSource;
  }

  public void setGenderPercentage(Double genderPercentage) {
    this.genderPercentage = genderPercentage;
  }


  public void setGenderValue(Double genderValue) {
    this.genderValue = genderValue;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setPhase(Phase phase) {
    this.phase = phase;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setRationale(String rationale) {
    this.rationale = rationale;
  }


  public void setYear(int year) {
    this.year = year;
  }


  @Override
  public String toString() {
    return "ProjectBudget [id=" + this.getId() + ", institution=" + institution + ", project=" + project + ", amount="
      + amount + ", budgetType=" + budgetType + ", year=" + year + ", fundingSource=" + fundingSource + "]";
  }


}

