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
package org.cgiar.ccafs.marlo.data.model;
// Generated Jul 13, 2016 11:45:52 AM by Hibernate Tools 4.3.1.Final


import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.IAuditLog;
import org.cgiar.ccafs.marlo.utils.CountryLocationLevel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;

/**
 * Project generated by hbm2java
 */
public class Project implements java.io.Serializable, IAuditLog {


  public static final int EMAIL_SUBJECT_IDENTIFIER = 4;


  public static final int EXCEL_IDENTIFIER_REPORT = 3;

  public static final int PDF_IDENTIFIER_REPORT = 2;


  private static final long serialVersionUID = -5737088425960023585L;

  public static final int STANDAR_IDENTIFIER = 1;


  @Expose
  private boolean active;

  @Expose
  private Date activeSince;


  private Set<Activity> activities = new HashSet<Activity>(0);

  @Expose
  private FileDB annualReportToDonnor;

  @Expose
  private FileDB bilateralContractName;


  private List<ProjectBudget> budgets;

  private List<ProjectBudgetsCluserActvity> budgetsCluserActvities;


  private List<Activity> closedProjectActivities;


  private List<ProjectClusterActivity> clusterActivities;


  @Expose
  private boolean cofinancing;

  @Expose
  private Date createDate;


  @Expose
  private User createdBy;

  @Expose
  private Boolean crossCuttingCapacity;

  @Expose
  private Boolean crossCuttingGender;


  @Expose
  private Boolean crossCuttingNa;


  @Expose
  private Boolean crossCuttingYouth;

  @Expose
  private Crp crp;

  private List<CrpClusterOfActivity> crpActivities;
  private Set<Deliverable> deliverables = new HashSet<Deliverable>(0);
  @Expose
  private String dimension;

  @Expose
  private Date endDate;


  private List<CrpProgram> flagships;


  private String flagshipValue;


  @Expose
  private String genderAnalysis;
  @Expose
  private Long id;
  @Expose
  private String leaderResponsabilities;
  @Expose
  private LiaisonInstitution liaisonInstitution;
  @Expose
  private LiaisonUser liaisonUser;


  @Expose
  private boolean locationGlobal;


  private List<ProjectLocation> locations;

  private List<CountryLocationLevel> locationsData;

  @Expose
  private String modificationJustification;

  @Expose
  private User modifiedBy;

  @Expose
  private Boolean noRegional;
  private List<ProjectOutcome> outcomes;
  private String overall;
  private List<ProjectPartner> partners;

  @Expose
  private Date presetDate;

  private List<Activity> projectActivities;
  private Set<ProjectBudget> projectBudgetCofinances = new HashSet<ProjectBudget>(0);

  private Set<ProjectBudget> projectBudgets = new HashSet<ProjectBudget>(0);


  private Set<ProjectBudgetsCluserActvity> projectBudgetsCluserActvities = new HashSet<ProjectBudgetsCluserActvity>(0);

  private Set<ProjectClusterActivity> projectClusterActivities = new HashSet<ProjectClusterActivity>(0);


  private Set<ProjectHighlight> projectHighlights = new HashSet<ProjectHighlight>(0);


  private List<ProjectHighlight> highligths;


  private ProjectComponentLesson projectComponentLesson;


  private ProjectComponentLesson projectComponentLessonPreview;
  private Set<ProjectComponentLesson> projectComponentLessons = new HashSet<ProjectComponentLesson>(0);

  private Set<CaseStudyProject> caseStudyProjects = new HashSet<CaseStudyProject>(0);


  private List<CaseStudy> caseStudies;


  private Set<ProjectCrpContribution> projectCrpContributions = new HashSet<ProjectCrpContribution>(0);

  private List<ProjectCrpContribution> crpContributions;


  private List<Deliverable> projectDeliverables;


  @Expose
  private boolean projectEditLeader;

  @Expose
  private Boolean administrative;
  private Set<ProjectFocus> projectFocuses = new HashSet<ProjectFocus>(0);
  private Set<ProjectLocationElementType> projectLocationElementTypes = new HashSet<ProjectLocationElementType>(0);


  private Set<ProjectLocation> projectLocations = new HashSet<ProjectLocation>(0);


  private Set<ProjectOutcome> projectOutcomes = new HashSet<ProjectOutcome>(0);


