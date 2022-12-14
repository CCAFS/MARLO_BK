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

import org.cgiar.ccafs.marlo.data.dao.ProjectInnovationOrganizationDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationOrganization;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

@Named
public class ProjectInnovationOrganizationMySQLDAO extends AbstractMarloDAO<ProjectInnovationOrganization, Long>
  implements ProjectInnovationOrganizationDAO {


  @Inject
  public ProjectInnovationOrganizationMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deleteProjectInnovationOrganization(long projectInnovationOrganizationId) {
    ProjectInnovationOrganization projectInnovationOrganization = this.find(projectInnovationOrganizationId);
    this.delete(projectInnovationOrganization);
  }

  @Override
  public boolean existProjectInnovationOrganization(long projectInnovationOrganizationID) {
    ProjectInnovationOrganization projectInnovationOrganization = this.find(projectInnovationOrganizationID);
    if (projectInnovationOrganization == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectInnovationOrganization find(long id) {
    return super.find(ProjectInnovationOrganization.class, id);

  }

  @Override
  public List<ProjectInnovationOrganization> findAll() {
    String query = "from " + ProjectInnovationOrganization.class.getName();
    List<ProjectInnovationOrganization> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectInnovationOrganization> getAllInnovationOrganizationsByInnovation(long innovationId) {
    String query =
      "select pio from ProjectInnovationOrganization pio where pio.projectInnovation.id = :innovationId order by pio.phase.id";

    Query createQuery = this.getSessionFactory().getCurrentSession().createQuery(query);
    createQuery.setParameter("innovationId", innovationId);

    List<ProjectInnovationOrganization> result = super.findAll(createQuery);

    return result;
  }

  @Override
  public ProjectInnovationOrganization projectInnovationOrganization(long innovationID, long organizationID,
    long phaseID) {
    String query = "from " + ProjectInnovationOrganization.class.getName() + " where project_innovation_id='"
      + innovationID + "' AND rep_ind_organization_type_id='" + organizationID + "' AND id_phase='" + phaseID + "'";
    List<ProjectInnovationOrganization> list = super.findAll(query);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;

  }

  @Override
  public ProjectInnovationOrganization save(ProjectInnovationOrganization projectInnovationOrganization) {
    if (projectInnovationOrganization.getId() == null) {
      super.saveEntity(projectInnovationOrganization);
    } else {
      projectInnovationOrganization = super.update(projectInnovationOrganization);
    }


    return projectInnovationOrganization;
  }
}