package org.cgiar.ccafs.marlo.data.model;
// Generated May 24, 2018 5:28:55 PM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ReportSynthesisFinancialSummaryBudget generated by hbm2java
 */
public class ReportSynthesisCrpFinancialReport extends MarloAuditableEntity implements java.io.Serializable, IAuditLog {

  /**
   * 
   */
  private static final long serialVersionUID = 2643113497655274570L;

  @Expose
  private ReportSynthesis reportSynthesis;

  @Expose
  private String financialStatusNarrative;

  @Expose
  private Double personnel2020Forecast;

  @Expose
  private Double personnel2021Budget;

  @Expose
  private String personnelComments;

  @Expose
  private Double consultancy2020Forecast;

  @Expose
  private Double consultancy2021Budget;

  @Expose
  private String consultancyComments;

  @Expose
  private Double travel2020Forecast;

  @Expose
  private Double travel2021Budget;

  @Expose
  private String travelComments;

  @Expose
  private Double operation2020Forecast;

  @Expose
  private Double operation2021Budget;

  @Expose
  private String operationComments;

  @Expose
  private Double collaboratorPartnerships2020Forecast;

  @Expose
  private Double collaboratorPartnerships2021Budget;

  @Expose
  private String collaboratorPartnershipsComments;

  @Expose
  private Double capitalEquipment2020Forecast;

  @Expose
  private Double capitalEquipment2021Budget;

  @Expose
  private String capitalEquipmentComments;

  @Expose
  private Double closeout2020Forecast;

  @Expose
  private Double closeout2021Budget;

  @Expose
  private String closeoutComments;

  @Expose
  private Double crpTotal2020Forecast;

  @Expose
  private Double crpTotal2021Budget;

  @Expose
  private String crpTotalComments;


  public ReportSynthesisCrpFinancialReport() {
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
    ReportSynthesisCrpFinancialReport other = (ReportSynthesisCrpFinancialReport) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }


  public Double getCapitalEquipment2020Forecast() {
    return capitalEquipment2020Forecast;
  }

  public Double getCapitalEquipment2021Budget() {
    return capitalEquipment2021Budget;
  }

  public String getCapitalEquipmentComments() {
    return capitalEquipmentComments;
  }

  public Double getCloseout2020Forecast() {
    return closeout2020Forecast;
  }

  public Double getCloseout2021Budget() {
    return closeout2021Budget;
  }

  public String getCloseoutComments() {
    return closeoutComments;
  }

  public Double getCollaboratorPartnerships2020Forecast() {
    return collaboratorPartnerships2020Forecast;
  }

  public Double getCollaboratorPartnerships2021Budget() {
    return collaboratorPartnerships2021Budget;
  }

  public String getCollaboratorPartnershipsComments() {
    return collaboratorPartnershipsComments;
  }

  public Double getConsultancy2020Forecast() {
    return consultancy2020Forecast;
  }

  public Double getConsultancy2021Budget() {
    return consultancy2021Budget;
  }

  public String getConsultancyComments() {
    return consultancyComments;
  }

  public Double getCrpTotal2020Forecast() {
    return crpTotal2020Forecast;
  }

  public Double getCrpTotal2021Budget() {
    return crpTotal2021Budget;
  }

  public String getCrpTotalComments() {
    return crpTotalComments;
  }

  public String getFinancialStatusNarrative() {
    return financialStatusNarrative;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }


  public Double getOperation2020Forecast() {
    return operation2020Forecast;
  }

  public Double getOperation2021Budget() {
    return operation2021Budget;
  }

  public String getOperationComments() {
    return operationComments;
  }

  public Double getPersonnel2020Forecast() {
    return personnel2020Forecast;
  }

  public Double getPersonnel2021Budget() {
    return personnel2021Budget;
  }

  public String getPersonnelComments() {
    return personnelComments;
  }

  public ReportSynthesis getReportSynthesis() {
    return reportSynthesis;
  }

  public Double getTravel2020Forecast() {
    return travel2020Forecast;
  }

  public Double getTravel2021Budget() {
    return travel2021Budget;
  }

  public String getTravelComments() {
    return travelComments;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }


  public void setCapitalEquipment2020Forecast(Double capitalEquipment2020Forecast) {
    this.capitalEquipment2020Forecast = capitalEquipment2020Forecast;
  }

  public void setCapitalEquipment2021Budget(Double capitalEquipment2021Budget) {
    this.capitalEquipment2021Budget = capitalEquipment2021Budget;
  }

  public void setCapitalEquipmentComments(String capitalEquipmentComments) {
    this.capitalEquipmentComments = capitalEquipmentComments;
  }

  public void setCloseout2020Forecast(Double closeout2020Forecast) {
    this.closeout2020Forecast = closeout2020Forecast;
  }

