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
// Generated May 26, 2016 9:42:28 AM by Hibernate Tools 4.3.1.Final


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * CrpProgramOutcome generated by hbm2java
 */
public class CrpProgramOutcome implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = 3014520962149158601L;


  @Expose
  private Long id;

  @Expose
  private CrpProgram crpProgram;


  @Expose
  private SrfTargetUnit srfTargetUnit;

  @Expose
  private String description;

  @Expose
  private String indicator;
  @Expose
  private Integer year;

  @Expose
  private BigDecimal value;

  private Set<CrpOutcomeSubIdo> crpOutcomeSubIdos = new HashSet<CrpOutcomeSubIdo>(0);
  private Set<CrpMilestone> crpMilestones = new HashSet<CrpMilestone>(0);

  private Set<ProjectOutcome> projectOutcomes = new HashSet<ProjectOutcome>(0);

  private Set<Deliverable> deliverables = new HashSet<Deliverable>(0);

  private Set<CrpClusterKeyOutputOutcome> crpClusterKeyOutputOutcomes = new HashSet<CrpClusterKeyOutputOutcome>(0);

  private Set<ProjectFurtherContribution> projectFurtherContributions = new HashSet<ProjectFurtherContribution>(0);
  @Expose
  private boolean active;
  @Expose
  private User createdBy;


  @Expose
  private Date activeSince;


  @Expose
  private User modifiedBy;

  @Expose
  private String modificationJustification;
  private List<CrpMilestone> milestones;

  private List<CrpOutcomeSubIdo> subIdos;

  public CrpProgramOutcome() {
  }


  public CrpProgramOutcome(CrpProgram crpProgram, SrfTargetUnit srfTargetUnit, String description, Integer year,
    BigDecimal value) {
    this.crpProgram = crpProgram;
    this.srfTargetUnit = srfTargetUnit;
    this.description = description;
    this.year = year;
    this.value = value;
  }

  public CrpProgramOutcome(CrpProgram crpProgram, SrfTargetUnit srfTargetUnit, String description, Integer year,
    BigDecimal value, Set<CrpOutcomeSubIdo> crpOutcomeSubIdos, Set<CrpMilestone> crpMilestones,
    Set<Deliverable> deliverables) {
    this.crpProgram = crpProgram;
    this.srfTargetUnit = srfTargetUnit;
    this.description = description;
    this.year = year;
    this.value = value;
    this.crpOutcomeSubIdos = crpOutcomeSubIdos;
    this.crpMilestones = crpMilestones;
    this.deliverables = deliverables;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }

    CrpProgramOutcome other = (CrpProgramOutcome) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.getId())) {
      return false;
    }
    return true;
  }

  public Date getActiveSince() {
    return activeSince;
  }

  public String getComposedName() {
    return this.getCrpProgram().getAcronym() + " Outcome :  " + description;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public Set<CrpClusterKeyOutputOutcome> getCrpClusterKeyOutputOutcomes() {
    return crpClusterKeyOutputOutcomes;
  }

  public Set<CrpMilestone> getCrpMilestones() {
    return this.crpMilestones;
  }

  public Set<CrpOutcomeSubIdo> getCrpOutcomeSubIdos() {
    return this.crpOutcomeSubIdos;
  }

  public CrpProgram getCrpProgram() {
    return this.crpProgram;
  }


  public Set<Deliverable> getDeliverables() {
    return deliverables;
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

  public List<CrpMilestone> getMilestones() {
    return milestones;
  }

  @Override
  public String getModificationJustification() {
    return modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public Set<ProjectFurtherContribution> getProjectFurtherContributions() {
    return projectFurtherContributions;
  }

  public Set<ProjectOutcome> getProjectOutcomes() {
    return projectOutcomes;
  }

  public SrfTargetUnit getSrfTargetUnit() {
    return this.srfTargetUnit;
  }

  public List<CrpOutcomeSubIdo> getSubIdos() {
    return subIdos;
  }

  public BigDecimal getValue() {
    return this.value;
  }

  public Integer getYear() {
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


  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }


  public void setCrpClusterKeyOutputOutcomes(Set<CrpClusterKeyOutputOutcome> crpClusterKeyOutputOutcomes) {
    this.crpClusterKeyOutputOutcomes = crpClusterKeyOutputOutcomes;
  }

  public void setCrpMilestones(Set<CrpMilestone> crpMilestones) {
    this.crpMilestones = crpMilestones;
  }

  public void setCrpOutcomeSubIdos(Set<CrpOutcomeSubIdo> crpOutcomeSubIdos) {
    this.crpOutcomeSubIdos = crpOutcomeSubIdos;
  }

  public void setCrpProgram(CrpProgram crpProgram) {
    this.crpProgram = crpProgram;
  }

  public void setDeliverables(Set<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setMilestones(List<CrpMilestone> milestones) {
    this.milestones = milestones;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setProjectFurtherContributions(Set<ProjectFurtherContribution> projectFurtherContributions) {
    this.projectFurtherContributions = projectFurtherContributions;
  }

  public void setProjectOutcomes(Set<ProjectOutcome> projectOutcomes) {
    this.projectOutcomes = projectOutcomes;
  }

  public void setSrfTargetUnit(SrfTargetUnit srfTargetUnit) {
    this.srfTargetUnit = srfTargetUnit;
  }

  public void setSubIdos(List<CrpOutcomeSubIdo> subIdos) {
    this.subIdos = subIdos;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return id.toString();
  }
}

