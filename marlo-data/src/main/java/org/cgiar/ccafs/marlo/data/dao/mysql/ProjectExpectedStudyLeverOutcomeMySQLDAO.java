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

import org.cgiar.ccafs.marlo.data.dao.ProjectExpectedStudyLeverOutcomeDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectExpectedStudyLeverOutcome;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

@Named
public class ProjectExpectedStudyLeverOutcomeMySQLDAO extends AbstractMarloDAO<ProjectExpectedStudyLeverOutcome, Long>
  implements ProjectExpectedStudyLeverOutcomeDAO {


  @Inject
  public ProjectExpectedStudyLeverOutcomeMySQLDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void deleteProjectExpectedStudyLeverOutcome(long projectExpectedStudyLeverOutcomeId) {
    ProjectExpectedStudyLeverOutcome projectExpectedStudyLeverOutcome = this.find(projectExpectedStudyLeverOutcomeId);
    this.delete(projectExpectedStudyLeverOutcome);
  }

  @Override
  public boolean existProjectExpectedStudyLeverOutcome(long projectExpectedStudyLeverOutcomeID) {
    ProjectExpectedStudyLeverOutcome projectExpectedStudyLeverOutcome = this.find(projectExpectedStudyLeverOutcomeID);
    if (projectExpectedStudyLeverOutcome == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectExpectedStudyLeverOutcome find(long id) {
    return super.find(ProjectExpectedStudyLeverOutcome.class, id);

  }

  @Override
  public List<ProjectExpectedStudyLeverOutcome> findAll() {
    String query = "from " + ProjectExpectedStudyLeverOutcome.class.getName();
    List<ProjectExpectedStudyLeverOutcome> list = super.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectExpectedStudyLeverOutcome> getAllStudyLeverOutcomesByStudy(long studyId) {
    String query =
      "select peslo from ProjectExpectedStudyLeverOutcome peslo where peslo.projectExpectedStudy.id = :studyId order by peslo.phase.id";

    Query createQuery = this.getSessionFactory().getCurrentSession().createQuery(query);
    createQuery.setParameter("studyId", studyId);

    List<ProjectExpectedStudyLeverOutcome> result = super.findAll(createQuery);

    return result;
  }

  @Override
  public ProjectExpectedStudyLeverOutcome getStudyLeverOutcomeByStudyLeverOutcomeAndPhase(long studyId,
    long leverOutcomeId, long idPhase) {
    String query = "select distinct pp from ProjectExpectedStudyLeverOutcome pp "
      + "where pp.projectExpectedStudy.id = :studyId and pp.phase.id = :idPhase and pp.leverOutcome.id = :leverOutcomeId";

    Query createQuery = this.getSessionFactory().getCurrentSession().createQuery(query);
    createQuery.setParameter("studyId", studyId);
    createQuery.setParameter("idPhase", idPhase);
    createQuery.setParameter("leverOutcomeId", leverOutcomeId);

    Object findSingleResult = super.findSingleResult(ProjectExpectedStudyLeverOutcome.class, createQuery);
    ProjectExpectedStudyLeverOutcome projectExpectedStudyLeverOutcome =
      (ProjectExpectedStudyLeverOutcome) findSingleResult;

    return projectExpectedStudyLeverOutcome;
  }

  @Override
  public ProjectExpectedStudyLeverOutcome save(ProjectExpectedStudyLeverOutcome projectExpectedStudyLeverOutcome) {
    if (projectExpectedStudyLeverOutcome.getId() == null) {
      super.saveEntity(projectExpectedStudyLeverOutcome);
    } else {
      projectExpectedStudyLeverOutcome = super.update(projectExpectedStudyLeverOutcome);
    }


    return projectExpectedStudyLeverOutcome;
  }
}