  public void setCloseout2021Budget(Double closeout2021Budget) {
    this.closeout2021Budget = closeout2021Budget;
  }

  public void setCloseoutComments(String closeoutComments) {
    this.closeoutComments = closeoutComments;
  }

  public void setCollaboratorPartnerships2020Forecast(Double collaboratorPartnerships2020Forecast) {
    this.collaboratorPartnerships2020Forecast = collaboratorPartnerships2020Forecast;
  }

  public void setCollaboratorPartnerships2021Budget(Double collaboratorPartnerships2021Budget) {
    this.collaboratorPartnerships2021Budget = collaboratorPartnerships2021Budget;
  }

  public void setCollaboratorPartnershipsComments(String collaboratorPartnershipsComments) {
    this.collaboratorPartnershipsComments = collaboratorPartnershipsComments;
  }

  public void setConsultancy2020Forecast(Double consultancy2020Forecast) {
    this.consultancy2020Forecast = consultancy2020Forecast;
  }

  public void setConsultancy2021Budget(Double consultancy2021Budget) {
    this.consultancy2021Budget = consultancy2021Budget;
  }

  public void setConsultancyComments(String consultancyComments) {
    this.consultancyComments = consultancyComments;
  }

  public void setCrpTotal2020Forecast(Double crpTotal2020Forecast) {
    this.crpTotal2020Forecast = crpTotal2020Forecast;
  }

  public void setCrpTotal2021Budget(Double crpTotal2021Budget) {
    this.crpTotal2021Budget = crpTotal2021Budget;
  }

  public void setCrpTotalComments(String crpTotalComments) {
    this.crpTotalComments = crpTotalComments;
  }

  public void setFinancialStatusNarrative(String financialStatusNarrative) {
    this.financialStatusNarrative = financialStatusNarrative;
  }

  public void setOperation2020Forecast(Double operation2020Forecast) {
    this.operation2020Forecast = operation2020Forecast;
  }

  public void setOperation2021Budget(Double operation2021Budget) {
    this.operation2021Budget = operation2021Budget;
  }

  public void setOperationComments(String operationComments) {
    this.operationComments = operationComments;
  }

  public void setPersonnel2020Forecast(Double personnel2020Forecast) {
    this.personnel2020Forecast = personnel2020Forecast;
  }

  public void setPersonnel2021Budget(Double personnel2021Budget) {
    this.personnel2021Budget = personnel2021Budget;
  }

  public void setPersonnelComments(String personnelComments) {
    this.personnelComments = personnelComments;
  }

  public void setReportSynthesis(ReportSynthesis reportSynthesis) {
    this.reportSynthesis = reportSynthesis;
  }

  public void setTravel2020Forecast(Double travel2020Forecast) {
    this.travel2020Forecast = travel2020Forecast;
  }

  public void setTravel2021Budget(Double travel2021Budget) {
    this.travel2021Budget = travel2021Budget;
  }

  public void setTravelComments(String travelComments) {
    this.travelComments = travelComments;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("reportSynthesis", this.reportSynthesis)
      .append("financialStatusNarrative", this.financialStatusNarrative)
      .append("personnel2020Forecast", this.personnel2020Forecast)
      .append("personnel2021Budget", this.personnel2021Budget).append("personnelComments", this.personnelComments)
      .append("consultancy2020Forecast", this.consultancy2020Forecast)
      .append("consultancy2021Budget", this.consultancy2021Budget)
      .append("consultancyComments", this.consultancyComments).append("travel2020Forecast", this.travel2020Forecast)
      .append("travel2021Budget", this.travel2021Budget).append("travelComments", this.travelComments)
      .append("operation2020Forecast", this.operation2020Forecast)
      .append("operation2021Budget", this.operation2021Budget).append("operationComments", this.operationComments)
      .append("collaboratorPartnerships2020Forecast", this.collaboratorPartnerships2020Forecast)
      .append("collaboratorPartnerships2021Budget", this.collaboratorPartnerships2021Budget)
      .append("collaboratorPartnershipsComments", this.collaboratorPartnershipsComments)
      .append("capitalEquipment2020Forecast", this.capitalEquipment2020Forecast)
      .append("capitalEquipment2021Budget", this.capitalEquipment2021Budget)
      .append("capitalEquipmentComments", this.capitalEquipmentComments)
      .append("closeout2020Forecast", this.closeout2020Forecast).append("closeout2021Budget", this.closeout2021Budget)
      .append("closeoutComments", this.closeoutComments).append("crpTotal2020Forecast", this.crpTotal2020Forecast)
      .append("crpTotal2021Budget", this.crpTotal2021Budget).append("crpTotalComments", this.crpTotalComments).build();
  }

}