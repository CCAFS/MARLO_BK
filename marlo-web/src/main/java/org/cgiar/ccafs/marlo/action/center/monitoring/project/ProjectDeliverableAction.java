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

package org.cgiar.ccafs.marlo.action.center.monitoring.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.AuditLogManager;
import org.cgiar.ccafs.marlo.data.manager.GlobalUnitManager;
import org.cgiar.ccafs.marlo.data.manager.ICapacityDevelopmentService;
import org.cgiar.ccafs.marlo.data.manager.ICenterDeliverableCrosscutingThemeManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterDeliverableDocumentManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterDeliverableManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterDeliverableOutputManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterDeliverableTypeManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterOutputManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterProjectManager;
import org.cgiar.ccafs.marlo.data.model.CapacityDevelopment;
import org.cgiar.ccafs.marlo.data.model.CenterArea;
import org.cgiar.ccafs.marlo.data.model.CenterDeliverable;
import org.cgiar.ccafs.marlo.data.model.CenterDeliverableCrosscutingTheme;
import org.cgiar.ccafs.marlo.data.model.CenterDeliverableDocument;
import org.cgiar.ccafs.marlo.data.model.CenterDeliverableOutput;
import org.cgiar.ccafs.marlo.data.model.CenterDeliverableType;
import org.cgiar.ccafs.marlo.data.model.CenterOutput;
import org.cgiar.ccafs.marlo.data.model.CenterProject;
import org.cgiar.ccafs.marlo.data.model.CenterProjectOutput;
import org.cgiar.ccafs.marlo.data.model.CrpProgram;
import org.cgiar.ccafs.marlo.data.model.GlobalUnit;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConfig;
import org.cgiar.ccafs.marlo.utils.AutoSaveReader;
import org.cgiar.ccafs.marlo.validation.center.monitoring.project.CenterDeliverableValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jim??nez - CIAT/CCAFS
 */
public class ProjectDeliverableAction extends BaseAction {


  private static final long serialVersionUID = 6553033204498654741L;


  private ICenterDeliverableManager deliverableService;


  private ICenterDeliverableTypeManager deliverableTypeService;


  private ICenterDeliverableCrosscutingThemeManager deliverableCrosscutingService;


  private ICenterDeliverableOutputManager deliverableOutputService;


  private ICenterOutputManager outputService;

  private ICenterDeliverableDocumentManager deliverableDocumentService;


  // GlobalUnit Manager
  private GlobalUnitManager centerService;


  private ICenterProjectManager projectService;


  private ICapacityDevelopmentService capdevService;

  private AuditLogManager auditLogService;


  private CenterDeliverableValidator validator;

  private long deliverableID;

  private long projectID;

  private long programID;

  private long areaID;

  private CenterProject project;
  private CenterArea selectedResearchArea;
  private CrpProgram selectedProgram;
  private GlobalUnit loggedCenter;
  private String is_capdev;
  private CenterDeliverable deliverable;
  private List<CenterArea> researchAreas;
  private List<CrpProgram> researchPrograms;
  private List<CenterDeliverableType> deliverableSubTypes;

  private List<CenterDeliverableType> deliverableTypeParent;
  private List<CenterOutput> outputs;

  private List<CapacityDevelopment> capdevs;
  private String transaction;

  @Inject
  public ProjectDeliverableAction(APConfig config, GlobalUnitManager centerService,
    ICenterDeliverableTypeManager deliverableTypeService, ICenterDeliverableManager deliverableService,
    ICenterProjectManager projectService, ICenterDeliverableDocumentManager deliverableDocumentService,
    CenterDeliverableValidator validator, ICenterDeliverableCrosscutingThemeManager deliverableCrosscutingService,
    ICenterDeliverableOutputManager deliverableOutputService, ICenterOutputManager outputService,
    AuditLogManager auditLogService, ICapacityDevelopmentService capdevService) {
    super(config);
    this.centerService = centerService;
    this.deliverableTypeService = deliverableTypeService;
    this.deliverableService = deliverableService;
    this.projectService = projectService;
    this.deliverableDocumentService = deliverableDocumentService;
    this.validator = validator;
    this.auditLogService = auditLogService;
    this.deliverableCrosscutingService = deliverableCrosscutingService;
    this.deliverableOutputService = deliverableOutputService;
    this.outputService = outputService;
    this.capdevService = capdevService;
  }

