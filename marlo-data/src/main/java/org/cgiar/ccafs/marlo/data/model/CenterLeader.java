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

import java.util.Date;

// Generated May 26, 2016 9:42:28 AM by Hibernate Tools 4.3.1.Final

import com.google.gson.annotations.Expose;

/**
 * CrpProgramLeader generated by hbm2java
 */
public class CenterLeader implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = 7345327536072331541L;


  @Expose
  private Long id;


  @Expose
  private CenterProgram researchProgram;


  @Expose
  private Center researchCenter;


  @Expose
  private CenterArea researchArea;


  @Expose
  private User user;


  @Expose
  private boolean active;


  @Expose
  private Date activeSince;


  @Expose
  private User createdBy;


  @Expose
  private User modifiedBy;


  @Expose
  private String modificationJustification;


  @Expose
  private CenterAllTypes type;


  public CenterLeader() {
  }

  /**
   * @param researchArea
   * @param user
   */
  public CenterLeader(CenterArea researchArea, User user) {
    super();
    this.researchArea = researchArea;
    this.user = user;
  }

  /**
   * @param researchCenter
   * @param user
   */
  public CenterLeader(Center researchCenter, User user) {
    super();
    this.researchCenter = researchCenter;
    this.user = user;
  }

  /**
   * @param researchProgram
   * @param researchCenter
   * @param researchArea
   * @param user
   */
  public CenterLeader(CenterProgram researchProgram, Center researchCenter, CenterArea researchArea,
    User user) {
    super();
    this.researchProgram = researchProgram;
    this.researchCenter = researchCenter;
    this.researchArea = researchArea;
    this.user = user;
  }

  /**
   * @param researchProgram
   * @param user
   */
  public CenterLeader(CenterProgram researchProgram, User user) {
    super();
    this.researchProgram = researchProgram;
    this.user = user;
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
    }
    CenterLeader other = (CenterLeader) obj;
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

  public User getCreatedBy() {
    return createdBy;
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

  /**
   * @return the researchArea
   */
  public CenterArea getResearchArea() {
    return researchArea;
  }

  /**
   * @return the researchCenter
   */
  public Center getResearchCenter() {
    return researchCenter;
  }


  /**
   * @return the researchProgram
   */
  public CenterProgram getResearchProgram() {
    return researchProgram;
  }


  public CenterAllTypes getType() {
    return type;
  }


  public User getUser() {
    return this.user;
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


  public void setId(Long id) {
    this.id = id;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   * @param researchArea the researchArea to set
   */
  public void setResearchArea(CenterArea researchArea) {
    this.researchArea = researchArea;
  }


  /**
   * @param researchCenter the researchCenter to set
   */
  public void setResearchCenter(Center researchCenter) {
    this.researchCenter = researchCenter;
  }


  /**
   * @param researchProgram the researchProgram to set
   */
  public void setResearchProgram(CenterProgram researchProgram) {
    this.researchProgram = researchProgram;
  }


  public void setType(CenterAllTypes type) {
    this.type = type;
  }


  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return id.toString();
  }

}
