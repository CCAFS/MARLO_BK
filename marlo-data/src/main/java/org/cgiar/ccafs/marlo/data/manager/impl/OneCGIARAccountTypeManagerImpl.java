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

/**************
 * @author Diego Perez - CIAT/CCAFS
 **************/

package org.cgiar.ccafs.marlo.data.manager.impl;

import org.cgiar.ccafs.marlo.data.dao.OneCGIARAccountTypeDAO;
import org.cgiar.ccafs.marlo.data.manager.OneCGIARAccountTypeManager;
import org.cgiar.ccafs.marlo.data.model.OneCGIARAccountType;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class OneCGIARAccountTypeManagerImpl implements OneCGIARAccountTypeManager {

  private OneCGIARAccountTypeDAO oneCGIARAccountTypeDAO;

  @Inject
  public OneCGIARAccountTypeManagerImpl(OneCGIARAccountTypeDAO oneCGIARAccountTypeDAO) {
    super();
    this.oneCGIARAccountTypeDAO = oneCGIARAccountTypeDAO;
  }

  @Override
  public void deleteOneCGIARAccountType(Long oneCGIARAccountTypeId) {
    this.oneCGIARAccountTypeDAO.deleteOneCGIARAccountType(oneCGIARAccountTypeId.longValue());
  }

  @Override
  public boolean existOneCGIARAccountType(Long oneCGIARAccountTypeID) {
    return this.oneCGIARAccountTypeDAO.existOneCGIARAccountType(oneCGIARAccountTypeID.longValue());
  }

  @Override
  public OneCGIARAccountType getAccountTypeByAcronym(String acronym) {
    return this.oneCGIARAccountTypeDAO.getAccountTypeByAcronym(acronym);
  }

  @Override
  public OneCGIARAccountType getAccountTypeById(Long id) {
    return oneCGIARAccountTypeDAO.getAccountTypeById(id.longValue());
  }

  @Override
  public List<OneCGIARAccountType> getAll() {
    return oneCGIARAccountTypeDAO.getAll();
  }

  @Override
  public OneCGIARAccountType save(OneCGIARAccountType oneCGIARAccountType) {
    return this.oneCGIARAccountTypeDAO.save(oneCGIARAccountType);
  }

}
