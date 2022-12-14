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

package org.cgiar.ccafs.marlo.data.model.anualreport.evidences;

import org.cgiar.ccafs.marlo.data.model.ProjectPolicy;

import java.io.Serializable;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */


public class ARPoliciesEvidence implements Serializable {


  private static final long serialVersionUID = -8349295005328781514L;

  private ProjectPolicy projectPolicy;
  private Boolean include;

  public Boolean getInclude() {
    return include;
  }


  public ProjectPolicy getProjectPolicy() {
    return projectPolicy;
  }


  public void setInclude(Boolean include) {
    this.include = include;
  }


  public void setProjectPolicy(ProjectPolicy projectPolicy) {
    this.projectPolicy = projectPolicy;
  }


}
