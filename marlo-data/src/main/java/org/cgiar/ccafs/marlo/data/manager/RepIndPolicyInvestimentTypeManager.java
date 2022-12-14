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

import org.cgiar.ccafs.marlo.data.model.Phase;
import org.cgiar.ccafs.marlo.data.model.ProjectPolicy;
import org.cgiar.ccafs.marlo.data.model.RepIndPolicyInvestimentType;
import org.cgiar.ccafs.marlo.data.model.ReportSynthesisPoliciesByOrganizationTypeDTO;
import org.cgiar.ccafs.marlo.data.model.ReportSynthesisPoliciesByRepIndPolicyInvestimentTypeDTO;

import java.util.List;


/**
 * @author Christian Garcia
 */

public interface RepIndPolicyInvestimentTypeManager {


  /**
   * This method removes a specific repIndPolicyInvestimentType value from the database.
   * 
   * @param repIndPolicyInvestimentTypeId is the repIndPolicyInvestimentType identifier.
   * @return true if the repIndPolicyInvestimentType was successfully deleted, false otherwise.
   */
  public void deleteRepIndPolicyInvestimentType(long repIndPolicyInvestimentTypeId);


  /**
   * This method validate if the repIndPolicyInvestimentType identify with the given id exists in the system.
   * 
   * @param repIndPolicyInvestimentTypeID is a repIndPolicyInvestimentType identifier.
   * @return true if the repIndPolicyInvestimentType exists, false otherwise.
   */
  public boolean existRepIndPolicyInvestimentType(long repIndPolicyInvestimentTypeID);


  /**
   * This method gets a list of repIndPolicyInvestimentType that are active
   * 
   * @return a list from RepIndPolicyInvestimentType null if no exist records
   */
  public List<RepIndPolicyInvestimentType> findAll();


  /**
   * This method gets a repIndPolicyInvestimentType object by a given repIndPolicyInvestimentType identifier.
   * 
   * @param repIndPolicyInvestimentTypeID is the repIndPolicyInvestimentType identifier.
   * @return a RepIndPolicyInvestimentType object.
   */
  public RepIndPolicyInvestimentType getRepIndPolicyInvestimentTypeById(long repIndPolicyInvestimentTypeID);

  /**
   * This method saves the information of the given repIndPolicyInvestimentType
   * 
   * @param repIndPolicyInvestimentType - is the repIndPolicyInvestimentType object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the repIndPolicyInvestimentType was
   *         updated
   *         or -1 is some error occurred.
   */
  public RepIndPolicyInvestimentType saveRepIndPolicyInvestimentType(RepIndPolicyInvestimentType repIndPolicyInvestimentType);


  /**
   * This method gets a list of ReportSynthesisPoliciesByRepIndPolicyInvestimentTypeDTO that are active:
   * List of Policies grouped by RepIndPolicyInvestimentType
   * 
   * @return a list from ReportSynthesisPoliciesByRepIndPolicyInvestimentTypeDTO null if no exist records
   */  
  public List<ReportSynthesisPoliciesByRepIndPolicyInvestimentTypeDTO> getPoliciesByInvestimentType(List<ProjectPolicy> selectedProjectPolicies, Phase phase);
}
