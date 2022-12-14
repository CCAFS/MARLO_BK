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

import org.cgiar.ccafs.marlo.data.model.ProjectPartnerPerson;

import java.util.List;


/**
 * @author Christian Garcia
 */

public interface ProjectPartnerPersonManager {


  /**
   * This method removes a specific projectPartnerPerson value from the database.
   * 
   * @param projectPartnerPersonId is the projectPartnerPerson identifier.
   * @return true if the projectPartnerPerson was successfully deleted, false otherwise.
   */
  public void deleteProjectPartnerPerson(long projectPartnerPersonId);


  /**
   * This method validate if the projectPartnerPerson identify with the given id exists in the system.
   * 
   * @param projectPartnerPersonID is a projectPartnerPerson identifier.
   * @return true if the projectPartnerPerson exists, false otherwise.
   */
  public boolean existProjectPartnerPerson(long projectPartnerPersonID);


  /**
   * This method gets a list of projectPartnerPerson that are active
   * 
   * @return a list from ProjectPartnerPerson null if no exist records
   */
  public List<ProjectPartnerPerson> findAll();


  public List<ProjectPartnerPerson> findAllActiveForProjectPartner(long projectPartnerId);

  public List<ProjectPartnerPerson> findAllForOtherPartnerTypeWithDeliverableIdAndPartnerId(long deliverableId,
    long partnerId);

  public List<ProjectPartnerPerson> findAllForProjectPartner(long projectPartnerId);


  /**
   * This method gets a projectPartnerPerson object by a given projectPartnerPerson identifier.
   * 
   * @param projectPartnerPersonID is the projectPartnerPerson identifier.
   * @return a ProjectPartnerPerson object.
   */
  public ProjectPartnerPerson getProjectPartnerPersonById(long projectPartnerPersonID);


  /**
   * This method saves the information of the given projectPartnerPerson
   * 
   * @param projectPartnerPerson - is the projectPartnerPerson object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the projectPartnerPerson was
   *         updated
   *         or -1 is some error occurred.
   */
  public ProjectPartnerPerson saveProjectPartnerPerson(ProjectPartnerPerson projectPartnerPerson);


}
