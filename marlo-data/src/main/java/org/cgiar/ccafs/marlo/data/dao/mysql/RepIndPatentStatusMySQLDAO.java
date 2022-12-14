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

import org.cgiar.ccafs.marlo.data.dao.RepIndPatentStatusDAO;
import org.cgiar.ccafs.marlo.data.model.RepIndPatentStatus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

@Named
public class RepIndPatentStatusMySQLDAO extends AbstractMarloDAO<RepIndPatentStatus, Long>
  implements RepIndPatentStatusDAO {


  @Inject
  public RepIndPatentStatusMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deleteRepIndPatentStatus(long repIndPatentStatusId) {
    RepIndPatentStatus repIndPatentStatus = this.find(repIndPatentStatusId);
    this.delete(repIndPatentStatus);
  }

  @Override
  public boolean existRepIndPatentStatus(long repIndPatentStatusID) {
    RepIndPatentStatus repIndPatentStatus = this.find(repIndPatentStatusID);
    if (repIndPatentStatus == null) {
      return false;
    }
    return true;

  }

  @Override
  public RepIndPatentStatus find(long id) {
    return super.find(RepIndPatentStatus.class, id);

  }

  @Override
  public List<RepIndPatentStatus> findAll() {
    String query = "from " + RepIndPatentStatus.class.getName();
    List<RepIndPatentStatus> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public RepIndPatentStatus save(RepIndPatentStatus repIndPatentStatus) {
    if (repIndPatentStatus.getId() == null) {
      super.saveEntity(repIndPatentStatus);
    } else {
      repIndPatentStatus = super.update(repIndPatentStatus);
    }


    return repIndPatentStatus;
  }


}