  private Set<ProjectLeverage> projectLeverages = new HashSet<ProjectLeverage>(0);


  private List<ProjectLeverage> leverages;
  private List<ProjectLeverage> leveragesClosed;


  private Set<ProjectPartner> projectPartners = new HashSet<ProjectPartner>(0);


  private Set<ProjectScope> projectScopes = new HashSet<ProjectScope>(0);

  private List<CrpProgram> regions;


  private Set<OtherContribution> otherContributions = new HashSet<OtherContribution>(0);

  private List<OtherContribution> otherContributionsList;

  private String regionsValue;
  @Expose
  private Boolean requiresWorkplanUpload;
  @Expose
  private int scale;
  private List<ProjectScope> scopes;

  private Set<SectionStatus> sectionStatuses = new HashSet<SectionStatus>(0);


  @Expose
  private Date startDate;


  @Expose
  private Long status;


  @Expose
  private String statusJustification;


  private Set<Submission> submissions = new HashSet<Submission>(0);

  @Expose
  private String summary;


  @Expose
  private String title;

  @Expose
  private String type;
  @Expose
  private FileDB workplan;

  private List<IpElement> outputs;
  private List<IpElement> mogs;

  private List<IpIndicator> indicators;

  private List<IpProjectIndicator> projectIndicators;
  private Set<IpProjectContributionOverview> ipProjectContributionOverviews =
    new HashSet<IpProjectContributionOverview>(0);


  private List<IpProjectContributionOverview> overviews;

  private Set<IpProjectContribution> ipProjectContributions = new HashSet<IpProjectContribution>(0);


  private Set<ProjectOtherContribution> projectOtherContributions = new HashSet<ProjectOtherContribution>(0);

  private List<ProjectOtherContribution> projectOtherContributionsList;
  private Set<ProjectFurtherContribution> projectFurtherContributions = new HashSet<ProjectFurtherContribution>(0);
  private Set<IpProjectIndicator> ipProjectIndicators = new HashSet<IpProjectIndicator>(0);

  private Set<ProjectFocusPrev> projectFocusPrevs = new HashSet<ProjectFocusPrev>(0);


  private Set<ProjectOutcomePandr> projectOutcomesPandr = new HashSet<ProjectOutcomePandr>(0);

  private List<ProjectOutcomePandr> outcomesPandr;

  public Project() {
  }

  public Project(Crp crp, LiaisonInstitution liaisonInstitution, LiaisonUser liaisonUser, User usersByCreatedBy,
    User usersByModifiedBy, String title, String summary, Date startDate, Date endDate, String type, boolean isGlobal,
    boolean isCofinancing, String leaderResponsabilities, Boolean requiresWorkplanUpload, FileDB workplanName,
    FileDB bilateralContractName, boolean isActive, Date activeSince, String modificationJustification,
    FileDB annualReportToDornor, Set<ProjectFocus> projectFocuseses, Set<Submission> submissions,
    Set<ProjectLocation> projectLocations, Set<ProjectScope> projectScopes, Set<Deliverable> deliverables,
    Date createDate, boolean locationGlobal, Set<ProjectLocationElementType> projectLocationElementTypes) {
    this.crp = crp;
    this.liaisonInstitution = liaisonInstitution;
    this.liaisonUser = liaisonUser;
    this.createdBy = usersByCreatedBy;
    this.modifiedBy = usersByModifiedBy;
    this.title = title;
    this.summary = summary;
    this.startDate = startDate;
    this.endDate = endDate;
    this.type = type;

    this.cofinancing = isCofinancing;
    this.leaderResponsabilities = leaderResponsabilities;
    this.requiresWorkplanUpload = requiresWorkplanUpload;
    this.workplan = workplanName;
    this.bilateralContractName = bilateralContractName;
    this.active = isActive;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
    this.annualReportToDonnor = annualReportToDornor;
    this.projectFocuses = projectFocuseses;
    this.submissions = submissions;
    this.projectLocations = projectLocations;
    this.projectScopes = projectScopes;
    this.deliverables = deliverables;
    this.createDate = createDate;
    this.locationGlobal = locationGlobal;
    this.projectLocationElementTypes = projectLocationElementTypes;
  }

