package org.cgiar.ccafs.marlo.data.model;
// Generated Apr 30, 2018 10:29:46 AM by Hibernate Tools 3.4.0.CR1


import com.google.gson.annotations.Expose;

/**
 * StudyType generated by hbm2java
 */
public class StudyType extends MarloBaseEntity implements java.io.Serializable {

  private static final long serialVersionUID = 5404311195258996896L;

  @Expose
  private String name;
  private int order;

  // AR 2020 added new fields description, key identifier, for, example. Used for type explanation for users
  private String description;
  private String keyIdentifier;
  private String forNarrative;
  private String example;


  public StudyType() {
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
    StudyType other = (StudyType) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }

  public String getDescription() {
    return description;
  }

  public String getExample() {
    return example;
  }

  public String getForNarrative() {
    return forNarrative;
  }

  public String getKeyIdentifier() {
    return keyIdentifier;
  }

  public String getName() {
    return name;
  }

  public int getOrder() {
    return order;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setExample(String example) {
    this.example = example;
  }

  public void setForNarrative(String forNarrative) {
    this.forNarrative = forNarrative;
  }

  public void setKeyIdentifier(String keyIdentifier) {
    this.keyIdentifier = keyIdentifier;
  }


  public void setName(String name) {
    this.name = name;
  }


  public void setOrder(int order) {
    this.order = order;
  }


}

