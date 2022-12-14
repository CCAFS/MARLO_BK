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
import org.cgiar.ccafs.marlo.data.manager.FileDBManager;
import org.cgiar.ccafs.marlo.data.manager.FundingSourceManager;
import org.cgiar.ccafs.marlo.data.manager.GlobalUnitManager;
import org.cgiar.ccafs.marlo.data.manager.GlobalUnitProjectManager;
import org.cgiar.ccafs.marlo.data.manager.LocElementManager;
import org.cgiar.ccafs.marlo.data.manager.LocElementTypeManager;
import org.cgiar.ccafs.marlo.data.manager.LocGeopositionManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInfoManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectLocationElementTypeManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectLocationManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectManager;
import org.cgiar.ccafs.marlo.data.model.CountryFundingSources;
import org.cgiar.ccafs.marlo.data.model.CrpLocElementType;
import org.cgiar.ccafs.marlo.data.model.FundingSource;
import org.cgiar.ccafs.marlo.data.model.FundingSourceInfo;
import org.cgiar.ccafs.marlo.data.model.FundingSourceLocation;
import org.cgiar.ccafs.marlo.data.model.GlobalUnit;
import org.cgiar.ccafs.marlo.data.model.GlobalUnitProject;
import org.cgiar.ccafs.marlo.data.model.LocElement;
import org.cgiar.ccafs.marlo.data.model.LocElementType;
import org.cgiar.ccafs.marlo.data.model.LocGeoposition;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectBudget;
import org.cgiar.ccafs.marlo.data.model.ProjectLocation;
import org.cgiar.ccafs.marlo.data.model.ProjectLocationElementType;
import org.cgiar.ccafs.marlo.data.model.ProjectSectionStatusEnum;
import org.cgiar.ccafs.marlo.data.model.ScopeData;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConfig;
import org.cgiar.ccafs.marlo.utils.AutoSaveReader;
import org.cgiar.ccafs.marlo.utils.CountryLocationLevel;
import org.cgiar.ccafs.marlo.utils.LocationLevel;
import org.cgiar.ccafs.marlo.validation.projects.ProjectLocationValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jim??nez - CIAT/CCAFS
 */
public class ProjectLocationAction extends BaseAction {

  private static final long serialVersionUID = -3215013554941621274L;

  // Managers
  private final AuditLogManager auditLogManager;
  private final GlobalUnitManager crpManager;
  private final FundingSourceManager fundingSourceManager;
  private final LocElementManager locElementManager;
  private final LocElementTypeManager locElementTypeManager;
  private final LocGeopositionManager locGeopositionManager;
  private final ProjectLocationElementTypeManager projectLocationElementTypeManager;
  private final ProjectLocationManager projectLocationManager;
  private final ProjectManager projectManager;
  private ProjectInfoManager projectInfoManager;
  private GlobalUnitProjectManager globalUnitProjectManager;
  private FileDBManager fileDBManager;

  // Variables
  private GlobalUnit loggedCrp;
  private Project project;
  private long projectID;
  private boolean region;
  private List<LocElement> regionLists;
  private List<ScopeData> scopeData;
  private List<LocElementType> scopeRegionLists;
  private List<LocElementType> scopeRegions;
  private String transaction;
  private List<LocationLevel> locationsLevels;
  private final ProjectLocationValidator locationValidator;

  @Inject
  public ProjectLocationAction(APConfig config, GlobalUnitManager crpManager, ProjectManager projectManager,
    LocElementTypeManager locElementTypeManager, LocElementManager locElementManager,
    ProjectLocationManager projectLocationManager, LocGeopositionManager locGeopositionManager,
    AuditLogManager auditLogManager, ProjectLocationValidator locationValidator, ProjectInfoManager projectInfoManager,
    ProjectLocationElementTypeManager projectLocationElementTypeManager, FundingSourceManager fundingSourceManager,
    GlobalUnitProjectManager globalUnitProjectManager, FileDBManager fileDBManager) {
    super(config);
    this.crpManager = crpManager;
    this.projectManager = projectManager;
    this.locElementTypeManager = locElementTypeManager;
    this.locElementManager = locElementManager;
    this.projectLocationManager = projectLocationManager;
    this.locGeopositionManager = locGeopositionManager;
    this.auditLogManager = auditLogManager;
    this.projectInfoManager = projectInfoManager;
    this.locationValidator = locationValidator;
    this.projectLocationElementTypeManager = projectLocationElementTypeManager;
    this.fundingSourceManager = fundingSourceManager;
    this.globalUnitProjectManager = globalUnitProjectManager;
    this.fileDBManager = fileDBManager;
  }

  @Override
  public String cancel() {

    Path path = this.getAutoSaveFilePath();

    if (path.toFile().exists()) {

      boolean fileDeleted = path.toFile().delete();
    }

    this.setDraft(false);
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionMessage("draft:" + this.getText("cancel.autoSave"));
    } else {
      this.addActionMessage("draft:" + this.getText("cancel.autoSave"));
    }
    messages = this.getActionMessages();