  public Project(Crp crp, User usersByModifiedBy, boolean isGlobal, boolean isCofinancing, boolean isActive,
    Date activeSince, String modificationJustification) {
    this.crp = crp;
    this.modifiedBy = usersByModifiedBy;

    this.cofinancing = isCofinancing;
    this.active = isActive;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    Project other = (Project) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }


  public Date getActiveSince() {
    return this.activeSince;
  }

  public Set<Activity> getActivities() {
    return activities;
  }

  public Boolean getAdministrative() {
    if (administrative != null) {
      return administrative;
    }
    return false;
  }

  /**
   * This method calculates all the years between the start date and the end date.
   * 
   * @return a List of numbers representing all the years, or an empty list if nothing found.
   */
  public List<Integer> getAllYears() {
    List<Integer> allYears = new ArrayList<>();
    if (startDate != null && endDate != null) {
      Calendar calendarStart = Calendar.getInstance();
      calendarStart.setTime(startDate);
      Calendar calendarEnd = Calendar.getInstance();
      calendarEnd.setTime(endDate);

      while (calendarStart.get(Calendar.YEAR) <= calendarEnd.get(Calendar.YEAR)) {
        // Adding the year to the list.
        allYears.add(calendarStart.get(Calendar.YEAR));
        // Adding a year (365 days) to the start date.
        calendarStart.add(Calendar.YEAR, 1);
      }
    }

    return allYears;
  }

  public FileDB getAnnualReportToDonnor() {
    return annualReportToDonnor;
  }

  public double getBilateralBudget(int year) {

    double total = 0;
    for (ProjectBudget projectBudget : this.getProjectBudgets().stream()
      .filter(c -> c.isActive() && c.getBudgetType().getId() == 3 && c.getYear() == year)
      .collect(Collectors.toList())) {
      if (projectBudget.getAmount() != null) {
        total = total + projectBudget.getAmount();
      }
    }

    return total;
  }


  public FileDB getBilateralContractName() {
    return bilateralContractName;
  }


  public List<ProjectBudget> getBudgets() {
    return budgets;
  }


  public List<ProjectBudgetsCluserActvity> getBudgetsCluserActvities() {
    return budgetsCluserActvities;
  }


  public List<CaseStudy> getCaseStudies() {
    return caseStudies;
  }

  public Set<CaseStudyProject> getCaseStudyProjects() {
    return caseStudyProjects;
  }

  public List<Activity> getClosedProjectActivities() {
    return closedProjectActivities;
  }


  public List<ProjectClusterActivity> getClusterActivities() {
    return clusterActivities;
  }


  public String getComposedName() {
    return "P" + this.id + " - " + this.title;
  }

  /**
   * This method gets all the coordinators working for this project.
   * 
   * @return a list of PartnerPerson with the information requested.
   */
  public List<ProjectPartnerPerson> getCoordinatorPersons() {
    List<ProjectPartnerPerson> projectCoordinators = new ArrayList<>();
    if (partners != null) {
      for (ProjectPartner partner : partners) {
        if (partner.getPartnerPersons() != null) {
          for (ProjectPartnerPerson person : partner.getPartnerPersons()) {
            if (person.getContactType().equals(APConstants.PROJECT_PARTNER_PC)) {
              projectCoordinators.add(person);
            }
          }
        }

      }
    } else {
      for (ProjectPartner partner : projectPartners.stream().filter(c -> c.isActive()).collect(Collectors.toList())) {
        if (partner.getProjectPartnerPersons() != null) {
          for (ProjectPartnerPerson person : partner.getProjectPartnerPersons()) {

            if (person.getContactType().equals(APConstants.PROJECT_PARTNER_PC) && person.isActive()) {
              projectCoordinators.add(person);
            }
          }
        }

      }
    }
    return projectCoordinators;
  }

  public double getCoreBudget(int year) {

    double total = 0;
    for (ProjectBudget projectBudget : this.getProjectBudgets().stream()
      .filter(c -> c.isActive() && c.getBudgetType().getId() == 1 && c.getYear() == year)
      .collect(Collectors.toList())) {
      if (projectBudget.getAmount() != null) {
        total = total + projectBudget.getAmount();
      }
    }

    return total;
  }


  public Date getCreateDate() {
    return createDate;
  }

  public User getCreatedBy() {
    return this.createdBy;
  }

  public Boolean getCrossCuttingCapacity() {
    return crossCuttingCapacity;
  }

