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


package org.cgiar.ccafs.marlo.data.dao;

import org.cgiar.ccafs.marlo.data.model.RepIndFillingType;

import java.util.List;


public interface RepIndFillingTypeDAO {

  /**
   * This method removes a specific repIndFillingType value from the database.
   * 
   * @param repIndFillingTypeId is the repIndFillingType identifier.
   * @return true if the repIndFillingType was successfully deleted, false otherwise.
   */
  public void deleteRepIndFillingType(long repIndFillingTypeId);

  /**
   * This method validate if the repIndFillingType identify with the given id exists in the system.
   * 
   * @param repIndFillingTypeID is a repIndFillingType identifier.
   * @return true if the repIndFillingType exists, false otherwise.
   */
  public boolean existRepIndFillingType(long repIndFillingTypeID);

  /**
   * This method gets a repIndFillingType object by a given repIndFillingType identifier.
   * 
   * @param repIndFillingTypeID is the repIndFillingType identifier.
   * @return a RepIndFillingType object.
   */
  public RepIndFillingType find(long id);

  /**
   * This method gets a list of repIndFillingType that are active
   * 
   * @return a list from RepIndFillingType null if no exist records
   */
  public List<RepIndFillingType> findAll();


  /**
   * This method saves the information of the given repIndFillingType
   * 
   * @param repIndFillingType - is the repIndFillingType object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the repIndFillingType was
   *         updated
   *         or -1 is some error occurred.
   */
  public RepIndFillingType save(RepIndFillingType repIndFillingType);
}
