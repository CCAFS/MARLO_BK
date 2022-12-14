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

import org.cgiar.ccafs.marlo.data.dao.PowbFinancialPlanDAO;
import org.cgiar.ccafs.marlo.data.model.PowbFinancialPlan;

import java.util.List;

import javax.inject.Named;
import javax.inject.Inject;
import org.hibernate.SessionFactory;

@Named
public class PowbFinancialPlanMySQLDAO extends AbstractMarloDAO<PowbFinancialPlan, Long> implements PowbFinancialPlanDAO {


  @Inject
  public PowbFinancialPlanMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deletePowbFinancialPlan(long powbFinancialPlanId) {
    PowbFinancialPlan powbFinancialPlan = this.find(powbFinancialPlanId);
    powbFinancialPlan.setActive(false);
    this.save(powbFinancialPlan);
  }

  @Override
  public boolean existPowbFinancialPlan(long powbFinancialPlanID) {
    PowbFinancialPlan powbFinancialPlan = this.find(powbFinancialPlanID);
    if (powbFinancialPlan == null) {
      return false;
    }
    return true;

  }

  @Override
  public PowbFinancialPlan find(long id) {
    return super.find(PowbFinancialPlan.class, id);

  }

  @Override
  public List<PowbFinancialPlan> findAll() {
    String query = "from " + PowbFinancialPlan.class.getName() + " where is_active=1";
    List<PowbFinancialPlan> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public PowbFinancialPlan save(PowbFinancialPlan powbFinancialPlan) {
    if (powbFinancialPlan.getId() == null) {
      super.saveEntity(powbFinancialPlan);
    } else {
      powbFinancialPlan = super.update(powbFinancialPlan);
    }


    return powbFinancialPlan;
  }


}