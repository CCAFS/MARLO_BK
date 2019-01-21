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
import org.cgiar.ccafs.marlo.data.manager.Lp6ContributionGeographicScopeManager;
import org.cgiar.ccafs.marlo.data.manager.PhaseManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectLp6ContributionDeliverableManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectLp6ContributionManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectManager;
import org.cgiar.ccafs.marlo.data.model.Institution;
import org.cgiar.ccafs.marlo.data.model.InstitutionType;
import org.cgiar.ccafs.marlo.data.model.LocElement;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectLp6Contribution;
import org.cgiar.ccafs.marlo.data.model.ProjectLp6ContributionDeliverable;
import org.cgiar.ccafs.marlo.utils.APConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.Parameter;
import org.pentaho.reporting.engine.classic.core.CompoundDataFactory;
import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.ItemBand;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.SubReport;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil;
import org.pentaho.reporting.engine.classic.core.util.TypedTableModel;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectContributionLp6SummaryAction extends BaseSummariesAction implements Summary {

  private static final long serialVersionUID = 4871536980466987571L;

  private static Logger LOG = LoggerFactory.getLogger(ProjectContributionLp6SummaryAction.class);

  public static double round(double value, int places) {
    if (places < 0) {
      throw new IllegalArgumentException();
    }

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }


  // Managers
  private final ResourceManager resourceManager;
  private final ProjectLp6ContributionManager projectLp6ContributionManager;
  private final ProjectLp6ContributionDeliverableManager projectLp6ContributionDeliverableManager;
  private final Lp6ContributionGeographicScopeManager lp6ContributionGeographicScopeManager;

  // Parameters
  private long startTime;
  HashMap<InstitutionType, Set<Institution>> institutionsPerType = new HashMap<InstitutionType, Set<Institution>>();
  HashMap<Institution, Set<Project>> projectsPerInstitution = new HashMap<Institution, Set<Project>>();
  HashMap<Institution, Set<LocElement>> countriesPerInstitution = new HashMap<Institution, Set<LocElement>>();
  Set<LocElement> countries = new HashSet<>();
  Set<Project> projects = new HashSet<>();

  private String partnerType;
  private ProjectLp6Contribution projectLp6Contribution;
  private List<ProjectLp6Contribution> projectLp6Contributions;


  // XLSX bytes
  private byte[] bytesXLSX;

  // Streams
  InputStream inputStream;

  @Inject
  public ProjectContributionLp6SummaryAction(APConfig config, GlobalUnitManager crpManager, PhaseManager phaseManager,
    ResourceManager resourceManager, ProjectManager projectManager,
    ProjectLp6ContributionManager projectLp6ContributionManager,
    ProjectLp6ContributionDeliverableManager projectLp6ContributionDeliverableManager,
    Lp6ContributionGeographicScopeManager lp6ContributionGeographicScopeManager) {
    super(config, crpManager, phaseManager, projectManager);
    this.resourceManager = resourceManager;
    this.projectLp6ContributionManager = projectLp6ContributionManager;
    this.projectLp6ContributionDeliverableManager = projectLp6ContributionDeliverableManager;
    this.lp6ContributionGeographicScopeManager = lp6ContributionGeographicScopeManager;
  }

  /**
   * Method to add i8n parameters to masterReport in Pentaho
   * 
   * @param masterReport
   * @return masterReport with i8n parameters added
   */
  private MasterReport addi8nParameters(MasterReport masterReport) {
    if (partnerType.equals("All")) {
      masterReport.getParameterValues().put("i8nSummaryDescription",
        this.getText("summaries.lp6contribution.description", new String[] {this.getSelectedCycle()}));
      masterReport.getParameterValues().put("i8nHeader",
        this.getText("summaries.lp6contribution.header", new String[] {this.getLoggedCrp().getAcronym()}));
    } else {
      masterReport.getParameterValues().put("i8nSummaryDescription",
        this.getText("summaries.partners.leader.description", new String[] {this.getSelectedCycle()}));
      masterReport.getParameterValues().put("i8nHeader",
        this.getText("summaries.partners.leader.header", new String[] {this.getLoggedCrp().getAcronym()}));
    }

    masterReport.getParameterValues().put("i8nProject", this.getText("summaries.lp6contribution.projectName"));
    masterReport.getParameterValues().put("i8nNarrative", this.getText("summaries.lp6contribution.narrative"));
    masterReport.getParameterValues().put("i8nDeliverables", this.getText("summaries.lp6contribution.deliverables"));
    masterReport.getParameterValues().put("i8nGeographicScope",
      this.getText("summaries.lp6contribution.geographicScope"));

    masterReport.getParameterValues().put("i8nWorkingAcross",
      this.getText("summaries.lp6contribution.isWorkingAcrossFlagships"));

    masterReport.getParameterValues().put("i8nWorkingAcrossNarrative",
      this.getText("summaries.lp6contribution.workingAcrossFlagships"));

    masterReport.getParameterValues().put("i8nUndertakingEfforts",
      this.getText("summaries.lp6contribution.isUndertakingEfforts"));

    masterReport.getParameterValues().put("i8nUndertakingEffortsNarrative",
      "Project " + this.getText("summaries.lp6contribution.effortsUndertaking"));

    masterReport.getParameterValues().put("i8nProviding",
      this.getText("summaries.lp6contribution.isProvidingPathways"));
    masterReport.getParameterValues().put("i8nKeyLearnings", this.getText("summaries.lp6contribution.keyLearnings"));
    masterReport.getParameterValues().put("i8nTop3", this.getText("summaries.lp6contribution.top3Partnerts"));
    masterReport.getParameterValues().put("i8nUndertakingCSA",
      this.getText("summaries.lp6contribution.isUndertakingEffortsCSA"));
    masterReport.getParameterValues().put("i8nUndertakingCSAN",
      this.getText("summaries.lp6contribution.undertakingEffortsCSA"));
    masterReport.getParameterValues().put("i8nUndertaking",
      this.getText("summaries.lp6contribution.isUndertakingInitiative"));
    masterReport.getParameterValues().put("i8nUndertakingN",
      this.getText("summaries.lp6contribution.undertakingInitiative"));


    return masterReport;
  }

  private void createIntitutionsProjectsList() {
  }

  @Override
  public String execute() throws Exception {

    if (this.getSelectedPhase() == null) {
      return NOT_FOUND;
    }

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      Resource reportResource = resourceManager
        .createDirectly(this.getClass().getResource("/pentaho/crp/ProjectLp6Contribution.prpt"), MasterReport.class);

      MasterReport masterReport = (MasterReport) reportResource.getResource();
      // Set Main_Query
      CompoundDataFactory cdf = CompoundDataFactory.normalize(masterReport.getDataFactory());
      String masterQueryName = "main";
      TableDataFactory sdf = (TableDataFactory) cdf.getDataFactoryForQuery(masterQueryName);
      TypedTableModel model = this.getMasterTableModel();
      sdf.addTable(masterQueryName, model);
      masterReport.setDataFactory(cdf);
      // Set i8n for pentaho
      masterReport = this.addi8nParameters(masterReport);
      // Get details band
      ItemBand masteritemBand = masterReport.getItemBand();
      // Create new empty subreport hash map
      HashMap<String, Element> hm = new HashMap<String, Element>();
      // method to get all the subreports in the prpt and store in the HashMap
      this.getAllSubreports(hm, masteritemBand);
      this.createIntitutionsProjectsList();

      this.fillSubreport((SubReport) hm.get("summary"), "summary");
      ExcelReportUtil.createXLSX(masterReport, os);
      bytesXLSX = os.toByteArray();
      os.close();
    } catch (Exception e) {
      LOG.error("Error generating Institutions " + e.getMessage());
      throw e;
    }
    // Calculate time of generation
    long stopTime = System.currentTimeMillis();
    stopTime = stopTime - startTime;
    LOG.info("Downloaded successfully: " + this.getFileName() + ". User: "
      + this.getCurrentUser().getComposedCompleteName() + ". CRP: " + this.getLoggedCrp().getAcronym() + ". Cycle: "
      + this.getSelectedCycle() + ". Time to generate: " + stopTime + "ms.");
    return SUCCESS;
  }

  private void fillSubreport(SubReport subReport, String query) {
    CompoundDataFactory cdf = CompoundDataFactory.normalize(subReport.getDataFactory());
    TableDataFactory sdf = (TableDataFactory) cdf.getDataFactoryForQuery(query);
    TypedTableModel model = null;
    switch (query) {
      case "details":
        model = this.getDetailsTableModel();
        break;
      case "summary":
        model = this.getDetailsTableModel();
        break;
      case "institution_types":
        model = this.getInstitutionTypesTableModel();
        break;
    }
    sdf.addTable(query, model);
    subReport.setDataFactory(cdf);
  }

  @Override
  public int getContentLength() {
    return bytesXLSX.length;
  }

  @Override
  public String getContentType() {
    return "application/xlsx";
  }

  private TypedTableModel getDetailsTableModel() {
    TypedTableModel model = new TypedTableModel(
      new String[] {"project", "narrative", "deliverables", "geographicScope", "workingAcross",
        "workingAcrossNarrative", "undertakingEfforts", "undertakingEffortsNarrative", "providing", "keyLearnings",
        "top3", "undertakingCSA", "undertakingCSAN", "undertaking", "undertakingN"},
      new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, String.class,
        String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class},
      0);

    Map<Institution, Set<Project>> result = projectsPerInstitution.entrySet().stream()
      .sorted((s1, s2) -> s1.getKey().getName().compareTo(s2.getKey().getName())).collect(
        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));


    for (ProjectLp6Contribution projectLp6Contribution : projectLp6Contributions) {
      String projectId = "", narrativeLp6 = "", deliverables = "", geographicScope = "", workingAcross = "",
        workingAcrossNarrative = "", undertakingEfforts = "", undertakingEffortsNarrative = "", providing = "",
        keyOutputs = "", top3 = "", undertakingCSA = "", undertakingCSANarrative = "", initiativeRelated = "",
        initiativeRelatedNarrative = "";

      projectId = projectLp6Contribution.getProject() != null && projectLp6Contribution.getProject().getId() != null
        ? projectLp6Contribution.getProject().getId() + "" : null;

      narrativeLp6 =
        projectLp6Contribution.getNarrative() != null && !projectLp6Contribution.getNarrative().trim().isEmpty()
          ? projectLp6Contribution.getNarrative() : null;
      if (projectLp6Contribution.getDeliverables() != null && !projectLp6Contribution.getDeliverables().isEmpty()) {
        for (ProjectLp6ContributionDeliverable deliverable : projectLp6Contribution.getDeliverables()) {
          deliverables += deliverable + ", ";
        }
      }

      geographicScope = projectLp6Contribution.getGeographicScope() != null
        && projectLp6Contribution.getGeographicScope().getName() != null
        && !projectLp6Contribution.getGeographicScope().getName().isEmpty()
          ? projectLp6Contribution.getGeographicScope().getName() : null;

      workingAcross = projectLp6Contribution.isWorkingAcrossFlagships() != null
        ? projectLp6Contribution.isWorkingAcrossFlagships() + "" : null;

      workingAcrossNarrative = projectLp6Contribution.getWorkingAcrossFlagshipsNarrative() != null
        && !projectLp6Contribution.getWorkingAcrossFlagshipsNarrative().isEmpty()
          ? projectLp6Contribution.getWorkingAcrossFlagshipsNarrative() : null;

      undertakingEfforts = projectLp6Contribution.isUndertakingEffortsLeading() != null
        ? projectLp6Contribution.isUndertakingEffortsLeading() + "" : null;

      undertakingEffortsNarrative = projectLp6Contribution.getUndertakingEffortsLeadingNarrative() != null
        && !projectLp6Contribution.getUndertakingEffortsLeadingNarrative().isEmpty()
          ? projectLp6Contribution.getUndertakingEffortsLeadingNarrative() + "" : null;

      providing =
        projectLp6Contribution.isProvidingPathways() != null ? projectLp6Contribution.isProvidingPathways() + "" : null;


      keyOutputs = projectLp6Contribution.getProvidingPathwaysNarrative() != null
        && !projectLp6Contribution.getProvidingPathwaysNarrative().isEmpty()
          ? projectLp6Contribution.getProvidingPathwaysNarrative() + "" : null;

      top3 = projectLp6Contribution.getTopThreePartnershipsNarrative() != null
        && !projectLp6Contribution.getTopThreePartnershipsNarrative().isEmpty()
          ? projectLp6Contribution.getTopThreePartnershipsNarrative() + "" : null;

      undertakingCSA = projectLp6Contribution.isUndertakingEffortsCsa() != null
        ? projectLp6Contribution.isUndertakingEffortsCsa() + "" : null;

      undertakingCSANarrative = projectLp6Contribution.getUndertakingEffortsCsaNarrative() != null
        && !projectLp6Contribution.getUndertakingEffortsCsaNarrative().isEmpty()
          ? projectLp6Contribution.getUndertakingEffortsCsaNarrative() + "" : null;

      initiativeRelated = projectLp6Contribution.isInitiativeRelated() != null
        ? projectLp6Contribution.isUndertakingEffortsCsa() + "" : null;

      initiativeRelatedNarrative = projectLp6Contribution.getInitiativeRelatedNarrative() != null
        && !projectLp6Contribution.getInitiativeRelatedNarrative().isEmpty()
          ? projectLp6Contribution.getInitiativeRelatedNarrative() + "" : null;

      model.addRow(new Object[] {projectId, narrativeLp6, deliverables, geographicScope, workingAcross,
        workingAcrossNarrative, undertakingEfforts, undertakingEffortsNarrative, providing, keyOutputs, top3,
        undertakingCSA, undertakingCSANarrative, initiativeRelated, initiativeRelatedNarrative});
    }

    return model;
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

    fileName.append("ProjectContributionToLP6-");

    fileName.append(this.getLoggedCrp().getAcronym() + "-");
    fileName.append(this.getSelectedCycle() + "-");
    fileName.append(this.getSelectedYear() + "_");
    fileName.append(new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()));
    fileName.append(".xlsx");
    return fileName.toString();
  }

  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesXLSX);
    }
    return inputStream;
  }

  private TypedTableModel getInstitutionTypesTableModel() {
    TypedTableModel model =
      new TypedTableModel(new String[] {"count_ins", "name_percentage"}, new Class[] {Integer.class, String.class}, 0);
    Map<InstitutionType, Set<Institution>> result = institutionsPerType.entrySet().stream()
      .sorted((s1, s2) -> new Integer(s2.getValue().size()).compareTo(new Integer(s1.getValue().size()))).collect(
        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    int totalInstitutions = projectsPerInstitution.size();
    DecimalFormat percentageFormat = new DecimalFormat("##.##%");

    for (InstitutionType institutionType : result.keySet()) {
      String namePercentage = "";
      Integer countIns = null;
      countIns = institutionsPerType.get(institutionType).size();
      namePercentage = institutionType.getName();
      Double percentajeOfTotal = 0.0;
      if (totalInstitutions > 0) {
        percentajeOfTotal = countIns * 100.0 / totalInstitutions;
      } else {
        percentajeOfTotal = 0.0;
      }

      model.addRow(
        new Object[] {countIns, namePercentage + " - " + percentageFormat.format(round(percentajeOfTotal / 100, 4))});
    }
    return model;
  }

  private TypedTableModel getMasterTableModel() {
    // Initialization of Model
    TypedTableModel model = new TypedTableModel(new String[] {"date", "center", "showDescription"},
      new Class[] {String.class, String.class, Boolean.class});
    String center = this.getLoggedCrp().getAcronym();

    ZonedDateTime timezone = ZonedDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d 'at' HH:mm ");
    String zone = timezone.getOffset() + "";
    if (zone.equals("Z")) {
      zone = "+0";
    }
    String date = timezone.format(format) + "(GMT" + zone + ")";
    model.addRow(new Object[] {date, center, this.hasSpecificities(APConstants.CRP_REPORTS_DESCRIPTION)});
    return model;
  }

  @Override
  public void prepare() {
    projectLp6Contributions = new ArrayList<>();
    projectLp6Contributions =
      projectLp6ContributionManager.findAll().stream().filter(c -> c.isActive()).collect(Collectors.toList());

    System.out.println("phase " + this.getActualPhase() + " selectedPhase " + this.getSelectedPhase());

    try {
      Map<String, Parameter> parameters = this.getParameters();
      partnerType = StringUtils.trim(parameters.get(APConstants.SUMMARY_PARTNER_TYPE).getMultipleValues()[0]);
    } catch (Exception e) {
      LOG.warn("Failed to get " + APConstants.SUMMARY_PARTNER_TYPE
        + " parameter. Parameter will be set as All. Exception: " + e.getMessage());
      partnerType = "All";
    }
    this.setGeneralParameters();
    // Calculate time to generate report
    startTime = System.currentTimeMillis();
    LOG.info(
      "Start report download: " + this.getFileName() + ". User: " + this.getCurrentUser().getComposedCompleteName()
        + ". CRP: " + this.getLoggedCrp().getAcronym() + ". Cycle: " + this.getSelectedCycle());
  }

}