package org.cgiar.ccafs.marlo.data.model;
// Generated Apr 30, 2018 10:52:36 AM by Hibernate Tools 3.4.0.CR1

import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;

/**
 * ProjectExpectedStudyInfo generated by hbm2java
 */
public class Safeguards extends MarloBaseEntity implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -7806050142645120199L;

  @Expose
  private FileDB file;
  @Expose
  private Phase phase;
  @Expose
  private Project project;
  @Expose
  private String link;
  @Expose
  private boolean active;

  private boolean hasFile;

  public Safeguards() {
  }

  public FileDB getFile() {
    return file;
  }

  public String getLink() {
    return link;
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

  public Phase getPhase() {
    return phase;
  }

  public Project getProject() {
    return project;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  public boolean isHasFile() {
    return hasFile;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setFile(FileDB file) {
    this.file = file;
  }

  public void setHasFile(boolean hasFile) {
    this.hasFile = hasFile;
  }

  public void setLink(String link) {
    this.link = link;
  }

  @Override
  public void setModifiedBy(User modifiedBy) {
    // TODO Auto-generated method stub

  }

  public void setPhase(Phase phase) {
    this.phase = phase;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
