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

import org.cgiar.ccafs.marlo.data.model.BudgetType;

import java.util.List;


/**
 * @author Christian Garcia
 */

public interface BudgetTypeManager {


  /**
   * This method removes a specific budgetType value from the database.
   * 
   * @param budgetTypeId is the budgetType identifier.
   * @return true if the budgetType was successfully deleted, false otherwise.
   */
  public void deleteBudgetType(long budgetTypeId);


  /**
   * This method validate if the budgetType identify with the given id exists in the system.
   * 
   * @param budgetTypeID is a budgetType identifier.
   * @return true if the budgetType exists, false otherwise.
   */
  public boolean existBudgetType(long budgetTypeID);


  /**
   * This method gets a list of budgetType that are active
   * 
   * @return a list from BudgetType null if no exist records
   */
  public List<BudgetType> findAll();

  public List<BudgetType> findAllFundingSourcesCGIAR();

  /**
   * This method gets a BudgetType object by a given financialCode identifier.
   * 
   * @param financialCode is the account financialCode identifier.
   * @return a BudgetType object.
   */
  public BudgetType getBudgetTypeByFinancialCode(String financialCode);

  /**
   * This method gets a budgetType object by a given budgetType identifier.
   * 
   * @param budgetTypeID is the budgetType identifier.
   * @return a BudgetType object.
   */
  public BudgetType getBudgetTypeById(long budgetTypeID);

  /**
   * This method saves the information of the given budgetType
   * 
   * @param budgetType - is the budgetType object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the budgetType was
   *         updated
   *         or -1 is some error occurred.
   */
  public BudgetType saveBudgetType(BudgetType budgetType);


}
