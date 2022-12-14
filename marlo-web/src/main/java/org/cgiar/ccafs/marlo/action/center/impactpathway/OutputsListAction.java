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

package org.cgiar.ccafs.marlo.action.center.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.CenterOutputsOutcomeManager;
import org.cgiar.ccafs.marlo.data.manager.CrpProgramManager;
import org.cgiar.ccafs.marlo.data.manager.GlobalUnitManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterAreaManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterOutputManager;
import org.cgiar.ccafs.marlo.data.manager.ICenterSectionStatusManager;
import org.cgiar.ccafs.marlo.data.manager.UserManager;
import org.cgiar.ccafs.marlo.data.model.CenterArea;
import org.cgiar.ccafs.marlo.data.model.CenterOutcome;
import org.cgiar.ccafs.marlo.data.model.CenterOutput;
import org.cgiar.ccafs.marlo.data.model.CenterOutputsOutcome;
import org.cgiar.ccafs.marlo.data.model.CenterSectionStatus;
import org.cgiar.ccafs.marlo.data.model.CenterTopic;
import org.cgiar.ccafs.marlo.data.model.CrpProgram;
import org.cgiar.ccafs.marlo.data.model.CrpProgramLeader;
import org.cgiar.ccafs.marlo.data.model.GlobalUnit;
import org.cgiar.ccafs.marlo.data.model.ProgramType;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.utils.APConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.Parameter;

/**
 * @author Hermes Jim??nez - CIAT/CCAFS
 */
public class OutputsListAction extends BaseAction {


  private static final long serialVersionUID = 6922866669964604405L;


  // GlobalUnit Manager
  private GlobalUnitManager centerService;

  private CrpProgramManager programService;


  private CenterOutputsOutcomeManager centerOutputsOutcomeManager;


  private ICenterAreaManager researchAreaService;


  private UserManager userService;


  private ICenterOutputManager outputService;


  private ICenterSectionStatusManager sectionStatusService;


  private List<CenterArea> researchAreas;


  private List<CrpProgram> researchPrograms;

  private List<CenterOutcome> outcomes;
  private List<CenterTopic> researchTopics;
  private List<CenterOutput> outputs;
  private CrpProgram selectedProgram;
  private CenterArea selectedResearchArea;
  private GlobalUnit loggedCenter;
  private long areaID;
  private long outputID;


  private String justification;
  private long crpProgramID;

  @Inject
  public OutputsListAction(APConfig config, GlobalUnitManager centerService, CrpProgramManager programService,
    ICenterAreaManager researchAreaService, UserManager userService, ICenterOutputManager outputService,
    ICenterSectionStatusManager sectionStatusService, CenterOutputsOutcomeManager centerOutputsOutcomeManager) {
    super(config);
    this.centerService = centerService;
    this.programService = programService;
    this.researchAreaService = researchAreaService;
    this.userService = userService;
    this.outputService = outputService;
    this.sectionStatusService = sectionStatusService;
    this.centerOutputsOutcomeManager = centerOutputsOutcomeManager;
  }


  @Override
  public String add() {
    CenterOutput output = new CenterOutput();
    output.setDateAdded(new Date());
    output = outputService.saveResearchOutput(output);
    output.setCenterProgram(selectedProgram);
    outputID = output.getId();

    if (outputID > 0) {
      return SUCCESS;
    } else {
      return INPUT;
    }
  }

  @Override
  public String delete() {
    // Map<String, Object> parameters = this.getParameters();
    Map<String, Parameter> parameters = this.getParameters();
    // outputID = Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.OUTPUT_ID))[0]));
    outputID = Long.parseLong(StringUtils.trim(parameters.get(APConstants.OUTPUT_ID).getMultipleValues()[0]));

    CenterOutput output = outputService.getResearchOutputById(outputID);

    crpProgramID = output.getCenterProgram().getId();

    if (output != null) {
      output.setModificationJustification(this.getJustification() == null ? "Output deleted" : this.getJustification());

      List<CenterOutputsOutcome> centerOutputsOutcomes = new ArrayList<>(
        output.getCenterOutputsOutcomes().stream().filter(co -> co.isActive()).collect(Collectors.toList()));

      for (CenterOutputsOutcome centerOutputsOutcome : centerOutputsOutcomes) {
        centerOutputsOutcomeManager.deleteCenterOutputsOutcome(centerOutputsOutcome.getId());
      }

      CenterSectionStatus status = sectionStatusService.getSectionStatusByOutput(crpProgramID, output.getId(),
        "outputsList", this.getActualPhase().getYear());

      if (status != null) {
        sectionStatusService.deleteSectionStatus(status.getId());
      }

      outputService.saveResearchOutput(output);

      outputService.deleteResearchOutput(output.getId());


      this.addActionMessage("message:" + this.getText("deleting.success"));
    }

    return SUCCESS;
  }

