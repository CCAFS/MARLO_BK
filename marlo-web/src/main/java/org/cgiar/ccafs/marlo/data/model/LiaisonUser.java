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
// Generated Jul 13, 2016 11:45:52 AM by Hibernate Tools 4.3.1.Final


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * LiaisonUser generated by hbm2java
 */
public class LiaisonUser implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = -2344785951769472166L;


  @Expose
  private Long id;


  @Expose
  private LiaisonInstitution liaisonInstitution;


  @Expose
  private User user;


  @Expose
  private GlobalUnit crp;
  @Expose
  private boolean active;

  private Set<Project> projects = new HashSet<Project>(0);

  public LiaisonUser() {
  }

  public LiaisonUser(LiaisonInstitution liaisonInstitution, User user) {
    this.liaisonInstitution = liaisonInstitution;
    this.user = user;
  }

  public LiaisonUser(LiaisonInstitution liaisonInstitution, User user, Set<Project> projectses) {
    this.liaisonInstitution = liaisonInstitution;
    this.user = user;
    this.projects = projectses;
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
    LiaisonUser other = (LiaisonUser) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.getId())) {
      return false;
    }
    return true;
  }

  public String getComposedName() {
    return user.getComposedName();
  }



  public GlobalUnit getCrp() {
    return crp;
  }

  @Override
  public Long getId() {
    return this.id;
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

  @Override
  public String getModificationJustification() {

    return "";
  }


  @Override
  public User getModifiedBy() {
    User user = new User();
    user.setId(new Long(3));
    return user;
  }


  public Set<Project> getProjects() {
    return projects;
  }

  public User getUser() {
    return user;
  }


  @Override
  public boolean isActive() {

    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setCrp(GlobalUnit crp) {
    this.crp = crp;
  }


  public void setId(Long id) {
    this.id = id;
  }

  public void setLiaisonInstitution(LiaisonInstitution liaisonInstitution) {
    this.liaisonInstitution = liaisonInstitution;
  }


  public void setProjects(Set<Project> projects) {
    this.projects = projects;
  }


  public void setUser(User user) {
    this.user = user;
  }


  @Override
  public String toString() {
    return "LiaisonUser [id=" + id + ", liaisonInstitution=" + liaisonInstitution + ", user=" + user + ", crp=" + crp
      + "]";
  }


}

