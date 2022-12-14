package org.cgiar.ccafs.marlo.data.model;
// Generated Apr 30, 2018 10:29:46 AM by Hibernate Tools 3.4.0.CR1

import com.google.gson.annotations.Expose;

/**
 * RepIndStageProcess generated by hbm2java
 */
public class RepIndStageProcess extends MarloBaseEntity implements java.io.Serializable {


  private static final long serialVersionUID = 9001922443942182228L;

  @Expose
  private String name;

  @Expose
  private String description;

  @Expose
  private Long year;


  @Expose
  private RepIndStageStudy repIndStageStudy;


  public RepIndStageProcess() {
  }


  public String getComposedName() {
    return this.name + ": " + this.description;
  }


  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }


  public RepIndStageStudy getRepIndStageStudy() {
    return repIndStageStudy;
  }


  public Long getYear() {
    return year;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRepIndStageStudy(RepIndStageStudy repIndStageStudy) {
    this.repIndStageStudy = repIndStageStudy;
  }

  public void setYear(Long year) {
    this.year = year;
  }


}

