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

package org.cgiar.ccafs.marlo.rest.controller.v2.controllist;

import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.AccountTypesItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.AccountsItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.BusinessCategoryItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.EnvironmentalBenefitsItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.GovernanceTypeItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.RegionTypesItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.RegionsItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.ScienceGroupItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.TechnicalFieldItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.TechnologyDevelopmentStageItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.TypeOfInnovationItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.oneCGIAR.UnitsItem;
import org.cgiar.ccafs.marlo.rest.dto.AccountTypeDTO;
import org.cgiar.ccafs.marlo.rest.dto.AccountsDTO;
import org.cgiar.ccafs.marlo.rest.dto.BusinessCategoryDTO;
import org.cgiar.ccafs.marlo.rest.dto.EnvironmentalBenefitsDTO;
import org.cgiar.ccafs.marlo.rest.dto.GovernanceTypeDTO;
import org.cgiar.ccafs.marlo.rest.dto.InnovationTypeDTO;
import org.cgiar.ccafs.marlo.rest.dto.OneCGIARRegionTypeDTO;
import org.cgiar.ccafs.marlo.rest.dto.OneCGIARRegionsDTO;
import org.cgiar.ccafs.marlo.rest.dto.ScienceGroupDTO;
import org.cgiar.ccafs.marlo.rest.dto.TechnicalFieldDTO;
import org.cgiar.ccafs.marlo.rest.dto.TechnologyDevelopmentStageDTO;
import org.cgiar.ccafs.marlo.rest.dto.UnitDTO;
import org.cgiar.ccafs.marlo.rest.errors.NotFoundException;
import org.cgiar.ccafs.marlo.security.Permission;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@PropertySource("classpath:clarisa.properties")
@RestController
@Api(tags = "All CGIAR Control Lists")
@Named
public class OneCGIARControlList {

  private RegionsItem<OneCGIARControlList> regionsItem;
  private RegionTypesItem<OneCGIARControlList> regionTypesItem;
  private ScienceGroupItem<OneCGIARControlList> scienceGroupItem;
  private AccountsItem<OneCGIARControlList> accountsItem;
  private AccountTypesItem<OneCGIARControlList> accountTypesItem;
  private UnitsItem<OneCGIARControlList> unitsItem;
  private BusinessCategoryItem<OneCGIARControlList> businessCategoryItem;
  private TechnicalFieldItem<OneCGIARControlList> technicalFieldItem;
  private TypeOfInnovationItem<OneCGIARControlList> innovationTypeItem;
  private GovernanceTypeItem<OneCGIARControlList> governanceTypeItem;
  private EnvironmentalBenefitsItem<OneCGIARControlList> environmentalBenefitsItem;
  private TechnologyDevelopmentStageItem<OneCGIARControlList> technologyDeploymentStageItem;

  @Autowired
  private Environment env;

