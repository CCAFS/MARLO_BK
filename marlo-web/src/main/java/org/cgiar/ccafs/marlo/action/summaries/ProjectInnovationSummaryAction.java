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

package org.cgiar.ccafs.marlo.action.summaries;

import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.GlobalUnitManager;
import org.cgiar.ccafs.marlo.data.manager.NATRedirectionLinkManager;
import org.cgiar.ccafs.marlo.data.manager.PhaseManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectExpectedStudyInnovationManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationContributingOrganizationManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationCountryManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationDeliverableManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationGeographicScopeManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationMilestoneManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationRegionManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectInnovationSubIdoManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectManager;
import org.cgiar.ccafs.marlo.data.model.Deliverable;
import org.cgiar.ccafs.marlo.data.model.DeliverableDissemination;
import org.cgiar.ccafs.marlo.data.model.NATRedirectionLink;
import org.cgiar.ccafs.marlo.data.model.Phase;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectExpectedStudyInnovation;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationCenter;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationContributingOrganization;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationCountry;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationCrp;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationDeliverable;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationEvidenceLink;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationGeographicScope;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationInfo;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationMilestone;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationOrganization;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationRegion;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationSdgTarget;
import org.cgiar.ccafs.marlo.data.model.ProjectInnovationSubIdo;
import org.cgiar.ccafs.marlo.utils.APConfig;
import org.cgiar.ccafs.marlo.utils.URLShortener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.Parameter;
import org.pentaho.reporting.engine.classic.core.CompoundDataFactory;
import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.ItemBand;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.SubReport;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.util.TypedTableModel;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andr??s Valencia - CIAT/CCAFS
 */
public class ProjectInnovationSummaryAction extends BaseSummariesAction implements Summary {

  private static final long serialVersionUID = 1L;
  private static Logger LOG = LoggerFactory.getLogger(ProjectInnovationSummaryAction.class);
  private final static String INDICATOR_NAME = "Innovation";

  // Managers
  private final ProjectInnovationContributingOrganizationManager projectInnovationContributingOrganizationManager;
  private final ProjectInnovationManager projectInnovationManager;
  private final ProjectInnovationDeliverableManager projectInnovationDeliverableManager;
  private final ResourceManager resourceManager;
  private final ProjectInnovationGeographicScopeManager projectInnovationGeographicScopeManager;
  private final ProjectInnovationRegionManager projectInnovationRegionManager;
  private final ProjectInnovationCountryManager projectInnovationCountryManager;
  private final ProjectInnovationMilestoneManager projectInnovationMilestoneManager;
  private final ProjectInnovationSubIdoManager projectInnovationSubIdoManager;
  private ProjectExpectedStudyInnovationManager projectExpectedStudyInnovationManager;
  private NATRedirectionLinkManager natRedirectionLinkManager;

  // Parameters
  private long startTime;
  private Long projectInnovationID;
  private ProjectInnovationInfo projectInnovationInfo;
  private String crp;

  // XLSX bytes
  private byte[] bytesPDF;
  // Streams
  InputStream inputStream;

  private String redirectUrl;

  @Inject
  public ProjectInnovationSummaryAction(APConfig config, GlobalUnitManager crpManager,
    ProjectInnovationManager projectInnovationManager, PhaseManager phaseManager, ResourceManager resourceManager,
    ProjectManager projectManager,
    ProjectInnovationContributingOrganizationManager projectInnovationContributingOrganizationManager,
    ProjectInnovationDeliverableManager projectInnovationDeliverableManager,
    ProjectInnovationGeographicScopeManager projectInnovationGeographicScopeManager,
    ProjectInnovationRegionManager projectInnovationRegionManager,
    ProjectInnovationCountryManager projectInnovationCountryManager,
    ProjectInnovationMilestoneManager projectInnovationMilestoneManager,
    ProjectInnovationSubIdoManager projectInnovationSubIdoManager,
    ProjectExpectedStudyInnovationManager projectExpectedStudyInnovationManager,
    NATRedirectionLinkManager natRedirectionLinkManager) {
    super(config, crpManager, phaseManager, projectManager);
    this.projectInnovationManager = projectInnovationManager;
    this.resourceManager = resourceManager;
    this.projectInnovationContributingOrganizationManager = projectInnovationContributingOrganizationManager;
    this.projectInnovationDeliverableManager = projectInnovationDeliverableManager;
    this.projectInnovationGeographicScopeManager = projectInnovationGeographicScopeManager;
    this.projectInnovationRegionManager = projectInnovationRegionManager;
    this.projectInnovationCountryManager = projectInnovationCountryManager;
    this.projectInnovationMilestoneManager = projectInnovationMilestoneManager;
    this.projectInnovationSubIdoManager = projectInnovationSubIdoManager;
    this.projectExpectedStudyInnovationManager = projectExpectedStudyInnovationManager;
    this.natRedirectionLinkManager = natRedirectionLinkManager;
  }

