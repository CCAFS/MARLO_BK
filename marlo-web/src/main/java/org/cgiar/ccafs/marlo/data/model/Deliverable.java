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

import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class Deliverable implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = 1867818669925473872L;


  @Expose
  private Long id;


  @Expose
  private Project project;


  @Expose
  private DeliverableType deliverableType;


  @Expose
  private String title;


  @Expose
  private String typeOther;


  @Expose
  private int year;


  @Expose
  private Integer newExpectedYear;


  @Expose
  private Integer status;


  @Expose
  private String statusDescription;


  @Expose
  private boolean active;


  @Expose
  private Date activeSince;


  @Expose
  private User createdBy;


  @Expose
  private User modifiedBy;


  @Expose
  private String modificationJustification;


  @Expose
  private CrpClusterKeyOutput crpClusterKeyOutput;


  @Expose
  private CrpProgramOutcome crpProgramOutcome;


  @Expose
  private Date createDate;


  private Set<DeliverableGenderLevel> deliverableGenderLevels = new HashSet<DeliverableGenderLevel>(0);


  private List<DeliverableGenderLevel> genderLevels;


  private Set<DeliverablePartnership> deliverablePartnerships = new HashSet<DeliverablePartnership>(0);


  private Set<DeliverableActivity> deliverableActivities = new HashSet<DeliverableActivity>(0);


  private Set<SectionStatus> sectionStatuses = new HashSet<SectionStatus>(0);


  private DeliverablePartnership responsiblePartner;


  private Set<DeliverableFundingSource> deliverableFundingSources = new HashSet<DeliverableFundingSource>(0);


  private List<DeliverableFundingSource> fundingSources;


  private List<DeliverablePartnership> otherPartners;


  private Set<DeliverableQualityCheck> deliverableQualityChecks = new HashSet<DeliverableQualityCheck>(0);


  @Expose
  private Boolean crossCuttingGender;


  @Expose
  private Boolean crossCuttingYouth;


  @Expose
  private Boolean crossCuttingCapacity;


  @Expose
  private Boolean crossCuttingNa;


  @Expose
  private String license;


  @Expose
  private boolean allowModifications;


  DeliverableQualityCheck qualityCheck;


  private Set<DeliverableMetadataElement> deliverableMetadataElements = new HashSet<DeliverableMetadataElement>(0);

  private Set<DeliverableDissemination> deliverableDisseminations = new HashSet<DeliverableDissemination>(0);

  private Set<DeliverableDataSharingFile> deliverableDataSharingFiles = new HashSet<DeliverableDataSharingFile>(0);


  private Set<DeliverablePublicationMetadata> deliverablePublicationMetadatas =
    new HashSet<DeliverablePublicationMetadata>(0);


  private Set<DeliverableDataSharing> deliverableDataSharings = new HashSet<DeliverableDataSharing>(0);

  private List<DeliverableMetadataElement> metadataElements;

  // Data List from Data Sharing

  private List<DeliverableDissemination> disseminations;


  private List<DeliverableDataSharingFile> dataSharingFiles;


  private List<DeliverablePublicationMetadata> publicationMetadatas;


  private List<DeliverableDataSharing> dataSharing;


  private List<MetadataElement> metadata;


  private DeliverableDissemination dissemination;


  public Deliverable() {
  }

  public Deliverable(Project project, DeliverableType deliverableType, String title, String typeOther, int year,
    Integer status, String statusDescription, boolean active, Date activeSince, User createdBy, User modifiedBy,
    String modificationJustification, CrpClusterKeyOutput crpClusterKeyOutput, CrpProgramOutcome crpProgramOutcome,
    Set<DeliverablePartnership> deliverablePartnerships, Set<DeliverableActivity> deliverableActivities,
    Date createDate) {
    this.project = project;
    this.deliverableType = deliverableType;
    this.title = title;
    this.typeOther = typeOther;
    this.year = year;
    this.status = status;
    this.statusDescription = statusDescription;
    this.active = active;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
    this.crpProgramOutcome = crpProgramOutcome;
    this.crpClusterKeyOutput = crpClusterKeyOutput;
    this.deliverablePartnerships = deliverablePartnerships;
    this.deliverableActivities = deliverableActivities;
    this.createDate = createDate;
  }


  public Deliverable(Project project, int year, boolean active, Date activeSince, User createdBy, User modifiedBy,
    String modificationJustification) {
    this.project = project;
    this.year = year;
    this.active = active;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
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
    Deliverable other = (Deliverable) obj;
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

  public String getComposedName() {
    try {
      return "<b> (D" + this.id + ") " + this.getDeliverableType().getDescription() + "</b> - " + this.title;
    } catch (Exception e) {
      return "<b> (D" + this.id + ") </b> - " + this.title;

    }
  }

  public Date getCreateDate() {
    return createDate;
  }

  public User getCreatedBy() {
    return createdBy;
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

  public CrpClusterKeyOutput getCrpClusterKeyOutput() {
    return crpClusterKeyOutput;
  }

  public CrpProgramOutcome getCrpProgramOutcome() {
    return crpProgramOutcome;
  }

  public List<DeliverableDataSharing> getDataSharing() {
    return dataSharing;
  }

  // End


  public List<DeliverableDataSharingFile> getDataSharingFiles() {
    return dataSharingFiles;
  }

  public Set<DeliverableActivity> getDeliverableActivities() {
    return deliverableActivities;
  }


  public Set<DeliverableDataSharingFile> getDeliverableDataSharingFiles() {
    return deliverableDataSharingFiles;
  }

  public Set<DeliverableDataSharing> getDeliverableDataSharings() {
    return deliverableDataSharings;
  }

  public Set<DeliverableDissemination> getDeliverableDisseminations() {
    return deliverableDisseminations;
  }

  public Set<DeliverableFundingSource> getDeliverableFundingSources() {
    return deliverableFundingSources;
  }

  public Set<DeliverableGenderLevel> getDeliverableGenderLevels() {
    return deliverableGenderLevels;
  }

  public Set<DeliverableMetadataElement> getDeliverableMetadataElements() {
    return deliverableMetadataElements;
  }

  public Set<DeliverablePartnership> getDeliverablePartnerships() {
    return deliverablePartnerships;
  }

  public Set<DeliverablePublicationMetadata> getDeliverablePublicationMetadatas() {
    return deliverablePublicationMetadatas;
  }

  public Set<DeliverableQualityCheck> getDeliverableQualityChecks() {
    return deliverableQualityChecks;
  }

  public DeliverableType getDeliverableType() {
    return this.deliverableType;
  }

  public DeliverableDissemination getDissemination() {
    return dissemination;
  }

  public List<DeliverableDissemination> getDisseminations() {
    return disseminations;
  }


  public List<DeliverableFundingSource> getFundingSources() {
    return fundingSources;
  }


  public List<DeliverableGenderLevel> getGenderLevels() {
    return genderLevels;
  }


  @Override
  public Long getId() {
    return id;
  }

  public String getLicense() {
    return license;
  }

  public String getLicenseType() {
    if (license != null) {
      if (Arrays.asList(LicensesTypeEnum.values()).contains(license)) {
        return license;
      } else {
        return "OTHER";
      }
    }
    return "";
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  public List<MetadataElement> getMetadata() {
    return metadata;
  }

  public List<DeliverableMetadataElement> getMetadataElements() {
    return metadataElements;
  }


  public int getMetadataID(String metadataName) {
    for (MetadataElement mData : metadata) {
      if (mData.getEcondedName().equals(metadataName)) {
        return mData.getId();
      }
    }
    return -1;
  }

  public int getMetadataIndex(String metadataName) {
    int c = 0;
    for (MetadataElement mData : metadata) {
      if (mData.getEcondedName().equals(metadataName)) {
        return c;
      }
      c++;
    }
    return -1;
  }


  public String getMetadataValue(int metadataID) {
    String value = "";
    for (DeliverableMetadataElement dmetadata : metadataElements) {
      if (dmetadata.getMetadataElement().getId() == metadataID) {
        value = dmetadata.getElementValue();
      }
    }

    return value;
  }

  public String getMetadataValue(String metadataName) {
    for (DeliverableMetadataElement mData : metadataElements) {
      if (mData.getMetadataElement().getElement().equals(metadataName)) {
        return mData.getElementValue();
      }
    }
    return "";
  }

  public String getMetadataValueByEncondedName(String metadataName) {
    for (DeliverableMetadataElement mData : metadataElements) {
      if (mData.getMetadataElement().getEcondedName().equals(metadataName)) {
        return mData.getElementValue();
      }
    }
    return "";
  }

  @Override
  public String getModificationJustification() {
    return this.modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public Integer getNewExpectedYear() {
    return newExpectedYear;
  }


  public List<DeliverablePartnership> getOtherPartners() {
    return otherPartners;
  }

  public Project getProject() {
    return project;
  }

  public List<DeliverablePublicationMetadata> getPublicationMetadatas() {
    return publicationMetadatas;
  }

  public DeliverableQualityCheck getQualityCheck() {
    return qualityCheck;
  }

  public DeliverablePartnership getResponsiblePartner() {
    return responsiblePartner;
  }


  public Set<SectionStatus> getSectionStatuses() {
    return sectionStatuses;
  }


  public Integer getStatus() {
    return this.status;
  }


  public String getStatusDescription() {
    return this.statusDescription;
  }


  public String getStatusName() {
    try {
      if (this.status != null) {
        return ProjectStatusEnum.getValue(this.status).getStatus() != null
          ? ProjectStatusEnum.getValue(this.status).getStatus() : "";
      } else {
        return "";
      }
    } catch (Exception e) {
      return "";
    }
  }


  public String getTitle() {
    return this.title;
  }


  public String getTypeOther() {
    return this.typeOther;
  }


  public int getYear() {
    return this.year;
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


  public boolean isAllowModifications() {
    return allowModifications;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }


  public void setAllowModifications(boolean allowModifications) {
    this.allowModifications = allowModifications;
  }


  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }


  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
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

  public void setCrpClusterKeyOutput(CrpClusterKeyOutput crpClusterKeyOutput) {
    this.crpClusterKeyOutput = crpClusterKeyOutput;
  }

  public void setCrpProgramOutcome(CrpProgramOutcome crpProgramOutcome) {
    this.crpProgramOutcome = crpProgramOutcome;
  }

  public void setDataSharing(List<DeliverableDataSharing> dataSharing) {
    this.dataSharing = dataSharing;
  }

  public void setDataSharingFiles(List<DeliverableDataSharingFile> dataSharingFiles) {
    this.dataSharingFiles = dataSharingFiles;
  }

  public void setDeliverableActivities(Set<DeliverableActivity> deliverableActivities) {
    this.deliverableActivities = deliverableActivities;
  }

  public void setDeliverableDataSharingFiles(Set<DeliverableDataSharingFile> deliverableDataSharingFiles) {
    this.deliverableDataSharingFiles = deliverableDataSharingFiles;
  }

  public void setDeliverableDataSharings(Set<DeliverableDataSharing> deliverableDataSharings) {
    this.deliverableDataSharings = deliverableDataSharings;
  }

  public void setDeliverableDisseminations(Set<DeliverableDissemination> deliverableDisseminations) {
    this.deliverableDisseminations = deliverableDisseminations;
  }


  public void setDeliverableFundingSources(Set<DeliverableFundingSource> deliverableFundingSources) {
    this.deliverableFundingSources = deliverableFundingSources;
  }

  public void setDeliverableGenderLevels(Set<DeliverableGenderLevel> deliverableGenderLevels) {
    this.deliverableGenderLevels = deliverableGenderLevels;
  }

  public void setDeliverableMetadataElements(Set<DeliverableMetadataElement> deliverableMetadataElements) {
    this.deliverableMetadataElements = deliverableMetadataElements;
  }

  public void setDeliverablePartnerships(Set<DeliverablePartnership> deliverablePartnerships) {
    this.deliverablePartnerships = deliverablePartnerships;
  }

  public void setDeliverablePublicationMetadatas(Set<DeliverablePublicationMetadata> deliverablePublicationMetadatas) {
    this.deliverablePublicationMetadatas = deliverablePublicationMetadatas;
  }

  public void setDeliverableQualityChecks(Set<DeliverableQualityCheck> deliverableQualityChecks) {
    this.deliverableQualityChecks = deliverableQualityChecks;
  }

  public void setDeliverableType(DeliverableType deliverableType) {
    this.deliverableType = deliverableType;
  }

  public void setDissemination(DeliverableDissemination dissemination) {
    this.dissemination = dissemination;
  }


  public void setDisseminations(List<DeliverableDissemination> disseminations) {
    this.disseminations = disseminations;
  }

  public void setFundingSources(List<DeliverableFundingSource> fundingSources) {
    this.fundingSources = fundingSources;
  }

  public void setGenderLevels(List<DeliverableGenderLevel> genderLevels) {
    this.genderLevels = genderLevels;
  }


  public void setId(Long id) {
    this.id = id;
  }

  public void setLicense(String license) {
    this.license = license;
  }

  public void setMetadata(List<MetadataElement> metadata) {
    this.metadata = metadata;
  }


  public void setMetadataElements(List<DeliverableMetadataElement> metadataElements) {
    this.metadataElements = metadataElements;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  public void setNewExpectedYear(Integer newExpectedYear) {
    this.newExpectedYear = newExpectedYear;
  }


  public void setOtherPartners(List<DeliverablePartnership> otherPartners) {
    this.otherPartners = otherPartners;
  }

  public void setProject(Project project) {
    this.project = project;
  }


  public void setPublicationMetadatas(List<DeliverablePublicationMetadata> publicationMetadatas) {
    this.publicationMetadatas = publicationMetadatas;
  }


  public void setQualityCheck(DeliverableQualityCheck qualityCheck) {
    this.qualityCheck = qualityCheck;
  }

  public void setResponsiblePartner(DeliverablePartnership responsiblePartner) {
    this.responsiblePartner = responsiblePartner;
  }

  public void setSectionStatuses(Set<SectionStatus> sectionStatuses) {
    this.sectionStatuses = sectionStatuses;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }


  public void setTitle(String title) {
    this.title = title;
  }


  public void setTypeOther(String typeOther) {
    this.typeOther = typeOther;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
