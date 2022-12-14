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


package org.cgiar.ccafs.marlo.interceptor.funding;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.FundingSourceManager;
import org.cgiar.ccafs.marlo.data.manager.GlobalUnitManager;
import org.cgiar.ccafs.marlo.data.manager.PhaseManager;
import org.cgiar.ccafs.marlo.data.manager.UserRoleManager;
import org.cgiar.ccafs.marlo.data.model.FundingSource;
import org.cgiar.ccafs.marlo.data.model.FundingSourceInfo;
import org.cgiar.ccafs.marlo.data.model.FundingStatusEnum;
import org.cgiar.ccafs.marlo.data.model.GlobalUnit;
import org.cgiar.ccafs.marlo.data.model.Phase;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.model.UserRole;
import org.cgiar.ccafs.marlo.security.Permission;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.dispatcher.Parameter;

public class FundingSourceInterceptor extends AbstractInterceptor implements Serializable {

  private static final long serialVersionUID = 1L;

  private Map<String, Parameter> parameters;
  private Map<String, Object> session;
  private GlobalUnit crp;
  private long fundingSourceID = 0;
  private Phase phase;
  private PhaseManager phaseManager;
  private final GlobalUnitManager crpManager;
  private final FundingSourceManager fundingSourceManager;
  private final UserRoleManager userRoleManager;

  @Inject
  public FundingSourceInterceptor(GlobalUnitManager crpManager, FundingSourceManager fundingSourceManager,
    PhaseManager phaseManager, UserRoleManager userRoleManager) {
    this.crpManager = crpManager;
    this.fundingSourceManager = fundingSourceManager;
    this.phaseManager = phaseManager;
    this.userRoleManager = userRoleManager;
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
    BaseAction baseAction = (BaseAction) invocation.getAction();
    baseAction.setSession(session);
    User user = (User) session.get(APConstants.SESSION_USER);
    baseAction.setSession(session);
    phase = baseAction.getActualPhase();
    phase = phaseManager.getPhaseById(phase.getId());
    boolean canEdit = false;
    boolean hasPermissionToEdit = false;
    boolean editParameter = false;

    // String projectParameter = ((String[]) parameters.get(APConstants.FUNDING_SOURCE_REQUEST_ID))[0];
    String fundingSourceParameter = parameters.get(APConstants.FUNDING_SOURCE_REQUEST_ID).getMultipleValues()[0];
    if (fundingSourceParameter == null) {
      throw new IllegalArgumentException("fundingSourceParameter must not be null!");
    }
    fundingSourceID = Long.parseLong(fundingSourceParameter);

    FundingSource fundingSource = fundingSourceManager.getFundingSourceById(fundingSourceID);

    if (fundingSource != null) {
      String params[] = {crp.getAcronym(), fundingSource.getId() + ""};


      if (baseAction.canAccessSuperAdmin() || baseAction.canEditCrpAdmin()) {
        canEdit = true;
      } else {
        boolean hasCPRole = false;
        List<UserRole> roles = userRoleManager.getUserRolesByUserId(user.getId());
        if (roles != null) {
          for (UserRole rol : roles) {
            if (rol.getRole() != null && rol.getRole().getAcronym() != null
              && rol.getRole().getAcronym().equals("CP")) {
              hasCPRole = true;
            }
          }
        }
        // List<FundingSource> fundingSources = fundingSourceManager.getFundingSource(user.getId(), crp.getAcronym());
        if ((hasCPRole
          || baseAction
            .hasPermission(baseAction.generatePermission(Permission.PROJECT_FUNDING_SOURCE_BASE_PERMISSION, params))
          || baseAction
            .hasPermission(baseAction.generatePermission(Permission.PROJECT_FUNDING_W1_BASE_PERMISSION, params))
          || baseAction
            .hasPermission(baseAction.generatePermission(Permission.PROJECT_FUNDING_W3_BASE_PERMISSION, params)))) {
          canEdit = true;

        }

        if (baseAction.isCrpClosed()) {
          if (!(baseAction.hasSpecificities(APConstants.CRP_PMU) && baseAction.isPMU())) {
            canEdit = false;
          }
        }
      }


      Calendar cal = Calendar.getInstance();
      /*
       * if (fundingSource.getFundingSourceInfo(baseAction.getActualPhase()).getEndDate() != null
       * && fundingSource.getFundingSourceInfo(baseAction.getActualPhase()).getStatus() != null) {
       * cal.setTime(fundingSource.getFundingSourceInfo(baseAction.getActualPhase()).getEndDate());
       * if (fundingSource.getFundingSourceInfo(baseAction.getActualPhase()).getStatus().longValue() == Long
       * .parseLong(FundingStatusEnum.Ongoing.getStatusId())
       * && baseAction.getActualPhase().getYear() > cal.get(Calendar.YEAR)) {
       * canEdit = false;
       * baseAction.setEditStatus(true);
       * }
       * }
       */
      if (fundingSource.getFundingSourceInfo(baseAction.getActualPhase()).getStatus().longValue() == Long
        .parseLong(FundingStatusEnum.Cancelled.getStatusId())

        || fundingSource.getFundingSourceInfo(baseAction.getActualPhase()).getStatus().longValue() == Long
          .parseLong(FundingStatusEnum.Complete.getStatusId())) {
        canEdit = false;
        baseAction.setEditStatus(true);
      }
      if (phase.getDescription().equals(APConstants.REPORTING)) {
        canEdit = false;
        baseAction.setCanEditPhase(false);
      }
      if (!phase.getEditable()) {
        canEdit = false;
        baseAction.setCanEditPhase(false);
      }

      FundingSourceInfo info = fundingSource.getFundingSourceInfo(this.phase);
      if (info != null && info.getBudgetType() != null && info.getBudgetType().getId() == 1) {
        if (!baseAction
          .hasPermission(baseAction.generatePermission(Permission.PROJECT_FUNDING_W1_BASE_PERMISSION, params))) {
          canEdit = false;
        }
      }

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
        hasPermissionToEdit = ((baseAction.canAccessSuperAdmin() || baseAction.canEditCrpAdmin())) ? true
          : (baseAction
            .hasPermission(baseAction.generatePermission(Permission.PROJECT_FUNDING_SOURCE_BASE_PERMISSION, params))
            || baseAction
              .hasPermission(baseAction.generatePermission(Permission.PROJECT_FUNDING_W1_BASE_PERMISSION, params)));
      }
      phase = baseAction.getActualPhase();
      phase = phaseManager.getPhaseById(phase.getId());

      /*
       * if (fundingSource.getFundingSourceInfo(phase) == null) {
       * List<FundingSourceInfo> infos =
       * fundingSource.getFundingSourceInfos().stream().filter(c -> c.isActive()).collect(Collectors.toList());
       * infos.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
       * baseAction.setAvailabePhase(false);
       * // baseAction.setActualPhase(infos.get(infos.size() - 1).getPhase());
       * }
       */
      // Set the variable that indicates if the user can edit the section
      if (parameters.get(APConstants.TRANSACTION_ID).isDefined()) {
        // String stringEditable = ((String[]) parameters.get(APConstants.EDITABLE_REQUEST))[0];

        editParameter = false;
        // If the user is not asking for edition privileges we don't need to validate them.

      }
      if (!editParameter) {
        baseAction.setEditStatus(false);
      }
      baseAction.setEditableParameter(editParameter && canEdit);
      baseAction.setCanEdit(canEdit);

    } else {
      throw new IllegalArgumentException(
        "FundingSource with fundingSourceID: " + fundingSourceID + " , could not be found");
    }
  }

}