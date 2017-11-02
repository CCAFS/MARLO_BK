package org.cgiar.ccafs.marlo.data.model;
// Generated Apr 10, 2017 7:46:39 AM by Hibernate Tools 4.3.1.Final

import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;

/**
 * GenderTypes generated by hbm2java
 */
public class GenderType implements java.io.Serializable, IAuditLog {


  /**
   * 
   */
  private static final long serialVersionUID = 1694753404214696770L;


  @Expose
  private Long id;


  private GlobalUnit crp;


  @Expose
  private String description;
  @Expose
  private String completeDescription;

  public GenderType() {
  }

  public GenderType(GlobalUnit crp, String description) {
    this.crp = crp;
    this.description = description;
  }

  public GenderType(String description) {
    this.description = description;
  }

  public String getCompleteDescription() {
    return completeDescription;
  }


  public GlobalUnit getCrp() {
    return crp;
  }


  public String getDescription() {
    return this.description;
  }

  @Override
  public Long getId() {
    return this.id;
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

  @Override
  public boolean isActive() {

    return true;
  }

  public void setCompleteDescription(String completeDescription) {
    this.completeDescription = completeDescription;
  }

  public void setCrp(GlobalUnit crp) {
    this.crp = crp;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public void setId(Long id) {
    this.id = id;
  }


}