  public Boolean getCrossCuttingGender() {
    return crossCuttingGender;
  }

  public Boolean getCrossCuttingNa() {
    return crossCuttingNa;
  }

  public Boolean getCrossCuttingYouth() {
    return crossCuttingYouth;
  }


  public Crp getCrp() {
    return crp;
  }


  public List<CrpClusterOfActivity> getCrpActivities() {
    return crpActivities;
  }


  public List<ProjectCrpContribution> getCrpContributions() {
    return crpContributions;
  }


  public Set<Deliverable> getDeliverables() {
    return deliverables;
  }


  public String getDimension() {
    return dimension;
  }


  public Date getEndDate() {
    return this.endDate;
  }


  public List<CrpProgram> getFlagships() {
    return flagships;
  }


  public String getFlagshipValue() {
    return flagshipValue;
  }


  public String getGenderAnalysis() {
    return genderAnalysis;
  }


  public List<ProjectHighlight> getHighligths() {
    return highligths;
  }

  @Override
  public Long getId() {
    return this.id;
  }

  public List<IpIndicator> getIndicators() {
    return indicators;
  }


  public Set<IpProjectContributionOverview> getIpProjectContributionOverviews() {
    return ipProjectContributionOverviews;
  }


  public Set<IpProjectContribution> getIpProjectContributions() {
    return ipProjectContributions;
  }

  public Set<IpProjectIndicator> getIpProjectIndicators() {
    return ipProjectIndicators;
  }

  public ProjectPartner getLeader() {

    if (partners != null) {
      for (ProjectPartner partner : partners) {

        if (partner.getPartnerPersons() != null) {
          for (ProjectPartnerPerson person : partner.getPartnerPersons()) {
            if (person.getContactType().equals(APConstants.PROJECT_PARTNER_PL)) {
              return partner;
            }
          }
        }

      }
    } else {
      for (ProjectPartner partner : projectPartners.stream().filter(c -> c.isActive()).collect(Collectors.toList())) {
        if (partner.isActive()) {


          for (ProjectPartnerPerson person : partner.getProjectPartnerPersons()) {
            if (person.isActive()) {
              if (person.getContactType().equals(APConstants.PROJECT_PARTNER_PL) && person.isActive()) {
                return partner;
              }
            }

          }
        }
      }


    }
    return null;
  }

  /**
   * This method returns the project partner person who is leading the project.
   * 
   * @return a PartnerPerson object with the information requested. Or null if the project doesn't have a leader.
   */
  public ProjectPartnerPerson getLeaderPerson() {

    if (partners != null) {
      for (ProjectPartner partner : partners) {
        for (ProjectPartnerPerson person : partner.getPartnerPersons()) {

          if (person.getContactType().equals(APConstants.PROJECT_PARTNER_PL)) {
            return person;

          }

        }
      }
    } else {
      for (ProjectPartner partner : projectPartners.stream().filter(c -> c.isActive()).collect(Collectors.toList())) {
        for (ProjectPartnerPerson person : partner.getProjectPartnerPersons()) {
          if (person.isActive()) {
            if (person.getContactType().equals(APConstants.PROJECT_PARTNER_PL)) {
              return person;
            }
          }
        }

      }

    }

    return null;
  }

  public String getLeaderResponsabilities() {
    return this.leaderResponsabilities;
  }

  public List<ProjectLeverage> getLeverages() {
    return leverages;
  }

  public List<ProjectLeverage> getLeveragesClosed() {
    return leveragesClosed;
  }

  public LiaisonInstitution getLiaisonInstitution() {
    return this.liaisonInstitution;
  }

  public LiaisonUser getLiaisonUser() {
    return this.liaisonUser;
  }

  public List<ProjectLocation> getLocations() {
    return locations;
  }


  public List<CountryLocationLevel> getLocationsData() {
    return locationsData;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();

    sb.append("Id : ").append(this.getId());


    return sb.toString();
  }


  @Override
  public String getModificationJustification() {
    return this.modificationJustification;
  }


  @Override
  public User getModifiedBy() {
    return this.modifiedBy;
  }

  public List<IpElement> getMogs() {
    return mogs;
  }


  public Boolean getNoRegional() {
    return noRegional;
  }


  public Set<OtherContribution> getOtherContributions() {
    return otherContributions;
  }

