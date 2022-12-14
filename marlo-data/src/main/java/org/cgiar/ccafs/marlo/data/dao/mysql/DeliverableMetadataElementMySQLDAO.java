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

import org.cgiar.ccafs.marlo.data.dao.DeliverableMetadataElementDAO;
import org.cgiar.ccafs.marlo.data.model.DeliverableMetadataElement;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

@Named
public class DeliverableMetadataElementMySQLDAO extends AbstractMarloDAO<DeliverableMetadataElement, Long>
  implements DeliverableMetadataElementDAO {


  @Inject
  public DeliverableMetadataElementMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deleteDeliverableMetadataElement(long deliverableMetadataElementId) {
    DeliverableMetadataElement deliverableMetadataElement = this.find(deliverableMetadataElementId);
    this.save(deliverableMetadataElement);
  }

  @Override
  public boolean existDeliverableMetadataElement(long deliverableMetadataElementID) {
    DeliverableMetadataElement deliverableMetadataElement = this.find(deliverableMetadataElementID);
    if (deliverableMetadataElement == null) {
      return false;
    }
    return true;

  }

  @Override
  public DeliverableMetadataElement find(long id) {
    return super.find(DeliverableMetadataElement.class, id);

  }

  @Override
  public List<DeliverableMetadataElement> findAll() {
    String query = "from " + DeliverableMetadataElement.class.getName();
    List<DeliverableMetadataElement> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }


  @Override
  public List<DeliverableMetadataElement> findAllByPhaseAndDeliverable(long phaseId, long deliverableId) {
    String query = "select distinct dm from DeliverableMetadataElement dm where phase.id = :phaseId "
      + "and deliverable.id= :deliverableId";
    Query createQuery = this.getSessionFactory().getCurrentSession().createQuery(query);
    createQuery.setParameter("phaseId", phaseId);
    createQuery.setParameter("deliverableId", deliverableId);
    List<DeliverableMetadataElement> result = super.findAll(createQuery);
    return result;
  }

  @Override
  public DeliverableMetadataElement findMetadataElementByPhaseAndDeliverable(long phaseId, long deliverableId,
    long metadataElementId) {
    String query = "select distinct dm from DeliverableMetadataElement dm where phase.id = :phaseId "
      + "and deliverable.id= :deliverableId and metadataElement.id = :metadataElementId";
    Query createQuery = this.getSessionFactory().getCurrentSession().createQuery(query);
    createQuery.setParameter("phaseId", phaseId);
    createQuery.setParameter("deliverableId", deliverableId);
    createQuery.setParameter("metadataElementId", metadataElementId);

    List<DeliverableMetadataElement> result = super.findAll(createQuery);
    if (result != null && !result.isEmpty()) {
      return result.get(0);
    }

    return null;
  }

  @Override
  public DeliverableMetadataElement save(DeliverableMetadataElement deliverableMetadataElement) {
    if (deliverableMetadataElement.getId() == null) {
      super.saveEntity(deliverableMetadataElement);
    } else {
      deliverableMetadataElement = super.update(deliverableMetadataElement);
    }

    return deliverableMetadataElement;
  }


}