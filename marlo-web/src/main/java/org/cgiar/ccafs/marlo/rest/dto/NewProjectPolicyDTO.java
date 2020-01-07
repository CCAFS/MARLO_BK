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

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class NewProjectPolicyDTO {

  @ApiModelProperty(notes = "Phase year/section", position = 140)
  private PhaseDTO phase;

  @ApiModelProperty(notes = "Project Identifier", position = 140)
  private ProjectDTO project;

  @ApiModelProperty(notes = "Project Policy info")
  private ProjectPoliciesInfoDTO projectPoliciesInfo;


  @ApiModelProperty(notes = "Contributing CRP/Plataform List", position = 3)
  private List<CGIAREntityDTO> projectPolicyCrpDTO;


  @ApiModelProperty(notes = "CGIAR Sub-IDOs List", position = 1)
  private List<SrfSubIdoDTO> srfSubIdoList;


  @ApiModelProperty(notes = "Geographic Scope List", position = 4)
  private List<GeographicScopeDTO> geographicScopes;


  @ApiModelProperty(notes = "Crosscutting Markers List", position = 5)
  private List<ProjectPolicyCrosscuttingMarkersDTO> crossCuttingMarkers;


  @ApiModelProperty(notes = "Regions ", position = 90)
  private List<RegionDTO> regions;


  @ApiModelProperty(notes = "Countries", position = 100)
  private List<CountryDTO> countries;


  public List<CountryDTO> getCountries() {
    return countries;
  }


  public List<ProjectPolicyCrosscuttingMarkersDTO> getCrossCuttingMarkers() {
    return crossCuttingMarkers;
  }


  public List<GeographicScopeDTO> getGeographicScopes() {
    return geographicScopes;
  }


  public PhaseDTO getPhase() {
    return phase;
  }


  public ProjectDTO getProject() {
    return project;
  }


  public ProjectPoliciesInfoDTO getProjectPoliciesInfo() {
    return projectPoliciesInfo;
  }


  public List<CGIAREntityDTO> getProjectPolicyCrpDTO() {
    return projectPolicyCrpDTO;
  }


  public List<RegionDTO> getRegions() {
    return regions;
  }


  public List<SrfSubIdoDTO> getSrfSubIdoList() {
    return srfSubIdoList;
  }


  public void setCountries(List<CountryDTO> countries) {
    this.countries = countries;
  }


  public void setCrossCuttingMarkers(List<ProjectPolicyCrosscuttingMarkersDTO> crossCuttingMarkers) {
    this.crossCuttingMarkers = crossCuttingMarkers;
  }

  public void setGeographicScopes(List<GeographicScopeDTO> geographicScopes) {
    this.geographicScopes = geographicScopes;
  }

  public void setPhase(PhaseDTO phase) {
    this.phase = phase;
  }

  public void setProject(ProjectDTO project) {
    this.project = project;
  }

  public void setProjectPoliciesInfo(ProjectPoliciesInfoDTO projectPoliciesInfo) {
    this.projectPoliciesInfo = projectPoliciesInfo;
  }

  public void setProjectPolicyCrpDTO(List<CGIAREntityDTO> projectPolicyCrpDTO) {
    this.projectPolicyCrpDTO = projectPolicyCrpDTO;
  }


  public void setRegions(List<RegionDTO> regions) {
    this.regions = regions;
  }


  public void setSrfSubIdoList(List<SrfSubIdoDTO> srfSubIdoList) {
    this.srfSubIdoList = srfSubIdoList;
  }


}
