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

package org.cgiar.ccafs.marlo.rest.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class FlagshipProgramDTO {

  @ApiModelProperty(notes = "The Generated Flagship/Program ID")
  private String code;

  @ApiModelProperty(notes = "The Flagship/program Name")
  @NotNull
  private String name;

  @ApiModelProperty(notes = "The Flagship/program Acronym")
  @NotNull
  private String acronym;

  @ApiModelProperty(notes = "The CRP/Platform of Flagship/program")
  @NotNull
  private CGIAREntityDTO cgiarEntityDTO;

  public String getAcronym() {
    return this.acronym;
  }

  public CGIAREntityDTO getCgiarEntityDTO() {
    return this.cgiarEntityDTO;
  }

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setCgiarEntityDTO(CGIAREntityDTO cgiarEntityDTO) {
    this.cgiarEntityDTO = cgiarEntityDTO;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }

}