  @Inject
  public OneCGIARControlList(RegionsItem<OneCGIARControlList> regionsItem, UnitsItem<OneCGIARControlList> unitsItem,
    RegionTypesItem<OneCGIARControlList> regionTypesItem, ScienceGroupItem<OneCGIARControlList> scienceGroupItem,
    AccountTypesItem<OneCGIARControlList> accountTypesItem, AccountsItem<OneCGIARControlList> accountsItem,
    BusinessCategoryItem<OneCGIARControlList> businessCategoryItem,
    TechnicalFieldItem<OneCGIARControlList> technicalFieldItem,
    TypeOfInnovationItem<OneCGIARControlList> innovationTypeItem,
    GovernanceTypeItem<OneCGIARControlList> governanceTypeItem,
    EnvironmentalBenefitsItem<OneCGIARControlList> environmentalBenefitsItem,
    TechnologyDevelopmentStageItem<OneCGIARControlList> technologyDeploymentStageItem) {
    super();
    this.regionsItem = regionsItem;
    this.regionTypesItem = regionTypesItem;
    this.scienceGroupItem = scienceGroupItem;
    this.accountTypesItem = accountTypesItem;
    this.accountsItem = accountsItem;
    this.unitsItem = unitsItem;
    this.businessCategoryItem = businessCategoryItem;
    this.technicalFieldItem = technicalFieldItem;
    this.innovationTypeItem = innovationTypeItem;
    this.governanceTypeItem = governanceTypeItem;
    this.environmentalBenefitsItem = environmentalBenefitsItem;
    this.technologyDeploymentStageItem = technologyDeploymentStageItem;
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.Accounts.all.value}",
    response = AccountsDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/accounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AccountsDTO>> findAllAccounts() {
    try {
      ResponseEntity<List<AccountsDTO>> response = this.accountsItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.Accounts.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.AccountTypes.all.value}",
    response = AccountTypeDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/accountTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AccountTypeDTO>> findAllAccountTypes() {
    try {
      ResponseEntity<List<AccountTypeDTO>> response = this.accountTypesItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.AccountsType.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.AccountTypes.all.value}",
    response = BusinessCategoryDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/business-categories", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BusinessCategoryDTO>> findAllBussinesCategories() {
    try {
      ResponseEntity<List<BusinessCategoryDTO>> response = this.businessCategoryItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.BussinessCategory.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.Regions.all.value}",
    response = OneCGIARRegionsDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/OneCGIARRegions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OneCGIARRegionsDTO>> findAllCGIARRegions() {
    try {
      ResponseEntity<List<OneCGIARRegionsDTO>> response = this.regionsItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.Regions.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.RegionTypes.all.value}",
    response = OneCGIARRegionTypeDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/allCGIARRegionsTypes", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OneCGIARRegionTypeDTO>> findAllCGIARRegionTypes() {

    ResponseEntity<List<OneCGIARRegionTypeDTO>> response = this.regionTypesItem.findAll();
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404", this.env.getProperty("CGIARControlList.RegionTypes.code.404"));
    }
    return response;
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.Units.all.value}",
    response = UnitDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/governance-types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GovernanceTypeDTO>> findAllGovernanceTypes() {
    try {
      ResponseEntity<List<GovernanceTypeDTO>> response = this.governanceTypeItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.Units.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.Units.all.value}",
    response = InnovationTypeDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/type-of-innovations", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<InnovationTypeDTO>> findAllInnovationTypes() {
    try {
      ResponseEntity<List<InnovationTypeDTO>> response = this.innovationTypeItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.Units.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.ScienceGroup.all.value}",
    response = ScienceGroupDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/scienceGroups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ScienceGroupDTO>> findAllScienceGroup() {

    ResponseEntity<List<ScienceGroupDTO>> response = this.scienceGroupItem.getAll();
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404", this.env.getProperty("CGIARControlList.ScienceGroup.code.404"));
    }
    return response;
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.AccountTypes.all.value}",
    response = TechnicalFieldDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/technical-fields", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TechnicalFieldDTO>> findAllTechnicalFields() {
    try {
      ResponseEntity<List<TechnicalFieldDTO>> response = this.technicalFieldItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.TechnicalField.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.Units.all.value}",
    response = UnitDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/units", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<UnitDTO>> findAllUnits() {
    try {
      ResponseEntity<List<UnitDTO>> response = this.unitsItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.Units.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.Units.all.value}",
    response = EnvironmentalBenefitsDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/environmental-benefits", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<EnvironmentalBenefitsDTO>> findEnvironmentalBenefits() {
    try {
      ResponseEntity<List<EnvironmentalBenefitsDTO>> response = this.environmentalBenefitsItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.Units.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @ApiOperation(tags = {"All CGIAR Control Lists"}, value = "${CGIARControlList.Units.all.value}",
    response = TechnologyDevelopmentStageDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/technology-development-stages", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TechnologyDevelopmentStageDTO>> findTechnologyDevelopmentStage() {
    try {
      ResponseEntity<List<TechnologyDevelopmentStageDTO>> response = this.technologyDeploymentStageItem.getAll();
      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NotFoundException("404", this.env.getProperty("CGIARControlList.Units.code.404"));
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
