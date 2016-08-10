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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SrfTargetUnit generated by hbm2java
 */
public class SrfTargetUnit implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = -5929916550523608124L;

  @Expose
  private Long id;

  @Expose
  private String name;

  private Set<CrpMilestone> crpMilestones = new HashSet<CrpMilestone>(0);


  private Set<SrfSloIndicatorTarget> srfSloIndicatorTargets = new HashSet<SrfSloIndicatorTarget>(0);

  private Set<CrpProgramOutcome> crpProgramOutcomes = new HashSet<CrpProgramOutcome>(0);


  private boolean active;


  private User createdBy;


  private Date activeSince;


  private User modifiedBy;


  private String modificationJustification;

  public SrfTargetUnit() {
  }

  public SrfTargetUnit(String name) {
    this.name = name;

  }

  public SrfTargetUnit(String name, Set<CrpMilestone> crpMilestones, Set<SrfSloIndicatorTarget> srfSloIndicatorTargets,
    Set<CrpProgramOutcome> crpProgramOutcomes) {
    this.name = name;

    this.crpMilestones = crpMilestones;
    this.srfSloIndicatorTargets = srfSloIndicatorTargets;
    this.crpProgramOutcomes = crpProgramOutcomes;
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
    SrfTargetUnit other = (SrfTargetUnit) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }


  public Date getActiveSince() {
    return activeSince;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public Set<CrpMilestone> getCrpMilestones() {
    return this.crpMilestones;
  }

  public Set<CrpProgramOutcome> getCrpProgramOutcomes() {
    return this.crpProgramOutcomes;
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
    return modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public String getName() {
    return this.name;
  }


  public Set<SrfSloIndicatorTarget> getSrfSloIndicatorTargets() {
    return this.srfSloIndicatorTargets;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setCrpMilestones(Set<CrpMilestone> crpMilestones) {
    this.crpMilestones = crpMilestones;
  }

  public void setCrpProgramOutcomes(Set<CrpProgramOutcome> crpProgramOutcomes) {
    this.crpProgramOutcomes = crpProgramOutcomes;
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

  public void setSrfSloIndicatorTargets(Set<SrfSloIndicatorTarget> srfSloIndicatorTargets) {
    this.srfSloIndicatorTargets = srfSloIndicatorTargets;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}

