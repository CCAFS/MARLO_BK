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

import org.cgiar.ccafs.marlo.data.dao.SrfSloIdoDAO;
import org.cgiar.ccafs.marlo.data.model.SrfSloIdo;

import java.util.List;

import com.google.inject.Inject;

public class SrfSloIdoMySQLDAO implements SrfSloIdoDAO {

  private StandardDAO dao;

  @Inject
  public SrfSloIdoMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteSrfSloIdo(long srfSloIdoId) {
    SrfSloIdo srfSloIdo = this.find(srfSloIdoId);
    srfSloIdo.setActive(false);
    return this.save(srfSloIdo) > 0;
  }

  @Override
  public boolean existSrfSloIdo(long srfSloIdoID) {
    SrfSloIdo srfSloIdo = this.find(srfSloIdoID);
    if (srfSloIdo == null) {
      return false;
    }
    return true;

  }

  @Override
  public SrfSloIdo find(long id) {
    return dao.find(SrfSloIdo.class, id);

  }

  @Override
  public List<SrfSloIdo> findAll() {
    String query = "from " + SrfSloIdo.class.getName() + " where is_active=1";
    List<SrfSloIdo> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public long save(SrfSloIdo srfSloIdo) {
    dao.saveOrUpdate(srfSloIdo);
    return srfSloIdo.getId();
  }


}