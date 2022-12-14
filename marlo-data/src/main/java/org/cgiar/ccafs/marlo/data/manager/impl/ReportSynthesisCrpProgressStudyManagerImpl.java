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


import org.cgiar.ccafs.marlo.data.dao.ReportSynthesisCrpProgressStudyDAO;
import org.cgiar.ccafs.marlo.data.manager.ReportSynthesisCrpProgressStudyManager;
import org.cgiar.ccafs.marlo.data.model.ReportSynthesisCrpProgressStudy;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author CCAFS
 */
@Named
public class ReportSynthesisCrpProgressStudyManagerImpl implements ReportSynthesisCrpProgressStudyManager {


  private ReportSynthesisCrpProgressStudyDAO reportSynthesisCrpProgressStudyDAO;
  // Managers


  @Inject
  public ReportSynthesisCrpProgressStudyManagerImpl(ReportSynthesisCrpProgressStudyDAO reportSynthesisCrpProgressStudyDAO) {
    this.reportSynthesisCrpProgressStudyDAO = reportSynthesisCrpProgressStudyDAO;


  }

  @Override
  public void deleteReportSynthesisCrpProgressStudy(long reportSynthesisCrpProgressStudyId) {

    reportSynthesisCrpProgressStudyDAO.deleteReportSynthesisCrpProgressStudy(reportSynthesisCrpProgressStudyId);
  }

  @Override
  public boolean existReportSynthesisCrpProgressStudy(long reportSynthesisCrpProgressStudyID) {

    return reportSynthesisCrpProgressStudyDAO.existReportSynthesisCrpProgressStudy(reportSynthesisCrpProgressStudyID);
  }

  @Override
  public List<ReportSynthesisCrpProgressStudy> findAll() {

    return reportSynthesisCrpProgressStudyDAO.findAll();

  }

  @Override
  public ReportSynthesisCrpProgressStudy getReportSynthesisCrpProgressStudyById(long reportSynthesisCrpProgressStudyID) {

    return reportSynthesisCrpProgressStudyDAO.find(reportSynthesisCrpProgressStudyID);
  }

  @Override
  public ReportSynthesisCrpProgressStudy saveReportSynthesisCrpProgressStudy(ReportSynthesisCrpProgressStudy reportSynthesisCrpProgressStudy) {

    return reportSynthesisCrpProgressStudyDAO.save(reportSynthesisCrpProgressStudy);
  }


}
