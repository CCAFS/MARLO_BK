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
package org.cgiar.ccafs.marlo.data.manager.impl;


import org.cgiar.ccafs.marlo.data.dao.ReportSynthesisSrfProgressTargetContributionDAO;
import org.cgiar.ccafs.marlo.data.manager.ReportSynthesisSrfProgressTargetContributionManager;
import org.cgiar.ccafs.marlo.data.model.ReportSynthesisSrfProgressTargetContribution;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author CCAFS
 */
@Named
public class ReportSynthesisSrfProgressTargetContributionManagerImpl
  implements ReportSynthesisSrfProgressTargetContributionManager {


  private ReportSynthesisSrfProgressTargetContributionDAO reportSynthesisSrfProgressTargetContributionDAO;
  // Managers


  @Inject
  public ReportSynthesisSrfProgressTargetContributionManagerImpl(
    ReportSynthesisSrfProgressTargetContributionDAO reportSynthesisSrfProgressTargetContributionDAO) {
    this.reportSynthesisSrfProgressTargetContributionDAO = reportSynthesisSrfProgressTargetContributionDAO;


  }

  @Override
  public void deleteReportSynthesisSrfProgressTargetContribution(long reportSynthesisSrfProgressTargetContributionId) {

    reportSynthesisSrfProgressTargetContributionDAO
      .deleteReportSynthesisSrfProgressTargetContribution(reportSynthesisSrfProgressTargetContributionId);
  }

  @Override
  public boolean
    existReportSynthesisSrfProgressTargetContribution(long reportSynthesisSrfProgressTargetContributionID) {

    return reportSynthesisSrfProgressTargetContributionDAO
      .existReportSynthesisSrfProgressTargetContribution(reportSynthesisSrfProgressTargetContributionID);
  }

  @Override
  public List<ReportSynthesisSrfProgressTargetContribution> findAll() {

    return reportSynthesisSrfProgressTargetContributionDAO.findAll();

  }

  @Override
  public List<ReportSynthesisSrfProgressTargetContribution> findBySloTargetID(long sloTargetID) {
    return reportSynthesisSrfProgressTargetContributionDAO.findBySloTargetID(sloTargetID);
  }

  @Override
  public List<ReportSynthesisSrfProgressTargetContribution> findBySloTargetSynthesis(long sloTargetID,
    long synthesisID) {
    return reportSynthesisSrfProgressTargetContributionDAO.findBySloTargetSynthesis(sloTargetID, synthesisID);
  }

  @Override
  public ReportSynthesisSrfProgressTargetContribution
    getReportSynthesisSrfProgressTargetContributionById(long reportSynthesisSrfProgressTargetContributionID) {

    return reportSynthesisSrfProgressTargetContributionDAO.find(reportSynthesisSrfProgressTargetContributionID);
  }

  @Override
  public ReportSynthesisSrfProgressTargetContribution saveReportSynthesisSrfProgressTargetContribution(
    ReportSynthesisSrfProgressTargetContribution reportSynthesisSrfProgressTargetContribution) {

    return reportSynthesisSrfProgressTargetContributionDAO.save(reportSynthesisSrfProgressTargetContribution);
  }


}
