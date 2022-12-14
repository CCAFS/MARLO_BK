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

import org.cgiar.ccafs.marlo.data.manager.UserManager;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.keyExternalPartnership.KeyExternalPartnershipItem;
import org.cgiar.ccafs.marlo.rest.dto.KeyExternalPartnershipDTO;
import org.cgiar.ccafs.marlo.rest.dto.NewKeyExternalPartnershipDTO;
import org.cgiar.ccafs.marlo.rest.errors.NotFoundException;
import org.cgiar.ccafs.marlo.security.Permission;

import java.util.List;

import javax.validation.Valid;

import com.opensymphony.xwork2.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Table 8 - Key external partnerships")
public class KeyExternalPartnership {

  private static final Logger LOG = LoggerFactory.getLogger(KeyExternalPartnership.class);
  @Autowired
  private Environment env;
  private final UserManager userManager;
  private KeyExternalPartnershipItem<KeyExternalPartnershipDTO> keyExternalPartnershipItem;

  @Inject
  public KeyExternalPartnership(KeyExternalPartnershipItem<KeyExternalPartnershipDTO> keyExternalPartnershipItem,
    UserManager userManager) {
    this.userManager = userManager;
    this.keyExternalPartnershipItem = keyExternalPartnershipItem;
  }

  @ApiOperation(tags = {"Table 8 - Key external partnerships"},
    value = "${KeyExternalPartnership.externalpartnerships.POST.value}", response = KeyExternalPartnershipDTO.class)
  @RequiresPermissions(Permission.FULL_CREATE_REST_API_PERMISSION)
  @RequestMapping(value = "/{CGIAREntity}/keyexternalpartnership", method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Long> createKeyExternalPartnership(
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.POST.param.CGIAR}",
      required = true) @PathVariable String CGIAREntity,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.POST.param.policy}",
      required = true) @Valid @RequestBody NewKeyExternalPartnershipDTO newKeyExternalPartnershipDTO) {

    Long keyExternalPartnershipID = this.keyExternalPartnershipItem
      .createKeyExternalPartnership(newKeyExternalPartnershipDTO, CGIAREntity, this.getCurrentUser());

    ResponseEntity<Long> response = new ResponseEntity<Long>(keyExternalPartnershipID, HttpStatus.OK);
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404",
        this.env.getProperty("KeyExternalPartnership.externalpartnerships.GET.id.404"));
    }

    return response;
  }

  @ApiOperation(tags = {"Table 8 - Key external partnerships"},
    value = "${KeyExternalPartnership.externalpartnerships.DELETE.id.value}",
    response = KeyExternalPartnershipDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/{CGIAREntity}/keyexternalpartnership/{id}", method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<KeyExternalPartnershipDTO> deleteKeyExternalPartnershipById(
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.DELETE.id.param.CGIAR}",
      required = true) @PathVariable String CGIAREntity,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.DELETE.id.param.id}",
      required = true) @PathVariable Long id,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.DELETE.id.param.year}",
      required = true) @RequestParam Integer year,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.DELETE.id.param.phase}",
      required = true) @RequestParam String phase) {

    ResponseEntity<KeyExternalPartnershipDTO> response = this.keyExternalPartnershipItem
      .deleteKeyExternalPartnershipById(id, CGIAREntity, year, phase, this.getCurrentUser());
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404",
        this.env.getProperty("KeyExternalPartnership.externalpartnerships.DELETE.id.404"));
    }

    return response;
  }

  @ApiOperation(tags = {"Table 8 - Key external partnerships"},
    value = "${KeyExternalPartnership.externalpartnerships.GET.all.value}", response = KeyExternalPartnershipDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/{CGIAREntity}/keyexternalpartnership", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public List<KeyExternalPartnershipDTO> findAllProgressTowardsByGlobalUnit(
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.GET.all.param.CGIAR}",
      required = true) @PathVariable String CGIAREntity,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.GET.all.param.year}",
      required = true) @RequestParam Integer year,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.GET.all.param.phase}",
      required = true) @RequestParam String phase) {
    return this.keyExternalPartnershipItem.findAllKeyExternalPartnershipByGlobalUnit(CGIAREntity, year, phase,
      this.getCurrentUser());
  }

  @ApiOperation(tags = {"Table 8 - Key external partnerships"},
    value = "${KeyExternalPartnership.externalpartnerships.GET.id.value}", response = KeyExternalPartnershipDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/{CGIAREntity}/keyexternalpartnership/{id}", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<KeyExternalPartnershipDTO> findKeyExternalPartnershipById(
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.GET.id.param.CGIAR}",
      required = true) @PathVariable String CGIAREntity,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.GET.id.param.id}",
      required = true) @PathVariable Long id,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.GET.id.param.year}",
      required = true) @RequestParam Integer year,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.GET.id.param.phase}",
      required = true) @RequestParam String phase) {

    ResponseEntity<KeyExternalPartnershipDTO> response = this.keyExternalPartnershipItem
      .findKeyExternalPartnershipById(id, CGIAREntity, year, phase, this.getCurrentUser());
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404",
        this.env.getProperty("KeyExternalPartnership.externalpartnerships.GET.id.404"));
    }

    return response;
  }

  private User getCurrentUser() {
    Subject subject = SecurityUtils.getSubject();
    Long principal = (Long) subject.getPrincipal();
    User user = this.userManager.getUser(principal);
    return user;
  }

  @ApiOperation(tags = {"Table 8 - Key external partnerships"},
    value = "${KeyExternalPartnership.externalpartnerships.PUT.value}", response = KeyExternalPartnershipDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/{CGIAREntity}/keyexternalpartnership/{id}", method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Long> putKeyExternalPartnershipById(
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.PUT.param.CGIAR}",
      required = true) @PathVariable String CGIAREntity,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.PUT.param.id}",
      required = true) @PathVariable Long id,
    @ApiParam(value = "${KeyExternalPartnership.externalpartnerships.PUT.param.externalpartnership}",
      required = true) @Valid @RequestBody NewKeyExternalPartnershipDTO newKeyExternalPartnershipDTO) {

    Long keyExternalPartnershipID = this.keyExternalPartnershipItem.putKeyExternalPartnershipById(id, newKeyExternalPartnershipDTO,
      CGIAREntity, this.getCurrentUser());

    ResponseEntity<Long> response = new ResponseEntity<Long>(keyExternalPartnershipID, HttpStatus.OK);
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404",
        this.env.getProperty("KeyExternalPartnership.externalpartnerships.PUT.id.404"));
    }

    return response;
  }

}
