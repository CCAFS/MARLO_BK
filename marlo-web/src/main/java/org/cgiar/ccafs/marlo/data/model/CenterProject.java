package org.cgiar.ccafs.marlo.data.model;
// Generated Feb 15, 2017 8:49:31 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * CenterProject generated by hbm2java
 */
public class CenterProject implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -4480927754617355650L;


  @Expose
  private Long id;


  @Expose
  private User modifiedBy;


  @Expose
  private User contactPerson;


  @Expose
  private User projectLeader;


  @Expose
  private User createdBy;


  @Expose
  private CenterProjectStatus projectStatus;


  @Expose
  private String name;


  @Expose
  private Date startDate;


  @Expose
  private Date endDate;

  @Expose
  private boolean sync;

  @Expose
  private Date syncDate;

  @Expose
  private boolean autoFill;

  @Expose
  private boolean active;

  @Expose
  private Date activeSince;

  @Expose
  private Date dateCreated;

  @Expose
  private String modificationJustification;

  @Expose
  private CenterProgram researchProgram;

  @Expose
  private CenterProjectCrosscutingTheme projectCrosscutingTheme;

  @Expose
  private Boolean global;

  @Expose
  private Boolean region;

  private String sGlobal;

  private String sRegion;

  @Expose
  private String description;

  @Expose
  private String suggestedName;

  @Expose
  private Project project;

  private Set<CenterSectionStatus> sectionStatuses = new HashSet<CenterSectionStatus>(0);

  private Set<CenterProjectOutput> projectOutputs = new HashSet<CenterProjectOutput>(0);

  private Set<CenterProjectFundingSource> projectFundingSources = new HashSet<CenterProjectFundingSource>(0);

  private Set<CenterDeliverable> deliverables = new HashSet<CenterDeliverable>(0);

  private Set<CenterSubmission> submissions = new HashSet<CenterSubmission>(0);

  private List<CenterProjectOutput> outputs;

  private List<CenterProjectFundingSource> fundingSources;

  private Set<CenterProjectPartner> projectPartners = new HashSet<CenterProjectPartner>(0);


  private List<CenterProjectPartner> partners;

  private Set<CenterProjectLocation> projectLocations = new HashSet<CenterProjectLocation>(0);


  private List<CenterProjectLocation> projectRegions;


  private List<CenterProjectLocation> projectCountries;

  public CenterProject() {
  }


  public CenterProject(boolean active) {
    this.active = active;
  }

  public CenterProject(User modifiedBy, User contactPerson, User projectLeader, User createdBy,
    CenterProjectStatus projectStatus, String name, Date startDate, Date endDate, boolean active, Date activeSince,
    String modificationJustification, Set<CenterProjectOutput> projectOutputs) {
    this.modifiedBy = modifiedBy;
    this.contactPerson = contactPerson;
    this.projectLeader = projectLeader;
    this.createdBy = createdBy;
    this.projectStatus = projectStatus;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
    this.projectOutputs = projectOutputs;
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
    CenterProject other = (CenterProject) obj;
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
    return activeSince;
  }

  public String getComposedName() {
    return "P" + this.id + "- " + (this.name != null ? this.name : "title not defined");
  }

  public User getContactPerson() {
    return contactPerson;
  }

  public User getCreatedBy() {
    return createdBy;
  }


  public Date getDateCreated() {
    return dateCreated;
  }


  public Set<CenterDeliverable> getDeliverables() {
    return deliverables;
  }


  public String getDescription() {
    return description;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getEndDateFormat() {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = "";
    if (this.endDate != null) {
      date = simpleDateFormat.format(this.endDate);
    }
    return date;
  }

  public List<CenterProjectFundingSource> getFundingSources() {
    return fundingSources;
  }

  public Boolean getGlobal() {
    return global;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  @Override
  public String getModificationJustification() {
    return modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public String getName() {
    return name;
  }

  public List<CenterProjectOutput> getOutputs() {
    return outputs;
  }


  public List<CenterProjectPartner> getPartners() {
    return partners;
  }

  public Project getProject() {
    return project;
  }

  public List<CenterProjectLocation> getProjectCountries() {
    return projectCountries;
  }

  public CenterProjectCrosscutingTheme getProjectCrosscutingTheme() {
    return projectCrosscutingTheme;
  }

  public Set<CenterProjectFundingSource> getProjectFundingSources() {
    return projectFundingSources;
  }

  public User getProjectLeader() {
    return projectLeader;
  }


  public Set<CenterProjectLocation> getProjectLocations() {
    return projectLocations;
  }


  public Set<CenterProjectOutput> getProjectOutputs() {
    return projectOutputs;
  }


  public Set<CenterProjectPartner> getProjectPartners() {
    return projectPartners;
  }


  public List<CenterProjectLocation> getProjectRegions() {
    return projectRegions;
  }


  public CenterProjectStatus getProjectStatus() {
    return projectStatus;
  }

  public Boolean getRegion() {
    return region;
  }

  public CenterProgram getResearchProgram() {
    return researchProgram;
  }

  public Set<CenterSectionStatus> getSectionStatuses() {
    return sectionStatuses;
  }

  public String getsGlobal() {
    return sGlobal;
  }

  public String getsRegion() {
    return sRegion;
  }

  public String getStarDateFormat() {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = "";
    if (this.startDate != null) {
      date = simpleDateFormat.format(this.startDate);
    }
    return date;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Set<CenterSubmission> getSubmissions() {
    return submissions;
  }

  public String getSuggestedName() {
    return suggestedName;
  }

  public Date getSyncDate() {
    return syncDate;
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


  public boolean isAutoFill() {
    return autoFill;
  }

  public boolean isSync() {
    return sync;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }


  public void setAutoFill(boolean autoFill) {
    this.autoFill = autoFill;
  }

  public void setContactPerson(User contactPerson) {
    this.contactPerson = contactPerson;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public void setDeliverables(Set<CenterDeliverable> deliverables) {
    this.deliverables = deliverables;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setFundingSources(List<CenterProjectFundingSource> fundingSources) {
    this.fundingSources = fundingSources;
  }

  public void setGlobal(Boolean global) {
    this.global = global;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  public void setName(String name) {
    this.name = name;
  }


  public void setOutputs(List<CenterProjectOutput> outputs) {
    this.outputs = outputs;
  }


  public void setPartners(List<CenterProjectPartner> partners) {
    this.partners = partners;
  }

  public void setProject(Project project) {
    this.project = project;
  }


  public void setProjectCountries(List<CenterProjectLocation> projectCountries) {
    this.projectCountries = projectCountries;
  }


  public void setProjectCrosscutingTheme(CenterProjectCrosscutingTheme projectCrosscutingTheme) {
    this.projectCrosscutingTheme = projectCrosscutingTheme;
  }


  public void setProjectFundingSources(Set<CenterProjectFundingSource> projectFundingSources) {
    this.projectFundingSources = projectFundingSources;
  }


  public void setProjectLeader(User projectLeader) {
    this.projectLeader = projectLeader;
  }


  public void setProjectLocations(Set<CenterProjectLocation> projectLocations) {
    this.projectLocations = projectLocations;
  }


  public void setProjectOutputs(Set<CenterProjectOutput> projectOutputs) {
    this.projectOutputs = projectOutputs;
  }

  public void setProjectPartners(Set<CenterProjectPartner> projectPartners) {
    this.projectPartners = projectPartners;
  }

  public void setProjectRegions(List<CenterProjectLocation> projectRegions) {
    this.projectRegions = projectRegions;
  }

  public void setProjectStatus(CenterProjectStatus projectStatus) {
    this.projectStatus = projectStatus;
  }

  public void setRegion(Boolean region) {
    this.region = region;
  }


  public void setResearchProgram(CenterProgram researchProgram) {
    this.researchProgram = researchProgram;
  }

  public void setSectionStatuses(Set<CenterSectionStatus> sectionStatuses) {
    this.sectionStatuses = sectionStatuses;
  }

  public void setsGlobal(String sGlobal) {
    this.sGlobal = sGlobal;
  }

  public void setsRegion(String sRegion) {
    this.sRegion = sRegion;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }


  public void setSubmissions(Set<CenterSubmission> submissions) {
    this.submissions = submissions;
  }


  public void setSuggestedName(String suggestedName) {
    this.suggestedName = suggestedName;
  }

  public void setSync(boolean sync) {
    this.sync = sync;
  }


  public void setSyncDate(Date syncDate) {
    this.syncDate = syncDate;
  }


  @Override
  public String toString() {
    return id.toString();
  }

}

