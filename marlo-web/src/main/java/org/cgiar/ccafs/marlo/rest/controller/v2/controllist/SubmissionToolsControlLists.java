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

import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.submissiontools.ActionAreasItem;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.submissiontools.ImpactAreasItem;
import org.cgiar.ccafs.marlo.rest.dto.ActionAreasDTO;
import org.cgiar.ccafs.marlo.rest.dto.ImpactAreasDTO;
import org.cgiar.ccafs.marlo.rest.errors.NotFoundException;
import org.cgiar.ccafs.marlo.security.Permission;

import java.util.List;

import javax.inject.Named;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Configuration
@PropertySource("classpath:clarisa.properties")
@RestController
@Api(tags = "Submission Tools Control Lists")
@Named
public class SubmissionToolsControlLists {


  private ActionAreasItem<SubmissionToolsControlLists> actionAreasItem;
  private ImpactAreasItem<SubmissionToolsControlLists> impactAreasItem;


  @Autowired
  private Environment env;


  public SubmissionToolsControlLists(ActionAreasItem<SubmissionToolsControlLists> actionAreasItem,
    ImpactAreasItem<SubmissionToolsControlLists> impactAreasItem) {
    super();
    this.actionAreasItem = actionAreasItem;
    this.impactAreasItem = impactAreasItem;
  }

  @ApiOperation(tags = {"Submission Tools Control Lists"},
    value = "${SubmissionToolsControlLists.action-areas.code.value}", response = ActionAreasDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/action-areas/{code}", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ActionAreasDTO>
    findActionAreaById(@ApiParam(value = "${SubmissionToolsControlLists.action-areas.code.param.code}",
      required = true) @PathVariable Long code) {

    ResponseEntity<ActionAreasDTO> response = this.actionAreasItem.findActionAreaById(code);
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404", this.env.getProperty("SubmissionToolsControlLists.action-areas.code.404"));
    }
    return response;
  }

  @ApiOperation(tags = {"Submission Tools Control Lists"},
    value = "${SubmissionToolsControlLists.impact-areas.code.value}", response = ImpactAreasDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/impact-areas/{code}", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ImpactAreasDTO>
    findImpactAreaById(@ApiParam(value = "${SubmissionToolsControlLists.impact-areas.code.param.code}",
      required = true) @PathVariable Long code) {

    ResponseEntity<ImpactAreasDTO> response = this.impactAreasItem.findActionAreaById(code);
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404", this.env.getProperty("SubmissionToolsControlLists.impact-areas.code.404"));
    }
    return response;
  }

  @ApiOperation(tags = {"Submission Tools Control Lists"},
    value = "${SubmissionToolsControlLists.action-areas.all.value}", response = ActionAreasDTO.class,
    responseContainer = "List")
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/action-areas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ActionAreasDTO> getAllActionAreas() {
    return this.actionAreasItem.getAllActionAreas();
  }

  @ApiOperation(tags = {"Submission Tools Control Lists"},
    value = "${SubmissionToolsControlLists.impact-areas.all.value}", response = ImpactAreasDTO.class,
    responseContainer = "List")
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/impact-areas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ImpactAreasDTO> getAllImpactAreas() {
    return this.impactAreasItem.getAllActionAreas();
  }


}
