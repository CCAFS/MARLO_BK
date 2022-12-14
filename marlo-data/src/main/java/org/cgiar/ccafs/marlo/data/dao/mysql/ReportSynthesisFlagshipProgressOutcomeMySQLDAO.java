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


package org.cgiar.ccafs.marlo.data.dao.mysql;

import org.cgiar.ccafs.marlo.data.dao.ReportSynthesisFlagshipProgressOutcomeDAO;
import org.cgiar.ccafs.marlo.data.model.ReportSynthesisFlagshipProgressOutcome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

@Named
public class ReportSynthesisFlagshipProgressOutcomeMySQLDAO extends
  AbstractMarloDAO<ReportSynthesisFlagshipProgressOutcome, Long> implements ReportSynthesisFlagshipProgressOutcomeDAO {


  @Inject
  public ReportSynthesisFlagshipProgressOutcomeMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deleteReportSynthesisFlagshipProgressOutcome(long reportSynthesisFlagshipProgressOutcomeId) {
    ReportSynthesisFlagshipProgressOutcome reportSynthesisFlagshipProgressOutcome =
      this.find(reportSynthesisFlagshipProgressOutcomeId);
    this.delete(reportSynthesisFlagshipProgressOutcome);
  }

  @Override
  public boolean existReportSynthesisFlagshipProgressOutcome(long reportSynthesisFlagshipProgressOutcomeID) {
    ReportSynthesisFlagshipProgressOutcome reportSynthesisFlagshipProgressOutcome =
      this.find(reportSynthesisFlagshipProgressOutcomeID);
    if (reportSynthesisFlagshipProgressOutcome == null) {
      return false;
    }
    return true;

  }

  @Override
  public ReportSynthesisFlagshipProgressOutcome find(long id) {
    return super.find(ReportSynthesisFlagshipProgressOutcome.class, id);

  }

  @Override
  public List<ReportSynthesisFlagshipProgressOutcome> findAll() {
    String query = "from " + ReportSynthesisFlagshipProgressOutcome.class.getName();
    List<ReportSynthesisFlagshipProgressOutcome> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public ReportSynthesisFlagshipProgressOutcome getOutcomeId(long progressID, long outcomeID) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT id as markerId FROM report_synthesis_flagship_progress_outcomes ");
    query.append("WHERE report_synthesis_flagship_progress_id = ");
    query.append(progressID);
    query.append(" AND crp_outcome_id = ");
    query.append(outcomeID);
    List<Map<String, Object>> list = super.findCustomQuery(query.toString());

    List<ReportSynthesisFlagshipProgressOutcome> reportSynthesisFlagshipProgressOutcomes =
      new ArrayList<ReportSynthesisFlagshipProgressOutcome>();
    for (Map<String, Object> map : list) {
      String markerId = map.get("markerId").toString();
      long longMarkerId = Long.parseLong(markerId);
      ReportSynthesisFlagshipProgressOutcome reportSynthesisFlagshipProgressOutcome = this.find(longMarkerId);
      reportSynthesisFlagshipProgressOutcomes.add(reportSynthesisFlagshipProgressOutcome);
    }
    if (reportSynthesisFlagshipProgressOutcomes.size() > 0) {
      return reportSynthesisFlagshipProgressOutcomes.get(0);
    } else {
      return null;
    }

  }

  @Override
  public ReportSynthesisFlagshipProgressOutcome
    save(ReportSynthesisFlagshipProgressOutcome reportSynthesisFlagshipProgressOutcome) {
    if (reportSynthesisFlagshipProgressOutcome.getId() == null) {
      super.saveEntity(reportSynthesisFlagshipProgressOutcome);
    } else {
      reportSynthesisFlagshipProgressOutcome = super.update(reportSynthesisFlagshipProgressOutcome);
    }


    return reportSynthesisFlagshipProgressOutcome;
  }


}