package org.cgiar.ccafs.marlo.data.model;
// Generated Apr 30, 2018 10:52:36 AM by Hibernate Tools 3.4.0.CR1

import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;

/**
 * ProjectExpectedStudyInfo generated by hbm2java
 */
public class ProjectExpectedStudyInfo extends MarloBaseEntity implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -7806050142645120199L;


  @Expose
  private RepIndGeographicScope repIndGeographicScope;

  @Expose
  private RepIndRegion repIndRegion;

  @Expose
  private RepIndOrganizationType repIndOrganizationType;

  @Expose
  private RepIndGenderYouthFocusLevel genderLevel;

  @Expose
  private RepIndGenderYouthFocusLevel youthLevel;

  @Expose
  private RepIndGenderYouthFocusLevel climateChangeLevel;

  @Expose
  private RepIndPolicyInvestimentType repIndPolicyInvestimentType;


  @Expose
  private FileDB outcomeFile;


  @Expose
  private RepIndStageProcess repIndStageProcess;


  @Expose
  private Phase phase;


  @Expose
  private FileDB referencesFile;


  @Expose
  private RepIndStageStudy repIndStageStudy;

  @Expose
  private ProjectExpectedStudy projectExpectedStudy;

  @Expose
  private RepIndGenderYouthFocusLevel capdevLevel;

  @Expose
  private StudyType studyType;

  @Expose
  private GeneralStatus status;

  @Expose
  private String title;

  @Expose
  private String outcomeImpactStatement;

  @Expose
  private Boolean isContribution;

  @Expose
  private Double policyAmount;

  @Expose
  private String topLevelComments;

  @Expose
  private String scopeComments;

  @Expose
  private String elaborationOutcomeImpactStatement;

  @Expose
  private String referencesText;

  @Expose
  private String quantification;

  @Expose
  private String describeGender;

  @Expose
  private String describeYouth;

  @Expose
  private String describeCapdev;

  @Expose
  private String describeClimateChange;

  @Expose
  private String otherCrossCuttingDimensions;

  @Expose
  private String comunicationsMaterial;

  @Expose
  private String contacts;

  @Expose
  private String commissioningStudy;

  @Expose
  private Integer year;

  // AR 2018 New fields
  @Expose
  private EvidenceTag evidenceTag;

  @Expose
  private String outcomeStory;


  @Expose
  private String isSrfTarget;


  @Expose
  private String cgiarInnovation;


  @Expose
  private String otherCrossCuttingSelection;

  @Expose
  private Boolean isPublic;

  @Expose
  public String otherStudyType;


  public ProjectExpectedStudyInfo() {
  }


  public ProjectExpectedStudyInfo(Phase phase, ProjectExpectedStudy projectExpectedStudy, String title,
    String outcomeImpactStatement, String topLevelComments, String scopeComments,
    String elaborationOutcomeImpactStatement, String referencesText, String quantification, String describeGender,
    String describeYouth, String describeCapdev, String otherCrossCuttingDimensions, String comunicationsMaterial,
    String contacts, Integer year) {
    super();
    this.phase = phase;
    this.projectExpectedStudy = projectExpectedStudy;
    this.title = title;
    this.outcomeImpactStatement = outcomeImpactStatement;
    this.topLevelComments = topLevelComments;
    this.scopeComments = scopeComments;
    this.elaborationOutcomeImpactStatement = elaborationOutcomeImpactStatement;
    this.referencesText = referencesText;
    this.quantification = quantification;
    this.describeGender = describeGender;
    this.describeYouth = describeYouth;
    this.describeCapdev = describeCapdev;
    this.otherCrossCuttingDimensions = otherCrossCuttingDimensions;
    this.comunicationsMaterial = comunicationsMaterial;
    this.contacts = contacts;
    this.year = year;
  }

  public RepIndGenderYouthFocusLevel getCapdevLevel() {
    return capdevLevel;
  }

  public String getCgiarInnovation() {
    return cgiarInnovation;
  }

  public RepIndGenderYouthFocusLevel getClimateChangeLevel() {
    return climateChangeLevel;
  }

  public String getCommissioningStudy() {
    return commissioningStudy;
  }

  public String getComunicationsMaterial() {
    return comunicationsMaterial;
  }


  public String getContacts() {
    return contacts;
  }


  public String getDescribeCapdev() {
    return describeCapdev;
  }


  public String getDescribeClimateChange() {
    return describeClimateChange;
  }


  public String getDescribeGender() {
    return describeGender;
  }


  public String getDescribeYouth() {
    return describeYouth;
  }


  public String getElaborationOutcomeImpactStatement() {
    return elaborationOutcomeImpactStatement;
  }


  public EvidenceTag getEvidenceTag() {
    return evidenceTag;
  }


  public RepIndGenderYouthFocusLevel getGenderLevel() {
    return genderLevel;
  }


  public Boolean getIsContribution() {
    return isContribution;
  }


  public Boolean getIsPublic() {
    return isPublic;
  }


  public String getIsSrfTarget() {
    return isSrfTarget;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  @Override
  public String getModificationJustification() {
    return "";
  }

  @Override
  public User getModifiedBy() {
    User u = new User();
    u.setId(new Long(3));
    return u;
  }

  public String getOtherCrossCuttingDimensions() {
    return otherCrossCuttingDimensions;
  }


  public String getOtherCrossCuttingSelection() {
    return otherCrossCuttingSelection;
  }

  public String getOtherStudyType() {
    return otherStudyType;
  }

  public FileDB getOutcomeFile() {
    return outcomeFile;
  }

  public String getOutcomeImpactStatement() {
    return outcomeImpactStatement;
  }


  public String getOutcomeStory() {
    return outcomeStory;
  }


  public Phase getPhase() {
    return phase;
  }


  public Double getPolicyAmount() {
    return policyAmount;
  }


  public ProjectExpectedStudy getProjectExpectedStudy() {
    return projectExpectedStudy;
  }

  public String getQuantification() {
    return quantification;
  }


  public FileDB getReferencesFile() {
    return referencesFile;
  }


  public String getReferencesText() {
    return referencesText;
  }


  public RepIndGeographicScope getRepIndGeographicScope() {
    return repIndGeographicScope;
  }


  public RepIndOrganizationType getRepIndOrganizationType() {
    return repIndOrganizationType;
  }


  public RepIndPolicyInvestimentType getRepIndPolicyInvestimentType() {
    return repIndPolicyInvestimentType;
  }


  public RepIndRegion getRepIndRegion() {
    return repIndRegion;
  }


  public RepIndStageProcess getRepIndStageProcess() {
    return repIndStageProcess;
  }

  public RepIndStageStudy getRepIndStageStudy() {
    return repIndStageStudy;
  }

  public String getScopeComments() {
    return scopeComments;
  }


  public GeneralStatus getStatus() {
    return status;
  }


  public String getStatusName() {
    if (this.getStatus() != null) {
      return this.getStatus().getName();
    }
    return null;
  }


  public StudyType getStudyType() {
    return studyType;
  }


  public String getTitle() {
    return title;
  }


  public String getTopLevelComments() {
    return topLevelComments;
  }


  public Integer getYear() {
    return year;
  }


  public RepIndGenderYouthFocusLevel getYouthLevel() {
    return youthLevel;
  }


  @Override
  public boolean isActive() {
    return true;
  }


  public void setCapdevLevel(RepIndGenderYouthFocusLevel capdevLevel) {
    this.capdevLevel = capdevLevel;
  }


  public void setCgiarInnovation(String cgiarInnovation) {
    this.cgiarInnovation = cgiarInnovation;
  }


  public void setClimateChangeLevel(RepIndGenderYouthFocusLevel climateChangeLevel) {
    this.climateChangeLevel = climateChangeLevel;
  }


  public void setCommissioningStudy(String commissioningStudy) {
    this.commissioningStudy = commissioningStudy;
  }

  public void setComunicationsMaterial(String comunicationsMaterial) {
    this.comunicationsMaterial = comunicationsMaterial;
  }


  public void setContacts(String contacts) {
    this.contacts = contacts;
  }


  public void setDescribeCapdev(String describeCapdev) {
    this.describeCapdev = describeCapdev;
  }

  public void setDescribeClimateChange(String describeClimateChange) {
    this.describeClimateChange = describeClimateChange;
  }

  public void setDescribeGender(String describeGender) {
    this.describeGender = describeGender;
  }

  public void setDescribeYouth(String describeYouth) {
    this.describeYouth = describeYouth;
  }

  public void setElaborationOutcomeImpactStatement(String elaborationOutcomeImpactStatement) {
    this.elaborationOutcomeImpactStatement = elaborationOutcomeImpactStatement;
  }

  public void setEvidenceTag(EvidenceTag evidenceTag) {
    this.evidenceTag = evidenceTag;
  }

  public void setGenderLevel(RepIndGenderYouthFocusLevel genderLevel) {
    this.genderLevel = genderLevel;
  }

  public void setIsContribution(Boolean isContribution) {
    this.isContribution = isContribution;
  }

  public void setIsPublic(Boolean isPublic) {
    this.isPublic = isPublic;
  }

  public void setIsSrfTarget(String isSrfTarget) {
    this.isSrfTarget = isSrfTarget;
  }


  @Override
  public void setModifiedBy(User modifiedBy) {

  }


  public void setOtherCrossCuttingDimensions(String otherCrossCuttingDimensions) {
    this.otherCrossCuttingDimensions = otherCrossCuttingDimensions;
  }


  public void setOtherCrossCuttingSelection(String otherCrossCuttingSelection) {
    this.otherCrossCuttingSelection = otherCrossCuttingSelection;
  }


  public void setOtherStudyType(String otherStudyType) {
    this.otherStudyType = otherStudyType;
  }

  public void setOutcomeFile(FileDB outcomeFile) {
    this.outcomeFile = outcomeFile;
  }

  public void setOutcomeImpactStatement(String outcomeImpactStatement) {
    this.outcomeImpactStatement = outcomeImpactStatement;
  }

  public void setOutcomeStory(String outcomeStory) {
    this.outcomeStory = outcomeStory;
  }

  public void setPhase(Phase phase) {
    this.phase = phase;
  }

  public void setPolicyAmount(Double policyAmount) {
    this.policyAmount = policyAmount;
  }

  public void setProjectExpectedStudy(ProjectExpectedStudy projectExpectedStudy) {
    this.projectExpectedStudy = projectExpectedStudy;
  }

  public void setQuantification(String quantification) {
    this.quantification = quantification;
  }

  public void setReferencesFile(FileDB referencesFile) {
    this.referencesFile = referencesFile;
  }


  public void setReferencesText(String referencesText) {
    this.referencesText = referencesText;
  }

  public void setRepIndGeographicScope(RepIndGeographicScope repIndGeographicScope) {
    this.repIndGeographicScope = repIndGeographicScope;
  }

  public void setRepIndOrganizationType(RepIndOrganizationType repIndOrganizationType) {
    this.repIndOrganizationType = repIndOrganizationType;
  }

  public void setRepIndPolicyInvestimentType(RepIndPolicyInvestimentType repIndPolicyInvestimentType) {
    this.repIndPolicyInvestimentType = repIndPolicyInvestimentType;
  }

  public void setRepIndRegion(RepIndRegion repIndRegion) {
    this.repIndRegion = repIndRegion;
  }

  public void setRepIndStageProcess(RepIndStageProcess repIndStageProcess) {
    this.repIndStageProcess = repIndStageProcess;
  }

  public void setRepIndStageStudy(RepIndStageStudy repIndStageStudy) {
    this.repIndStageStudy = repIndStageStudy;
  }

  public void setScopeComments(String scopeComments) {
    this.scopeComments = scopeComments;
  }

  public void setStatus(GeneralStatus status) {
    this.status = status;
  }


  public void setStudyType(StudyType studyType) {
    this.studyType = studyType;
  }


  public void setTitle(String title) {
    this.title = title;
  }


  public void setTopLevelComments(String topLevelComments) {
    this.topLevelComments = topLevelComments;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public void setYouthLevel(RepIndGenderYouthFocusLevel youthLevel) {
    this.youthLevel = youthLevel;
  }


  @Override
  public String toString() {
    return "ProjectExpectedStudyInfo [phase=" + phase + ", title=" + title + ", year=" + year + "]";
  }

  /**
   * Add the save/update information to reply the next Phase
   * 
   * @param projectExpectedStudyInfoUpdate - a ProjectExpectedStudyInfo object.
   * @param phase - The next Phase
   */
  public void updateProjectExpectedStudyInfoInfo(ProjectExpectedStudyInfo projectExpectedStudyInfoUpdate, Phase phase) {

    this.setPhase(phase);
    this.setRepIndGeographicScope(projectExpectedStudyInfoUpdate.getRepIndGeographicScope());
    // this.setRepIndRegion(projectExpectedStudyInfoUpdate.getRepIndRegion());
    this.setRepIndOrganizationType(projectExpectedStudyInfoUpdate.getRepIndOrganizationType());
    this.setGenderLevel(projectExpectedStudyInfoUpdate.getGenderLevel());
    this.setYouthLevel(projectExpectedStudyInfoUpdate.getYouthLevel());
    this.setRepIndPolicyInvestimentType(projectExpectedStudyInfoUpdate.getRepIndPolicyInvestimentType());
    this.setOutcomeFile(projectExpectedStudyInfoUpdate.getOutcomeFile());
    this.setRepIndStageProcess(projectExpectedStudyInfoUpdate.getRepIndStageProcess());
    this.setReferencesFile(projectExpectedStudyInfoUpdate.getReferencesFile());
    this.setRepIndStageStudy(projectExpectedStudyInfoUpdate.getRepIndStageStudy());
    this.setProjectExpectedStudy(projectExpectedStudyInfoUpdate.getProjectExpectedStudy());
    this.setCapdevLevel(projectExpectedStudyInfoUpdate.getCapdevLevel());
    this.setStudyType(projectExpectedStudyInfoUpdate.getStudyType());
    this.setStatus(projectExpectedStudyInfoUpdate.getStatus());
    this.setTitle(projectExpectedStudyInfoUpdate.getTitle());
    this.setOutcomeImpactStatement(projectExpectedStudyInfoUpdate.getOutcomeImpactStatement());
    this.setIsContribution(projectExpectedStudyInfoUpdate.getIsContribution());
    this.setPolicyAmount(projectExpectedStudyInfoUpdate.getPolicyAmount());
    this.setTopLevelComments(projectExpectedStudyInfoUpdate.getTopLevelComments());
    this.setScopeComments(projectExpectedStudyInfoUpdate.getScopeComments());
    this.setElaborationOutcomeImpactStatement(projectExpectedStudyInfoUpdate.getElaborationOutcomeImpactStatement());
    this.setReferencesText(projectExpectedStudyInfoUpdate.getReferencesText());
    this.setQuantification(projectExpectedStudyInfoUpdate.getQuantification());
    this.setDescribeGender(projectExpectedStudyInfoUpdate.getDescribeGender());
    this.setDescribeYouth(projectExpectedStudyInfoUpdate.getDescribeYouth());
    this.setDescribeCapdev(projectExpectedStudyInfoUpdate.getDescribeCapdev());
    this.setOtherCrossCuttingDimensions(projectExpectedStudyInfoUpdate.getOtherCrossCuttingDimensions());
    this.setComunicationsMaterial(projectExpectedStudyInfoUpdate.getComunicationsMaterial());
    this.setContacts(projectExpectedStudyInfoUpdate.getContacts());
    this.setCommissioningStudy(projectExpectedStudyInfoUpdate.getCommissioningStudy());
    this.setYear(projectExpectedStudyInfoUpdate.getYear());
    this.setEvidenceTag(projectExpectedStudyInfoUpdate.getEvidenceTag());
    this.setOutcomeStory(projectExpectedStudyInfoUpdate.getOutcomeStory());
    this.setIsSrfTarget(projectExpectedStudyInfoUpdate.getIsSrfTarget());
    this.setCgiarInnovation(projectExpectedStudyInfoUpdate.getCgiarInnovation());
    this.setOtherCrossCuttingSelection(projectExpectedStudyInfoUpdate.getOtherCrossCuttingSelection());
    this.setIsPublic(projectExpectedStudyInfoUpdate.getIsPublic());
    this.setClimateChangeLevel(projectExpectedStudyInfoUpdate.getClimateChangeLevel());
    this.setDescribeClimateChange(projectExpectedStudyInfoUpdate.getDescribeClimateChange());
    this.setOtherStudyType(projectExpectedStudyInfoUpdate.getOtherStudyType());
  }

}

