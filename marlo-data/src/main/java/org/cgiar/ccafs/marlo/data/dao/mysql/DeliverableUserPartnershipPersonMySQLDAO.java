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

import org.cgiar.ccafs.marlo.data.dao.DeliverableUserPartnershipPersonDAO;
import org.cgiar.ccafs.marlo.data.model.DeliverableUserPartnershipPerson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

@Named
public class DeliverableUserPartnershipPersonMySQLDAO extends AbstractMarloDAO<DeliverableUserPartnershipPerson, Long> implements DeliverableUserPartnershipPersonDAO {


  @Inject
  public DeliverableUserPartnershipPersonMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deleteDeliverableUserPartnershipPerson(long deliverableUserPartnershipPersonId) {
    DeliverableUserPartnershipPerson deliverableUserPartnershipPerson = this.find(deliverableUserPartnershipPersonId);
    deliverableUserPartnershipPerson.setActive(false);
    this.update(deliverableUserPartnershipPerson);
  }

  @Override
  public boolean existDeliverableUserPartnershipPerson(long deliverableUserPartnershipPersonID) {
    DeliverableUserPartnershipPerson deliverableUserPartnershipPerson = this.find(deliverableUserPartnershipPersonID);
    if (deliverableUserPartnershipPerson == null) {
      return false;
    }
    return true;

  }

  @Override
  public DeliverableUserPartnershipPerson find(long id) {
    return super.find(DeliverableUserPartnershipPerson.class, id);

  }

  @Override
  public List<DeliverableUserPartnershipPerson> findAll() {
    String query = "from " + DeliverableUserPartnershipPerson.class.getName() + " where is_active=1";
    List<DeliverableUserPartnershipPerson> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public DeliverableUserPartnershipPerson save(DeliverableUserPartnershipPerson deliverableUserPartnershipPerson) {
    if (deliverableUserPartnershipPerson.getId() == null) {
      super.saveEntity(deliverableUserPartnershipPerson);
    } else {
      deliverableUserPartnershipPerson = super.update(deliverableUserPartnershipPerson);
    }


    return deliverableUserPartnershipPerson;
  }


}