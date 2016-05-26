/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.marlo.data.model;
// Generated May 26, 2016 9:42:28 AM by Hibernate Tools 4.3.1.Final


import java.math.BigDecimal;

/**
 * SrfSloIndicatorTarget generated by hbm2java
 */
public class SrfSloIndicatorTarget implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -6059800120149718264L;
  private Long id;
  private SrfSloIndicator srfSloIndicator;
  private SrfTargetUnit srfTargetUnit;
  private BigDecimal value;
  private int year;

  public SrfSloIndicatorTarget() {
  }

  public SrfSloIndicatorTarget(SrfSloIndicator srfSloIndicators, SrfTargetUnit srfTargetUnits, BigDecimal value,
    int year) {
    this.srfSloIndicator = srfSloIndicators;
    this.srfTargetUnit = srfTargetUnits;
    this.value = value;
    this.year = year;
  }

  public Long getId() {
    return this.id;
  }

  public SrfSloIndicator getSrfSloIndicator() {
    return this.srfSloIndicator;
  }

  public SrfTargetUnit getSrfTargetUnit() {
    return this.srfTargetUnit;
  }

  public BigDecimal getValue() {
    return this.value;
  }

  public int getYear() {
    return this.year;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setSrfSloIndicator(SrfSloIndicator srfSloIndicators) {
    this.srfSloIndicator = srfSloIndicators;
  }

  public void setSrfTargetUnit(SrfTargetUnit srfTargetUnits) {
    this.srfTargetUnit = srfTargetUnits;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public void setYear(int year) {
    this.year = year;
  }


}