  public List<OtherContribution> getOtherContributionsList() {
    return otherContributionsList;
  }


  public List<ProjectOutcome> getOutcomes() {
    return outcomes;
  }

  public List<ProjectOutcomePandr> getOutcomesPandr() {
    return outcomesPandr;
  }


  public List<IpElement> getOutputs() {
    return outputs;
  }

  public String getOverall() {
    return overall;
  }


  public List<IpProjectContributionOverview> getOverviews() {
    return overviews;
  }


  public List<ProjectPartner> getPartners() {
    return partners;
  }

  public Date getPresetDate() {
    return presetDate;
  }


  public List<Activity> getProjectActivities() {
    return projectActivities;
  }

  public Set<ProjectBudget> getProjectBudgetCofinances() {
    return projectBudgetCofinances;
  }


  public Set<ProjectBudget> getProjectBudgets() {
    return projectBudgets;
  }

  public Set<ProjectBudgetsCluserActvity> getProjectBudgetsCluserActvities() {
    return projectBudgetsCluserActvities;
  }

  public Set<ProjectClusterActivity> getProjectClusterActivities() {
    return projectClusterActivities;
  }

  public ProjectComponentLesson getProjectComponentLesson() {
    return projectComponentLesson;
  }

  public ProjectComponentLesson getProjectComponentLessonPreview() {
    return projectComponentLessonPreview;
  }


  public Set<ProjectComponentLesson> getProjectComponentLessons() {
    return projectComponentLessons;
  }

  public Set<ProjectCrpContribution> getProjectCrpContributions() {
    return projectCrpContributions;
  }

  public List<Deliverable> getProjectDeliverables() {
    return projectDeliverables;
  }

  public Set<ProjectFocus> getProjectFocuses() {
    return this.projectFocuses;
  }


  public Set<ProjectFocusPrev> getProjectFocusPrevs() {
    return projectFocusPrevs;
  }

  public Set<ProjectFurtherContribution> getProjectFurtherContributions() {
    return projectFurtherContributions;
  }

  public Set<ProjectHighlight> getProjectHighligths() {
    return projectHighlights;
  }


  public List<IpProjectIndicator> getProjectIndicators() {
    return projectIndicators;
  }

  public Set<ProjectLeverage> getProjectLeverages() {
    return projectLeverages;
  }

  public Set<ProjectLocationElementType> getProjectLocationElementTypes() {
    return projectLocationElementTypes;
  }

  public Set<ProjectLocation> getProjectLocations() {
    return projectLocations;
  }


  public Set<ProjectOtherContribution> getProjectOtherContributions() {
    return projectOtherContributions;
  }

  public List<ProjectOtherContribution> getProjectOtherContributionsList() {
    return projectOtherContributionsList;
  }

  public Set<ProjectOutcome> getProjectOutcomes() {
    return projectOutcomes;
  }

  public Set<ProjectOutcomePandr> getProjectOutcomesPandr() {
    return projectOutcomesPandr;
  }

  public Set<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public Set<ProjectScope> getProjectScopes() {
    return projectScopes;
  }

  public List<CrpProgram> getRegions() {
    return regions;
  }


  public String getRegionsValue() {
    return regionsValue;
  }

  public Boolean getRequiresWorkplanUpload() {
    return this.requiresWorkplanUpload;
  }

  public int getScale() {
    return scale;
  }

  public List<ProjectScope> getScopes() {
    return scopes;
  }


  public Set<SectionStatus> getSectionStatuses() {
    return sectionStatuses;
  }


  public String getStandardIdentifier(int typeCodification) {
    StringBuilder result = new StringBuilder();

    switch (typeCodification) {
      // Standar identifier
      case Project.EMAIL_SUBJECT_IDENTIFIER:
        result.append("P" + this.getId());
        break;

      default:
        // Do nothing
        break;

    }


    return result.toString();
  }

  public Date getStartDate() {
    return this.startDate;
  }


  public Long getStatus() {
    return status;
  }

  public String getStatusJustification() {
    return statusJustification;
  }


  public Set<Submission> getSubmissions() {
    return submissions;
  }


  public String getSummary() {
    return this.summary;
  }


  public String getTitle() {
    return this.title;
  }


  public String getType() {
    return this.type;
  }

