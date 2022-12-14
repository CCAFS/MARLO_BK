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

import org.cgiar.ccafs.marlo.data.dao.PowbFinancialPlannedBudgetDAO;
import org.cgiar.ccafs.marlo.data.model.PowbFinancialPlannedBudget;

import java.util.List;

import javax.inject.Named;
import javax.inject.Inject;
import org.hibernate.SessionFactory;

@Named
public class PowbFinancialPlannedBudgetMySQLDAO extends AbstractMarloDAO<PowbFinancialPlannedBudget, Long> implements PowbFinancialPlannedBudgetDAO {


  @Inject
  public PowbFinancialPlannedBudgetMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deletePowbFinancialPlannedBudget(long powbFinancialPlannedBudgetId) {
    PowbFinancialPlannedBudget powbFinancialPlannedBudget = this.find(powbFinancialPlannedBudgetId);
    powbFinancialPlannedBudget.setActive(false);
    this.save(powbFinancialPlannedBudget);
  }

  @Override
  public boolean existPowbFinancialPlannedBudget(long powbFinancialPlannedBudgetID) {
    PowbFinancialPlannedBudget powbFinancialPlannedBudget = this.find(powbFinancialPlannedBudgetID);
    if (powbFinancialPlannedBudget == null) {
      return false;
    }
    return true;

  }

  @Override
  public PowbFinancialPlannedBudget find(long id) {
    return super.find(PowbFinancialPlannedBudget.class, id);

  }

  @Override
  public List<PowbFinancialPlannedBudget> findAll() {
    String query = "from " + PowbFinancialPlannedBudget.class.getName() + " where is_active=1";
    List<PowbFinancialPlannedBudget> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public PowbFinancialPlannedBudget save(PowbFinancialPlannedBudget powbFinancialPlannedBudget) {
    if (powbFinancialPlannedBudget.getId() == null) {
      super.saveEntity(powbFinancialPlannedBudget);
    } else {
      powbFinancialPlannedBudget = super.update(powbFinancialPlannedBudget);
    }


    return powbFinancialPlannedBudget;
  }


}