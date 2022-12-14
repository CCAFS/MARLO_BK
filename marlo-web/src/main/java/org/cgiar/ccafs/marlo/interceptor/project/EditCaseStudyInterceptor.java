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
import org.cgiar.ccafs.marlo.data.manager.CaseStudyManager;
import org.cgiar.ccafs.marlo.data.manager.CaseStudyProjectManager;
import org.cgiar.ccafs.marlo.data.manager.GlobalUnitManager;
import org.cgiar.ccafs.marlo.data.manager.LiaisonUserManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectManager;
import org.cgiar.ccafs.marlo.data.model.CaseStudy;
import org.cgiar.ccafs.marlo.data.model.CaseStudyProject;
import org.cgiar.ccafs.marlo.data.model.GlobalUnit;
import org.cgiar.ccafs.marlo.data.model.Institution;
import org.cgiar.ccafs.marlo.data.model.LiaisonInstitution;
import org.cgiar.ccafs.marlo.data.model.LiaisonUser;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectPartner;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.security.Permission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.dispatcher.Parameter;

/**
 * @author Hermes Jim??nez - CIAT/CCAFS
 */
public class EditCaseStudyInterceptor extends AbstractInterceptor implements Serializable {

  private static final long serialVersionUID = 7287623847333177230L;

  private Map<String, Parameter> parameters;
  private Map<String, Object> session;
  private GlobalUnit crp;
  private long caseStudyId = 0;

  private CaseStudyManager caseStudyManager;
  private CaseStudyProjectManager caseStudyProjectManager;
  private ProjectManager projectManager;
  // GlobalUnit Manager
  private GlobalUnitManager crpManager;
  private final LiaisonUserManager liaisonUserManager;
  private GlobalUnit loggedCrp;