  @Override
  public String cancel() {

    final Path path = this.getAutoSaveFilePath();

    if (path.toFile().exists()) {

      final boolean fileDeleted = path.toFile().delete();
    }

    this.setDraft(false);
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      final String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionMessage("draft:" + this.getText("cancel.autoSave"));
    } else {
      this.addActionMessage("draft:" + this.getText("cancel.autoSave"));
    }
    messages = this.getActionMessages();

    return SUCCESS;
  }

  public long getAreaID() {
    return areaID;
  }

  private Path getAutoSaveFilePath() {
    final String composedClassName = deliverable.getClass().getSimpleName();
    final String actionFile = this.getActionName().replace("/", "_");
    final String autoSaveFile = deliverable.getId() + "_" + composedClassName + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
  }

  public List<CapacityDevelopment> getCapdevs() {
    return capdevs;
  }

  public CenterDeliverable getDeliverable() {
    return deliverable;
  }

  public long getDeliverableID() {
    return deliverableID;
  }

  public List<CenterDeliverableType> getDeliverableSubTypes() {
    return deliverableSubTypes;
  }

  public List<CenterDeliverableType> getDeliverableTypeParent() {
    return deliverableTypeParent;
  }

  public String getIs_capdev() {
    return is_capdev;
  }

  public GlobalUnit getLoggedCenter() {
    return loggedCenter;
  }

  public List<CenterOutput> getOutputs() {
    return outputs;
  }

  public long getProgramID() {
    return programID;
  }

  public void getProgramOutputs() {

    outputs = new ArrayList<>();

    final List<CenterProjectOutput> projectOutputs =
      new ArrayList<>(project.getProjectOutputs().stream().filter(po -> po.isActive()).collect(Collectors.toList()));

    for (final CenterProjectOutput projectOutput : projectOutputs) {
      outputs.add(projectOutput.getResearchOutput());
    }
  }

  public CenterProject getProject() {
    return project;
  }

  public long getProjectID() {
    return projectID;
  }


  public List<CenterArea> getResearchAreas() {
    return researchAreas;
  }

  public List<CrpProgram> getResearchPrograms() {
    return researchPrograms;
  }

  public CrpProgram getSelectedProgram() {
    return selectedProgram;
  }


  public CenterArea getSelectedResearchArea() {
    return selectedResearchArea;
  }

  public String getTransaction() {
    return transaction;
  }


  @Override
  public void prepare() throws Exception {
    loggedCenter = (GlobalUnit) this.getSession().get(APConstants.SESSION_CRP);
    loggedCenter = centerService.getGlobalUnitById(loggedCenter.getId());

    researchAreas =
      new ArrayList<>(loggedCenter.getCenterAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    try {
      deliverableID =
        Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_DELIVERABLE_ID)));
    } catch (final Exception e) {
      deliverableID = -1;
      projectID = -1;
    }

    if (this.getRequest().getParameter(APConstants.TRANSACTION_ID) != null) {

      transaction = StringUtils.trim(this.getRequest().getParameter(APConstants.TRANSACTION_ID));
      final CenterDeliverable history = (CenterDeliverable) auditLogService.getHistory(transaction);

      if (history != null) {
        deliverable = history;
      } else {
        this.transaction = null;
        this.setTransaction("-1");
      }

    } else {
      deliverable = deliverableService.getDeliverableById(deliverableID);
    }


    if (deliverable != null) {
      CenterDeliverable deliverableDB = deliverableService.getDeliverableById(deliverable.getId());

      is_capdev = String.valueOf(deliverable.isCapdevD());
      capdevs = capdevService.findAll().stream()
        .filter(
          c -> (c.getProject() != null) && (c.getProject().getId() == deliverable.getProject().getId()) && c.isActive())
        .collect(Collectors.toList());
      Collections.sort(capdevs, (ra1, ra2) -> (int) (ra2.getId() - ra1.getId()));
      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists() && this.getCurrentUser().isAutoSave() && this.isEditable()) {
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(path.toFile()));

        Gson gson = new GsonBuilder().create();
        JsonObject jReader = gson.fromJson(reader, JsonObject.class);
        reader.close();

        AutoSaveReader autoSaveReader = new AutoSaveReader();


        deliverable = (CenterDeliverable) autoSaveReader.readFromJson(jReader);

        if (deliverable.getOutputs() != null) {
          final List<CenterDeliverableOutput> outputs = new ArrayList<>();
          for (final CenterDeliverableOutput output : deliverable.getOutputs()) {

            if (output.getId() != null) {
              final CenterDeliverableOutput deliverableOutput =
                deliverableOutputService.getDeliverableOutputById(output.getId());
              outputs.add(deliverableOutput);


            } else {
              final CenterOutput researchOutput =
                outputService.getResearchOutputById(output.getResearchOutput().getId());
              final CenterDeliverableOutput deliverableOutput = new CenterDeliverableOutput();
              deliverableOutput.setResearchOutput(researchOutput);
              deliverableOutput.setDeliverable(deliverableDB);
              outputs.add(deliverableOutput);
            }


          }

          deliverable.setOutputs(new ArrayList<>(outputs));
        }


        this.setDraft(true);

      } else {
        this.setDraft(false);

        CenterDeliverableCrosscutingTheme deliverableCrosscutingTheme;
        if (this.isEditable()) {
          deliverableCrosscutingTheme =
            deliverableCrosscutingService.getDeliverableCrosscutingThemeById(deliverable.getId());
        } else {
          deliverableCrosscutingTheme = deliverable.getDeliverableCrosscutingTheme();
        }

        deliverable.setDeliverableCrosscutingTheme(deliverableCrosscutingTheme);

        deliverable.setDocuments(new ArrayList<>(
          deliverable.getDeliverableDocuments().stream().filter(dd -> dd.isActive()).collect(Collectors.toList())));

        deliverable.setOutputs(
          deliverable.getDeliverableOutputs().stream().filter(o -> o.isActive()).collect(Collectors.toList()));
      }

      deliverableDB = deliverableService.getDeliverableById(deliverable.getId());
      projectID = deliverableDB.getProject().getId();
      project = projectService.getCenterProjectById(projectID);
      deliverable.setProject(project);

      // selectedProgram = project.getResearchProgram();
      programID = selectedProgram.getId();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();
      researchPrograms = new ArrayList<>(
        selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));


      if (deliverable.getDeliverableType() != null) {
        final Long deliverableTypeParentId = deliverable.getDeliverableType().getDeliverableType().getId();

        deliverableSubTypes = new ArrayList<>(deliverableTypeService.findAll().stream()
          .filter(
            dt -> (dt.getDeliverableType() != null) && (dt.getDeliverableType().getId() == deliverableTypeParentId))
          .collect(Collectors.toList()));
      }
    }

    deliverableTypeParent = new ArrayList<>(deliverableTypeService.findAll().stream()
      .filter(dt -> dt.isActive() && (dt.getDeliverableType() == null)).collect(Collectors.toList()));


    this.getProgramOutputs();

    final String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + "",
      projectID + "", deliverableID + ""};
    this.setBasePermission(this.getText(Permission.CENTER_PROJECT_DEIVERABLE_BASE_PERMISSION, params));

    if (this.isHttpPost()) {

      if (outputs != null) {
        outputs.clear();
      }

      if (deliverableTypeParent != null) {
        deliverableTypeParent.clear();
      }

      if (deliverableSubTypes != null) {
        deliverableSubTypes.clear();
      }

      if (deliverable.getDocuments() != null) {
        deliverable.getDocuments().clear();
      }

      if (deliverable.getOutputs() != null) {
        deliverable.getOutputs().clear();
      }

      if (deliverable.getDeliverableCrosscutingTheme() != null) {
        deliverable.getDeliverableCrosscutingTheme().setPoliciesInstitutions(null);
        deliverable.getDeliverableCrosscutingTheme().setGender(null);
        deliverable.getDeliverableCrosscutingTheme().setYouth(null);
        deliverable.getDeliverableCrosscutingTheme().setClimateChange(null);
        deliverable.getDeliverableCrosscutingTheme().setCapacityDevelopment(null);
        deliverable.getDeliverableCrosscutingTheme().setNa(null);
        deliverable.getDeliverableCrosscutingTheme().setBigData(null);
        deliverable.getDeliverableCrosscutingTheme().setImpactAssessment(null);
      }
    }

  }

  @Override
  public String save() {
    if (this.hasPermissionCenter("*")) {

      CenterDeliverable deliverableDB = deliverableService.getDeliverableById(deliverableID);


      deliverableDB.setName(deliverable.getName());
      deliverableDB.setStartDate(deliverable.getStartDate());
      deliverableDB.setEndDate(deliverable.getEndDate());

      deliverableDB.setCapdevD(deliverable.isCapdevD());
      if (deliverable.isCapdevD() != null) {
        if (!deliverable.isCapdevD()) {
          deliverableDB.setCapdev(null);
        }
      }

      if (deliverable.getCapdev() != null) {
        if (deliverable.getCapdev().getId() != -1) {
          CapacityDevelopment capdevDB = capdevService.getCapacityDevelopmentById(deliverable.getCapdev().getId());
          deliverableDB.setCapdev(capdevDB);
        } else {
          deliverableDB.setCapdev(null);
        }
      }

      if (deliverable.getDeliverableType() != null) {
        if (deliverable.getDeliverableType().getId() != -1) {
          CenterDeliverableType deliverableType =
            deliverableTypeService.getDeliverableTypeById(deliverable.getDeliverableType().getId());
          deliverableDB.setDeliverableType(deliverableType);
        }
      }


      deliverableDB = deliverableService.saveDeliverable(deliverableDB);

      if (deliverable.getDeliverableCrosscutingTheme() != null) {
        this.saveCrossCuting(deliverableDB);
      }

      this.saveDocuments(deliverableDB);
      this.saveOutputs(deliverableDB);


      final List<String> relationsName = new ArrayList<>();
      relationsName.add(APConstants.DELIVERABLE_DOCUMENT_RELATION);
      relationsName.add(APConstants.DELIVERABLE_OUTPUTS_RELATION);
      deliverable = deliverableService.getDeliverableById(deliverableID);

      /**
       * The following is required because we need to update something on the @Deliverable if we want a row created in
       * the auditlog table.
       */
      this.setModificationJustification(deliverable);

      deliverableService.saveDeliverable(deliverable, this.getActionName(), relationsName);

      final Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists()) {
        path.toFile().delete();
      }

      // check if there is a url to redirect
      if ((this.getUrl() == null) || this.getUrl().isEmpty()) {
        // check if there are missing field
        if (!this.getInvalidFields().isEmpty()) {
          this.setActionMessages(null);
          // this.addActionMessage(Map.toString(this.getInvalidFields().toArray()));
          final List<String> keys = new ArrayList<String>(this.getInvalidFields().keySet());
          for (final String key : keys) {
            this.addActionMessage(key + ": " + this.getInvalidFields().get(key));
          }
        } else {
          this.addActionMessage("message:" + this.getText("saving.saved"));
        }
        return SUCCESS;
      } else {
        // No messages to next page
        this.addActionMessage("");
        this.setActionMessages(null);
        // redirect the url select by user
        return REDIRECT;
      }
    } else {
      return NOT_AUTHORIZED;
    }
  }

  public void saveCrossCuting(CenterDeliverable deliverableDB) {
    final CenterDeliverableCrosscutingTheme crosscutingTheme = deliverable.getDeliverableCrosscutingTheme();

    final CenterDeliverableCrosscutingTheme crosscutingThemeSave = deliverableCrosscutingService
      .getDeliverableCrosscutingThemeById(deliverableDB.getDeliverableCrosscutingTheme().getId());

    crosscutingThemeSave
      .setClimateChange(crosscutingTheme.getClimateChange() != null ? crosscutingTheme.getClimateChange() : false);
    crosscutingThemeSave.setGender(crosscutingTheme.getGender() != null ? crosscutingTheme.getGender() : false);
    crosscutingThemeSave.setYouth(crosscutingTheme.getYouth() != null ? crosscutingTheme.getYouth() : false);
    crosscutingThemeSave.setPoliciesInstitutions(
      crosscutingTheme.getPoliciesInstitutions() != null ? crosscutingTheme.getPoliciesInstitutions() : false);
    crosscutingThemeSave.setCapacityDevelopment(
      crosscutingTheme.getCapacityDevelopment() != null ? crosscutingTheme.getCapacityDevelopment() : false);
    crosscutingThemeSave.setBigData(crosscutingTheme.getBigData() != null ? crosscutingTheme.getBigData() : false);
    crosscutingThemeSave.setImpactAssessment(
      crosscutingTheme.getImpactAssessment() != null ? crosscutingTheme.getImpactAssessment() : false);
    crosscutingThemeSave.setNa(crosscutingTheme.getNa() != null ? crosscutingTheme.getNa() : false);

    crosscutingThemeSave.setDeliverable(deliverableDB);

    deliverableCrosscutingService.saveDeliverableCrosscutingTheme(crosscutingThemeSave);


  }


  public void saveDocuments(CenterDeliverable deliverableDB) {

    if ((deliverableDB.getDeliverableDocuments() != null) && (deliverableDB.getDeliverableDocuments().size() > 0)) {
      final List<CenterDeliverableDocument> deliverableDocuments = new ArrayList<>(
        deliverableDB.getDeliverableDocuments().stream().filter(dd -> dd.isActive()).collect(Collectors.toList()));

      for (final CenterDeliverableDocument deliverableDocument : deliverableDocuments) {
        if (!deliverable.getDocuments().contains(deliverableDocument)) {
          deliverableDocumentService.deleteDeliverableDocument(deliverableDocument.getId());
        }
      }
    }

    if (deliverable.getDocuments() != null) {
      for (final CenterDeliverableDocument deliverableDocument : deliverable.getDocuments()) {

        if ((deliverableDocument.getId() == null) || (deliverableDocument.getId() == -1)) {
          final CenterDeliverableDocument documentSave = new CenterDeliverableDocument();

          documentSave.setLink(deliverableDocument.getLink());

          final CenterDeliverable deliverable = deliverableService.getDeliverableById(deliverableID);
          documentSave.setDeliverable(deliverable);

          deliverableDocumentService.saveDeliverableDocument(documentSave);


        } else {
          boolean hasChanges = false;
          final CenterDeliverableDocument documentPrew =
            deliverableDocumentService.getDeliverableDocumentById(deliverableDocument.getId());

          if (!documentPrew.getLink().equals(deliverableDocument.getLink())) {
            hasChanges = true;
            documentPrew.setLink(deliverableDocument.getLink());
          }

          if (hasChanges) {
            deliverableDocumentService.saveDeliverableDocument(documentPrew);
          }

        }

      }
    }

  }

  public void saveOutputs(CenterDeliverable deliverableDB) {
    if ((deliverableDB.getDeliverableOutputs() != null) && (deliverableDB.getDeliverableOutputs().size() > 0)) {
      final List<CenterDeliverableOutput> deliverableOutputsPrew = new ArrayList<>(
        deliverableDB.getDeliverableOutputs().stream().filter(d -> d.isActive()).collect(Collectors.toList()));

      for (final CenterDeliverableOutput deliverableOutput : deliverableOutputsPrew) {
        if (!deliverable.getOutputs().contains(deliverableOutput)) {
          deliverableOutputService.deleteDeliverableOutput(deliverableOutput.getId());
        }
      }

    }

    if (deliverable.getOutputs() != null) {
      for (final CenterDeliverableOutput deliverableOutput : deliverable.getOutputs()) {
        if ((deliverableOutput.getId() == null) || (deliverableOutput.getId() == -1)) {
          final CenterDeliverableOutput deliverableOutputSave = new CenterDeliverableOutput();

          final CenterOutput output =
            outputService.getResearchOutputById(deliverableOutput.getResearchOutput().getId());
          deliverableOutputSave.setResearchOutput(output);
          deliverableOutputSave.setDeliverable(deliverableDB);

          deliverableOutputService.saveDeliverableOutput(deliverableOutputSave);

        }
      }
    }
  }

  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  public void setCapdevs(List<CapacityDevelopment> capdevs) {
    this.capdevs = capdevs;
  }

  public void setDeliverable(CenterDeliverable deliverable) {
    this.deliverable = deliverable;
  }

  public void setDeliverableID(long deliverableID) {
    this.deliverableID = deliverableID;
  }

  public void setDeliverableSubTypes(List<CenterDeliverableType> deliverableSubTypes) {
    this.deliverableSubTypes = deliverableSubTypes;
  }


  public void setDeliverableTypeParent(List<CenterDeliverableType> deliverableTypeParent) {
    this.deliverableTypeParent = deliverableTypeParent;
  }

  public void setIs_capdev(String is_capdev) {
    this.is_capdev = is_capdev;
  }

  public void setLoggedCenter(GlobalUnit loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setOutputs(List<CenterOutput> outputs) {
    this.outputs = outputs;
  }

  public void setProgramID(long programID) {
    this.programID = programID;
  }


  public void setProject(CenterProject project) {
    this.project = project;
  }

  public void setProjectID(long projectID) {
    this.projectID = projectID;
  }

  public void setResearchAreas(List<CenterArea> researchAreas) {
    this.researchAreas = researchAreas;
  }

  public void setResearchPrograms(List<CrpProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

  public void setSelectedProgram(CrpProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }


  public void setSelectedResearchArea(CenterArea selectedResearchArea) {
    this.selectedResearchArea = selectedResearchArea;
  }


  public void setTransaction(String transaction) {
    this.transaction = transaction;
  }


  @Override
  public void validate() {
    if (save) {
      // validator.validate(this, deliverable, project, selectedProgram, true);
    }
  }

}
