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

import org.cgiar.ccafs.marlo.data.model.CrpsSiteIntegration;

import java.util.List;


/**
 * @author Christian Garcia
 */

public interface CrpsSiteIntegrationManager {


  /**
   * This method removes a specific crpsSiteIntegration value from the database.
   * 
   * @param crpsSiteIntegrationId is the crpsSiteIntegration identifier.
   * @return true if the crpsSiteIntegration was successfully deleted, false otherwise.
   */
  public void deleteCrpsSiteIntegration(long crpsSiteIntegrationId);


  /**
   * This method validate if the crpsSiteIntegration identify with the given id exists in the system.
   * 
   * @param crpsSiteIntegrationID is a crpsSiteIntegration identifier.
   * @return true if the crpsSiteIntegration exists, false otherwise.
   */
  public boolean existCrpsSiteIntegration(long crpsSiteIntegrationID);


  /**
   * This method gets a list of crpsSiteIntegration that are active
   * 
   * @return a list from CrpsSiteIntegration null if no exist records
   */
  public List<CrpsSiteIntegration> findAll();


  /**
   * This method gets a crpsSiteIntegration object by a given crpsSiteIntegration identifier.
   * 
   * @param crpsSiteIntegrationID is the crpsSiteIntegration identifier.
   * @return a CrpsSiteIntegration object.
   */
  public CrpsSiteIntegration getCrpsSiteIntegrationById(long crpsSiteIntegrationID);

  /**
   * This method saves the information of the given crpsSiteIntegration
   * 
   * @param crpsSiteIntegration - is the crpsSiteIntegration object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the crpsSiteIntegration was
   *         updated
   *         or -1 is some error occurred.
   */
  public CrpsSiteIntegration saveCrpsSiteIntegration(CrpsSiteIntegration crpsSiteIntegration);


}
