/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.ccafs.marlo.data.model;
// Generated May 17, 2016 9:53:46 AM by Hibernate Tools 4.3.1.Final


/**
 * UserRoles generated by hbm2java
 */
public class UserRole implements java.io.Serializable {


  private static final long serialVersionUID = -4464617364263332726L;
  private Long id;
  private Role role;
  private User user;

  public UserRole() {
  }

  public UserRole(Role roles, User users) {
    this.role = roles;
    this.user = users;
  }

  public Long getId() {
    return this.id;
  }

  public Role getRole() {
    return this.role;
  }

  public User getUser() {
    return this.user;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setRole(Role roles) {
    this.role = roles;
  }

  public void setUser(User users) {
    this.user = users;
  }


}

