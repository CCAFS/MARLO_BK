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
package org.cgiar.ccafs.marlo.data.manager.impl;


import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.dao.PhaseDAO;
import org.cgiar.ccafs.marlo.data.dao.ProjectInnovationDeliverableDAO;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationDeliverableManager;
import org.cgiar.ccafs.marlo.data.model.Phase;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationDeliverable;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author CCAFS
 */
@Named
public class ProjectInnovationDeliverableManagerImpl implements ProjectInnovationDeliverableManager {


  private ProjectInnovationDeliverableDAO projectInnovationDeliverableDAO;
  // Managers
  private PhaseDAO phaseDAO;


  @Inject
  public ProjectInnovationDeliverableManagerImpl(ProjectInnovationDeliverableDAO projectInnovationDeliverableDAO,
    PhaseDAO phaseDAO) {
    this.projectInnovationDeliverableDAO = projectInnovationDeliverableDAO;
    this.phaseDAO = phaseDAO;


  }

  @Override
  public void deleteProjectInnovationDeliverable(long projectInnovationDeliverableId) {

    ProjectInnovationDeliverable projectInnovationDeliverable =
      this.getProjectInnovationDeliverableById(projectInnovationDeliverableId);


    // Conditions to Project Innovation Works In AR phase and Upkeep Phase
    if (projectInnovationDeliverable.getPhase().getDescription().equals(APConstants.PLANNING)
      && projectInnovationDeliverable.getPhase().getNext() != null) {
      this.deleteProjectInnovationDeliverablePhase(projectInnovationDeliverable.getPhase().getNext(),
        projectInnovationDeliverable.getProjectInnovation().getId(), projectInnovationDeliverable);
    }

    if (projectInnovationDeliverable.getPhase().getDescription().equals(APConstants.REPORTING)) {
      if (projectInnovationDeliverable.getPhase().getNext() != null
        && projectInnovationDeliverable.getPhase().getNext().getNext() != null) {
        Phase upkeepPhase = projectInnovationDeliverable.getPhase().getNext().getNext();
        if (upkeepPhase != null) {
          this.deleteProjectInnovationDeliverablePhase(upkeepPhase,
            projectInnovationDeliverable.getProjectInnovation().getId(), projectInnovationDeliverable);
        }
      }
    }

    projectInnovationDeliverableDAO.deleteProjectInnovationDeliverable(projectInnovationDeliverableId);
  }

  public void deleteProjectInnovationDeliverablePhase(Phase next, long innovationID,
    ProjectInnovationDeliverable projectInnovationDeliverable) {
    Phase phase = phaseDAO.find(next.getId());

    List<ProjectInnovationDeliverable> projectInnovationDeliverables = phase.getProjectInnovationDeliverables().stream()
      .filter(c -> c.isActive() && c.getProjectInnovation().getId().longValue() == innovationID
        && c.getDeliverable().getId().equals(projectInnovationDeliverable.getDeliverable().getId()))
      .collect(Collectors.toList());

    for (ProjectInnovationDeliverable projectInnovationDeliverableDB : projectInnovationDeliverables) {
      projectInnovationDeliverableDAO.deleteProjectInnovationDeliverable(projectInnovationDeliverableDB.getId());
    }

    if (phase.getNext() != null) {
      this.deleteProjectInnovationDeliverablePhase(phase.getNext(), innovationID, projectInnovationDeliverable);
    }
  }


  @Override
  public boolean existProjectInnovationDeliverable(long projectInnovationDeliverableID) {

    return projectInnovationDeliverableDAO.existProjectInnovationDeliverable(projectInnovationDeliverableID);
  }

  @Override
  public List<ProjectInnovationDeliverable> findAll() {

    return projectInnovationDeliverableDAO.findAll();

  }

  @Override
  public List<ProjectInnovationDeliverable> getAllInnovationDeliverablesByInnovation(Long innovationId) {
    return this.projectInnovationDeliverableDAO.getAllInnovationDeliverablesByInnovation(innovationId.longValue());
  }

  @Override
  public ProjectInnovationDeliverable getProjectInnovationDeliverableById(long projectInnovationDeliverableID) {

    return projectInnovationDeliverableDAO.find(projectInnovationDeliverableID);
  }

  public void saveInnovationDeliverablePhase(Phase next, long innovationid,
    ProjectInnovationDeliverable projectInnovationDeliverable) {
    Phase phase = phaseDAO.find(next.getId());

    List<ProjectInnovationDeliverable> projectInnovationDeliverables = phase.getProjectInnovationDeliverables().stream()
      .filter(c -> c.getProjectInnovation().getId().longValue() == innovationid
        && c.getDeliverable().getId().equals(projectInnovationDeliverable.getDeliverable().getId()))
      .collect(Collectors.toList());

    if (projectInnovationDeliverables.isEmpty()) {
      ProjectInnovationDeliverable projectInnovationDeliverableAdd = new ProjectInnovationDeliverable();
      projectInnovationDeliverableAdd.setProjectInnovation(projectInnovationDeliverable.getProjectInnovation());
      projectInnovationDeliverableAdd.setPhase(phase);
      projectInnovationDeliverableAdd.setDeliverable(projectInnovationDeliverable.getDeliverable());
      projectInnovationDeliverableDAO.save(projectInnovationDeliverableAdd);
    }


    if (phase.getNext() != null) {
      this.saveInnovationDeliverablePhase(phase.getNext(), innovationid, projectInnovationDeliverable);
    }
  }

  @Override
  public ProjectInnovationDeliverable
    saveProjectInnovationDeliverable(ProjectInnovationDeliverable projectInnovationDeliverable) {

    ProjectInnovationDeliverable deliverable = projectInnovationDeliverableDAO.save(projectInnovationDeliverable);
    Phase phase = phaseDAO.find(deliverable.getPhase().getId());


    // Conditions to Project Innovation Works In AR phase and Upkeep Phase
    if (phase.getDescription().equals(APConstants.PLANNING) && phase.getNext() != null) {
      this.saveInnovationDeliverablePhase(deliverable.getPhase().getNext(), deliverable.getProjectInnovation().getId(),
        projectInnovationDeliverable);
    }

    if (phase.getDescription().equals(APConstants.REPORTING)) {
      if (phase.getNext() != null && phase.getNext().getNext() != null) {
        Phase upkeepPhase = phase.getNext().getNext();
        if (upkeepPhase != null) {
          this.saveInnovationDeliverablePhase(upkeepPhase, deliverable.getProjectInnovation().getId(),
            projectInnovationDeliverable);
        }
      }
    }

    return deliverable;
  }
}
