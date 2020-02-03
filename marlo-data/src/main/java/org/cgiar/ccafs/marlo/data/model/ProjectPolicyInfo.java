package org.cgiar.ccafs.marlo.data.model;
// Generated Jan 8, 2019 3:50:56 PM by Hibernate Tools 5.3.6.Final

import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;

/**
 * ProjectPolicyInfo generated by hbm2java
 */
public class ProjectPolicyInfo extends MarloBaseEntity implements java.io.Serializable, IAuditLog {

	private static final long serialVersionUID = 2885275104787062400L;
	@Expose
	private Phase phase;
	@Expose
	private ProjectExpectedStudy projectExpectedStudy;
	@Expose
	private ProjectPolicy projectPolicy;
	@Expose
	private RepIndGeographicScope repIndGeographicScope;
	@Expose
	private RepIndPolicyInvestimentType repIndPolicyInvestimentType;
	@Expose
	private RepIndStageProcess repIndStageProcess;
	@Expose
	private Long year;
	@Expose
	private String title;
	@Expose
	private String description;
	@Expose
	private Double amount;
	@Expose
	private String other;
	@Expose
	private String narrativeEvidence;
	@Expose
	private Boolean hasMilestones;

	public ProjectPolicyInfo() {

	}

	public ProjectPolicyInfo(Phase phase, ProjectExpectedStudy projectExpectedStudy, ProjectPolicy projectPolicy,
			RepIndGeographicScope repIndGeographicScope, RepIndOrganizationType repIndOrganizationType,
			RepIndPolicyInvestimentType repIndPolicyInvestimentType, RepIndStageProcess repIndStageProcess, Long year,
			String title, Double amount, String other) {
		super();
		this.phase = phase;
		this.projectExpectedStudy = projectExpectedStudy;
		this.projectPolicy = projectPolicy;
		this.repIndGeographicScope = repIndGeographicScope;
		this.repIndPolicyInvestimentType = repIndPolicyInvestimentType;
		this.repIndStageProcess = repIndStageProcess;
		this.year = year;
		this.title = title;
		this.amount = amount;
		this.other = other;
	}

	public ProjectPolicyInfo(Phase phase, ProjectPolicy projectPolicy, Long year) {
		super();
		this.phase = phase;
		this.projectPolicy = projectPolicy;
		this.year = year;
	}

	public Double getAmount() {
		return amount;
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

	public String getNarrativeEvidence() {
		return narrativeEvidence;
	}

	public String getOther() {
		return other;
	}

	public Phase getPhase() {
		return phase;
	}

	public ProjectExpectedStudy getProjectExpectedStudy() {
		return projectExpectedStudy;
	}

	public ProjectPolicy getProjectPolicy() {
		return projectPolicy;
	}

	public RepIndGeographicScope getRepIndGeographicScope() {
		return repIndGeographicScope;
	}

	public RepIndPolicyInvestimentType getRepIndPolicyInvestimentType() {
		return repIndPolicyInvestimentType;
	}

	public RepIndStageProcess getRepIndStageProcess() {
		return repIndStageProcess;
	}

	public String getTitle() {
		return title;
	}

	public Long getYear() {
		return year;
	}

	public Boolean getHasMilestones() {
		return hasMilestones;
	}

	public void setHasMilestones(Boolean hasMilestones) {
		this.hasMilestones = hasMilestones;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public Boolean isPrevious() {
		if (this.getYear() != null) {
			if (this.getYear() < phase.getYear()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public Boolean isRequired() {
		if (this.getYear() != null && this.getPhase() != null) {
			if (this.getYear() == this.getPhase().getYear()) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public void setModifiedBy(User modifiedBy) {

	}

	public void setNarrativeEvidence(String narrativeEvidence) {
		this.narrativeEvidence = narrativeEvidence;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public void setProjectExpectedStudy(ProjectExpectedStudy projectExpectedStudy) {
		this.projectExpectedStudy = projectExpectedStudy;
	}

	public void setProjectPolicy(ProjectPolicy projectPolicy) {
		this.projectPolicy = projectPolicy;
	}

	public void setRepIndGeographicScope(RepIndGeographicScope repIndGeographicScope) {
		this.repIndGeographicScope = repIndGeographicScope;
	}

	public void setRepIndPolicyInvestimentType(RepIndPolicyInvestimentType repIndPolicyInvestimentType) {
		this.repIndPolicyInvestimentType = repIndPolicyInvestimentType;
	}

	public void setRepIndStageProcess(RepIndStageProcess repIndStageProcess) {
		this.repIndStageProcess = repIndStageProcess;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProjectPolicyInfo [id=" + this.getId() + "projectPolicy=" + projectPolicy + ", phase=" + phase
				+ ", title=" + title + ", year=" + year + "]";
	}

	/**
	 * Add the save information to reply the next Phase
	 * 
	 * @param projectInnovationInfoUpdate - a ProjectInnovationInfo object.
	 * @param phase                       - The next Phase
	 */
	public void updateProjectPolicyInfo(ProjectPolicyInfo projectPolicyInfoUpdate, Phase phase) {

		this.setPhase(phase);

		this.setTitle(projectPolicyInfoUpdate.getTitle());
		this.setTitle(projectPolicyInfoUpdate.getDescription());
		this.setAmount(projectPolicyInfoUpdate.getAmount());
		this.setYear(projectPolicyInfoUpdate.getYear());
		this.setRepIndStageProcess(projectPolicyInfoUpdate.getRepIndStageProcess());
		this.setRepIndPolicyInvestimentType(projectPolicyInfoUpdate.getRepIndPolicyInvestimentType());
		this.setRepIndGeographicScope(projectPolicyInfoUpdate.getRepIndGeographicScope());
		this.setProjectPolicy(projectPolicyInfoUpdate.getProjectPolicy());
		this.setProjectExpectedStudy(projectPolicyInfoUpdate.getProjectExpectedStudy());
		this.setOther(projectPolicyInfoUpdate.getOther());
		this.setHasMilestones(projectPolicyInfoUpdate.getHasMilestones());
	}

}
