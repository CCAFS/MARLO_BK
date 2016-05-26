/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/


package org.cgiar.ccafs.marlo.data.dao.mysql;

import org.cgiar.ccafs.marlo.data.dao.CrpSubIdosContributionDAO;
import org.cgiar.ccafs.marlo.data.model.CrpSubIdosContribution;

import java.util.List;

import com.google.inject.Inject;

public class CrpSubIdosContributionMySQLDAO implements CrpSubIdosContributionDAO {

  private StandardDAO dao;

  @Inject
  public CrpSubIdosContributionMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteCrpSubIdosContribution(long crpSubIdosContributionId) {
    CrpSubIdosContribution crpSubIdosContribution = this.find(crpSubIdosContributionId);
    crpSubIdosContribution.setActive(false);
    return this.save(crpSubIdosContribution) > 0;
  }

  @Override
  public boolean existCrpSubIdosContribution(long crpSubIdosContributionID) {
    CrpSubIdosContribution crpSubIdosContribution = this.find(crpSubIdosContributionID);
    if (crpSubIdosContribution == null) {
      return false;
    }
    return true;

  }

  @Override
  public CrpSubIdosContribution find(long id) {
    return dao.find(CrpSubIdosContribution.class, id);

  }

  @Override
  public List<CrpSubIdosContribution> findAll() {
    String query = "from " + CrpSubIdosContribution.class.getName() + " where is_active=1";
    List<CrpSubIdosContribution> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public long save(CrpSubIdosContribution crpSubIdosContribution) {
    dao.saveOrUpdate(crpSubIdosContribution);
    return crpSubIdosContribution.getId();
  }


}