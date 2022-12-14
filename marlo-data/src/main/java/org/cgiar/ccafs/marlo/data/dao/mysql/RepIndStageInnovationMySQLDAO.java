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

import org.cgiar.ccafs.marlo.data.dao.RepIndStageInnovationDAO;
import org.cgiar.ccafs.marlo.data.model.RepIndStageInnovation;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

@Named
public class RepIndStageInnovationMySQLDAO extends AbstractMarloDAO<RepIndStageInnovation, Long>
  implements RepIndStageInnovationDAO {


  @Inject
  public RepIndStageInnovationMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deleteRepIndStageInnovation(long repIndStageInnovationId) {
    RepIndStageInnovation repIndStageInnovation = this.find(repIndStageInnovationId);
    this.delete(repIndStageInnovation);
  }

  @Override
  public boolean existRepIndStageInnovation(long repIndStageInnovationID) {
    RepIndStageInnovation repIndStageInnovation = this.find(repIndStageInnovationID);
    if (repIndStageInnovation == null) {
      return false;
    }
    return true;

  }

  @Override
  public RepIndStageInnovation find(long id) {
    return super.find(RepIndStageInnovation.class, id);

  }

  @Override
  public List<RepIndStageInnovation> findAll() {
    String query = "from " + RepIndStageInnovation.class.getName();
    List<RepIndStageInnovation> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public RepIndStageInnovation save(RepIndStageInnovation repIndStageInnovation) {
    if (repIndStageInnovation.getId() == null) {
      super.saveEntity(repIndStageInnovation);
    } else {
      repIndStageInnovation = super.update(repIndStageInnovation);
    }


    return repIndStageInnovation;
  }


}