  public double getW3Budget(int year) {
    double total = 0;
    for (ProjectBudget projectBudget : this.getProjectBudgets().stream()
      .filter(c -> c.isActive() && c.getBudgetType().getId() == 2 && c.getYear() == year)
      .collect(Collectors.toList())) {
      if (projectBudget.getAmount() != null) {
        total = total + projectBudget.getAmount();
      }
    }

    return total;
  }


  public FileDB getWorkplan() {
    return workplan;
  }


  public List<Integer> getYears(int year) {
    List<Integer> allYears = new ArrayList<>();
    if (startDate != null && endDate != null) {

      Calendar calendarEnd = Calendar.getInstance();
      calendarEnd.setTime(endDate);

      while (year < calendarEnd.get(Calendar.YEAR)) {
        year++;
        // Adding the year to the list.
        allYears.add(year);
        // Adding a year (365 days) to the start date.

      }
    }

    return allYears;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }


  @Override
  public boolean isActive() {
    return active;
  }

  public boolean isBilateralProject() {
    return (type != null) ? type.equals(APConstants.PROJECT_BILATERAL) : false;
  }


  /**
   * A project is bilateral stand alone if it is bilateral and it is NOT contributing to any Core project.
   * 
   * @return true if the project is bilateral stand alone, false if is bilateral and is contributing to some core
   *         project.
   */
  public boolean isBilateralStandAlone() {
    return (type != null) ? (this.isBilateralProject() && !this.cofinancing) : false;
  }


  public boolean isCofinancing() {
    return cofinancing;
  }

  public boolean isCoFundedProject() {
    return (type != null) ? type.equals(APConstants.PROJECT_CCAFS_COFUNDED) : false;
  }


  public boolean isCoreProject() {
    return (type != null) ? type.equals(APConstants.PROJECT_CORE) : false;
  }

  public boolean isLocationGlobal() {
    return locationGlobal;
  }


  public Boolean isNoRegional() {
    return noRegional;
  }

  public boolean isProjectEditLeader() {
    return projectEditLeader;
  }

  public void setActive(boolean active) {
    this.active = active;
  }


  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setActivities(Set<Activity> activities) {
    this.activities = activities;
  }


  public void setAdministrative(Boolean administrative) {
    this.administrative = administrative;
  }

  public void setAnnualReportToDonnor(FileDB annualReportToDonnor) {
    this.annualReportToDonnor = annualReportToDonnor;
  }


  public void setBilateralContractName(FileDB bilateralContractName) {
    this.bilateralContractName = bilateralContractName;
  }

  public void setBudgets(List<ProjectBudget> budgets) {
    this.budgets = budgets;
  }


  public void setBudgetsCluserActvities(List<ProjectBudgetsCluserActvity> budgetsCluserActvities) {
    this.budgetsCluserActvities = budgetsCluserActvities;
  }


  public void setCaseStudies(List<CaseStudy> caseStudies) {
    this.caseStudies = caseStudies;
  }

  public void setCaseStudyProjects(Set<CaseStudyProject> caseStudyProjects) {
    this.caseStudyProjects = caseStudyProjects;
  }


  public void setClosedProjectActivities(List<Activity> closedProjectActivities) {
    this.closedProjectActivities = closedProjectActivities;
  }

  public void setClusterActivities(List<ProjectClusterActivity> clusterActivities) {
    this.clusterActivities = clusterActivities;
  }

  public void setCofinancing(boolean cofinancing) {
    this.cofinancing = cofinancing;
  }


  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public void setCreatedBy(User usersByCreatedBy) {
    this.createdBy = usersByCreatedBy;
  }


  public void setCrossCuttingCapacity(Boolean crossCuttingCapacity) {
    this.crossCuttingCapacity = crossCuttingCapacity;
  }

  public void setCrossCuttingGender(Boolean crossCuttingGender) {
    this.crossCuttingGender = crossCuttingGender;
  }


  public void setCrossCuttingNa(Boolean crossCuttingNa) {
    this.crossCuttingNa = crossCuttingNa;
  }


  public void setCrossCuttingYouth(Boolean crossCuttingYouth) {
    this.crossCuttingYouth = crossCuttingYouth;
  }

  public void setCrp(Crp crp) {
    this.crp = crp;
  }


  public void setCrpActivities(List<CrpClusterOfActivity> crpActivities) {
    this.crpActivities = crpActivities;
  }