  @Inject
  public EditCaseStudyInterceptor(CaseStudyManager caseStudyManager, ProjectManager projectManager,
    GlobalUnitManager crpManager, CaseStudyProjectManager caseStudyProjectManager,
    LiaisonUserManager liaisonUserManager) {
    this.crpManager = crpManager;
    this.projectManager = projectManager;
    this.caseStudyManager = caseStudyManager;
    this.caseStudyProjectManager = caseStudyProjectManager;
    this.liaisonUserManager = liaisonUserManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {

    parameters = invocation.getInvocationContext().getParameters();
    session = invocation.getInvocationContext().getSession();
    crp = (GlobalUnit) session.get(APConstants.SESSION_CRP);
    crp = crpManager.getGlobalUnitById(crp.getId());
    try {
      this.setPermissionParameters(invocation);
      return invocation.invoke();
    } catch (Exception e) {
      e.printStackTrace();
      BaseAction action = (BaseAction) invocation.getAction();
      return action.NOT_FOUND;
    }
  }

  void setPermissionParameters(ActionInvocation invocation) {

    User user = (User) session.get(APConstants.SESSION_USER);
    BaseAction baseAction = (BaseAction) invocation.getAction();
    boolean canEdit = false;
    boolean hasPermissionToEdit = false;
    boolean editParameter = false;
    boolean canSwitchProject = false;
    baseAction.setSession(session);
    loggedCrp = (GlobalUnit) session.get(APConstants.SESSION_CRP);
    loggedCrp = crpManager.getGlobalUnitById(loggedCrp.getId());

    // String projectParameter = ((String[]) parameters.get(APConstants.CASE_STUDY_REQUEST_ID))[0];
    String projectParameter = parameters.get(APConstants.CASE_STUDY_REQUEST_ID).getMultipleValues()[0];
    caseStudyId = Long.parseLong(projectParameter);
    CaseStudy caseStudy = caseStudyManager.getCaseStudyById(caseStudyId);
    String projectIDParameter;
    boolean contactPointEditProject = baseAction.hasSpecificities(APConstants.CRP_CONTACT_POINT_EDIT_PROJECT);

    try {
      // projectIDParameter = ((String[]) parameters.get(APConstants.PROJECT_REQUEST_ID))[0];
      projectIDParameter = parameters.get(APConstants.PROJECT_REQUEST_ID).getMultipleValues()[0];
    } catch (Exception e) {
      projectIDParameter = String.valueOf(caseStudy.getCaseStudyProjects().stream().filter(cs -> cs.isActive())
        .collect(Collectors.toList()).get(0).getProject().getId());
    }

    Project project = projectManager.getProjectById(Long.parseLong(projectIDParameter));

    if (caseStudy != null && caseStudy.isActive()) {

      String params[] = {crp.getAcronym(), projectIDParameter};

      if (baseAction.canAccessSuperAdmin() || baseAction.canEditCrpAdmin()) {

        canEdit = true;
        canSwitchProject = true;

      } else {
        List<Project> projects = projectManager.getUserProjects(user.getId(), crp.getAcronym());
        if (projects.contains(project) && baseAction
          .hasPermission(baseAction.generatePermission(Permission.PROJECT_CASE_STUDY_EDIT_PERMISSION, params))) {
          canEdit = true;

        }
        if (baseAction.isSubmit(project.getId()) && !baseAction.getActualPhase().getUpkeep()) {
          canEdit = false;

        }
        if (baseAction.isCrpClosed()) {
          if (!(baseAction.hasSpecificities(APConstants.CRP_PMU) && baseAction.isPMU())) {
            canEdit = false;
          }

        }

      }

      // TODO Validate is the project is new
      if (parameters.get(APConstants.EDITABLE_REQUEST).isDefined()) {
        // String stringEditable = ((String[]) parameters.get(APConstants.EDITABLE_REQUEST))[0];
        String stringEditable = parameters.get(APConstants.EDITABLE_REQUEST).getMultipleValues()[0];
        editParameter = stringEditable.equals("true");
        if (!editParameter) {
          baseAction.setEditableParameter(hasPermissionToEdit);
        }
      }

      // Check the permission if user want to edit or save the form
      if (editParameter || parameters.get("save").isDefined()) {
        hasPermissionToEdit = ((baseAction.canAccessSuperAdmin() || baseAction.canEditCrpAdmin())) ? true : baseAction
          .hasPermission(baseAction.generatePermission(Permission.PROJECT_CASE_STUDY_EDIT_PERMISSION, params));
      }

      LiaisonUser lUser = liaisonUserManager.getLiaisonUserByUserId(user.getId(), loggedCrp.getId());
      if (contactPointEditProject && lUser != null) {
        LiaisonInstitution liaisonInstitution = lUser.getLiaisonInstitution();
        ProjectPartner projectPartner = project.getLeader();

        Institution institutionProject = projectPartner.getInstitution();

        Institution institutionCp = liaisonInstitution.getInstitution();

        if (institutionCp != null) {
          if (institutionCp.getId().equals(institutionProject.getId())) {
            canSwitchProject = true;
          } else {
            if (baseAction.hasPermission(baseAction.generatePermission(Permission.PROJECT__SWITCH, params))) {
              canSwitchProject = true;
            }
          }
        } else {
          if (baseAction.hasPermission(baseAction.generatePermission(Permission.PROJECT__SWITCH, params))) {
            canSwitchProject = true;
          }
        }
      } else {
        if (baseAction.hasPermission(baseAction.generatePermission(Permission.PROJECT__SWITCH, params))) {
          canSwitchProject = true;
        }
      }

      List<CaseStudyProject> caseStudyProjects = new ArrayList<>(caseStudy.getCaseStudyProjects().stream()
        .filter(
          cs -> cs.isActive() && cs.getProject().getId().longValue() == project.getId().longValue() && cs.isActive())
        .collect(Collectors.toList()));

      if (caseStudyProjects.isEmpty()) {
        canEdit = false;
      }
      if (caseStudy.getYear() != baseAction.getCurrentCycleYear()) {
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
