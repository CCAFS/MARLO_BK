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

import org.cgiar.ccafs.marlo.data.model.ProjectExpectedStudyLever;

import java.util.List;


public interface ProjectExpectedStudyLeverDAO {

  /**
   * This method removes a specific projectExpectedStudyLever value from the database.
   * 
   * @param projectExpectedStudyLeverId is the projectExpectedStudyLever identifier.
   * @return true if the projectExpectedStudyLever was successfully deleted, false otherwise.
   */
  public void deleteProjectExpectedStudyLever(long projectExpectedStudyLeverId);

  /**
   * This method validate if the projectExpectedStudyLever identify with the given id exists in the system.
   * 
   * @param projectExpectedStudyLeverID is a projectExpectedStudyLever identifier.
   * @return true if the projectExpectedStudyLever exists, false otherwise.
   */
  public boolean existProjectExpectedStudyLever(long projectExpectedStudyLeverID);

  /**
   * This method gets a projectExpectedStudyLever object by a given projectExpectedStudyLever identifier.
   * 
   * @param projectExpectedStudyLeverID is the projectExpectedStudyLever identifier.
   * @return a ProjectExpectedStudyLever object.
   */
  public ProjectExpectedStudyLever find(long id);

  /**
   * This method gets a list of projectExpectedStudyLever that are active
   * 
   * @return a list from ProjectExpectedStudyLever null if no exist records
   */
  public List<ProjectExpectedStudyLever> findAll();


  /**
   * This method gets a list of projectExpectedStudyLever by a given projectExpectedStudy identifier.
   * 
   * @param studyId is the projectExpectedStudy identifier.
   * @return a list of projectExpectedStudyLever objects.
   */
  public List<ProjectExpectedStudyLever> getAllStudyLeversByStudy(long studyId);

  /**
   * Gets a ProjectExpectedStudyLever by a study id, a lever id and a phase id
   * 
   * @param studyId the ProjectExpectedStudy id
   * @param leverId the AllianceLever id
   * @param idPhase the Phase id
   * @return a ProjectExpectedStudyLever if found; else null
   */
  public ProjectExpectedStudyLever getStudyLeverByStudyLeverAndPhase(long studyId, long leverId, long idPhase);

  /**
   * This method saves the information of the given projectExpectedStudyLever
   * 
   * @param projectExpectedStudyLever - is the projectExpectedStudyLever object with the new information to be
   *        added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the
   *         projectExpectedStudyLever was
   *         updated
   *         or -1 is some error occurred.
   */
  public ProjectExpectedStudyLever save(ProjectExpectedStudyLever projectExpectedStudyLever);
}
