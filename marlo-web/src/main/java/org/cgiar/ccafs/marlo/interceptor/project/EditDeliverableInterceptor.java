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

package org.cgiar.ccafs.marlo.interceptor.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.CrpManager;
import org.cgiar.ccafs.marlo.data.manager.DeliverableManager;
import org.cgiar.ccafs.marlo.data.manager.PhaseManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectManager;
import org.cgiar.ccafs.marlo.data.model.Crp;
import org.cgiar.ccafs.marlo.data.model.Deliverable;
import org.cgiar.ccafs.marlo.data.model.Phase;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectInfo;
import org.cgiar.ccafs.marlo.data.model.ProjectStatusEnum;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.NoPhaseException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class EditDeliverableInterceptor extends AbstractInterceptor implements Serializable {

  private static final long serialVersionUID = 7287623847333177230L;


  private Map<String, Object> parameters;
  private Map<String, Object> session;
  private Crp crp;
  private long deliverableId = 0;
  private Phase phase;
  private PhaseManager phaseManager;
  private DeliverableManager deliverableManager;
  private ProjectManager projectManager;
  private CrpManager crpManager;

  @Inject
  public EditDeliverableInterceptor(DeliverableManager deliverableManager, ProjectManager projectManager,
    PhaseManager phaseManager, CrpManager crpManager) {
    this.crpManager = crpManager;
    this.phaseManager = phaseManager;
    this.projectManager = projectManager;
    this.deliverableManager = deliverableManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {

    parameters = invocation.getInvocationContext().getParameters();
    session = invocation.getInvocationContext().getSession();
    crp = (Crp) session.get(APConstants.SESSION_CRP);
    crp = crpManager.getCrpById(crp.getId());
    try {
      this.setPermissionParameters(invocation);
      return invocation.invoke();
    } catch (Exception e) {
      BaseAction action = (BaseAction) invocation.getAction();
      return action.NOT_FOUND;
    }
  }

  void setPermissionParameters(ActionInvocation invocation) throws NoPhaseException {

    User user = (User) session.get(APConstants.SESSION_USER);
    BaseAction baseAction = (BaseAction) invocation.getAction();
    boolean canEdit = false;
    boolean hasPermissionToEdit = false;
    boolean editParameter = false;
    boolean canSwitchProject = false;
    baseAction.setSession(session);
    String projectParameter = ((String[]) parameters.get(APConstants.PROJECT_DELIVERABLE_REQUEST_ID))[0];
    phase = baseAction.getActualPhase(session, crp.getId());
    phase = phaseManager.getPhaseById(phase.getId());
    deliverableId = Long.parseLong(projectParameter);

    Deliverable deliverable = deliverableManager.getDeliverableById(deliverableId);

    if (deliverable != null && deliverable.isActive()) {

      String params[] = {crp.getAcronym(), deliverable.getProject().getId() + ""};

      if (baseAction.canAccessSuperAdmin() || baseAction.canEditCrpAdmin() || baseAction.canAcessCrpAdmin()) {

        if (!baseAction.isSubmit(deliverable.getProject().getId())) {

          canSwitchProject = true;
        }

        canEdit = true;
      } else {
        List<Project> projects = projectManager.getUserProjects(user.getId(), crp.getAcronym());
        if (projects.contains(deliverable.getProject()) && baseAction
          .hasPermission(baseAction.generatePermission(Permission.PROJECT_DELIVERABLE_EDIT_PERMISSION, params))) {
          canEdit = true;
        }

        if (baseAction.isSubmit(deliverable.getProject().getId())) {
          canEdit = false;

        }

        if (baseAction.isCrpClosed()) {
          if (!(baseAction.hasSpecificities(APConstants.CRP_PMU) && baseAction.isPMU())) {
            canEdit = false;
          }

        }


      }

      // TODO Validate is the project is new
      if (parameters.get(APConstants.EDITABLE_REQUEST) != null) {
        String stringEditable = ((String[]) parameters.get(APConstants.EDITABLE_REQUEST))[0];
        editParameter = stringEditable.equals("true");
        if (!editParameter) {
          baseAction.setEditableParameter(hasPermissionToEdit);
        }
      }

      // Check the permission if user want to edit or save the form
      if (editParameter || parameters.get("save") != null) {
        hasPermissionToEdit =
          ((baseAction.canAccessSuperAdmin() || baseAction.canEditCrpAdmin() || baseAction.canAcessCrpAdmin())) ? true
            : baseAction.hasPermission(
              baseAction.generatePermission(Permission.PROJECT_DELIVERABLE_LIST_EDIT_PERMISSION, params));
      }

      if (baseAction.hasPermission(baseAction.generatePermission(Permission.PROJECT__SWITCH, params))) {
        canSwitchProject = true;
      }


      if (deliverable.getDeliverableInfo(baseAction.getActualPhase()).getStatus() != null) {
        if (deliverable.getDeliverableInfo(baseAction.getActualPhase()).getStatus().intValue() == Integer
          .parseInt(ProjectStatusEnum.Complete.getStatusId())) {
          canEdit = false;
        }
      }


      if (baseAction.isReportingActive()
        && deliverable.getDeliverableInfo(baseAction.getActualPhase()).getStatus() != null
        && deliverable.getDeliverableInfo(baseAction.getActualPhase()).getStatus().intValue() == Integer
          .parseInt(ProjectStatusEnum.Complete.getStatusId())
        && deliverable.getDeliverableInfo(baseAction.getActualPhase()).getYear() == baseAction.getCurrentCycleYear()) {
        canEdit = true;
      }

      if (baseAction.isReportingActive()
        && deliverable.getDeliverableInfo(baseAction.getActualPhase()).getStatus() != null
        && deliverable.getDeliverableInfo(baseAction.getActualPhase()).getStatus().intValue() == Integer
          .parseInt(ProjectStatusEnum.Complete.getStatusId())
        && deliverable.getDeliverableInfo(baseAction.getActualPhase()).getNewExpectedYear() != null
        && deliverable.getDeliverableInfo(baseAction.getActualPhase()).getNewExpectedYear().intValue() == baseAction
          .getCurrentCycleYear()) {
        canEdit = true;
      }

      if (phase.getProjectPhases().stream()
        .filter(c -> c.isActive() && c.getProject().getId().longValue() == deliverable.getProject().getId())
        .collect(Collectors.toList()).isEmpty()) {
        List<ProjectInfo> infos =
          deliverable.getProject().getProjectInfos().stream().filter(c -> c.isActive()).collect(Collectors.toList());
        infos.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));

        baseAction.setActualPhase(infos.get(infos.size() - 1).getPhase());
      }
      if (!baseAction.getActualPhase().getEditable()) {
        canEdit = false;
      }
      // Set the variable that indicates if the user can edit the section
      baseAction.setEditableParameter(hasPermissionToEdit && canEdit);
      baseAction.setCanEdit(canEdit);
      baseAction.setCanSwitchProject(canSwitchProject);

    } else {
      throw new NullPointerException();
    }
  }

}
