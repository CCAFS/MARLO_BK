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

import org.cgiar.ccafs.marlo.data.dao.mysql.FundingSourceMySQLDAO;
import org.cgiar.ccafs.marlo.data.model.FundingSource;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(FundingSourceMySQLDAO.class)
public interface FundingSourceDAO {

  /**
   * This method removes a specific fundingSource value from the database.
   * 
   * @param fundingSourceId is the fundingSource identifier.
   * @return true if the fundingSource was successfully deleted, false otherwise.
   */
  public boolean deleteFundingSource(long fundingSourceId);

  /**
   * This method validate if the fundingSource identify with the given id exists in the system.
   * 
   * @param fundingSourceID is a fundingSource identifier.
   * @return true if the fundingSource exists, false otherwise.
   */
  public boolean existFundingSource(long fundingSourceID);

  /**
   * This method gets a fundingSource object by a given fundingSource identifier.
   * 
   * @param fundingSourceID is the fundingSource identifier.
   * @return a FundingSource object.
   */
  public FundingSource find(long id);

  /**
   * This method gets a list of fundingSource that are active
   * 
   * @return a list from FundingSource null if no exist records
   */
  public List<FundingSource> findAll();


  /**
   * This method saves the information of the given fundingSource
   * 
   * @param fundingSource - is the fundingSource object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the fundingSource was
   *         updated
   *         or -1 is some error occurred.
   */
  public long save(FundingSource fundingSource);
}
