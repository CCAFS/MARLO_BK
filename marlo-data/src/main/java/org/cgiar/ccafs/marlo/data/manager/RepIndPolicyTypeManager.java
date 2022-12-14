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

import org.cgiar.ccafs.marlo.data.model.RepIndPolicyType;

import java.util.List;


/**
 * @author CCAFS
 */

public interface RepIndPolicyTypeManager {


  /**
   * This method removes a specific repIndPolicyType value from the database.
   * 
   * @param repIndPolicyTypeId is the repIndPolicyType identifier.
   * @return true if the repIndPolicyType was successfully deleted, false otherwise.
   */
  public void deleteRepIndPolicyType(long repIndPolicyTypeId);


  /**
   * This method validate if the repIndPolicyType identify with the given id exists in the system.
   * 
   * @param repIndPolicyTypeID is a repIndPolicyType identifier.
   * @return true if the repIndPolicyType exists, false otherwise.
   */
  public boolean existRepIndPolicyType(long repIndPolicyTypeID);


  /**
   * This method gets a list of repIndPolicyType that are active
   * 
   * @return a list from RepIndPolicyType null if no exist records
   */
  public List<RepIndPolicyType> findAll();


  /**
   * This method gets a repIndPolicyType object by a given repIndPolicyType identifier.
   * 
   * @param repIndPolicyTypeID is the repIndPolicyType identifier.
   * @return a RepIndPolicyType object.
   */
  public RepIndPolicyType getRepIndPolicyTypeById(long repIndPolicyTypeID);

  /**
   * This method saves the information of the given repIndPolicyType
   * 
   * @param repIndPolicyType - is the repIndPolicyType object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the repIndPolicyType was
   *         updated
   *         or -1 is some error occurred.
   */
  public RepIndPolicyType saveRepIndPolicyType(RepIndPolicyType repIndPolicyType);


}