    return SUCCESS;
  }

  /**
   * The name of the autosave file is constructed and the path is searched
   * 
   * @return Auto save file path
   */
  private Path getAutoSaveFilePath() {
    // get the class simple name
    String composedClassName = project.getClass().getSimpleName();
    // get the action name and replace / for _
    String actionFile = this.getActionName().replace("/", "_");
    // concatane name and add the .json extension
    String autoSaveFile = project.getId() + "_" + composedClassName + "_" + this.getActualPhase().getName() + "_"
      + this.getActualPhase().getYear() + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
  }

  public List<ProjectLocation> getDBLocations() {
    List<ProjectLocation> locations = projectLocationManager.findAll().stream()
      .filter(p -> p.isActive() && p.getProject().getId().longValue() == projectID && p.getPhase() != null
        && p.getPhase().equals(this.getActualPhase()))
      .collect(Collectors.toList());
    return locations;
  }

  public List<LocationLevel> getLocationsLevels() {
    return locationsLevels;
  }

  public String getPath() {
    return config.getDownloadURL() + "/" + this.getProjectLocationActivitiesCSVSourceFolder().replace('\\', '/');
  }

  public Project getProject() {
    return project;
  }

  public long getProjectID() {
    return projectID;
  }

  private String getProjectLocationActivitiesCSVSourceFolder() {
    return APConstants.PROJECTS_LOCATIONS_ACTIVITIES_CSV_FOLDER.concat(File.separator).concat(this.getCrpSession())
      .concat(File.separator).concat(File.separator).concat(this.getCrpSession() + "_")
      .concat(ProjectSectionStatusEnum.LOCATIONS.getStatus()).concat(File.separator);
  }

  public List<CountryLocationLevel> getProjectLocationsData() {

    List<Map<String, Object>> parentLocations = new ArrayList<>();
    List<CountryLocationLevel> locationLevels = new ArrayList<>();
    List<ProjectLocationElementType> locationsElementType = new ArrayList<>(
      project.getProjectLocationElementTypes().stream().filter(pl -> pl.getIsGlobal()).collect(Collectors.toList()));

    project.setLocations((this.getDBLocations().stream()
      .filter(p -> p.isActive() && p.getLocElementType() == null && p.getLocElement() != null
        && p.getLocElement().getLocElementType() != null && p.getLocElement().getLocElementType().getId() != null
        && p.getLocElement().getLocElementType().getId().longValue() != 1 && p.getPhase().equals(this.getActualPhase()))
      .collect(Collectors.toList())));
    Map<String, Object> locationParent;
    if (!project.getLocations().isEmpty()) {

      if (locationsElementType != null) {
        for (ProjectLocationElementType projectLocationElementType : locationsElementType) {
          boolean existElementType = false;
          for (ProjectLocation location : project.getLocations()) {
            if (projectLocationElementType.getLocElementType().getId() == location.getLocElement().getLocElementType()
              .getId()) {
              existElementType = true;
            }
          }
          if (!existElementType) {
            locationParent = new HashMap<String, Object>();
            if (!parentLocations.isEmpty()) {
              locationParent.put(projectLocationElementType.getLocElementType().getName(),
                projectLocationElementType.getLocElementType().getId());
              if (!parentLocations.contains(locationParent)) {
                parentLocations.add(locationParent);
              }
            } else {
              locationParent.put(projectLocationElementType.getLocElementType().getName(),
                projectLocationElementType.getLocElementType().getId());
              parentLocations.add(locationParent);
            }
          }
        }
      }

      for (ProjectLocation location : project.getLocations()) {
        locationParent = new HashMap<String, Object>();
        if (!parentLocations.isEmpty()) {
          locationParent.put(location.getLocElement().getLocElementType().getName(),
            location.getLocElement().getLocElementType().getId());
          if (!parentLocations.contains(locationParent)) {
            parentLocations.add(locationParent);
          }
        } else {
          locationParent.put(location.getLocElement().getLocElementType().getName(),
            location.getLocElement().getLocElementType().getId());
          parentLocations.add(locationParent);
        }

      }

    } else {
      if (!locationsElementType.isEmpty()) {
        for (ProjectLocationElementType projectLocationElementType : locationsElementType) {
          locationParent = new HashMap<String, Object>();
          if (!parentLocations.isEmpty()) {
            locationParent.put(projectLocationElementType.getLocElementType().getName(),
              projectLocationElementType.getLocElementType().getId());
            if (!parentLocations.contains(locationParent)) {
              parentLocations.add(locationParent);
            }
          } else {
            locationParent.put(projectLocationElementType.getLocElementType().getName(),
              projectLocationElementType.getLocElementType().getId());
            parentLocations.add(locationParent);
          }
        }
      }
    }

    CountryLocationLevel countryLocationLevel;
    ProjectLocationElementType locationElementType = null;
    for (Map<String, Object> map : parentLocations) {

      for (Map.Entry<String, Object> entry : map.entrySet()) {
        countryLocationLevel = new CountryLocationLevel();
        countryLocationLevel.setId(Long.parseLong(entry.getValue().toString()));
        countryLocationLevel.setName(entry.getKey());
        countryLocationLevel.setLocElements(new ArrayList<LocElement>());

        LocElementType elementType =
          locElementTypeManager.getLocElementTypeById(Long.parseLong(entry.getValue().toString()));

        countryLocationLevel.setAllElements(new ArrayList<LocElement>(elementType.getLocElements()));

        for (ProjectLocation projectLocation : project.getLocations().stream().filter(l -> l.isActive())
          .collect(Collectors.toList())) {
          if (projectLocation.getLocElement().getLocElementType().getId() == Long
            .parseLong(entry.getValue().toString())) {
            countryLocationLevel.getLocElements().add(projectLocation.getLocElement());
          }
        }

        if (elementType.getId() == 2 || elementType.getCrp() != null) {

          locationElementType =
            projectLocationElementTypeManager.getByProjectAndElementType(projectID, elementType.getId());

          countryLocationLevel.setList(true);

        } else {
          countryLocationLevel.setList(false);

        }

        locationLevels.add(countryLocationLevel);
      }

    }

    return locationLevels;
  }

  public List<LocElement> getRegionLists() {
    return regionLists;
  }

  public List<ScopeData> getScopeData() {
    return scopeData;
  }

  public List<LocElementType> getScopeRegionLists() {
    return scopeRegionLists;
  }

  public List<LocElementType> getScopeRegions() {
    return scopeRegions;
  }

  public String getTransaction() {
    return transaction;
  }

  public boolean isRegion() {
    return region;
  }

  public void listScopeRegions() {

    List<LocElementType> scopeRegionsPrew = locElementTypeManager.findAll().stream()
      .filter(et -> et.isActive() && et.isScope() && et.getCrp().getId() == loggedCrp.getId())
      .collect(Collectors.toList());

    scopeRegions = new ArrayList<>();

    if (project.getLocationsData() != null) {
      for (CountryLocationLevel locationData : project.getLocationsData()) {
        if (locationData.getLocElements() != null) {
          for (LocElement locElement : locationData.getLocElements()) {
            if (locElement.getId() != null && locElement.getId() != -1) {

              LocElement elementReview = locElementManager.getLocElementById(locElement.getId());

              while (true) {
                long elementReviewType = elementReview.getLocElementType().getId();

                if (elementReviewType == 2) {

                  for (LocElementType locElementType : scopeRegionsPrew) {

                    List<LocElement> scopeElements = new ArrayList<>(locElementType.getLocElements().stream()
                      .filter(lc -> lc.isActive()).collect(Collectors.toList()));

                    for (LocElement scopeElement : scopeElements) {
                      LocElement scopeParentElement = scopeElement.getLocElement();
                      if (scopeParentElement.equals(elementReview)) {
                        if (scopeRegions.isEmpty()) {
                          scopeRegions.add(locElementType);
                        } else {
                          if (!scopeRegions.contains(locElementType)) {
                            scopeRegions.add(locElementType);
                          }
                        }
                      }
                    }

                  }

                  break;

                } else {
                  if (elementReview.getLocElement() != null) {
                    elementReview = locElementManager.getLocElementById(elementReview.getLocElement().getId());
                  } else {
                    break;
                  }
                }
              }

            }
          }
        }
      }
    }
  }

  /**
   * 
   */
  public void locationLevels() {

    locationsLevels = new ArrayList<>();
    List<CountryLocationLevel> countryLocationLevels = new ArrayList<>();
    countryLocationLevels = new ArrayList<>();
    List<LocElementType> customElementTypes =
      locElementTypeManager.findAll().stream().filter(let -> let.isActive() && let.getCrp() != null
        && let.getCrp().equals(loggedCrp) && let.getId() != 1 && !let.isScope()).collect(Collectors.toList());

    for (LocElementType locElementType : customElementTypes) {
      CountryLocationLevel countryLocationLevel = new CountryLocationLevel();
      countryLocationLevel.setId(locElementType.getId());
      countryLocationLevel.setName(locElementType.getName());
      countryLocationLevel.setAllElements(new ArrayList<LocElement>(locElementType.getLocElements()));
      countryLocationLevel.setList(true);

      countryLocationLevels.add(countryLocationLevel);

    }

    locationsLevels
      .add(new LocationLevel(loggedCrp.getAcronym().toUpperCase() + " Custom Locations", countryLocationLevels));

    countryLocationLevels = new ArrayList<>();
    List<LocElementType> elementTypes = new ArrayList<>();
    GlobalUnit crpBD = crpManager.getGlobalUnitById(this.getCrpID());
    for (CrpLocElementType locElementType : crpBD.getCrpLocElementTypes().stream().filter(c -> c.isActive())
      .collect(Collectors.toList())) {
      elementTypes.add(locElementType.getLocElementType());
    }

    Collections.sort(elementTypes, (tu1, tu2) -> tu1.getName().compareTo(tu2.getName()));
    for (LocElementType locElementType : elementTypes) {
      CountryLocationLevel countryLocationLevel = new CountryLocationLevel();
      countryLocationLevel.setId(locElementType.getId());
      countryLocationLevel.setName(locElementType.getName());
      countryLocationLevel.setAllElements(new ArrayList<LocElement>(locElementType.getLocElements()));
      if (locElementType.getId() != 2) {
        countryLocationLevel.setList(false);
      } else {
        countryLocationLevel.setList(true);
      }

      countryLocationLevels.add(countryLocationLevel);
    }

    locationsLevels.add(new LocationLevel("Other Locations", countryLocationLevels));

  }

  public boolean locElementSelected(long locElementID) {

    if (project.getLocationsData() != null) {

      if (locElementManager.getLocElementById(locElementID).getLocElementType().getId().longValue() == 1) {

        if (project.getProjectRegions() != null) {
          List<ProjectLocation> locElements = project.getProjectRegions().stream()
            .filter(c -> c.getLocElement() != null && c.getLocElement().getId().longValue() == locElementID
              && c.getPhase() != null && c.getPhase().equals(this.getActualPhase()))
            .collect(Collectors.toList());

          return !locElements.isEmpty();
        } else {
          return false;
        }

      } else {
        List<CountryLocationLevel> locElements = project.getLocationsData().stream()
          .filter(c -> c.getLocElements() != null
            && c.getLocElements().contains(locElementManager.getLocElementById(locElementID)))
          .collect(Collectors.toList());
        return !locElements.isEmpty();

      }
    } else {
      Project projectDB = projectManager.getProjectById(projectID);
      List<ProjectLocation> locElements = this.getDBLocations().stream()
        .filter(c -> c.isActive() && c.getLocElement() != null && c.getLocElement().getId().longValue() == locElementID
          && c.getPhase() != null && c.getPhase().equals(this.getActualPhase()))
        .collect(Collectors.toList());

      return !locElements.isEmpty();
    }

  }

  public boolean locElementTypeSelected(long locElementID) {

    if (project.getProjectRegions() != null) {
      List<ProjectLocation> locElements = project.getProjectRegions().stream()
        .filter(c -> c.getLocElementType() != null && c.getLocElementType().getId().longValue() == locElementID
          && c.getPhase() != null && c.getPhase().equals(this.getActualPhase()))
        .collect(Collectors.toList());

      return !locElements.isEmpty();
    } else {
      Project projectDB = projectManager.getProjectById(projectID);
      List<ProjectLocation> locElements = this.getDBLocations().stream()
        .filter(c -> c.isActive() && c.getLocElementType() != null
          && c.getLocElementType().getId().longValue() == locElementID && c.getPhase() != null
          && c.getPhase().equals(this.getActualPhase()))
        .collect(Collectors.toList());

      return !locElements.isEmpty();
    }

  }

  @Override
  public void prepare() throws Exception {

    loggedCrp = (GlobalUnit) this.getSession().get(APConstants.SESSION_CRP);
    loggedCrp = crpManager.getGlobalUnitById(loggedCrp.getId());

    projectID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

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
    this.locationLevels();
    if (project != null) {
      GlobalUnitProject gp = globalUnitProjectManager.findByProjectId(project.getId());
      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists() && this.getCurrentUser().isAutoSave()) {

        BufferedReader reader = null;

        reader = new BufferedReader(new FileReader(path.toFile()));

        Gson gson = new GsonBuilder().create();

        JsonObject jReader = gson.fromJson(reader, JsonObject.class);
        reader.close();

        AutoSaveReader autoSaveReader = new AutoSaveReader();

        project = (Project) autoSaveReader.readFromJson(jReader);
        Project projectDb = projectManager.getProjectById(project.getId());
        project.getProjectInfo()
          .setProjectEditLeader(projectDb.getProjecInfoPhase(this.getActualPhase()).isProjectEditLeader());
        // project.setProjectLocations(projectDb.getProjectLocations());
        project.getProjectInfo()
          .setAdministrative(projectDb.getProjecInfoPhase(this.getActualPhase()).getAdministrative());
        if (project.getLocationsData() != null) {
          for (CountryLocationLevel level : project.getLocationsData()) {
            LocElementType elementType = locElementTypeManager.getLocElementTypeById(level.getId());
            if (elementType.getId() == 2 || elementType.getCrp() != null) {
              level.setAllElements(
                elementType.getLocElements().stream().filter(le -> le.isActive()).collect(Collectors.toList()));
            }
          }

        }
        if (project.getProjectRegions() != null) {
          for (ProjectLocation projectLocation : project.getProjectRegions()) {
            if (projectLocation.getLocElement() != null && projectLocation.getLocElement().getId() != null) {
              projectLocation
                .setLocElement(locElementManager.getLocElementById(projectLocation.getLocElement().getId()));
              projectLocation.setLocElementType(null);
            } else {
              projectLocation.setLocElementType(
                locElementTypeManager.getLocElementTypeById(projectLocation.getLocElementType().getId()));
              projectLocation.setLocElement(null);
            }
          }
        }

        List<CountryFundingSources> reCountryFundingSources = new ArrayList<>();
        List<CountryFundingSources> coCountryFundingSources = new ArrayList<>();
        if (project.getRegionFS() != null) {
          for (CountryFundingSources co : project.getRegionFS()) {
            if (co.getLocElement() != null) {
              co.setLocElement(locElementManager.getLocElementById(co.getLocElement().getId()));
              List<FundingSource> sources =
                fundingSourceManager.searchFundingSourcesByLocElement(projectID, co.getLocElement().getId(),
                  this.getCurrentCycleYear(), gp.getGlobalUnit().getId(), this.getActualPhase().getId());
              for (FundingSource fundingSource : sources) {
                fundingSource.getFundingSourceInfo(this.getActualPhase());
              }
              co.setFundingSources(sources);
            } else {
              co.setLocElementType(locElementTypeManager.getLocElementTypeById(co.getLocElementType().getId()));
              List<FundingSource> sources = fundingSourceManager.searchFundingSourcesByLocElementType(projectID,
                co.getLocElementType().getId(), this.getCurrentCycleYear(), gp.getGlobalUnit().getId());
              for (FundingSource fundingSource : sources) {
                fundingSource.getFundingSourceInfo(this.getActualPhase());
              }
              co.setFundingSources(sources);
            }
            if (!co.isSelected()) {
              if (co.getLocElement() != null) {
                co.setSelected(this.locElementSelected(co.getLocElement().getId()));
              } else {
                co.setSelected(this.locElementTypeSelected(co.getLocElementType().getId()));
              }
            }
            if (!co.getFundingSources().stream()
              .filter(c -> c.isActive() && c.getProjectBudgets().stream()
                .filter(
                  fp -> fp.isActive() && fp.getProject().isActive() && fp.getProject().getId().longValue() == projectID)
                .collect(Collectors.toList()).size() > 0)
              .collect(Collectors.toList()).isEmpty()) {
              reCountryFundingSources.add(co);
            }

          }
        }
        project.setRegionFS(reCountryFundingSources);
        if (project.getCountryFS() != null) {
          for (CountryFundingSources co : project.getCountryFS()) {
            if (co.getLocElement() != null) {
              co.setLocElement(locElementManager.getLocElementById(co.getLocElement().getId()));

              List<FundingSource> sources =
                fundingSourceManager.searchFundingSourcesByLocElement(projectID, co.getLocElement().getId(),
                  this.getCurrentCycleYear(), gp.getGlobalUnit().getId(), this.getActualPhase().getId());
              for (FundingSource fundingSource : sources) {
                fundingSource.getFundingSourceInfo(this.getActualPhase());
              }
              co.setFundingSources(new ArrayList<>(sources));

            } else {
              co.setLocElementType(locElementTypeManager.getLocElementTypeById(co.getLocElementType().getId()));
            }
            if (!co.isSelected()) {
              if (co.getLocElement() != null) {
                co.setSelected(this.locElementSelected(co.getLocElement().getId()));
              } else {
                co.setSelected(this.locElementTypeSelected(co.getLocElementType().getId()));
              }
            }
            if (!co.getFundingSources().stream()
              .filter(c -> c.isActive() && c.getProjectBudgets().stream()
                .filter(
                  fp -> fp.isActive() && fp.getProject().isActive() && fp.getProject().getId().longValue() == projectID)
                .collect(Collectors.toList()).size() > 0)
              .collect(Collectors.toList()).isEmpty()) {
              coCountryFundingSources.add(co);
            }
          }
          project.setCountryFS(coCountryFundingSources);
        }

        this.prepareFundingList();
        if (this.hasSpecificities(this.crpLocationCsvActivities())) {

          if (project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile() != null) {
            if (project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile().getId() != null) {
              project.getProjecInfoPhase(this.getActualPhase()).setActivitiesCSVFile(fileDBManager
                .getFileDBById(project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile().getId()));
            } else {
              project.getProjecInfoPhase(this.getActualPhase()).setActivitiesCSVFile(null);
            }
          }
        }

        this.setDraft(true);
      } else {
        this.setDraft(false);

        this.prepareFundingList();

        for (CountryFundingSources locElement : project.getCountryFS()) {
          locElement.setSelected(this.locElementSelected(locElement.getLocElement().getId()));
        }
        for (CountryFundingSources locElement : project.getRegionFS()) {
          if (locElement.getLocElement() != null) {
            locElement.setSelected(this.locElementSelected(locElement.getLocElement().getId()));
          } else {
            locElement.setSelected(this.locElementTypeSelected(locElement.getLocElementType().getId()));
          }

        }
        project.setLocationsData(this.getProjectLocationsData());
        project.setProjectRegions(new ArrayList<ProjectLocation>(this.getDBLocations().stream()
          .filter(p -> p.isActive() && p.getLocElementType() == null && p.getLocElement() != null
            && p.getLocElement().getLocElementType().getId().longValue() == 1 && p.getPhase() != null
            && p.getPhase().equals(this.getActualPhase()))
          .collect(Collectors.toList())));
        project.getProjectRegions()
          .addAll(
            this
              .getDBLocations().stream().filter(p -> p.isActive() && p.getLocElementType() != null
                && p.getLocElement() == null && p.getPhase().equals(this.getActualPhase()))
              .collect(Collectors.toList()));

      }
      if (this.hasSpecificities(this.crpLocationCsvActivities())) {
        if (project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile() != null) {
          if (project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile().getId() != null) {
            project.getProjecInfoPhase(this.getActualPhase()).setActivitiesCSVFile(fileDBManager
              .getFileDBById(project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile().getId()));
          }
        }
      }

    }

    this.listScopeRegions();

    Collection<LocElement> fsLocs = new ArrayList<>();

    if (project.getCountryFS() == null) {
      project.setCountryFS(new ArrayList<>());
    }
    if (project.getRegionFS() == null) {
      project.setRegionFS(new ArrayList<>());
    }

    for (CountryFundingSources locElement : project.getCountryFS()) {
      fsLocs.add(locElement.getLocElement());
    }

    if (project.getLocationsData() == null) {
      project.setLocationsData(new ArrayList<>());
    }

    // Fix Ull Collection when autosave gets the suggeste country - 10/13/2017
    for (CountryLocationLevel countryLocationLevel : project.getLocationsData()) {
      if (countryLocationLevel.getLocElements() != null) {
        Collection<LocElement> similar = new HashSet<LocElement>(countryLocationLevel.getLocElements());
        Collection<LocElement> different = new HashSet<LocElement>();
        different.addAll(countryLocationLevel.getLocElements());
        different.addAll(fsLocs);
        similar.retainAll(fsLocs);
        different.removeAll(similar);

        countryLocationLevel.getLocElements().removeAll(similar);
      }

    }

    Collection<LocElement> fsLocsRegions = new ArrayList<>();
    for (CountryFundingSources locElement : project.getRegionFS()) {
      if (locElement.getLocElement() != null) {
        fsLocsRegions.add(locElement.getLocElement());
      }

    }

    if (project.getProjectRegions() != null) {
      for (ProjectLocation projectLocation : project.getProjectRegions().stream().filter(c -> c.getLocElement() != null)
        .collect(Collectors.toList())) {

        if (fsLocsRegions.contains(projectLocation.getLocElement())) {
          project.getProjectRegions().remove(projectLocation);
        }

      }
    }

    Collection<LocElementType> fsLocsCustomRegions = new ArrayList<>();
    for (CountryFundingSources locElement : project.getRegionFS()) {
      if (locElement.getLocElementType() != null) {
        fsLocsCustomRegions.add(locElement.getLocElementType());

      }

    }

    if (project.getProjectRegions() != null) {
      for (ProjectLocation projectLocation : project.getProjectRegions().stream()
        .filter(c -> c.getLocElementType() != null).collect(Collectors.toList())) {

        if (fsLocsCustomRegions.contains(projectLocation.getLocElementType())) {
          project.getProjectRegions().remove(projectLocation);
        }

      }
    }

    regionLists = new ArrayList<>(locElementManager.findAll().stream()
      .filter(le -> le.isActive() && le.getLocElementType() != null && le.getLocElementType().getId() == 1)
      .collect(Collectors.toList()));
    Collections.sort(regionLists, (r1, r2) -> r1.getName().compareTo(r2.getName()));
    scopeRegionLists = new ArrayList<>(locElementTypeManager.findAll().stream()
      .filter(le -> le.isActive() && le.getCrp() != null && le.getCrp().equals(loggedCrp) && le.isScope())
      .collect(Collectors.toList()));
    String params[] = {loggedCrp.getAcronym(), project.getId() + ""};
    this.setBasePermission(this.getText(Permission.PROJECT_LOCATION_BASE_PERMISSION, params));

    if (!project.getLocationsData().stream().filter(c -> c.getId().longValue() != 2).collect(Collectors.toList())
      .isEmpty()) {
      region = true;
    } else {
      region = false;
    }
    if (this.isHttpPost()) {
      if (project.getLocationsData() != null) {
        project.getLocationsData().clear();
      }

      project.getProjecInfoPhase(this.getActualPhase()).setLocationGlobal(false);
      if (this.hasSpecificities(this.crpLocationCsvActivities())) {
        project.getProjecInfoPhase(this.getActualPhase()).setActivitiesCSV(null);
      }

      if (project.getCountryFS() != null) {
        project.getCountryFS().clear();
      }
      if (project.getRegionFS() != null) {
        project.getRegionFS().clear();
      }
      if (project.getRegions() != null) {
        project.getRegions().clear();
      }
      if (project.getProjectRegions() != null) {
        project.getProjectRegions().clear();
      }
      if (this.hasSpecificities(this.crpLocationCsvActivities())) {
        project.getProjecInfoPhase(this.getActualPhase()).setActivitiesCSVFile(null);
      }
    }

  }

  public void prepareFundingList() {

    Project projectDB = projectManager.getProjectById(project.getId());

    List<ProjectBudget> projectBudgets =
      new ArrayList<>(projectDB.getProjectBudgets().stream().filter(pb -> pb.isActive() && pb.getProject().isActive()
        && pb.getPhase() != null && pb.getPhase().equals(this.getActualPhase())).collect(Collectors.toList()));

    List<FundingSource> fundingSources = new ArrayList<>();
    for (ProjectBudget projectBudget : projectBudgets) {
      FundingSource fundingSourceDb =
        fundingSourceManager.getFundingSourceById(projectBudget.getFundingSource().getId());
      fundingSources.add(fundingSourceDb);

    }

    HashSet<FundingSource> fuHashSet = new HashSet<>();
    fuHashSet.addAll(fundingSources);

    fundingSources = new ArrayList<>(fuHashSet);
    for (FundingSource fundingSource : fundingSources) {
      fundingSource.getFundingSourceInfo(this.getActualPhase());
    }

    List<LocElement> locElements = new ArrayList<>();
    List<LocElementType> locElementTypes = new ArrayList<>();
    if (project.getProjecInfoPhase(this.getActualPhase()).getLocationRegional() == null) {
      project.getProjecInfoPhase(this.getActualPhase()).setLocationRegional(false);
    }

    boolean calculateYesOrNo = !this.hasSpecificities(APConstants.CRP_OTHER_LOCATIONS);
    if (calculateYesOrNo) {
      project.getProjecInfoPhase(this.getActualPhase()).setLocationGlobal(false);
      project.getProjecInfoPhase(this.getActualPhase()).setLocationRegional(false);
    }
    for (FundingSource fundingSource : fundingSources) {
      fundingSource = fundingSourceManager.getFundingSourceById(fundingSource.getId());
      fundingSource.setFundingSourceInfo(fundingSource.getFundingSourceInfo(this.getActualPhase()));
      if (fundingSource.getFundingSourceInfo() != null) {

        if (calculateYesOrNo) {
          project.getProjecInfoPhase(this.getActualPhase())
            .setLocationGlobal(project.getProjecInfoPhase(this.getActualPhase()).getLocationGlobal()
              || fundingSource.getFundingSourceInfo(this.getActualPhase()).isGlobal());

        }
        // get the funding source info from db
        fundingSource = fundingSourceManager.getFundingSourceById(fundingSource.getId());
        FundingSourceInfo fundingSourceInfo = fundingSource.getFundingSourceInfo(this.getActualPhase());
        // Filter The Fundign Source Location Acroding
        List<FundingSourceLocation> fundingSourceLocations = new ArrayList<>(fundingSource.getFundingSourceLocations()
          .stream().filter(fs -> fs.isActive() && fs.getPhase().equals(fundingSourceInfo.getPhase()))
          .collect(Collectors.toList()));

        for (FundingSourceLocation fundingSourceLocation : fundingSourceLocations) {
          if (fundingSourceLocation.getLocElementType() == null) {
            locElements.add(fundingSourceLocation.getLocElement());
            if (fundingSourceLocation != null && fundingSourceLocation.getLocElement() != null
              && fundingSourceLocation.getLocElement().getLocElementType() != null
              && fundingSourceLocation.getLocElement().getLocElementType().getId() != null
              && fundingSourceLocation.getLocElement().getLocElementType().getId() != 2) {
              if (calculateYesOrNo) {
                project.getProjecInfoPhase(this.getActualPhase())
                  .setLocationRegional(project.getProjecInfoPhase(this.getActualPhase()).getLocationRegional() || true);
              }

            }

          } else {
            locElementTypes.add(fundingSourceLocation.getLocElementType());
            if (calculateYesOrNo) {
              project.getProjecInfoPhase(this.getActualPhase())
                .setLocationRegional(project.getProjecInfoPhase(this.getActualPhase()).getLocationRegional() || true);
            }
          }
        }
      }

    }

    if (project.getCountryFS() == null) {
      project.setCountryFS(new ArrayList<>());
    }

    if (project.getRegionFS() == null) {
      project.setRegionFS(new ArrayList<>());
    }

    HashSet<LocElement> hashElements = new HashSet<>();
    hashElements.addAll(locElements);
    locElements = new ArrayList<>(hashElements);

    GlobalUnitProject gp = globalUnitProjectManager.findByProjectId(projectID);

    for (LocElement locElement : hashElements) {
      if (locElement != null && locElement.getId() != null) {
        CountryFundingSources countryFundingSources = new CountryFundingSources();
        countryFundingSources.setLocElement(locElement);

        List<FundingSource> sources = fundingSourceManager.searchFundingSourcesByLocElement(projectID,
          locElement.getId(), this.getCurrentCycleYear(), gp.getGlobalUnit().getId(), this.getActualPhase().getId());
        for (FundingSource fundingSourceElement : sources) {
          fundingSourceElement.setFundingSourceInfo(fundingSourceElement.getFundingSourceInfo(this.getActualPhase()));
        }
        countryFundingSources.setFundingSources(new ArrayList<>(sources));
        if (locElement.getLocElementType().getId().longValue() == 2) {
          if (!project.getCountryFS().contains(countryFundingSources)) {
            project.getCountryFS().add(countryFundingSources);
          }

        } else {
          if (!project.getRegionFS().contains(countryFundingSources)) {
            project.getRegionFS().add(countryFundingSources);
          }
        }
      }
    }

    HashSet<LocElementType> hashElementTypes = new HashSet<>();
    hashElementTypes.addAll(locElementTypes);
    locElementTypes = new ArrayList<>(hashElementTypes);

    for (LocElementType locElementType : hashElementTypes) {
      CountryFundingSources countryFundingSources = new CountryFundingSources();
      countryFundingSources.setLocElementType(locElementType);
      List<FundingSource> sources = fundingSourceManager.searchFundingSourcesByLocElementType(projectID,
        locElementType.getId(), this.getCurrentCycleYear(), loggedCrp.getId());
      for (FundingSource fundingSourceElement : sources) {
        fundingSourceElement.setFundingSourceInfo(fundingSourceElement.getFundingSourceInfo(this.getActualPhase()));
      }
      countryFundingSources.setFundingSources(new ArrayList<>(sources));
      if (!project.getRegionFS().contains(countryFundingSources)) {
        project.getRegionFS().add(countryFundingSources);
      }

    }
    Collections.sort(project.getCountryFS(),
      (tu1, tu2) -> tu1.getLocElement().getName().compareTo(tu2.getLocElement().getName()));

  }

  public void projectLocationNewData() {

    List<CountryLocationLevel> locationsDataPrew = this.getProjectLocationsData();

    if (project.getLocationsData() != null) {
      for (CountryLocationLevel locationData : project.getLocationsData()) {

        if (!locationsDataPrew.contains(locationData)) {

          if (locationData.getLocElements() != null && !locationData.getLocElements().isEmpty()) {
            for (LocElement locElement : locationData.getLocElements()) {
              if (locElement.getId() != null && locElement.getId() != -1) {

                LocElement element = locElementManager.getLocElementById(locElement.getId());

                if (!element.getName().equals(locElement.getName())) {
                  element.setName(locElement.getName());
                  locElementManager.saveLocElement(element);
                }

                ProjectLocation existProjectLocation = projectLocationManager.getProjectLocationByProjectAndLocElement(
                  project.getId(), locElement.getId(), this.getActualPhase().getId());

                if (existProjectLocation == null) {
                  ProjectLocation projectLocation = new ProjectLocation();
                  projectLocation.setProject(project);
                  projectLocation.setLocElement(element);
                  projectLocation.setPhase(this.getActualPhase());
                  projectLocationManager.saveProjectLocation(projectLocation);

                } else {

                  if (!existProjectLocation.isActive()) {
                    existProjectLocation.setActive(true);
                    existProjectLocation.setPhase(this.getActualPhase());
                    projectLocationManager.saveProjectLocation(existProjectLocation);
                  }
                }
              } else {
                this.saveGeoProjectLocation(locElement, locationData.getId());
              }
            }

            ProjectLocationElementType projectLocationElementType =
              projectLocationElementTypeManager.getByProjectAndElementType(project.getId(), locationData.getId());

            if (projectLocationElementType == null) {
              ProjectLocationElementType newProjectLocationElementType = new ProjectLocationElementType();

              LocElementType locElementType = locElementTypeManager.getLocElementTypeById(locationData.getId());

              newProjectLocationElementType.setLocElementType(locElementType);

              Project project = projectManager.getProjectById(this.project.getId());

              newProjectLocationElementType.setProject(project);

              newProjectLocationElementType.setIsGlobal(false);

              projectLocationElementTypeManager.saveProjectLocationElementType(newProjectLocationElementType);

            } else {
              projectLocationElementType.setIsGlobal(false);

              projectLocationElementTypeManager.saveProjectLocationElementType(projectLocationElementType);
            }

          }
        } else {

          if (locationData.getLocElements() != null) {
            for (LocElement locElement : locationData.getLocElements()) {
              if (locElement.getId() != null && locElement.getId() != -1) {

                LocElement element = locElementManager.getLocElementById(locElement.getId());

                if (!element.getName().equals(locElement.getName())) {
                  element.setName(locElement.getName());
                  locElementManager.saveLocElement(element);
                }
                if (locElement.getLocGeoposition() != null && locElement.getLocGeoposition().getId() != null) {
                  locElement.setLocGeoposition(
                    locGeopositionManager.getLocGeopositionById(locElement.getLocGeoposition().getId()));
                }

                if (element.getLocGeoposition() != null && element.getLocElementType().getCrp() == null) {
                  if ((element.getLocGeoposition().getLatitude() != locElement.getLocGeoposition().getLatitude())
                    || (element.getLocGeoposition().getLongitude() != locElement.getLocGeoposition().getLongitude())) {
                    element.getLocGeoposition().setLongitude(locElement.getLocGeoposition().getLongitude());
                    element.getLocGeoposition().setLatitude(locElement.getLocGeoposition().getLatitude());

                    locGeopositionManager.saveLocGeoposition(element.getLocGeoposition());
                  }
                } else {
                  ProjectLocation existProjectLocation =
                    projectLocationManager.getProjectLocationByProjectAndLocElement(project.getId(), locElement.getId(),
                      this.getActualPhase().getId());
                  if (existProjectLocation == null) {

                    ProjectLocation projectLocation = new ProjectLocation();
                    projectLocation.setProject(project);
                    projectLocation.setLocElement(element);
                    projectLocation.setPhase(this.getActualPhase());
                    projectLocationManager.saveProjectLocation(projectLocation);
                  }
                }
              } else {
                this.saveGeoProjectLocation(locElement, locationData.getId());
              }
            }
          }

        }
      }
    }

    Project projectDB = projectManager.getProjectById(projectID);
    List<LocElement> regionsCustomSaved = new ArrayList<>();

    for (ProjectLocation projectLocation : this.getDBLocations().stream().filter(
      c -> c.isActive() && c.getLocElement() != null && c.getLocElement().getLocElementType().getId().longValue() == 2)
      .collect(Collectors.toList())) {

      regionsCustomSaved.add(projectLocation.getLocElement());

    }

    if (project.getCountryFS() == null) {
      project.setCountryFS(new ArrayList<>());
    }

    for (CountryFundingSources countryFundingSources : project.getCountryFS()) {

      ProjectLocation projectLocationSave = new ProjectLocation();
      projectLocationSave.setProject(project);
      projectLocationSave.setPhase(this.getActualPhase());
      if (!regionsCustomSaved.contains(countryFundingSources.getLocElement()) && countryFundingSources.isSelected()) {

        projectLocationSave.setLocElement(countryFundingSources.getLocElement());
        projectLocationManager.saveProjectLocation(projectLocationSave);
      }

    }

  }

  public void projectLocationPreviousData() {
    List<CountryLocationLevel> locationsDataPrew = this.getProjectLocationsData();

    for (CountryLocationLevel countryLocationLevel : locationsDataPrew) {
      if (!project.getLocationsData().contains(countryLocationLevel)) {
        for (LocElement locElement : countryLocationLevel.getLocElements()) {
          ProjectLocation projectLocation = this.getDBLocations().stream()
            .filter(
              pl -> pl.isActive() && pl.getLocElement() != null && pl.getLocElement().getId() == locElement.getId())
            .collect(Collectors.toList()).get(0);

          if (locElementManager.getLocElementById(projectLocation.getLocElement().getId()).getLocElementType().getId()
            .longValue() == 2) {
            if (project.getCountryFS().stream()
              .filter(c -> c.getLocElement() != null
                && c.getLocElement().getId().longValue() == projectLocation.getLocElement().getId().longValue()
                && c.isSelected())
              .collect(Collectors.toList()).isEmpty()) {
              projectLocationManager.deleteProjectLocation(projectLocation.getId());
            }
          } else {
            projectLocationManager.deleteProjectLocation(projectLocation.getId());
          }

        }

        ProjectLocationElementType projectLocationElementType =
          projectLocationElementTypeManager.getByProjectAndElementType(project.getId(), countryLocationLevel.getId());

        if (projectLocationElementType != null) {
          projectLocationElementTypeManager.deleteProjectLocationElementType(projectLocationElementType.getId());
        }
      } else {
        for (CountryLocationLevel locationData : project.getLocationsData()) {
          if (locationData.equals(countryLocationLevel)) {
            List<LocElement> locElements = countryLocationLevel.getLocElements();
            for (LocElement element : locElements) {
              if (locationData.getLocElements() != null) {
                if (!locationData.getLocElements().contains(element)) {
                  ProjectLocation projectLocation = this.getDBLocations().stream().filter(
                    pl -> pl.isActive() && pl.getLocElement() != null && pl.getLocElement().getId() == element.getId())
                    .collect(Collectors.toList()).get(0);
                  if (locElementManager.getLocElementById(projectLocation.getLocElement().getId()).getLocElementType()
                    .getId().longValue() == 2) {
                    if (project.getCountryFS().stream()
                      .filter(c -> c.getLocElement() != null
                        && c.getLocElement().getId().longValue() == projectLocation.getLocElement().getId().longValue()
                        && c.isSelected())
                      .collect(Collectors.toList()).isEmpty()) {
                      projectLocationManager.deleteProjectLocation(projectLocation.getId());
                    }
                  } else {
                    projectLocationManager.deleteProjectLocation(projectLocation.getId());
                  }
                }
              } else {
                ProjectLocation projectLocation = this.getDBLocations().stream()
                  .filter(
                    pl -> pl.isActive() && pl.getLocElement() != null && pl.getLocElement().getId() == element.getId())
                  .collect(Collectors.toList()).get(0);
                projectLocationManager.deleteProjectLocation(projectLocation.getId());
              }

            }
          }
        }
      }
    }

  }

  @Override
  public String save() {
    if (this.hasPermission("canEdit")) {

      Project projectDB = projectManager.getProjectById(project.getId());

      boolean isProjectGlobal = project.getProjecInfoPhase(this.getActualPhase()).getLocationGlobal();
      boolean isProjectRegional = project.getProjecInfoPhase(this.getActualPhase()).getLocationRegional();
      this.projectLocationPreviousData();

      this.projectLocationNewData();
      this.saveRegions();

      if (this.hasSpecificities(this.crpLocationCsvActivities())) {
        if (project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile() != null) {
          if (project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile().getId() == null) {
            project.getProjecInfoPhase(this.getActualPhase()).setActivitiesCSVFile(null);
          } else {
            projectDB.getProjecInfoPhase(this.getActualPhase())
              .setActivitiesCSVFile(project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile());
          }
        }
      }

      List<String> relationsName = new ArrayList<>();
      relationsName.add(APConstants.PROJECT_LOCATIONS_RELATION);
      relationsName.add(APConstants.PROJECT_INFO_RELATION);

      project = projectManager.getProjectById(projectID);
      project.getProjecInfoPhase(this.getActualPhase()).setModificationJustification(this.getJustification());
      projectDB.getProjecInfoPhase(this.getActualPhase()).setLocationGlobal(isProjectGlobal);
      projectDB.getProjecInfoPhase(this.getActualPhase()).setLocationRegional(isProjectRegional);
      projectInfoManager.saveProjectInfo(projectDB.getProjecInfoPhase(this.getActualPhase()));
      /**
       * The following is required because we need to update something on the @Project
       * if we want a row created in the auditlog table.
       */
      this.setModificationJustification(project);
      projectManager.saveProject(project, this.getActionName(), relationsName, this.getActualPhase());
      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists()) {
        path.toFile().delete();
      }
      if (this.getUrl() == null || this.getUrl().isEmpty()) {
        Collection<String> messages = this.getActionMessages();
        if (!this.getInvalidFields().isEmpty()) {
          this.setActionMessages(null);
          // this.addActionMessage(Map.toString(this.getInvalidFields().toArray()));
          List<String> keys = new ArrayList<String>(this.getInvalidFields().keySet());
          for (String key : keys) {
            this.addActionMessage(key + ": " + this.getInvalidFields().get(key));
          }

        } else {
          this.addActionMessage("message:" + this.getText("saving.saved"));
        }
        return SUCCESS;
      } else {
        this.addActionMessage("");
        this.setActionMessages(null);
        return REDIRECT;
      }
    }
    return SUCCESS;
  }

  public void saveGeoProjectLocation(LocElement locElement, Long elementTypeId) {
    LocElement parentElement = locElementManager.getLocElementByISOCode(locElement.getIsoAlpha2());
    LocElementType typeLement = locElementTypeManager.getLocElementTypeById(elementTypeId);

    LocGeoposition geoposition = new LocGeoposition();
    geoposition.setLatitude(
      locElement.getLocGeoposition().getLatitude() != null ? locElement.getLocGeoposition().getLatitude() : 0.0);
    geoposition.setLongitude(
      locElement.getLocGeoposition().getLongitude() != null ? locElement.getLocGeoposition().getLongitude() : 0.0);

    locGeopositionManager.saveLocGeoposition(geoposition);

    if (geoposition.getId() != null) {
      LocElement element = new LocElement();
      element.setCrp(loggedCrp);
      element.setLocElement(parentElement);
      element.setLocElementType(typeLement);
      element.setName(locElement.getName());
      element.setLocGeoposition(geoposition);
      element.setIsSiteIntegration(false);

      element = locElementManager.saveLocElement(element);

      ProjectLocation projectLocation = new ProjectLocation();
      projectLocation.setProject(project);
      projectLocation.setLocElement(element);
      projectLocation.setPhase(this.getActualPhase());
      projectLocationManager.saveProjectLocation(projectLocation);
    }

  }

  public void saveRegions() {

    Project projectDB = projectManager.getProjectById(projectID);

    if (project.getProjectRegions() == null) {
      project.setProjectRegions(new ArrayList<>());
    }

    List<ProjectLocation> regions = new ArrayList<>(this
      .getDBLocations().stream().filter(fl -> fl.isActive() && fl.getLocElement() != null
        && fl.getLocElement().getLocElementType() != null && fl.getLocElement().getLocElementType().getId() == 1)
      .collect(Collectors.toList()));
    regions.addAll(this.getDBLocations().stream()
      .filter(fl -> fl.isActive() && fl.getLocElement() == null && fl.getLocElementType() != null)
      .collect(Collectors.toList()));
    if (regions != null && regions.size() > 0) {

      if (project.getProjecInfoPhase(this.getActualPhase()).getLocationRegional()) {
        for (ProjectLocation projectLocation : regions) {

          if (projectLocation != null) {
            if (!project.getProjectRegions().contains(projectLocation)) {
              if (projectLocation.getLocElementType() != null && projectLocation.getLocElementType().getId() != null) {
                if (project.getRegionFS().stream()
                  .filter(c -> c.getLocElementType() != null && c.getLocElementType().getId()
                    .longValue() == projectLocation.getLocElementType().getId().longValue() && c.isSelected())
                  .collect(Collectors.toList()).isEmpty()) {
                  projectLocationManager.deleteProjectLocation(projectLocation.getId());
                }
              }

            }
          }

        }
      } else {
        for (ProjectLocation projectLocation : regions) {
          projectLocationManager.deleteProjectLocation(projectLocation.getId());
        }
      }
    }

    regions = new ArrayList<>(this.getDBLocations().stream()
      .filter(fl -> fl.isActive() && fl.getLocElementType() == null && fl.getLocElement() != null
        && fl.getLocElement().getLocElementType() != null
        && fl.getLocElement().getLocElementType().getId().longValue() == 1)
      .collect(Collectors.toList()));

    if (regions != null && regions.size() > 0) {

      if (project.getProjecInfoPhase(this.getActualPhase()).getLocationRegional()) {
        for (ProjectLocation projectLocation : regions) {
          if (projectLocation != null) {
            if (!project.getProjectRegions().contains(projectLocation)) {
              if (projectLocation.getLocElement() != null && projectLocation.getLocElement().getId() != null) {
                if (project.getRegionFS().stream()
                  .filter(c -> c.getLocElement() != null
                    && c.getLocElement().getId().longValue() == projectLocation.getLocElement().getId().longValue()
                    && c.isSelected())
                  .collect(Collectors.toList()).isEmpty()) {
                  projectLocationManager.deleteProjectLocation(projectLocation.getId());
                }
              }

            }
          }

        }
      } else {
        for (ProjectLocation projectLocation : regions) {
          projectLocationManager.deleteProjectLocation(projectLocation.getId());
        }
      }
    }

    for (ProjectLocation projectLocation : project.getProjectRegions()) {

      if (projectLocation.getId() == null || projectLocation.getId() == -1) {

        ProjectLocation projectLocationSave = new ProjectLocation();
        projectLocationSave.setProject(project);
        projectLocationSave.setPhase(this.getActualPhase());
        if (!projectLocation.isScope()) {
          LocElement locElement = locElementManager.getLocElementById(projectLocation.getLocElement().getId());

          projectLocationSave.setLocElement(locElement);
        } else {
          long elementId = projectLocation.getLocElement().getId();
          LocElementType elementType = locElementTypeManager.getLocElementTypeById(elementId);

          projectLocationSave.setLocElementType(elementType);
        }

        projectLocationManager.saveProjectLocation(projectLocationSave);
      }
    }

    projectDB = projectManager.getProjectById(projectID);
    regions = new ArrayList<>(this
      .getDBLocations().stream().filter(fl -> fl.isActive() && fl.getLocElement() != null
        && fl.getLocElement().getLocElementType() != null && fl.getLocElement().getLocElementType().getId() == 1)
      .collect(Collectors.toList()));
    regions.addAll(this.getDBLocations().stream()
      .filter(fl -> fl.isActive() && fl.getLocElement() == null && fl.getLocElementType() != null)
      .collect(Collectors.toList()));

    List<LocElement> regionsSaved = new ArrayList<>();
    List<LocElementType> regionsCustomSaved = new ArrayList<>();

    for (ProjectLocation projectLocation : regions) {
      if (projectLocation.getLocElement() == null) {
        regionsCustomSaved.add(projectLocation.getLocElementType());
      } else {
        regionsSaved.add(projectLocation.getLocElement());
      }
    }

    if (project.getRegionFS() == null) {
      project.setRegionFS(new ArrayList<>());
    }

    for (CountryFundingSources countryFundingSources : project.getRegionFS()) {

      ProjectLocation projectLocationSave = new ProjectLocation();
      projectLocationSave.setProject(project);
      projectLocationSave.setPhase(this.getActualPhase());
      if (countryFundingSources.getLocElement() == null) {
        if (!regionsCustomSaved.contains(countryFundingSources.getLocElementType())
          && countryFundingSources.isSelected()) {

          projectLocationSave.setLocElementType(countryFundingSources.getLocElementType());
          projectLocationManager.saveProjectLocation(projectLocationSave);
        }

      } else {
        if (!regionsSaved.contains(countryFundingSources.getLocElement()) && countryFundingSources.isSelected()) {

          projectLocationSave.setLocElement(countryFundingSources.getLocElement());
          projectLocationManager.saveProjectLocation(projectLocationSave);
        }
      }
    }
  }

  public void setLocationsLevels(List<LocationLevel> locationsLevels) {
    this.locationsLevels = locationsLevels;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(long projectID) {
    this.projectID = projectID;
  }

  public void setRegion(boolean region) {
    this.region = region;
  }

  public void setRegionLists(List<LocElement> regionLists) {
    this.regionLists = regionLists;
  }

  public void setScopeData(List<ScopeData> scopeData) {
    this.scopeData = scopeData;
  }

  public void setScopeRegionLists(List<LocElementType> scopeRegionLists) {
    this.scopeRegionLists = scopeRegionLists;
  }

  public void setScopeRegions(List<LocElementType> scopeRegions) {
    this.scopeRegions = scopeRegions;
  }

  public void setTransaction(String transaction) {
    this.transaction = transaction;
  }

  @Override
  public void validate() {
    if (save) {
      if (this.hasSpecificities(this.crpLocationCsvActivities())) {
        if (project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile() != null
          && (project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile().getId() == null
            || project.getProjecInfoPhase(this.getActualPhase()).getActivitiesCSVFile().getId().longValue() == -1)) {
          project.getProjecInfoPhase(this.getActualPhase()).setActivitiesCSVFile(null);
        }
      }
      locationValidator.validate(this, project, true);
    }
  }
}