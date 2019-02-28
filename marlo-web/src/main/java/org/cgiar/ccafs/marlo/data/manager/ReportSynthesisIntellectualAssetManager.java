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

import org.cgiar.ccafs.marlo.data.model.ReportSynthesisIntellectualAsset;

import java.util.List;


/**
 * @author CCAFS
 */

public interface ReportSynthesisIntellectualAssetManager {


  /**
   * This method removes a specific reportSynthesisIntellectualAsset value from the database.
   * 
   * @param reportSynthesisIntellectualAssetId is the reportSynthesisIntellectualAsset identifier.
   * @return true if the reportSynthesisIntellectualAsset was successfully deleted, false otherwise.
   */
  public void deleteReportSynthesisIntellectualAsset(long reportSynthesisIntellectualAssetId);


  /**
   * This method validate if the reportSynthesisIntellectualAsset identify with the given id exists in the system.
   * 
   * @param reportSynthesisIntellectualAssetID is a reportSynthesisIntellectualAsset identifier.
   * @return true if the reportSynthesisIntellectualAsset exists, false otherwise.
   */
  public boolean existReportSynthesisIntellectualAsset(long reportSynthesisIntellectualAssetID);


  /**
   * This method gets a list of reportSynthesisIntellectualAsset that are active
   * 
   * @return a list from ReportSynthesisIntellectualAsset null if no exist records
   */
  public List<ReportSynthesisIntellectualAsset> findAll();


  /**
   * This method gets a reportSynthesisIntellectualAsset object by a given reportSynthesisIntellectualAsset identifier.
   * 
   * @param reportSynthesisIntellectualAssetID is the reportSynthesisIntellectualAsset identifier.
   * @return a ReportSynthesisIntellectualAsset object.
   */
  public ReportSynthesisIntellectualAsset getReportSynthesisIntellectualAssetById(long reportSynthesisIntellectualAssetID);

  /**
   * This method saves the information of the given reportSynthesisIntellectualAsset
   * 
   * @param reportSynthesisIntellectualAsset - is the reportSynthesisIntellectualAsset object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the reportSynthesisIntellectualAsset was
   *         updated
   *         or -1 is some error occurred.
   */
  public ReportSynthesisIntellectualAsset saveReportSynthesisIntellectualAsset(ReportSynthesisIntellectualAsset reportSynthesisIntellectualAsset);


}
