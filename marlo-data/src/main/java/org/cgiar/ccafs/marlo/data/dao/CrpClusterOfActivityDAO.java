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

import org.cgiar.ccafs.marlo.data.dto.ImpactPathwaysClusterDTO;
import org.cgiar.ccafs.marlo.data.model.CrpClusterOfActivity;

import java.util.List;


public interface CrpClusterOfActivityDAO {

  /**
   * This method removes a specific crpClusterOfActivity value from the database.
   * 
   * @param crpClusterOfActivityId is the crpClusterOfActivity identifier.
   * @return true if the crpClusterOfActivity was successfully deleted, false otherwise.
   */
  public void deleteCrpClusterOfActivity(long crpClusterOfActivityId);

  /**
   * This method validate if the crpClusterOfActivity identify with the given id exists in the system.
   * 
   * @param crpClusterOfActivityID is a crpClusterOfActivity identifier.
   * @return true if the crpClusterOfActivity exists, false otherwise.
   */
  public boolean existCrpClusterOfActivity(long crpClusterOfActivityID);

  /**
   * This method gets a crpClusterOfActivity object by a given crpClusterOfActivity identifier.
   * 
   * @param crpClusterOfActivityID is the crpClusterOfActivity identifier.
   * @return a CrpClusterOfActivity object.
   */
  public CrpClusterOfActivity find(long id);

  /**
   * This method gets a list of crpClusterOfActivity that are active
   * 
   * @return a list from CrpClusterOfActivity null if no exist records
   */
  public List<CrpClusterOfActivity> findAll();

  /**
   * This method gets a list of crpClusterOfActivity that are active for a program and phase
   * 
   * @param crpProgram id the program to filter clusters
   * @param phase id the phase to filter clusters
   * @return a list from CrpClusterOfActivity null if no exist records
   */
  public List<CrpClusterOfActivity> findClusterProgramPhase(long crpProgramID, long phaseID);

  /**
   * This method is meant to be used by the ImpactPathways report
   * 
   * @param phaseId the Phase identifier
   * @return all the cluster info (including leaders, key outputs and linked outcomes)
   */
  public List<ImpactPathwaysClusterDTO> getAllClusterInfoFromPhase(long phaseId);

  /**
   * Gets the CrpClusterOfActivity by a given phase, flagship and identifier
   * 
   * @param phaseId the Phase identifier
   * @param fpId the CrpProgram identifier
   * @param crpClusterOfActivityIdentefier the CrpClusterOfActivity identifier
   * @return a CrpClusterOfActivity object; null if not found.
   */
  public CrpClusterOfActivity getCrpClusterOfActivityByIdentifierFlagshipAndPhase(String crpClusterOfActivityIdentefier,
    long fpId, long phaseId);

  /**
   * This method saves the information of the given crpClusterOfActivity
   * 
   * @param crpClusterOfActivity - is the crpClusterOfActivity object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the crpClusterOfActivity was
   *         updated
   *         or -1 is some error occurred.
   */
  public CrpClusterOfActivity save(CrpClusterOfActivity crpClusterOfActivity);

}