  public void setCrpContributions(List<ProjectCrpContribution> crpContributions) {
    this.crpContributions = crpContributions;
  }

  public void setDeliverables(Set<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }


  public void setDimension(String dimension) {
    this.dimension = dimension;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }


  public void setFlagships(List<CrpProgram> flagships) {
    this.flagships = flagships;
  }


  public void setFlagshipValue(String flagshipValue) {
    this.flagshipValue = flagshipValue;
  }


  public void setGenderAnalysis(String genderAnalysis) {
    this.genderAnalysis = genderAnalysis;
  }

  public void setHighligths(List<ProjectHighlight> highligths) {
    this.highligths = highligths;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public void setIndicators(List<IpIndicator> indicators) {
    this.indicators = indicators;
  }


  public void setIpProjectContributionOverviews(Set<IpProjectContributionOverview> ipProjectContributionOverviews) {
    this.ipProjectContributionOverviews = ipProjectContributionOverviews;
  }

  public void setIpProjectContributions(Set<IpProjectContribution> ipProjectContributions) {
    this.ipProjectContributions = ipProjectContributions;
  }


  public void setIpProjectIndicators(Set<IpProjectIndicator> ipProjectIndicators) {
    this.ipProjectIndicators = ipProjectIndicators;
  }


  public void setLeaderResponsabilities(String leaderResponsabilities) {
    this.leaderResponsabilities = leaderResponsabilities;
  }

  public void setLeverages(List<ProjectLeverage> leverages) {
    this.leverages = leverages;
  }


  public void setLeveragesClosed(List<ProjectLeverage> leveragesClosed) {
    this.leveragesClosed = leveragesClosed;
  }

  public void setLiaisonInstitution(LiaisonInstitution liaisonInstitution) {
    this.liaisonInstitution = liaisonInstitution;
  }

  public void setLiaisonUser(LiaisonUser liaisonUser) {
    this.liaisonUser = liaisonUser;
  }

  public void setLocationGlobal(boolean locationGlobal) {
    this.locationGlobal = locationGlobal;
  }


  public void setLocations(List<ProjectLocation> locations) {
    this.locations = locations;
  }

  public void setLocationsData(List<CountryLocationLevel> locationsData) {
    this.locationsData = locationsData;
  }


  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User usersByModifiedBy) {
    this.modifiedBy = usersByModifiedBy;
  }

  public void setMogs(List<IpElement> mogs) {
    this.mogs = mogs;
  }

  public void setNoRegional(Boolean noRegional) {
    this.noRegional = noRegional;
  }

  public void setOtherContributions(Set<OtherContribution> otherContributions) {
    this.otherContributions = otherContributions;
  }

  public void setOtherContributionsList(List<OtherContribution> otherContributionsList) {
    this.otherContributionsList = otherContributionsList;
  }

  public void setOutcomes(List<ProjectOutcome> outcomes) {
    this.outcomes = outcomes;
  }

  public void setOutcomesPandr(List<ProjectOutcomePandr> outcomesPandr) {
    this.outcomesPandr = outcomesPandr;
  }


  public void setOutputs(List<IpElement> outputs) {
    this.outputs = outputs;
  }


  public void setOverall(String overall) {
    this.overall = overall;
  }


  public void setOverviews(List<IpProjectContributionOverview> overviews) {
    this.overviews = overviews;
  }

  public void setPartners(List<ProjectPartner> partners) {
    this.partners = partners;
  }

  public void setPresetDate(Date presetDate) {
    this.presetDate = presetDate;
  }

  public void setProjectActivities(List<Activity> openProjectActivities) {
    this.projectActivities = openProjectActivities;
  }

  public void setProjectBudgetCofinances(Set<ProjectBudget> projectBudgetCofinances) {
    this.projectBudgetCofinances = projectBudgetCofinances;
  }

  public void setProjectBudgets(Set<ProjectBudget> projectBudgets) {
    this.projectBudgets = projectBudgets;
  }

  public void setProjectBudgetsCluserActvities(Set<ProjectBudgetsCluserActvity> projectBudgetsCluserActvities) {
    this.projectBudgetsCluserActvities = projectBudgetsCluserActvities;
  }

  public void setProjectClusterActivities(Set<ProjectClusterActivity> projectClusterActivities) {
    this.projectClusterActivities = projectClusterActivities;
  }

