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
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * CrpsSiteIntegration generated by hbm2java
 */
public class CrpsSiteIntegration implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -7674530393075404476L;


  @Expose
  private Long id;

  @Expose
  private GlobalUnit crp;


  @Expose
  private LocElement locElement;


  private Set<CrpSitesLeader> crpSitesLeaders = new HashSet<CrpSitesLeader>(0);

  @Expose
  private boolean active;

  @Expose
  private boolean regional;


  @Expose
  private User createdBy;

  @Expose
  private Date activeSince;

  @Expose
  private User modifiedBy;

  @Expose
  private String modificationJustification;


  private List<String> programName;

  private List<CrpSitesLeader> siteLeaders;

  public CrpsSiteIntegration() {
  }

  public CrpsSiteIntegration(GlobalUnit crps, LocElement locElements) {
    this.crp = crps;
    this.locElement = locElements;
  }

  public CrpsSiteIntegration(GlobalUnit crps, LocElement locElements, Set<CrpSitesLeader> crpSitesLeaders) {
    this.crp = crps;
    this.locElement = locElements;
    this.crpSitesLeaders = crpSitesLeaders;
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
    CrpsSiteIntegration other = (CrpsSiteIntegration) obj;
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

  public GlobalUnit getCrp() {
    return crp;
  }

  public Set<CrpSitesLeader> getCrpSitesLeaders() {
    return this.crpSitesLeaders;
  }


  @Override
  public Long getId() {
    return this.id;
  }


  public LocElement getLocElement() {
    return this.locElement;
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

  public List<String> getProgramName() {
    return programName;
  }

  public List<CrpSitesLeader> getSiteLeaders() {
    return siteLeaders;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  public boolean isRegional() {
    return regional;
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

  public void setCrp(GlobalUnit crp) {
    this.crp = crp;
  }


  public void setCrpSitesLeaders(Set<CrpSitesLeader> crpSitesLeaders) {
    this.crpSitesLeaders = crpSitesLeaders;
  }


  public void setId(Long id) {
    this.id = id;
  }

  public void setLocElement(LocElement locElements) {
    this.locElement = locElements;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setProgramName(List<String> programName) {
    this.programName = programName;
  }

  public void setRegional(boolean regional) {
    this.regional = regional;
  }

  public void setSiteLeaders(List<CrpSitesLeader> siteLeaders) {
    this.siteLeaders = siteLeaders;
  }

  @Override
  public String toString() {
     return "CrpsSiteIntegration [id=" + id + ", crp=" + crp + ", locElement=" + locElement + ", regional=" + regional
      + ", programName=" + programName + "]";
  }

}

