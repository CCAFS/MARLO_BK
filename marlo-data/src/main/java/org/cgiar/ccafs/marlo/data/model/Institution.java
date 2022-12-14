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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;

public class Institution extends MarloBaseEntity implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = 3635585962414755020L;

  @Expose
  private InstitutionType institutionType;


  @Expose
  private String name;

  @Expose
  private String acronym;


  @Expose
  private String websiteLink;


  @Expose
  private Long programId;


  @Expose
  private Date added;


  private Set<CrpPpaPartner> crpPpaPartners = new HashSet<CrpPpaPartner>(0);

  private Set<LiaisonInstitution> liaisonInstitutions = new HashSet<LiaisonInstitution>(0);
  private Set<ProjectPartner> projectPartners = new HashSet<ProjectPartner>(0);


  private Set<ProjectBudget> projectBudgets = new HashSet<ProjectBudget>(0);

  private Set<FundingSource> fundingSourcesOriginalDonor = new HashSet<FundingSource>(0);

  private Set<FundingSource> fundingSourcesDirectDonor = new HashSet<FundingSource>(0);

  private Set<ProjectPartnerPerson> projectPartnerPersons = new HashSet<>(0);
  private Set<InstitutionLocation> institutionsLocations = new HashSet<InstitutionLocation>(0);

  private Set<Institution> branches = new HashSet<Institution>(0);


  private List<InstitutionLocation> locations;
  private Set<GlobalUnit> globalUnits = new HashSet<GlobalUnit>(0);

  // agresso integration
  private List<InstitutionDictionary> institutionsRelated;

  private Boolean is_parent;

  private Institution parent;


  // fields not mapped in hibernate
  private String type;


  private Long typeId;


  private String hqLocation;


  private String hqLocationISOalpha2;

  public Institution() {
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    Institution other = (Institution) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }

  public String getAcronym() {
    return this.acronym;
  }

  public String getAcronymName() {
    if (this.getAcronym() != null) {
      if (this.getAcronym().trim().length() != 0) {
        try {
          return this.getAcronym();
        } catch (Exception e) {
          return this.getName();
        }
      }
    }
    return this.getName();
  }

  public Date getAdded() {
    return this.added;
  }

  public Set<Institution> getBranches() {
    return branches;
  }


  public String getComposedName() {
    if (this.getAcronym() != null) {
      if (this.getAcronym().length() != 0) {
        try {
          return this.getAcronym() + " - " + this.getName();
        } catch (Exception e) {
          return this.getName();
        }

      }
    }
    return this.getName();


  }

  public String getComposedNameLoc() {
    if (this.getAcronym() != null) {
      if (this.getAcronym().length() != 0) {
        try {
          return this.getAcronym() + " - " + this.getName();
        } catch (Exception e) {
          return this.getName();
        }

      }
    }
    return this.getName();
  }


  public Set<CrpPpaPartner> getCrpPpaPartners() {
    return crpPpaPartners;
  }


  public Set<FundingSource> getFundingSourcesDirectDonor() {
    return fundingSourcesDirectDonor;
  }


  public Set<FundingSource> getFundingSourcesOriginalDonor() {
    return fundingSourcesOriginalDonor;
  }


  public Set<GlobalUnit> getGlobalUnits() {
    return globalUnits;
  }


  public String getHqLocation() {
    return hqLocation;
  }


  public String getHqLocationISOalpha2() {
    return hqLocationISOalpha2;
  }

  public Set<InstitutionLocation> getInstitutionsLocations() {
    return institutionsLocations;
  }

  public List<InstitutionDictionary> getInstitutionsRelated() {
    return institutionsRelated;
  }

  public InstitutionType getInstitutionType() {
    return institutionType;
  }


  public Boolean getIs_parent() {
    return is_parent;
  }


  public Set<LiaisonInstitution> getLiaisonInstitutions() {
    return liaisonInstitutions;
  }


  public List<InstitutionLocation> getLocations() {
    return locations;
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
    return null;
  }


  public String getName() {
    return this.name;
  }


  public Institution getParent() {
    return parent;
  }


  public Long getProgramId() {
    return this.programId;
  }

  public Set<ProjectBudget> getProjectBudgets() {
    return projectBudgets;
  }


  public Set<ProjectPartnerPerson> getProjectPartnerPersons() {
    return projectPartnerPersons;
  }

  public Set<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  /*
   * public String getComposedLocation() {
   * try {
   * if (this.headquarter == null) {
   * // Verify if there exist a city to show
   * if (this.city != null && this.city != "") {
   * return this.city + ", " + this.locElement.getName();
   * }
   * return this.locElement.getName();
   * } else {
   * // Verify if there exist a city to show
   * if (this.city != null && this.city != "") {
   * return this.city + ", " + this.locElement.getName();
   * }
   * return this.locElement.getName();
   * }
   * } catch (Exception e) {
   * return this.name;
   * }
   * }
   */

  public String getType() {
    return type;
  }

  public Long getTypeId() {
    return typeId;
  }

  public String getWebsiteLink() {
    return this.websiteLink;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }


  @Override
  public boolean isActive() {
    return true;
  }

  public boolean isPPA(long crpID, Phase phase) {
    if (this.getCrpPpaPartners().stream()
      .filter(c -> c.getCrp().getId().longValue() == crpID && c.isActive() && c.getPhase().equals(phase))
      .collect(Collectors.toList()).size() > 0) {
      return true;
    }
    return false;
  }


  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  public void setAdded(Date added) {
    this.added = added;
  }


  public void setBranches(Set<Institution> branches) {
    this.branches = branches;
  }


  public void setCrpPpaPartners(Set<CrpPpaPartner> crpPpaPartners) {
    this.crpPpaPartners = crpPpaPartners;
  }

  public void setFundingSourcesDirectDonor(Set<FundingSource> fundingSourcesDirectDonor) {
    this.fundingSourcesDirectDonor = fundingSourcesDirectDonor;
  }

  public void setFundingSourcesOriginalDonor(Set<FundingSource> fundingSourcesOriginalDonor) {
    this.fundingSourcesOriginalDonor = fundingSourcesOriginalDonor;
  }

  public void setGlobalUnits(Set<GlobalUnit> globalUnits) {
    this.globalUnits = globalUnits;
  }

  public void setHqLocation(String hqLocation) {
    this.hqLocation = hqLocation;
  }

  public void setHqLocationISOalpha2(String hqLocationISOalpha2) {
    this.hqLocationISOalpha2 = hqLocationISOalpha2;
  }

  public void setInstitutionsLocations(Set<InstitutionLocation> institutionsLocations) {
    this.institutionsLocations = institutionsLocations;
  }

  public void setInstitutionsRelated(List<InstitutionDictionary> institutionsRelated) {
    this.institutionsRelated = institutionsRelated;
  }


  public void setInstitutionType(InstitutionType institutionType) {
    this.institutionType = institutionType;
  }

  public void setIs_parent(Boolean is_parent) {
    this.is_parent = is_parent;
  }

  public void setLiaisonInstitutions(Set<LiaisonInstitution> liaisonInstitutions) {
    this.liaisonInstitutions = liaisonInstitutions;
  }

  public void setLocations(List<InstitutionLocation> locations) {
    this.locations = locations;
  }


  @Override
  public void setModifiedBy(User modifiedBy) {

  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParent(Institution parent) {
    this.parent = parent;
  }

  public void setProgramId(Long programId) {
    this.programId = programId;
  }

  public void setProjectBudgets(Set<ProjectBudget> projectBudgets) {
    this.projectBudgets = projectBudgets;
  }

  public void setProjectPartnerPersons(Set<ProjectPartnerPerson> projectPartnerPersons) {
    this.projectPartnerPersons = projectPartnerPersons;
  }

  public void setProjectPartners(Set<ProjectPartner> projectPartners) {
    this.projectPartners = projectPartners;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }

  public void setWebsiteLink(String websiteLink) {
    this.websiteLink = websiteLink;
  }

  @Override
  public String toString() {
    return "Institution [id=" + this.getId() + ", institutionType=" + institutionType + ", name=" + name + ", acronym="
      + acronym + ", programId=" + programId + "]";
  }
}