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

import io.swagger.annotations.ApiModelProperty;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

public class PolicyMilestoneDTO {

  @ApiModelProperty(notes = "Milestone Identifier", position = 1)
  private MilestoneDTO crpMilestone;

  @ApiModelProperty(notes = "Milestone as primary", position = 1)
  private Boolean primary;


  public MilestoneDTO getCrpMilestone() {
    return crpMilestone;
  }

  public Boolean getPrimary() {
    return primary;
  }


  public void setCrpMilestone(MilestoneDTO crpMilestone) {
    this.crpMilestone = crpMilestone;
  }

  public void setPrimary(Boolean primary) {
    this.primary = primary;
  }
}