  public long getAreaID() {
    return areaID;
  }

  public long getCrpProgramID() {
    return crpProgramID;
  }

  @Override
  public String getJustification() {
    return justification;
  }


  public GlobalUnit getLoggedCenter() {
    return loggedCenter;
  }

  // public List<CenterOutput> allProgramOutput() {
  //
  // outcomes = new ArrayList<>();
  // List<CenterOutput> ouList = new ArrayList<>();
  //
  // List<CenterTopic> researchTopics = new ArrayList<>(
  // selectedProgram.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
  //
  // for (CenterTopic researchTopic : researchTopics) {
  // List<CenterOutcome> researchOutcomes = new ArrayList<>(
  // researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
  //
  // for (CenterOutcome researchOutcome : researchOutcomes) {
  // outcomes.add(researchOutcome);
  // List<CenterOutput> researchOutputs = new ArrayList<>(
  // researchOutcome.getResearchOutputs().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
  //
  // for (CenterOutput researchOutput : researchOutputs) {
  // ouList.add(researchOutput);
  // }
  // }
  // }
  //
  // return ouList;
  //
  // }

  public List<CenterOutcome> getOutcomes() {
    return outcomes;
  }

  public long getOutputID() {
    return outputID;
  }

  public List<CenterOutput> getOutputs() {
    return outputs;
  }

  public List<CenterArea> getResearchAreas() {
    return researchAreas;
  }


  public List<CrpProgram> getResearchPrograms() {
    return researchPrograms;
  }

  public List<CenterTopic> getResearchTopics() {
    return researchTopics;
  }

  public CrpProgram getSelectedProgram() {
    return selectedProgram;
  }

  public CenterArea getSelectedResearchArea() {
    return selectedResearchArea;
  }


