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

/**************
 * @author Diego Perez - CIAT/CCAFS
 **************/

package org.cgiar.ccafs.marlo.rest.dto;


public class InstitutionsRelatedDTO {

  private String source;
  private String clarisaId;
  private String clarisaInstitutionName;
  private String institutionName;
  private String institutionCode;


  public String getClarisaId() {
    return clarisaId;
  }


  public String getClarisaInstitutionName() {
    return clarisaInstitutionName;
  }

  public String getInstitutionCode() {
    return institutionCode;
  }

  public String getInstitutionName() {
    return institutionName;
  }

  public String getSource() {
    return source;
  }

  public void setClarisaId(String clarisaId) {
    this.clarisaId = clarisaId;
  }

  public void setClarisaInstitutionName(String clarisaInstitutionName) {
    this.clarisaInstitutionName = clarisaInstitutionName;
  }

  public void setInstitutionCode(String institutionCode) {
    this.institutionCode = institutionCode;
  }

  public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
  }

  public void setSource(String source) {
    this.source = source;
  }

}
