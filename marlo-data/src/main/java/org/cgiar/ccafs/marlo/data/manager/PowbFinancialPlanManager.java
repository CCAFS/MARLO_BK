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

import org.cgiar.ccafs.marlo.data.model.PowbFinancialPlan;

import java.util.List;


/**
 * @author Christian Garcia
 */

public interface PowbFinancialPlanManager {


  /**
   * This method removes a specific powbFinancialPlan value from the database.
   * 
   * @param powbFinancialPlanId is the powbFinancialPlan identifier.
   * @return true if the powbFinancialPlan was successfully deleted, false otherwise.
   */
  public void deletePowbFinancialPlan(long powbFinancialPlanId);


  /**
   * This method validate if the powbFinancialPlan identify with the given id exists in the system.
   * 
   * @param powbFinancialPlanID is a powbFinancialPlan identifier.
   * @return true if the powbFinancialPlan exists, false otherwise.
   */
  public boolean existPowbFinancialPlan(long powbFinancialPlanID);


  /**
   * This method gets a list of powbFinancialPlan that are active
   * 
   * @return a list from PowbFinancialPlan null if no exist records
   */
  public List<PowbFinancialPlan> findAll();


  /**
   * This method gets a powbFinancialPlan object by a given powbFinancialPlan identifier.
   * 
   * @param powbFinancialPlanID is the powbFinancialPlan identifier.
   * @return a PowbFinancialPlan object.
   */
  public PowbFinancialPlan getPowbFinancialPlanById(long powbFinancialPlanID);

  /**
   * This method saves the information of the given powbFinancialPlan
   * 
   * @param powbFinancialPlan - is the powbFinancialPlan object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the powbFinancialPlan was
   *         updated
   *         or -1 is some error occurred.
   */
  public PowbFinancialPlan savePowbFinancialPlan(PowbFinancialPlan powbFinancialPlan);


}
