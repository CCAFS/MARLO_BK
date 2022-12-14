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
package org.cgiar.ccafs.marlo.data.model;
// Generated Jul 6, 2016 9:31:00 AM by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;

/**
 * SectionStatus generated by hbm2java
 */
public class Nexus extends MarloBaseEntity implements java.io.Serializable {

  private static final long serialVersionUID = -7527583315860965374L;

  @Expose
  private String name;

  private Set<ProjectExpectedStudyNexus> projectExpectedStudyNexus = new HashSet<ProjectExpectedStudyNexus>(0);

  private List<ProjectExpectedStudyNexus> studyNexus;

  public Nexus() {
  }

  public String getComposedName() {
    return this.getName();
  }

  public String getName() {
    return name;
  }

  public Set<ProjectExpectedStudyNexus> getProjectExpectedStudyNexus() {
    return projectExpectedStudyNexus;
  }

  public List<ProjectExpectedStudyNexus> getStudyNexus() {
    return studyNexus;
  }

  public List<ProjectExpectedStudyNexus> getStudyNexus(Phase phase) {
    return new ArrayList<>(this.getProjectExpectedStudyNexus().stream()
      .filter(pp -> pp.isActive() && pp.getPhase().equals(phase) && pp.getProjectExpectedStudy() != null
        && pp.getProjectExpectedStudy().getProjectExpectedStudyInfo(phase) != null)
      .collect(Collectors.toList()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setProjectExpectedStudyNexus(Set<ProjectExpectedStudyNexus> projectExpectedStudyNexus) {
    this.projectExpectedStudyNexus = projectExpectedStudyNexus;
  }

  public void setStudyNexus(List<ProjectExpectedStudyNexus> studyNexus) {
    this.studyNexus = studyNexus;
  }

}

