package org.cgiar.ccafs.marlo.data.model;
// Generated Feb 7, 2017 2:38:52 PM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * IpLiaisonInstitution generated by hbm2java
 */
public class IpLiaisonInstitution implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -418826851418998505L;


  @Expose
  private Long id;


  @Expose
  private Institution institution;


  @Expose
  private String name;


  @Expose
  private String acronym;


  @Expose
  private Integer ipProgram;


  private Set<CrpIndicatorReport> crpIndicatorReportses = new HashSet<CrpIndicatorReport>(0);

  private Set<SectionStatus> sectionStatus = new HashSet<SectionStatus>(0);
  private List<CrpIndicatorReport> indicatorReports;
  private Set<IpLiaisonUser> ipLiaisonUsers = new HashSet<IpLiaisonUser>(0);

  public IpLiaisonInstitution() {
  }

  public IpLiaisonInstitution(Institution institution, String name) {
    this.institution = institution;
    this.name = name;
  }

  public IpLiaisonInstitution(Institution institution, String name, String acronym, Integer ipProgram,
    Set<IpLiaisonUser> ipLiaisonUsers) {
    this.institution = institution;
    this.name = name;
    this.acronym = acronym;
    this.ipProgram = ipProgram;
    this.ipLiaisonUsers = ipLiaisonUsers;
  }

  public String getAcronym() {
    return acronym;
  }

  public Set<CrpIndicatorReport> getCrpIndicatorReportses() {
    return crpIndicatorReportses;
  }


  @Override
  public Long getId() {
    return id;
  }


  public List<CrpIndicatorReport> getIndicatorReports() {
    return indicatorReports;
  }


  public Institution getInstitution() {
    return institution;
  }


  public Set<IpLiaisonUser> getIpLiaisonUsers() {
    return ipLiaisonUsers;
  }


  public Integer getIpProgram() {
    return ipProgram;
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
    User user = new User();
    user.setId(new Long(3));
    return user;
  }

  public String getName() {
    return name;
  }

  public Set<SectionStatus> getSectionStatus() {
    return sectionStatus;
  }

  @Override
  public boolean isActive() {
    return true;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setCrpIndicatorReportses(Set<CrpIndicatorReport> crpIndicatorReportses) {
    this.crpIndicatorReportses = crpIndicatorReportses;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public void setIndicatorReports(List<CrpIndicatorReport> indicatorReports) {
    this.indicatorReports = indicatorReports;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }


  public void setIpLiaisonUsers(Set<IpLiaisonUser> ipLiaisonUsers) {
    this.ipLiaisonUsers = ipLiaisonUsers;
  }


  public void setIpProgram(Integer ipProgram) {
    this.ipProgram = ipProgram;
  }


  public void setName(String name) {
    this.name = name;
  }


  public void setSectionStatus(Set<SectionStatus> sectionStatus) {
    this.sectionStatus = sectionStatus;
  }


}

