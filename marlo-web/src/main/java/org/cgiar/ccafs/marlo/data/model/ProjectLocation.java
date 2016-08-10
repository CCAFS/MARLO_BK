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

import java.util.Date;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectLocation implements java.io.Serializable {


  private static final long serialVersionUID = 7097495494769482469L;


  @Expose
  private Long id;

  @Expose
  private LocElement locElement;


  @Expose
  private LocElement regionLocElement;

  @Expose
  private Project project;

  @Expose
  private boolean active;


  @Expose
  private Date activeSince;

  @Expose
  private long createdBy;


  @Expose
  private long modifiedBy;

  @Expose
  private String modificationJustification;

  @Expose
  private CrpProgram crpProgram;

  public ProjectLocation() {
  }

  public ProjectLocation(LocElement locElement, Project project, boolean active, Date activeSince, long createdBy,
    long modifiedBy, String modificationJustification, CrpProgram crpProgram, LocElement regionLocElement) {
    this.locElement = locElement;
    this.project = project;
    this.active = active;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
    this.crpProgram = crpProgram;
    this.regionLocElement = regionLocElement;
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
    ProjectLocation other = (ProjectLocation) obj;
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

  public long getCreatedBy() {
    return createdBy;
  }

  public CrpProgram getCrpProgram() {
    return crpProgram;
  }

  public Long getId() {
    return id;
  }

  public LocElement getLocElement() {
    return locElement;
  }


  public String getModificationJustification() {
    return modificationJustification;
  }


  public long getModifiedBy() {
    return modifiedBy;
  }


  public Project getProject() {
    return project;
  }


  public LocElement getRegionLocElement() {
    return regionLocElement;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }


  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }


  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }


  public void setCrpProgram(CrpProgram crpProgram) {
    this.crpProgram = crpProgram;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setLocElement(LocElement locElement) {
    this.locElement = locElement;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(long modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setRegionLocElement(LocElement regionLocElement) {
    this.regionLocElement = regionLocElement;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
