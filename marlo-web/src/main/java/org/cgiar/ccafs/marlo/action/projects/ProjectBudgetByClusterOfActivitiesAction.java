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


package org.cgiar.ccafs.marlo.action.projects;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.AuditLogManager;
import org.cgiar.ccafs.marlo.data.manager.BudgetTypeManager;
import org.cgiar.ccafs.marlo.data.manager.CrpClusterOfActivityManager;
import org.cgiar.ccafs.marlo.data.manager.CrpManager;
import org.cgiar.ccafs.marlo.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectBilateralCofinancingManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectBudgetManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectBudgetsCluserActvityManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectManager;
import org.cgiar.ccafs.marlo.data.model.Crp;
import org.cgiar.ccafs.marlo.data.model.CrpClusterOfActivity;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectBudget;
import org.cgiar.ccafs.marlo.data.model.ProjectBudgetsCluserActvity;
import org.cgiar.ccafs.marlo.data.model.ProjectClusterActivity;
import org.cgiar.ccafs.marlo.security.APCustomRealm;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConfig;
import org.cgiar.ccafs.marlo.utils.AutoSaveReader;
import org.cgiar.ccafs.marlo.validation.projects.ProjectBudgetsValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class ProjectBudgetByClusterOfActivitiesAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = -7931655721857302103L;


  private CrpClusterOfActivityManager crpClusterOfActivityManager;


  private BudgetTypeManager budgetTypeManager;


  private ProjectManager projectManager;


  private ProjectBudgetsCluserActvityManager projectBudgetsCluserActvityManager;


  private CrpManager crpManager;
  private long projectID;
  private Crp loggedCrp;
  private Project project;
  private String transaction;
  private AuditLogManager auditLogManager;

  @Inject
  public ProjectBudgetByClusterOfActivitiesAction(APConfig config,
    CrpClusterOfActivityManager crpClusterOfActivityManager, ProjectManager projectManager, CrpManager crpManager,
    ProjectBudgetManager projectBudgetManager, AuditLogManager auditLogManager, BudgetTypeManager budgetTypeManager,
    ProjectBilateralCofinancingManager projectBilateralCofinancingManager,
    LiaisonInstitutionManager liaisonInstitutionManager, ProjectBudgetsValidator projectBudgetsValidator,
    ProjectBudgetsCluserActvityManager projectBudgetsCluserActvityManager) {
    super(config);
    this.crpClusterOfActivityManager = crpClusterOfActivityManager;
    this.projectManager = projectManager;
    this.crpManager = crpManager;

    this.auditLogManager = auditLogManager;
    this.budgetTypeManager = budgetTypeManager;
    this.projectBudgetsCluserActvityManager = projectBudgetsCluserActvityManager;
  }


  @Override
  public String cancel() {
    Path path = this.getAutoSaveFilePath();

    if (path.toFile().exists()) {

      boolean fileDeleted = path.toFile().delete();
      System.out.println(fileDeleted);
    }

    this.setDraft(false);
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionWarning(this.getText("cancel.autoSave") + validationMessage);
    } else {
      this.addActionMessage(this.getText("cancel.autoSave"));
    }
    messages = this.getActionMessages();

    return SUCCESS;
  }

  /**
   * This method clears the cache and re-load the user permissions in the next iteration.
   */
  public void clearPermissionsCache() {
    ((APCustomRealm) securityContext.getRealm())
      .clearCachedAuthorizationInfo(securityContext.getSubject().getPrincipals());
  }

  private Path getAutoSaveFilePath() {
    String composedClassName = project.getClass().getSimpleName();
    String actionFile = this.getActionName().replace("/", "_");
    String autoSaveFile = project.getId() + "_" + composedClassName + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
  }


  public ProjectBudgetsCluserActvity getBudget(Long activitiyId, int year, long type) {
    if (project.getBudgetsCluserActvities() == null) {
      project.setBudgetsCluserActvities(new ArrayList<>());
    }
    return project.getBudgetsCluserActvities().get(this.getIndexBudget(activitiyId, year, type));
  }

  public int getIndexBudget(Long activitiyId, int year, long type) {
    if (project.getBudgetsCluserActvities() != null) {
      int i = 0;
      for (ProjectBudgetsCluserActvity projectBudget : project.getBudgetsCluserActvities()) {
        if (projectBudget.getCrpClusterOfActivity() != null) {
          if (projectBudget.getCrpClusterOfActivity().getId().longValue() == activitiyId.longValue()
            && year == projectBudget.getYear() && type == projectBudget.getBudgetType().getId().longValue()) {
            return i;
          }

        }
        i++;
      }

    } else {
      project.setBudgetsCluserActvities(new ArrayList<>());
    }

    ProjectBudgetsCluserActvity projectBudget = new ProjectBudgetsCluserActvity();
    projectBudget.setCrpClusterOfActivity(crpClusterOfActivityManager.getCrpClusterOfActivityById(activitiyId));
    projectBudget.setYear(year);
    projectBudget.setBudgetType(budgetTypeManager.getBudgetTypeById(type));
    project.getBudgetsCluserActvities().add(projectBudget);

    return this.getIndexBudget(activitiyId, year, type);
  }

  public Crp getLoggedCrp() {
    return loggedCrp;
  }

  public Project getProject() {
    return project;
  }


  public long getProjectID() {
    return projectID;
  }


  public String getTransaction() {
    return transaction;
  }


  public boolean hasBudgets(Long type, int year) {
    Project projectBD = projectManager.getProjectById(projectID);
    List<ProjectBudget> budgets = projectBD.getProjectBudgets()
      .stream().filter(c -> c.isActive() && c.getYear() == year
        && c.getBudgetType().getId().longValue() == type.longValue() && (c.getAmount() != null && c.getAmount() > 0))
      .collect(Collectors.toList());

    return budgets.size() > 0;
  }

  @Override
  public void prepare() throws Exception {
    projectID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    loggedCrp = (Crp) this.getSession().get(APConstants.SESSION_CRP);
    loggedCrp = crpManager.getCrpById(loggedCrp.getId());


    if (this.getRequest().getParameter(APConstants.TRANSACTION_ID) != null) {


      transaction = StringUtils.trim(this.getRequest().getParameter(APConstants.TRANSACTION_ID));
      Project history = (Project) auditLogManager.getHistory(transaction);

      if (history != null) {
        project = history;
      } else {
        this.transaction = null;

        this.setTransaction("-1");
      }

    } else {
      project = projectManager.getProjectById(projectID);
    }


    if (project != null) {
      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists() && this.getCurrentUser().isAutoSave()) {

        BufferedReader reader = null;

        reader = new BufferedReader(new FileReader(path.toFile()));

        Gson gson = new GsonBuilder().create();


        JsonObject jReader = gson.fromJson(reader, JsonObject.class);

        AutoSaveReader autoSaveReader = new AutoSaveReader();

        project = (Project) autoSaveReader.readFromJson(jReader);
        Project projectDb = projectManager.getProjectById(project.getId());
        project.setProjectEditLeader(projectDb.isProjectEditLeader());
        reader.close();

        this.setDraft(true);
      } else {
        this.setDraft(false);


        project.setBudgetsCluserActvities(
          project.getProjectBudgetsCluserActvities().stream().filter(c -> c.isActive()).collect(Collectors.toList()));


      }


      Project projectBD = projectManager.getProjectById(projectID);
      project.setStartDate(projectBD.getStartDate());
      project.setEndDate(projectBD.getEndDate());


      List<CrpClusterOfActivity> activities = new ArrayList<CrpClusterOfActivity>();
      for (ProjectClusterActivity crpClusterOfActivity : projectBD.getProjectClusterActivities().stream()
        .filter(c -> c.isActive()).collect(Collectors.toList())) {
        activities.add(crpClusterOfActivity.getCrpClusterOfActivity());
      }
      project.setCrpActivities(activities);

    }
    if (project.getBudgetsCluserActvities() != null) {
      for (ProjectBudgetsCluserActvity projectBudgetsCluserActvity : project.getBudgetsCluserActvities()) {
        projectBudgetsCluserActvity.setCrpClusterOfActivity(crpClusterOfActivityManager
          .getCrpClusterOfActivityById(projectBudgetsCluserActvity.getCrpClusterOfActivity().getId()));
      }
    }

    String params[] = {loggedCrp.getAcronym(), project.getId() + ""};
    this.setBasePermission(this.getText(Permission.PROJECT_BUDGET_CLUSTER_BASE_PERMISSION, params));


    if (this.isHttpPost()) {
      if (project.getCrpActivities() != null) {
        project.getCrpActivities().clear();
      }

      if (project.getBudgetsCluserActvities() != null) {
        project.getBudgetsCluserActvities().clear();
      }


    }

  }

  @Override
  public String save() {
    if (this.hasPermission("canEdit")) {
      this.saveBasicBudgets();

      List<String> relationsName = new ArrayList<>();
      relationsName.add(APConstants.PROJECT_BUDGETS_ACTVITIES_RELATION);

      project = projectManager.getProjectById(projectID);
      project.setModifiedBy(this.getCurrentUser());
      project.setActiveSince(new Date());
      projectManager.saveProject(project, this.getActionName(), relationsName);
      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists()) {
        path.toFile().delete();
      }
      Collection<String> messages = this.getActionMessages();
      if (!messages.isEmpty()) {
        String validationMessage = messages.iterator().next();
        this.setActionMessages(null);
        this.addActionWarning(this.getText("saving.saved") + validationMessage);
      } else {
        this.addActionMessage(this.getText("saving.saved"));
      }
      return SUCCESS;
    } else {
      return NOT_AUTHORIZED;
    }

  }

  public void saveBasicBudgets() {
    Project projectDB = projectManager.getProjectById(projectID);


    projectDB.setBudgetsCluserActvities(
      projectDB.getProjectBudgetsCluserActvities().stream().filter(c -> c.isActive()).collect(Collectors.toList()));


    for (ProjectBudgetsCluserActvity projectBudget : projectDB.getBudgetsCluserActvities().stream()
      .filter(c -> c.isActive()).collect(Collectors.toList())) {

      if (project.getBudgetsCluserActvities() == null) {
        project.setBudgetsCluserActvities(new ArrayList<>());
      }
      if (projectBudget.getYear() == this.getCurrentCycleYear()) {
        if (!project.getBudgetsCluserActvities().contains(projectBudget)) {
          projectBudgetsCluserActvityManager.deleteProjectBudgetsCluserActvity(projectBudget.getId());

        }
      }

    }

    if (project.getBudgetsCluserActvities() != null) {
      for (ProjectBudgetsCluserActvity projectBudget : project.getBudgetsCluserActvities()) {
        if (projectBudget != null) {
          if (projectBudget.getId() == null) {
            projectBudget.setCreatedBy(this.getCurrentUser());

            projectBudget.setActiveSince(new Date());
            projectBudget.setActive(true);
            projectBudget.setProject(project);
            projectBudget.setModifiedBy(this.getCurrentUser());
            projectBudget.setModificationJustification("");

          } else {
            ProjectBudgetsCluserActvity ProjectBudgetDB =
              projectBudgetsCluserActvityManager.getProjectBudgetsCluserActvityById(projectBudget.getId());
            projectBudget.setCreatedBy(ProjectBudgetDB.getCreatedBy());

            projectBudget.setActiveSince(ProjectBudgetDB.getActiveSince());
            projectBudget.setActive(true);
            projectBudget.setProject(project);
            projectBudget.setModifiedBy(this.getCurrentUser());
            projectBudget.setModificationJustification("");
          }


          projectBudgetsCluserActvityManager.saveProjectBudgetsCluserActvity(projectBudget);
        }

      }
    }
  }

  public void setLoggedCrp(Crp loggedCrp) {
    this.loggedCrp = loggedCrp;
  }


  public void setProject(Project project) {
    this.project = project;
  }


  public void setProjectID(long projectID) {
    this.projectID = projectID;
  }


  public void setTransaction(String transaction) {
    this.transaction = transaction;
  }


}