  /**
   * Method to add i8n parameters to masterReport in Pentaho
   * 
   * @param masterReport
   * @return masterReport with i8n parameters added
   */
  private MasterReport addi8nParameters(MasterReport masterReport, boolean isAlliance) {
    masterReport.getParameterValues().put("i8nInnovationNoData", this.getText("summaries.innovation.noData"));
    masterReport.getParameterValues().put("i8nInnovationsRInnovation", this.getText("summaries.innovation"));
    masterReport.getParameterValues().put("i8nInnovationRTitle", this.getText("projectInnovations.title"));
    masterReport.getParameterValues().put("i8nInnovationRNarrative",
      this.getText("projectInnovations.narrative.readText"));
    masterReport.getParameterValues().put("i8nInnovationRPhaseResearch", this.getText("projectInnovations.phase"));
    masterReport.getParameterValues().put("i8nInnovationRStageInnovation", this.getText("projectInnovations.stage"));
    masterReport.getParameterValues().put("i8nInnovationRInnovationType",
      this.getText("projectInnovations.innovationType"));
    masterReport.getParameterValues().put("i8nInnovationRContributionOfCrp",
      this.getText("projectInnovations.contributionOfCrp"));
    masterReport.getParameterValues().put("i8nInnovationRDegreeInnovation",
      this.getText("projectInnovations.degreeInnovation"));
    masterReport.getParameterValues().put("i8nInnovationRGeographicScope",
      this.getText("projectInnovations.geographicScope"));
    masterReport.getParameterValues().put("i8nInnovationRInnovationNumber",
      this.getText("projectInnovations.innovationNumber"));
    masterReport.getParameterValues().put("i8nInnovationRRegion", this.getText("projectInnovations.region"));
    masterReport.getParameterValues().put("i8nInnovationRCountries", this.getText("projectInnovations.countries"));
    masterReport.getParameterValues().put("i8nInnovationROrganizations",
      this.getText("summaries.innovation.organizationalType"));
    masterReport.getParameterValues().put("i8nInnovationRProjectExpectedStudy",
      this.getText("caseStudy.caseStudyTitle"));
    masterReport.getParameterValues().put("i8nInnovationRDescriptionStage",
      this.getText("projectInnovations.stageDescription.readText"));
    masterReport.getParameterValues().put("i8nInnovationHasMilestones",
      this.getText("projectInnovations.hasMilestones"));
    masterReport.getParameterValues().put("i8nInnovationMilestones", this.getText("projectInnovations.milestones"));
    masterReport.getParameterValues().put("i8nInnovationSubIdos", this.getText("projectInnovations.subIdos"));

    masterReport.getParameterValues().put("i8nInnovationLeadOrganization",
      this.getText("projectInnovations.leadOrganization"));
    masterReport.getParameterValues().put("i8nInnovationContributionOrganization",
      this.getText("projectInnovations.contributingOrganizations"));
    masterReport.getParameterValues().put("i8nInnovationContributionCenters",
      this.getText("projectInnovations.contributingCenters"));
    masterReport.getParameterValues().put("i8nInnovationAdaptativeResearch", "projectInnovations.adaptativeResearch");
    masterReport.getParameterValues().put("i8nInnovationREvidenceLink",
      this.getText("summaries.innovation.evidenceLink"));
    masterReport.getParameterValues().put("i8nInnovationRDeliverables",
      this.getText("projectInnovations.deliverableId"));
    masterReport.getParameterValues().put("i8nInnovationRCrps",
      this.getText(isAlliance ? "innovation.legacyCrp" : "projectInnovations.contributing"));
    masterReport.getParameterValues().put("i8nInnovationRGenderFocusLevel",
      this.getText("projectInnovations.genderRelevance"));
    masterReport.getParameterValues().put("i8nInnovationRNew", this.getText("projectInnovations.isNew"));
    masterReport.getParameterValues().put("i8nInnovationRYouthFocusLevel",
      this.getText("projectInnovations.youthRelevance"));
    masterReport.getParameterValues().put("i8nInnovationRYouthExplaniation",
      this.getText("projectInnovations.youthRelevance.explanation.readText"));
    masterReport.getParameterValues().put("i8nInnovationRNew",
      this.getText("projectInnovations.youthRelevance.explanation.readText"));
    masterReport.getParameterValues().put("i8nProject", this.getText("summaries.oaprojects.projectTitle"));

    if (isAlliance) {
      masterReport.getParameterValues().put("i8nInnovationRHasLegacyCrpsText",
        this.getText("innovation.legacyCrp.question"));
      masterReport.getParameterValues().put("i8nInnovationRSdgTargets", this.getText("innovation.sdgTargets"));
    }

    return masterReport;
  }

