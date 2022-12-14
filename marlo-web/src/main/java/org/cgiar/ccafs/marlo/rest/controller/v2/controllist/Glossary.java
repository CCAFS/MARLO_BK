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

import org.cgiar.ccafs.marlo.rest.controller.v2.controllist.items.generallists.GlossaryItem;
import org.cgiar.ccafs.marlo.rest.dto.GlossaryDTO;
import org.cgiar.ccafs.marlo.rest.errors.NotFoundException;
import org.cgiar.ccafs.marlo.security.Permission;

import java.util.List;

import javax.inject.Inject;
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
@Api(tags = "General Control Lists")
@Named
public class Glossary {

  private GlossaryItem<Glossary> glossaryItem;
  @Autowired
  private Environment env;

  @Inject
  public Glossary(GlossaryItem<Glossary> glossaryItem) {
    super();
    this.glossaryItem = glossaryItem;
  }

  @ApiOperation(tags = {"General Control Lists"}, value = "${CGIARControlList.Glossary.value}",
    response = GlossaryDTO.class)
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/glossaryById/{code}", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GlossaryDTO> findImpactAreaById(
    @ApiParam(value = "${CGIARControlList.Glossary.param.code}", required = true) @PathVariable Long code) {

    ResponseEntity<GlossaryDTO> response = this.glossaryItem.findGlossaryById(code);
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new NotFoundException("404", this.env.getProperty("CGIARControlList.Glossary.code.404"));
    }
    return response;
  }

  @ApiOperation(tags = {"General Control Lists"}, value = "${CGIARControlList.Glossary.all.value}",
    response = GlossaryDTO.class, responseContainer = "List")
  @RequiresPermissions(Permission.FULL_READ_REST_API_PERMISSION)
  @RequestMapping(value = "/glossary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<GlossaryDTO> getAllGlossary() {
    return this.glossaryItem.getAllGlossary();
  }

}