  @Override
  public void prepare() throws Exception {
    areaID = -1;
    crpProgramID = -1;


    loggedCenter = (GlobalUnit) this.getSession().get(APConstants.SESSION_CRP);
    loggedCenter = centerService.getGlobalUnitById(loggedCenter.getId());

    researchAreas =
      new ArrayList<>(loggedCenter.getCenterAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    Collections.sort(researchAreas, (ra1, ra2) -> ra1.getId().compareTo(ra2.getId()));

    if (researchAreas != null) {

      try {
        areaID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_AREA_ID)));
      } catch (Exception e) {
        try {
          crpProgramID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CRP_PROGRAM_ID)));
        } catch (Exception ex) {
          User user = userService.getUser(this.getCurrentUser().getId());
          // TODO crpProgram
          // Check if the User is an Area Leader
          List<CrpProgramLeader> userAreaLeads =
            new ArrayList<>(user.getCrpProgramLeaders().stream()
              .filter(rl -> rl.isActive()
                && rl.getCrpProgram().getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue()
                && rl.getCrpProgram().getResearchArea() != null)
              .collect(Collectors.toList()));
          if (!userAreaLeads.isEmpty()) {
            areaID = userAreaLeads.get(0).getCrpProgram().getResearchArea().getId();
          } else {
            // Check if the User is a Program Leader
            List<CrpProgramLeader> userProgramLeads = new ArrayList<>(user.getCrpProgramLeaders().stream()
              .filter(rl -> rl.isActive()
                && rl.getCrpProgram().getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue()
                && rl.getCrpProgram().getResearchArea() != null)
              .collect(Collectors.toList()));
            if (!userProgramLeads.isEmpty()) {
              crpProgramID = userProgramLeads.get(0).getCrpProgram().getId();
            } else {
              List<CrpProgram> rps = researchAreas.get(0).getResearchPrograms().stream().filter(r -> r.isActive())
                .collect(Collectors.toList());
              Collections.sort(rps, (rp1, rp2) -> rp1.getId().compareTo(rp2.getId()));
              CrpProgram rp = rps.get(0);
              crpProgramID = rp.getId();
              areaID = rp.getResearchArea().getId();
            }
          }
        }
      }

      if (areaID != -1 && crpProgramID == -1) {
        selectedResearchArea = researchAreaService.find(areaID);
        researchPrograms = new ArrayList<>(
          selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));
        Collections.sort(researchPrograms, (rp1, rp2) -> rp1.getId().compareTo(rp2.getId()));
        if (researchPrograms != null) {
          try {
            crpProgramID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CRP_PROGRAM_ID)));
          } catch (Exception e) {
            User user = userService.getUser(this.getCurrentUser().getId());

            List<CrpProgramLeader> userLeads = new ArrayList<>(user.getCrpProgramLeaders().stream()
              .filter(rl -> rl.isActive()
                && rl.getCrpProgram().getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue()
                && rl.getCrpProgram().getResearchArea() != null)
              .collect(Collectors.toList()));

            if (!userLeads.isEmpty()) {
              crpProgramID = userLeads.get(0).getCrpProgram().getId();
            } else {
              if (!researchPrograms.isEmpty()) {
                crpProgramID = researchPrograms.get(0).getId();
              }
            }
          }
        }

        if (crpProgramID != -1) {
          selectedProgram = programService.getCrpProgramById(crpProgramID);
        }
      } else {
        if (crpProgramID != -1) {
          selectedProgram = programService.getCrpProgramById(crpProgramID);
          areaID = selectedProgram.getResearchArea().getId();
          selectedResearchArea = researchAreaService.find(areaID);
        }
      }

      outputs = new ArrayList<>();
      if (selectedProgram.getCenterOutputs() != null) {
        outputs = selectedProgram.getCenterOutputs().stream().filter(co -> co.isActive()).collect(Collectors.toList());
      }

      // if (selectedProgram.getResearchTopics() != null) {
      //
      // researchTopics = new ArrayList<>(selectedProgram.getResearchTopics().stream()
      // .filter(rt -> rt.isActive() && rt.getResearchTopic().trim().length() > 0).collect(Collectors.toList()));
      //
      // Collections.sort(researchTopics, (ra1, ra2) -> ra1.getOrder().compareTo(ra2.getOrder()));
      // try {
      //
      // topicID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.RESEARCH_TOPIC_ID)));
      //
      // if (topicID == -1) {
      // outputs = this.allProgramOutput();
      // } else {
      // selectedResearchTopic = researchTopicService.getResearchTopicById(topicID);
      // if (selectedResearchTopic != null) {
      // if (selectedResearchTopic.getResearchOutcomes() != null) {
      // outcomes = selectedResearchTopic.getResearchOutcomes().stream()
      // .filter(ro -> ro.isActive() && ro.getDescription() != null && ro.getTargetYear() != -1)
      // .collect(Collectors.toList());
      // if (!outcomes.isEmpty()) {
      // selectedResearchOutcome = outcomes.get(0);
      // }
      // }
      // }
      // }
      //
      //
      // } catch (Exception e) {
      //
      // try {
      // outcomeID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.OUTCOME_ID)));
      // selectedResearchOutcome = outcomeService.getResearchOutcomeById(outcomeID);
      // selectedResearchTopic = selectedResearchOutcome.getResearchTopic();
      // outcomes = selectedResearchTopic.getResearchOutcomes().stream()
      // .filter(ro -> ro.isActive() && ro.getDescription() != null && ro.getTargetYear() != -1)
      // .collect(Collectors.toList());
      // } catch (Exception ex) {
      //
      // if (!researchTopics.isEmpty()) {
      // outputs = this.allProgramOutput();
      // }
      //
      // // if (selectedResearchTopic != null) {
      // // if (selectedResearchTopic.getResearchOutcomes() != null) {
      // // outcomes = selectedResearchTopic.getResearchOutcomes().stream()
      // // .filter(ro -> ro.isActive() && ro.getDescription() != null && ro.getTargetYear() != -1)
      // // .collect(Collectors.toList());
      // //
      // // if (!outcomes.isEmpty()) {
      // // selectedResearchOutcome = outcomes.get(0);
      // // }
      // //
      // // }
      // // }
      // }
      // }
      //
      // if (selectedResearchOutcome != null) {
      // if (selectedResearchOutcome.getResearchOutputs() != null) {
      // outputs = selectedResearchOutcome.getResearchOutputs().stream().filter(ro -> ro.isActive())
      // .collect(Collectors.toList());
      // }
      // }
      //
      //
      // }
    }
  }


  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }


  public void setCrpProgramID(long crpProgramID) {
    this.crpProgramID = crpProgramID;
  }


  @Override
  public void setJustification(String justification) {
    this.justification = justification;
  }

  public void setLoggedCenter(GlobalUnit loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setOutcomes(List<CenterOutcome> outcomes) {
    this.outcomes = outcomes;
  }


  public void setOutputID(long outputID) {
    this.outputID = outputID;
  }

  public void setOutputs(List<CenterOutput> outputs) {
    this.outputs = outputs;
  }

  public void setResearchAreas(List<CenterArea> researchAreas) {
    this.researchAreas = researchAreas;
  }

  public void setResearchPrograms(List<CrpProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

  public void setResearchTopics(List<CenterTopic> researchTopics) {
    this.researchTopics = researchTopics;
  }


  public void setSelectedProgram(CrpProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }


  public void setSelectedResearchArea(CenterArea selectedResearchArea) {
    this.selectedResearchArea = selectedResearchArea;
  }


}