  @Override
  public String execute() throws Exception {

    if (this.getSelectedPhase() == null) {
      return NOT_FOUND;
    }

    if (projectInnovationID != null && projectInnovationID > 0) {
      NATRedirectionLink redirectionLink =
        this.natRedirectionLinkManager.findByIndicatorAndId(INDICATOR_NAME, projectInnovationID);

      if (redirectionLink != null) {
        this.redirectUrl = redirectionLink.getRedirectionUrl();
        return REDIRECT;
      }
    }

    LOG.info("Start report download: " + this.getFileName() + ". User: " + this.getDownloadByUser() + ". CRP: "
      + this.getLoggedCrp().getAcronym());

    if (projectInnovationID == null || projectInnovationManager.getProjectInnovationById(projectInnovationID) == null
      || projectInnovationManager.getProjectInnovationById(projectInnovationID)
        .getProjectInnovationInfo(this.getSelectedPhase()) == null) {
      LOG.error("Project Innovation " + projectInnovationID + " Not found");
      return NOT_FOUND;
    } else {
      projectInnovationInfo = projectInnovationManager.getProjectInnovationById(projectInnovationID)
        .getProjectInnovationInfo(this.getSelectedPhase());
    }
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      crp = this.getLoggedCrp().getAcronym();
      if (crp == null || crp.isEmpty()) {
        String[] actionMap = ActionContext.getContext().getName().split("/");
        if (actionMap.length > 1) {
          String enteredCrp = actionMap[0];
          crp = crpManager.findGlobalUnitByAcronym(enteredCrp).getAcronym();
        }
      }

      String center = crp;
      boolean isAlliance = "Alliance".equalsIgnoreCase(center);

      Resource reportResource = resourceManager
        .createDirectly(this.getClass().getResource("/pentaho/crp/ProjectInnovationPDF.prpt"), MasterReport.class);
      MasterReport masterReport = (MasterReport) reportResource.getResource();
      // Get datetime
      ZonedDateTime timezone = ZonedDateTime.now();
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d 'at' HH:mm ");
      String zone = timezone.getOffset() + "";
      if (zone.equals("Z")) {
        zone = "+0";
      }
      String date = timezone.format(format) + "(GMT" + zone + ")";
      // Set Main_Query
      CompoundDataFactory cdf = CompoundDataFactory.normalize(masterReport.getDataFactory());
      String masterQueryName = "main";
      TableDataFactory sdf = (TableDataFactory) cdf.getDataFactoryForQuery(masterQueryName);
      TypedTableModel model = this.getMasterTableModel(center, date, String.valueOf(this.getSelectedYear()));
      sdf.addTable(masterQueryName, model);
      masterReport.setDataFactory(cdf);
      // Set i8n for pentaho
      masterReport = this.addi8nParameters(masterReport, isAlliance);
      // Get details band
      ItemBand masteritemBand = masterReport.getItemBand();
      // Create new empty subreport hash map
      HashMap<String, Element> hm = new HashMap<String, Element>();
      // method to get all the subreports in the prpt and store in the HashMap
      this.getAllSubreports(hm, masteritemBand);
      // Uncomment to see which Subreports are detecting the method getAllSubreports
      // System.out.println("Pentaho SubReports: " + hm);
      this.fillSubreport((SubReport) hm.get("project_innovation"), "project_innovation", isAlliance);
      PdfReportUtil.createPDF(masterReport, os);
      bytesPDF = os.toByteArray();
      os.close();
    } catch (Exception e) {
      LOG.error("Error generating ProjectInnovation Summary: " + e.getMessage());
      throw e;
    }
    // Calculate time of generation
    long stopTime = System.currentTimeMillis();
    stopTime = stopTime - startTime;
    LOG.info("Downloaded successfully: " + this.getFileName() + ". User: " + this.getDownloadByUser() + ". CRP: "
      + this.getLoggedCrp().getAcronym() + ". Time to generate: " + stopTime + "ms.");
    return SUCCESS;
  }

  private void fillSubreport(SubReport subReport, String query, boolean isAlliance) {
    CompoundDataFactory cdf = CompoundDataFactory.normalize(subReport.getDataFactory());
    TableDataFactory sdf = (TableDataFactory) cdf.getDataFactoryForQuery(query);
    TypedTableModel model = null;
    switch (query) {
      case "project_innovation":
        model = this.getProjectInnovationTableModel(isAlliance);
        break;
    }
    sdf.addTable(query, model);
    subReport.setDataFactory(cdf);
  }

  public byte[] getBytesPDF() {
    return bytesPDF;
  }


  @Override
  public int getContentLength() {
    return bytesPDF.length;
  }

  @Override
  public String getContentType() {
    return "application/pdf";
  }

  private String getDeliverableDirectLink(Deliverable deliverable, Phase phase) {
    String url = "<b>Not disseminated</b>";
    DeliverableDissemination disseminationPhase = deliverable.getDissemination(phase);
    if (disseminationPhase != null) {
      if (disseminationPhase.getAlreadyDisseminated() != null && disseminationPhase.getAlreadyDisseminated()) {
        url = StringUtils.trimToNull(disseminationPhase.getDisseminationUrl());

        if (url == null) {
          if (!ListUtils.emptyIfNull(deliverable.getMetadataElements(phase)).isEmpty()) {
            deliverable.setMetadataElements(deliverable.getMetadataElements(phase));
            url = StringUtils.trimToNull(deliverable.getMetadataValueByEncondedName(APConstants.METADATAELEMENTDOI));
            if (url == null) {
              url =
                StringUtils.trimToNull(deliverable.getMetadataValueByEncondedName(APConstants.METADATAELEMENTHANDLE));
            }
          }
        }

        if (url == null && disseminationPhase != null) {
          url = StringUtils.trimToNull(disseminationPhase.getArticleUrl());
        }

        if (url == null) {
          url = "<b>Not provided</b>";
        }
      } else {
        if (disseminationPhase.getConfidential() != null && disseminationPhase.getConfidential()) {
          // url = StringUtils.trimToNull(disseminationPhase.getConfidentialUrl());
          url = "<b>Marked as Confidential</b>";
        } else {
          url = "<b>Not disseminated</b>";
        }
      }
    }

    return url;
  }


  @SuppressWarnings("unused")
  private File getFile(String fileName) {
    // Get file from resources folder
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    return file;
  }

  @Override
  public String getFileName() {
    StringBuffer fileName = new StringBuffer();
    fileName.append("ProjectInnovationSummary-");
    fileName.append(this.getLoggedCrp().getAcronym() + "-");
    try {
      if (projectInnovationInfo != null && projectInnovationInfo.getProjectInnovation().getProject() != null) {
        fileName.append(projectInnovationInfo.getProjectInnovation().getProject()
          .getStandardIdentifier(Project.EMAIL_SUBJECT_IDENTIFIER) + "-");
      }
    } catch (Exception e) {
      LOG.info("Error getting project for innovation: " + projectInnovationID);
    }
    fileName.append("I" + projectInnovationID + "-");
    fileName.append(this.getSelectedYear() + "_");
    fileName.append(new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()));
    fileName.append(".pdf");
    return fileName.toString();
  }

  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesPDF);
    }
    return inputStream;
  }

  private TypedTableModel getMasterTableModel(String center, String date, String year) {
    // Initialization of Model
    TypedTableModel model = new TypedTableModel(new String[] {"center", "date", "year", "id", "cycle"},
      new Class[] {String.class, String.class, String.class, Long.class, String.class});
    model.addRow(new Object[] {center, date, year, projectInnovationID, this.getSelectedCycle()});
    return model;
  }

  private TypedTableModel getProjectInnovationTableModel(boolean isAlliance) {
    TypedTableModel model = new TypedTableModel(
      new String[] {"id", "isRegional", "isNational", "isStage4", "title", "narrative", "stageInnovation",
        "innovationType", "contributionOfCrp", "degreeInnovation", "geographicScope", "region", "countries",
        "organizations", "projectExpectedStudy", "descriptionStage", "leadOrganization", "contributingOrganization",
        "adaptativeResearch", "evidenceLink", "deliverables", "crps", "genderFocusLevel", "genderExplaniation",
        "youthFocusLevel", "youthExplaniation", "project", "oicr", "centers", "hasMilestones", "milestones", "subIdos",
        "deliverableLink", "phaseID", "center", "isNew", "innovationNumber", "isAlliance", "hasLegacyCrps",
        "hasLegacyCrpsText", "sdgTargets"},
      new Class[] {Long.class, Boolean.class, Boolean.class, Boolean.class, String.class, String.class, String.class,
        String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,
        String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,
        String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class,
        String.class, String.class, String.class, String.class, String.class, String.class, Boolean.class,
        Boolean.class, String.class, String.class},
      0);
    Long id = null;
    String title = null, narrative = null, phaseResearch = null, stageInnovation = null, innovationType = null,
      contributionOfCrp = null, degreeInnovation = null, geographicScope = null, region = null, countries = null,
      organizations = null, projectExpectedStudy = null, projectExpectedStudies = null, descriptionStage = null,
      leadOrganization = null, contributingOrganization = null, adaptativeResearch = null, links = null,
      deliverables = null, crps = null, genderFocusLevel = null, genderExplaniation = null, youthFocusLevel = null,
      youthExplaniation = null, project = null, oicr = "", centers = "", hasMilestones = "", milestones = null,
      subIdos = null, deliverableLink = "", phaseID = "", deliverableID = "", isNew = null, innovationNumber = null,
      hasLegacyCrpsText = "", sdgTargets = "";
    Boolean isRegional = false, isNational = false, isStage4 = false, hasLegacyCrps = false;
    StringBuffer evidenceLink = new StringBuffer();
    URLShortener urlShortener = new URLShortener();
    // Id
    id = projectInnovationID;
    // Title
    if (projectInnovationInfo.getTitle() != null && !projectInnovationInfo.getTitle().trim().isEmpty()) {
      title = projectInnovationInfo.getTitle();
    }
    // Narrative
    if (projectInnovationInfo.getNarrative() != null && !projectInnovationInfo.getNarrative().trim().isEmpty()) {
      narrative = projectInnovationInfo.getNarrative();
    }
    // Phase Research
    if (projectInnovationInfo.getRepIndPhaseResearchPartnership() != null) {
      phaseResearch = projectInnovationInfo.getRepIndPhaseResearchPartnership().getName();
    }
    // Stage
    if (projectInnovationInfo.getRepIndStageInnovation() != null) {
      stageInnovation = projectInnovationInfo.getRepIndStageInnovation().getName();
      if (projectInnovationInfo.getRepIndStageInnovation().getId()
        .equals(APConstants.REP_IND_STAGE_INNOVATION_STAGE4)) {
        isStage4 = true;
        // Organizations
        List<ProjectInnovationOrganization> projectInnovationOrganizations =
          projectInnovationInfo.getProjectInnovation().getProjectInnovationOrganizations().stream()
            .filter(o -> o.isActive() && o.getPhase() != null && o.getPhase().equals(this.getSelectedPhase()))
            .collect(Collectors.toList());
        if (projectInnovationOrganizations != null && projectInnovationOrganizations.size() > 0) {
          Set<String> organizationSet = new HashSet<>();
          for (ProjectInnovationOrganization projectInnovationOrganization : projectInnovationOrganizations) {
            organizationSet.add(
              "<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + projectInnovationOrganization.getRepIndOrganizationType().getName());
          }
          organizations = String.join("", organizationSet);
        }

        // Studies
        List<ProjectExpectedStudyInnovation> studyInnovations = new ArrayList<>();

        // Expected Study Innovations List
        if (projectExpectedStudyInnovationManager.findAll() != null && projectExpectedStudyInnovationManager.findAll()
          .stream().filter(p -> p != null && p.getPhase().getId().equals(this.getActualPhase().getId())
            && p.getProjectInnovation().getId().equals(projectInnovationInfo.getProjectInnovation().getId())) != null) {
          studyInnovations = projectExpectedStudyInnovationManager.findAll().stream()
            .filter(p -> p != null && p.getPhase().getId().equals(this.getActualPhase().getId())
              && p.getProjectInnovation().getId().equals(projectInnovationInfo.getProjectInnovation().getId()))
            .collect(Collectors.toList());
        }

        if (projectInnovationInfo.getProjectExpectedStudy() != null && projectInnovationInfo.getProjectExpectedStudy()
          .getProjectExpectedStudyInfo(this.getSelectedPhase()) != null) {
          String studyUrl = null;

          if (studyInnovations != null) {
            projectExpectedStudy = projectInnovationInfo.getProjectExpectedStudy().getId() + " - "
              + projectInnovationInfo.getProjectExpectedStudy().getProjectExpectedStudyInfo().getTitle();

          }

          for (ProjectExpectedStudyInnovation studyInnovation : studyInnovations) {

            studyUrl = this.getBaseUrl() + "/projects/" + this.getLoggedCrp().getAcronym() + "/studySummary.do?studyID="
              + studyInnovation.getProjectExpectedStudy().getId() + "&cycle=" + this.getSelectedCycle() + "&year="
              + this.getSelectedPhase().getYear() + "";
            studyUrl = urlShortener.getShortUrlService(studyUrl);

            projectExpectedStudies = "<br>&nbsp;&nbsp;&nbsp;&nbsp; ???"
              + studyInnovation.getProjectExpectedStudy().getId() + " - "
              + studyInnovation.getProjectExpectedStudy().getProjectExpectedStudyInfo(this.getActualPhase()).getTitle()
              + " (" + studyUrl + ")";

            /*
             * oicr += this.getBaseUrl() + "/projects/" + this.getLoggedCrp().getAcronym() + "/studySummary.do?studyID="
             * + studyInnovation.getProjectExpectedStudy().getId() + "&cycle=" + this.getSelectedCycle() + "&year="
             * + this.getSelectedPhase().getYear() + "";
             */
          }

          // oicr link
          /*
           * oicr = this.getBaseUrl() + "/projects/" + this.getLoggedCrp().getAcronym() + "/studySummary.do?studyID="
           * + projectInnovationInfo.getProjectExpectedStudy().getId() + "&cycle=" + this.getSelectedCycle() + "&year="
           * + this.getSelectedPhase().getYear();
           */
        }
      }
    }
    // Type
    if (projectInnovationInfo.getRepIndInnovationType() != null) {
      innovationType = projectInnovationInfo.getRepIndInnovationType().getName();
    }
    // Degree
    if (projectInnovationInfo.getRepIndDegreeInnovation() != null) {
      degreeInnovation = projectInnovationInfo.getRepIndDegreeInnovation().getName();
    }

    // Is new Innovation
    if (this.isInnovationNew(projectInnovationID) != null) {
      if (this.isInnovationNew(projectInnovationID) == true) {
        isNew = "Yes";
      } else {
        isNew = "No";
      }
    }

    // Has milestones
    if (projectInnovationInfo.getHasMilestones() != null) {

      if (projectInnovationInfo.getHasMilestones() == true) {
        hasMilestones = "Yes";
      }
      if (projectInnovationInfo.getHasMilestones() == false) {
        hasMilestones = "No";
      }
    } else {
      hasMilestones = "<Not Defined>";
    }

    // Number of individual improved lines/varieties
    if (projectInnovationInfo.getRepIndInnovationType() != null
      && projectInnovationInfo.getRepIndInnovationType().getId() != null
      && projectInnovationInfo.getRepIndInnovationType().getId().longValue() == 1L) {
      if (projectInnovationInfo.getInnovationNumber() != null) {
        innovationNumber = String.valueOf(projectInnovationInfo.getInnovationNumber());
      } else {
        innovationNumber = "&lt;Not Defined&gt;";
      }
    } else {
      innovationNumber = "&lt;Not Applicable&gt;";
    }

    // Milestones
    /*
     * projectInnovationInfo.getProjectInnovation().setMilestones(
     * new ArrayList<>(projectInnovationInfo.getProjectInnovation().getProjectInnovationMilestones().stream()
     * .filter(o -> o.getPhase().getId().equals(this.getSelectedPhase().getId())).collect(Collectors.toList())));
     */
    /*
     * if (projectInnovationInfo.getProjectInnovation().getMilestones() != null) {
     * Set<String> milestonesSet = new HashSet<>();
     * for (ProjectInnovationMilestone milestone : projectInnovationInfo.getProjectInnovation().getMilestones()) {
     * if (milestone.getCrpMilestone() != null && milestone.getCrpMilestone().getComposedName() != null) {
     * milestonesSet.add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + milestone.getCrpMilestone().getComposedName());
     * }
     * }
     * milestones = String.join("", milestonesSet);
     * } else {
     * milestones = "<Not Defined>";
     * }
     */


    // List<ProjectInnovationMilestone> projectInnovationMilestoneList = new ArrayList<>();
    List<ProjectInnovationMilestone> projectInnovationMilestoneList = new ArrayList<>();
    projectInnovationMilestoneList = projectInnovationMilestoneManager.findAll().stream()
      .filter(pi -> pi.getPhase().getId().equals(this.getSelectedPhase().getId())
        && pi.getProjectInnovation().getId().equals(projectInnovationInfo.getProjectInnovation().getId()))
      .collect(Collectors.toList());

    if (projectInnovationMilestoneList != null && !projectInnovationMilestoneList.isEmpty()) {
      Set<String> milestonesSet = new HashSet<>();
      for (ProjectInnovationMilestone milestone : projectInnovationMilestoneList) {
        if (milestone.getCrpMilestone() != null && milestone.getCrpMilestone().getTitle() != null) {
          milestonesSet.add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + milestone.getCrpMilestone().getTitle());
        }
      }
      milestones = String.join("", milestonesSet);
    } else {
      milestones = "No milestones associated";
    }

    // Sub Idos

    List<ProjectInnovationSubIdo> subIdosList = projectInnovationSubIdoManager.findAll().stream()
      .filter(pi -> pi.getPhase().getId().equals(this.getSelectedPhase().getId())
        && pi.getProjectInnovation().getId().equals(projectInnovationInfo.getProjectInnovation().getId()))
      .collect(Collectors.toList());

    /*
     * List<ProjectInnovationSubIdo> subIdosList =
     * new ArrayList<>(projectInnovationInfo.getProjectInnovation().getProjectInnovationSubIdos().stream()
     * .filter(o -> o.getPhase().getId().equals(this.getSelectedPhase().getId())).collect(Collectors.toList()));
     */
    if (subIdosList != null && !subIdosList.isEmpty()) {
      Set<String> subIdosSet = new HashSet<>();
      for (ProjectInnovationSubIdo subIdo : subIdosList) {
        subIdosSet.add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + subIdo.getSrfSubIdo().getId() + " - "
          + subIdo.getSrfSubIdo().getDescription());
      }
      subIdos = String.join("", subIdosSet);

    } else {
      subIdos = this.notDefined;
    }

    // phaseID
    phaseID = this.getSelectedPhase().getId().toString();

    // Geographic Scope
    List<ProjectInnovationGeographicScope> projectInnovationGeographicScopeList =
      projectInnovationGeographicScopeManager.findAll().stream()
        .filter(p -> p.getPhase().getId().equals(this.getSelectedPhase().getId())
          && p.getProjectInnovation() == projectInnovationInfo.getProjectInnovation())
        .collect(Collectors.toList());

    List<ProjectInnovationRegion> projectInnovationRegionList = projectInnovationRegionManager.findAll().stream()
      .filter(p -> p.getPhase().getId().equals(this.getSelectedPhase().getId())
        && p.getProjectInnovation().getId().equals(projectInnovationID))
      .collect(Collectors.toList());
    List<ProjectInnovationCountry> projectInnovationCountryList = projectInnovationCountryManager.findAll().stream()
      .filter(p -> p.getPhase().getId().equals(this.getSelectedPhase().getId())
        && p.getProjectInnovation().getId().equals(projectInnovationID))
      .collect(Collectors.toList());

    try {
      if (projectInnovationGeographicScopeList != null) {
        if (projectInnovationGeographicScopeList.get(0) != null
          && projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope() != null
          && projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope().getName() != null) {
          geographicScope = projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope().getName();
        }

        // Regional
        if (projectInnovationGeographicScopeList != null && projectInnovationGeographicScopeList.get(0) != null
          && projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope() != null
          && projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope().getId() != null) {
          if (projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope().getId()
            .equals(this.getReportingIndGeographicScopeRegional())) {
            isRegional = true;
            if (projectInnovationRegionList != null && projectInnovationRegionList.size() > 0) {
              Set<String> regionsSet = new HashSet<>();
              for (ProjectInnovationRegion innovationRegion : projectInnovationRegionList) {
                regionsSet.add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + innovationRegion.getLocElement().getName());
              }
              region = String.join("", regionsSet);
            }
          }
        }
        // Country
        if (projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope() != null
          && projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope().getId() != null) {
          if (!projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope().getId()
            .equals(this.getReportingIndGeographicScopeGlobal())
            && !projectInnovationGeographicScopeList.get(0).getRepIndGeographicScope().getId()
              .equals(this.getReportingIndGeographicScopeRegional())) {
            isNational = true;
            if (projectInnovationCountryList != null && projectInnovationCountryList.size() > 0) {
              Set<String> countriesSet = new HashSet<>();
              for (ProjectInnovationCountry innovationCountry : projectInnovationCountryList) {
                countriesSet.add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + innovationCountry.getLocElement().getName());
              }
              countries = String.join("", countriesSet);
            }
          }
        }
        /*
         * if (region != null) {
         * geographicScope += region;
         * }
         * if (countries != null) {
         * geographicScope += countries;
         * }
         */
      }
    } catch (Exception e) {

    }

    // Description
    if (projectInnovationInfo.getDescriptionStage() != null
      && !projectInnovationInfo.getDescriptionStage().trim().isEmpty()) {
      descriptionStage = projectInnovationInfo.getDescriptionStage();
    }
    // Lead Organization
    if (projectInnovationInfo.getLeadOrganization() != null) {
      if (projectInnovationInfo.getLeadOrganization().getComposedName() != null
        && !projectInnovationInfo.getLeadOrganization().getComposedName().isEmpty()) {
        leadOrganization = projectInnovationInfo.getLeadOrganization().getComposedName();
      }
    }

    // Contributing Organization
    List<ProjectInnovationContributingOrganization> contributingOrganizationsList =
      new ArrayList<ProjectInnovationContributingOrganization>();
    List<Deliverable> deliverableList = new ArrayList<Deliverable>();
    contributingOrganizationsList = projectInnovationContributingOrganizationManager.findAll();
    if (contributingOrganizationsList != null && contributingOrganizationsList.size() > 0) {
      contributingOrganizationsList = contributingOrganizationsList.stream()
        .filter(p -> p.getProjectInnovation().getId().equals(projectInnovationInfo.getProjectInnovation().getId())
          && p.getPhase().getId() == this.getSelectedPhase().getId())
        .collect(Collectors.toList());
    }
    if (contributingOrganizationsList != null && !contributingOrganizationsList.isEmpty()) {
      Set<String> contributingSet = new HashSet<>();
      for (ProjectInnovationContributingOrganization contributingOrganizationItem : contributingOrganizationsList) {
        if (contributingOrganizationItem.getInstitution() != null) {
          contributingSet
            .add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + contributingOrganizationItem.getInstitution().getComposedName());
        }
      }
      contributingOrganization = String.join("", contributingSet);
    }

    // Adaptative research
    if (projectInnovationInfo.getAdaptativeResearchNarrative() != null
      && !projectInnovationInfo.getAdaptativeResearchNarrative().trim().isEmpty()) {
      adaptativeResearch = projectInnovationInfo.getAdaptativeResearchNarrative();
    }

    // Evidence Link
    if (("AR".equals(this.getSelectedPhase().getName()) && 2021 == this.getSelectedPhase().getYear())
      || this.getSelectedPhase().getYear() > 2021) {
      if (this.isNotEmpty(this.projectInnovationInfo.getProjectInnovation().getProjectInnovationEvidenceLinks())) {
        List<ProjectInnovationEvidenceLink> currentPhaseLinks = this.projectInnovationInfo.getProjectInnovation()
          .getProjectInnovationEvidenceLinks().stream().filter(l -> l != null && l.getId() != null
            && l.getPhase() != null && l.getPhase().getId() != null && l.getPhase().equals(this.getSelectedPhase()))
          .collect(Collectors.toList());
        for (ProjectInnovationEvidenceLink evidenceLinkObject : currentPhaseLinks) {
          if (StringUtils.isNotEmpty(evidenceLinkObject.getLink())) {
            String link = StringUtils.trimToEmpty(evidenceLinkObject.getLink());
            evidenceLink =
              evidenceLink.append("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? ").append(urlShortener.getShortUrlService(link));
          }
        }
      } else {
        evidenceLink = evidenceLink.append(notProvided);
      }
    } else {
      if (StringUtils.isNotEmpty(projectInnovationInfo.getEvidenceLink())) {

        /*
         * Get short url calling tinyURL service
         */
        evidenceLink = evidenceLink.append(urlShortener.getShortUrlService(projectInnovationInfo.getEvidenceLink()));
      } else {
        evidenceLink = evidenceLink.append(notProvided);
      }
    }

    // Deliverables
    List<ProjectInnovationDeliverable> projectInnovationDeliverables =
      projectInnovationDeliverableManager.findAll().stream()
        .filter(p -> p.getProjectInnovation().getId().equals(projectInnovationInfo.getProjectInnovation().getId())
          && p.getPhase().getId().equals(this.getSelectedPhase().getId()))
        .collect(Collectors.toList());
    if (projectInnovationDeliverables != null && projectInnovationDeliverables.size() > 0) {
      Set<String> deliverablesSet = new HashSet<>();
      for (ProjectInnovationDeliverable projectInnovationDeliverable : projectInnovationDeliverables) {
        if (projectInnovationDeliverable.getDeliverable() != null
          && projectInnovationDeliverable.getDeliverable().getId() != null
          && projectInnovationDeliverable.getDeliverable().getDeliverableInfo(this.getSelectedPhase()) != null) {
          /*
           * String url = this.getBaseUrl() + "/projects/" + this.crp + "/deliverable.do?deliverableID="
           * + projectInnovationDeliverable.getDeliverable().getId() + "&phaseID=" + phaseID;
           */
          String url =
            this.getDeliverableDirectLink(projectInnovationDeliverable.getDeliverable(), this.getSelectedPhase());
          url = urlShortener.getShortUrlService(url);
          deliverablesSet
            .add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + "D" + projectInnovationDeliverable.getDeliverable().getId() + " - "
              + projectInnovationDeliverable.getDeliverable().getDeliverableInfo().getTitle()
              + " <font color=\"blue\">(" + url + ")</font>");
        }
      }
      deliverables = String.join("", deliverablesSet);
    }

    if (projectInnovationInfo.getProjectInnovation().getProjectInnovationCenters() != null) {
      projectInnovationInfo.getProjectInnovation()
        .setCenters(new ArrayList<>(projectInnovationInfo.getProjectInnovation().getProjectInnovationCenters().stream()
          .filter(c -> c.isActive() && c.getPhase().getId().equals(this.getSelectedPhase().getId()))
          .collect(Collectors.toList())));
    }
    if (projectInnovationInfo.getProjectInnovation().getCenters() != null) {
      Set<String> centerSet = new HashSet<>();

      for (ProjectInnovationCenter center : projectInnovationInfo.getProjectInnovation().getCenters()) {
        if (center.getInstitution() != null && center.getInstitution().getComposedName() != null) {
          centerSet.add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + center.getInstitution().getComposedName());
        }
      }
      centers = String.join("", centerSet);
    }

    // Contributions CRPS/Platforms
    hasLegacyCrps = BooleanUtils.isTrue(projectInnovationInfo.getHasLegacyCrpContribution());
    hasLegacyCrpsText = this.getYesNoStringOrNotDefined(projectInnovationInfo.getHasLegacyCrpContribution(), true);
    List<ProjectInnovationCrp> projectInnovationCrps =
      projectInnovationInfo.getProjectInnovation().getProjectInnovationCrps().stream()
        .filter(o -> o.isActive() && o.getPhase() != null && o.getPhase().equals(this.getSelectedPhase()))
        .collect(Collectors.toList());
    if (projectInnovationCrps != null && projectInnovationCrps.size() > 0) {
      Set<String> crpsSet = new HashSet<>();
      for (ProjectInnovationCrp projectInnovationCrp : projectInnovationCrps) {
        crpsSet.add("<br>&nbsp;&nbsp;&nbsp;&nbsp; ??? " + projectInnovationCrp.getGlobalUnit().getComposedName());
      }
      crps = String.join("", crpsSet);
    }

    // Gender Relevance
    if (projectInnovationInfo.getGenderFocusLevel() != null) {
      genderFocusLevel = projectInnovationInfo.getGenderFocusLevel().getName();
    }
    if (projectInnovationInfo.getGenderExplaniation() != null
      && !projectInnovationInfo.getGenderExplaniation().trim().isEmpty()) {
      genderExplaniation = projectInnovationInfo.getGenderExplaniation();
    }

    // Youth Relevance
    if (projectInnovationInfo.getYouthFocusLevel() != null) {
      youthFocusLevel = projectInnovationInfo.getYouthFocusLevel().getName();
    }
    if (projectInnovationInfo.getYouthExplaniation() != null
      && !projectInnovationInfo.getYouthExplaniation().trim().isEmpty()) {
      youthExplaniation = projectInnovationInfo.getYouthExplaniation();
    }

    if (projectInnovationInfo.getProjectInnovation().getProject() != null && projectInnovationInfo
      .getProjectInnovation().getProject().getProjecInfoPhase(this.getSelectedPhase()) != null) {
      project = projectInnovationInfo.getProjectInnovation().getProject().getComposedName();
    }

    if (isAlliance) {
      // SDG Target Contribution
      List<ProjectInnovationSdgTarget> innovationSdgTargets =
        projectInnovationInfo.getProjectInnovation().getProjectInnovationSdgTargets().stream()
          .filter(s -> s.isActive() && s.getPhase() != null && s.getPhase().equals(this.getSelectedPhase()))
          .collect(Collectors.toList());

      Set<String> innovationSdgTargetsSet = new HashSet<>();
      if (this.isNotEmpty(innovationSdgTargets)) {
        for (ProjectInnovationSdgTarget projectInnovationSdgTarget : innovationSdgTargets) {
          if (projectInnovationSdgTarget.getSdgTarget() != null
            && projectInnovationSdgTarget.getSdgTarget().getId() != null) {
            innovationSdgTargetsSet
              .add("<br>&nbsp;&nbsp;&nbsp;&nbsp;??? " + projectInnovationSdgTarget.getSdgTarget().getComposedName());
          }
        }

        sdgTargets = String.join("", innovationSdgTargetsSet);
      } else {
        sdgTargets = this.notDefinedHtml;
      }
    }

    model.addRow(new Object[] {id, isRegional, isNational, isStage4, title, narrative, stageInnovation, innovationType,
      contributionOfCrp, degreeInnovation, geographicScope, region, countries, organizations, projectExpectedStudies,
      descriptionStage, leadOrganization, contributingOrganization, adaptativeResearch, evidenceLink, deliverables,
      crps, genderFocusLevel, genderExplaniation, youthFocusLevel, youthExplaniation, project, oicr, centers,
      hasMilestones, milestones, subIdos, deliverableLink, phaseID, this.crp, isNew, innovationNumber, isAlliance,
      hasLegacyCrps, hasLegacyCrpsText, sdgTargets});
    return model;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  @Override
  public boolean isPublicRoute() {
    return true;
  }

  @Override
  public void prepare() throws Exception {
    Map<String, Parameter> parameters = this.getParameters();
    this.setPublicAccessParameters();
    projectInnovationID =
      Long.parseLong(StringUtils.trim(parameters.get(APConstants.INNOVATION_REQUEST_ID).getMultipleValues()[0]));

    redirectUrl = null;
    // Calculate time to generate report
    startTime = System.currentTimeMillis();
  }


  public void setBytesPDF(byte[] bytesPDF) {
    this.bytesPDF = bytesPDF;
  }

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }


}