  public void setProjectComponentLesson(ProjectComponentLesson projectComponentLesson) {
    this.projectComponentLesson = projectComponentLesson;
  }

  public void setProjectComponentLessonPreview(ProjectComponentLesson projectComponentLessonPreview) {
    this.projectComponentLessonPreview = projectComponentLessonPreview;
  }


  public void setProjectComponentLessons(Set<ProjectComponentLesson> projectComponentLessons) {
    this.projectComponentLessons = projectComponentLessons;
  }


  public void setProjectCrpContributions(Set<ProjectCrpContribution> projectCrpContributions) {
    this.projectCrpContributions = projectCrpContributions;
  }

  public void setProjectDeliverables(List<Deliverable> projectDeliverables) {
    this.projectDeliverables = projectDeliverables;
  }

  public void setProjectEditLeader(boolean projectEditLeader) {
    this.projectEditLeader = projectEditLeader;
  }

  public void setProjectFocuses(Set<ProjectFocus> projectFocuseses) {
    this.projectFocuses = projectFocuseses;
  }

  public void setProjectFocusPrevs(Set<ProjectFocusPrev> projectFocusPrevs) {
    this.projectFocusPrevs = projectFocusPrevs;
  }

  public void setProjectFurtherContributions(Set<ProjectFurtherContribution> projectFurtherContributions) {
    this.projectFurtherContributions = projectFurtherContributions;
  }

  public void setProjectHighligths(Set<ProjectHighlight> projectHighlights) {
    this.projectHighlights = projectHighlights;
  }


  public void setProjectIndicators(List<IpProjectIndicator> projectIndicators) {
    this.projectIndicators = projectIndicators;
  }


  public void setProjectLeverages(Set<ProjectLeverage> projectLeverages) {
    this.projectLeverages = projectLeverages;
  }


  public void setProjectLocationElementTypes(Set<ProjectLocationElementType> projectLocationElementTypes) {
    this.projectLocationElementTypes = projectLocationElementTypes;
  }


  public void setProjectLocations(Set<ProjectLocation> projectLocations) {
    this.projectLocations = projectLocations;
  }


  public void setProjectOtherContributions(Set<ProjectOtherContribution> projectOtherContributions) {
    this.projectOtherContributions = projectOtherContributions;
  }


  public void setProjectOtherContributionsList(List<ProjectOtherContribution> projectOtherContributionsList) {
    this.projectOtherContributionsList = projectOtherContributionsList;
  }


  public void setProjectOutcomes(Set<ProjectOutcome> projectOutcomes) {
    this.projectOutcomes = projectOutcomes;
  }


  public void setProjectOutcomesPandr(Set<ProjectOutcomePandr> projectOutcomesPandr) {
    this.projectOutcomesPandr = projectOutcomesPandr;
  }


  public void setProjectPartners(Set<ProjectPartner> projectPartners) {
    this.projectPartners = projectPartners;
  }

  public void setProjectScopes(Set<ProjectScope> projectScopes) {
    this.projectScopes = projectScopes;
  }

  public void setRegions(List<CrpProgram> regions) {
    this.regions = regions;
  }

  public void setRegionsValue(String regionsValue) {
    this.regionsValue = regionsValue;
  }


  public void setRequiresWorkplanUpload(Boolean requiresWorkplanUpload) {
    this.requiresWorkplanUpload = requiresWorkplanUpload;
  }


  public void setScale(int scale) {
    this.scale = scale;
  }

  public void setScopes(List<ProjectScope> scopes) {
    this.scopes = scopes;
  }

  public void setSectionStatuses(Set<SectionStatus> sectionStatuses) {
    this.sectionStatuses = sectionStatuses;
  }


  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }


  public void setStatus(Long status) {
    this.status = status;
  }


  public void setStatusJustification(String statusJustification) {
    this.statusJustification = statusJustification;
  }


  public void setSubmissions(Set<Submission> submissions) {
    this.submissions = submissions;
  }


  public void setSummary(String summary) {
    this.summary = summary;
  }


  public void setTitle(String title) {
    this.title = title;
  }


  public void setType(String type) {
    this.type = type;
  }


  public void setWorkplan(FileDB workplan) {
    this.workplan = workplan;
  }

  @Override
  public String toString() {
    return id.toString();
  }
}

