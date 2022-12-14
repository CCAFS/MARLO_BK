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
package org.cgiar.ccafs.marlo.data.manager;

import org.cgiar.ccafs.marlo.data.model.Role;

import java.util.List;


/**
 * @author Christian Garcia
 */

public interface RoleManager {


  /**
   * This method removes a specific role value from the database.
   * 
   * @param roleId is the role identifier.
   * @return true if the role was successfully deleted, false otherwise.
   */
  public void deleteRole(long roleId);


  /**
   * This method validate if the role identify with the given id exists in the system.
   * 
   * @param roleID is a role identifier.
   * @return true if the role exists, false otherwise.
   */
  public boolean existRole(long roleID);


  /**
   * This method gets a list of role that are active
   * 
   * @return a list from Role null if no exist records
   */
  public List<Role> findAll();


  /**
   * This method gets a role object by a given role identifier.
   * 
   * @param roleID is the role identifier.
   * @return a Role object.
   */
  public Role getRoleById(long roleID);

  /**
   * This method saves the information of the given role
   * 
   * @param role - is the role object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the role was
   *         updated
   *         or -1 is some error occurred.
   */
  public Role saveRole(Role role);


}
