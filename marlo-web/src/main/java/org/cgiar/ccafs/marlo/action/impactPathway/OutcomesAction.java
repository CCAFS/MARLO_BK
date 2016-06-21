/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.ccafs.marlo.action.impactPathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.CrpManager;
import org.cgiar.ccafs.marlo.data.manager.CrpProgramManager;
import org.cgiar.ccafs.marlo.data.manager.CrpProgramOutcomeManager;
import org.cgiar.ccafs.marlo.data.manager.SrfIdoManager;
import org.cgiar.ccafs.marlo.data.manager.SrfTargetUnitManager;
import org.cgiar.ccafs.marlo.data.model.Crp;
import org.cgiar.ccafs.marlo.data.model.CrpProgram;
import org.cgiar.ccafs.marlo.data.model.CrpProgramOutcome;
import org.cgiar.ccafs.marlo.data.model.ProgramType;
import org.cgiar.ccafs.marlo.data.model.SrfIdo;
import org.cgiar.ccafs.marlo.data.model.SrfTargetUnit;
import org.cgiar.ccafs.marlo.utils.APConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 * @author Christian Garcia - CIAT/CCAFS
 */
public class OutcomesAction extends BaseAction {

  private static final long serialVersionUID = -793652591843623397L;
  private Crp loggedCrp;
  private HashMap<Long, String> targetUnitList;
  private SrfTargetUnitManager srfTargetUnitManager;
  private CrpProgramOutcomeManager crpProgramOutcomeManager;
  private CrpManager crpManager;
  private CrpProgramManager crpProgramManager;

  private SrfIdoManager srfIdoManager;
  private List<CrpProgramOutcome> outcomes;
  private HashMap<Long, String> idoList;
  private List<CrpProgram> programs;
  private CrpProgram selectedProgram;

  private long crpProgramID;


  @Inject
  public OutcomesAction(APConfig config, SrfTargetUnitManager srfTargetUnitManager, SrfIdoManager srfIdoManager,
    CrpProgramOutcomeManager crpProgramOutcomeManager, CrpManager crpManager, CrpProgramManager crpProgramManager) {
    super(config);
    this.srfTargetUnitManager = srfTargetUnitManager;
    this.srfIdoManager = srfIdoManager;
    this.crpProgramOutcomeManager = crpProgramOutcomeManager;
    this.crpManager = crpManager;
    this.crpProgramManager = crpProgramManager;
  }


  public long getCrpProgramID() {
    return crpProgramID;
  }


  public HashMap<Long, String> getIdoList() {
    return idoList;
  }


  public Crp getLoggedCrp() {
    return loggedCrp;
  }

  public List<CrpProgramOutcome> getOutcomes() {
    return outcomes;
  }


  public List<CrpProgram> getPrograms() {
    return programs;
  }


  public CrpProgram getSelectedProgram() {
    return selectedProgram;
  }


  public HashMap<Long, String> getTargetUnitList() {
    return targetUnitList;
  }


  @Override
  public void prepare() throws Exception {
    loggedCrp = (Crp) this.getSession().get(APConstants.SESSION_CRP);
    outcomes = new ArrayList<CrpProgramOutcome>();

    List<CrpProgram> allPrograms = crpProgramManager.findAll();
    crpProgramID = -1;
    if (allPrograms != null) {

      this.programs = allPrograms.stream()
        .filter(c -> c.getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue() && c.isActive())
        .collect(Collectors.toList());

      try {
        crpProgramID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CRP_PROGRAM_ID)));
      } catch (Exception e) {
        if (!this.programs.isEmpty()) {
          crpProgramID = this.programs.get(0).getId();
        }
      }
    } else {
      programs = new ArrayList<>();
    }

    if (crpProgramID != -1) {
      selectedProgram = crpProgramManager.getCrpProgramById(crpProgramID);
    }
    outcomes.addAll(selectedProgram.getCrpProgramOutcomes());

    for (CrpProgramOutcome crpProgramOutcome : outcomes) {
      crpProgramOutcome.setMilestones(
        crpProgramOutcome.getCrpMilestones().stream().filter(c -> c.isActive()).collect(Collectors.toList()));
      crpProgramOutcome.setSubIdos(
        crpProgramOutcome.getCrpOutcomeSubIdos().stream().filter(c -> c.isActive()).collect(Collectors.toList()));
    }
    targetUnitList = new HashMap<>();
    if (srfTargetUnitManager.findAll() != null) {
      List<SrfTargetUnit> targetUnits =
        srfTargetUnitManager.findAll().stream().filter(c -> c.isActive()).collect(Collectors.toList());
      for (SrfTargetUnit srfTargetUnit : targetUnits) {
        targetUnitList.put(srfTargetUnit.getId(), srfTargetUnit.getAcronym());
      }
    }

    idoList = new HashMap<>();
    for (SrfIdo srfIdo : srfIdoManager.findAll().stream().filter(c -> c.isActive()).collect(Collectors.toList())) {
      idoList.put(srfIdo.getId(), srfIdo.getDescription());
    }

    if (this.isHttpPost()) {
      outcomes.clear();
    }

  }


  @Override
  public String save() {


    for (CrpProgramOutcome crpProgramOutcome : outcomes) {
      crpProgramOutcomeManager.saveCrpProgramOutcome(crpProgramOutcome);
    }
    return SUCCESS;


  }


  public void setCrpProgramID(long crpProgramID) {
    this.crpProgramID = crpProgramID;
  }


  public void setIdoList(HashMap<Long, String> idoList) {
    this.idoList = idoList;
  }


  public void setLoggedCrp(Crp loggedCrp) {
    this.loggedCrp = loggedCrp;
  }

  public void setOutcomes(List<CrpProgramOutcome> outcomes) {
    this.outcomes = outcomes;
  }

  public void setPrograms(List<CrpProgram> programs) {
    this.programs = programs;
  }


  public void setSelectedProgram(CrpProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }


  public void setTargetUnitList(HashMap<Long, String> targetUnitList) {
    this.targetUnitList = targetUnitList;
  }


}
