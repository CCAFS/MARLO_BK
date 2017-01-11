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
// Generated Jul 6, 2016 9:31:00 AM by Hibernate Tools 4.3.1.Final

/**
 * SectionStatus generated by hbm2java
 */
public class SectionStatus implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -7527583315860965374L;


  private Long id;

  private CrpProgram crpProgram;


  private String sectionName;

  private String missingFields;


  private String cycle;


  private Integer year;


  private Project project;


  private Deliverable deliverable;


  private ProjectOutcome projectOutcome;

  private CaseStudy caseStudy;
  private ProjectHighlight projectHighlight;

  public SectionStatus() {
  }

  public SectionStatus(CrpProgram crpProgram, String sectionName, String missingFields, String cycle, Integer year) {
    this.crpProgram = crpProgram;
    this.sectionName = sectionName;
    this.missingFields = missingFields;
    this.cycle = cycle;
    this.year = year;
  }

  public SectionStatus(String sectionName) {
    this.sectionName = sectionName;
  }

  public CaseStudy getCaseStudy() {
    return caseStudy;
  }

  public CrpProgram getCrpProgram() {
    return this.crpProgram;
  }

  public String getCycle() {
    return this.cycle;
  }

  public Deliverable getDeliverable() {
    return deliverable;
  }

  public Long getId() {
    return this.id;
  }

  public String getMissingFields() {
    return this.missingFields;
  }

  public Project getProject() {
    return project;
  }


  public ProjectHighlight getProjectHighlight() {
    return projectHighlight;
  }


  public ProjectOutcome getProjectOutcome() {
    return projectOutcome;
  }

  public String getSectionName() {
    return this.sectionName;
  }


  public Integer getYear() {
    return this.year;
  }

  public void setCaseStudy(CaseStudy caseStudy) {
    this.caseStudy = caseStudy;
  }

  public void setCrpProgram(CrpProgram crpProgram) {
    this.crpProgram = crpProgram;
  }


  public void setCycle(String cycle) {
    this.cycle = cycle;
  }


  public void setDeliverable(Deliverable deliverable) {
    this.deliverable = deliverable;
  }


  public void setId(Long id) {
    this.id = id;
  }

  public void setMissingFields(String missingFields) {
    this.missingFields = missingFields;
  }


  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectHighlight(ProjectHighlight projectHighlight) {
    this.projectHighlight = projectHighlight;
  }


  public void setProjectOutcome(ProjectOutcome projectOutcome) {
    this.projectOutcome = projectOutcome;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return id.toString();
  }
}

