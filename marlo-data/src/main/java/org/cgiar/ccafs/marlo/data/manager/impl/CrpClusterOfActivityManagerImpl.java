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
package org.cgiar.ccafs.marlo.data.manager.impl;

import org.cgiar.ccafs.marlo.data.dao.CrpClusterActivityLeaderDAO;
import org.cgiar.ccafs.marlo.data.dao.CrpClusterKeyOutputDAO;
import org.cgiar.ccafs.marlo.data.dao.CrpClusterKeyOutputOutcomeDAO;
import org.cgiar.ccafs.marlo.data.dao.CrpClusterOfActivityDAO;
import org.cgiar.ccafs.marlo.data.dao.CrpProgramOutcomeDAO;
import org.cgiar.ccafs.marlo.data.dao.PhaseDAO;
import org.cgiar.ccafs.marlo.data.dto.ImpactPathwaysClusterDTO;
import org.cgiar.ccafs.marlo.data.manager.CrpClusterOfActivityManager;
import org.cgiar.ccafs.marlo.data.model.CrpClusterActivityLeader;
import org.cgiar.ccafs.marlo.data.model.CrpClusterKeyOutput;
import org.cgiar.ccafs.marlo.data.model.CrpClusterKeyOutputOutcome;
import org.cgiar.ccafs.marlo.data.model.CrpClusterOfActivity;
import org.cgiar.ccafs.marlo.data.model.CrpProgram;
import org.cgiar.ccafs.marlo.data.model.CrpProgramOutcome;
import org.cgiar.ccafs.marlo.data.model.Phase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Christian Garcia
 */
@Named
public class CrpClusterOfActivityManagerImpl implements CrpClusterOfActivityManager {

  private CrpClusterOfActivityDAO crpClusterOfActivityDAO;
  // Managers
  private PhaseDAO phaseMySQLDAO;
  private CrpClusterActivityLeaderDAO crpClusterActivityLeaderDAO;
  private CrpClusterKeyOutputDAO crpClusterKeyOutputDAO;
  private CrpProgramOutcomeDAO crpProgramOutcomeDAO;

  private CrpClusterKeyOutputOutcomeDAO crpClusterKeyOutputOutcomeDAO;

  @Inject
  public CrpClusterOfActivityManagerImpl(CrpClusterOfActivityDAO crpClusterOfActivityDAO, PhaseDAO phaseMySQLDAO,
    CrpClusterActivityLeaderDAO crpClusterActivityLeaderDAO, CrpClusterKeyOutputDAO crpClusterKeyOutputDAO,
    CrpProgramOutcomeDAO crpProgramOutcomeDAO, CrpClusterKeyOutputOutcomeDAO crpClusterKeyOutputOutcomeDAO) {
    this.crpClusterOfActivityDAO = crpClusterOfActivityDAO;
    this.phaseMySQLDAO = phaseMySQLDAO;
    this.crpClusterActivityLeaderDAO = crpClusterActivityLeaderDAO;
    this.crpClusterKeyOutputDAO = crpClusterKeyOutputDAO;
    this.crpClusterKeyOutputOutcomeDAO = crpClusterKeyOutputOutcomeDAO;
    this.crpProgramOutcomeDAO = crpProgramOutcomeDAO;
  }

  /**
   * clone or update the outcome for next phases
   * 
   * @param next the next phase to clone
   * @param crpProgramID the program id we are working
   * @param crpCluster the CrpClusterOfActivity to clone
   */
  private void addCrpClusterPhase(Phase next, long crpProgramID, CrpClusterOfActivity crpCluster) {
    Phase phase = phaseMySQLDAO.find(next.getId());
    List<CrpClusterOfActivity> clusters =
      phase.getClusters().stream().filter(c -> c.isActive() && c.getCrpProgram().getId().longValue() == crpProgramID
        && c.getIdentifier().equals(crpCluster.getIdentifier())).collect(Collectors.toList());
    if (clusters.isEmpty()) {
      CrpClusterOfActivity clusterAdd = new CrpClusterOfActivity();
      clusterAdd.setPhase(phase);
      clusterAdd.setCrpProgram(crpCluster.getCrpProgram());
      clusterAdd.setDescription(crpCluster.getDescription());
      clusterAdd.setIdentifier(crpCluster.getIdentifier());
      clusterAdd = crpClusterOfActivityDAO.save(clusterAdd);
      this.addLeaders(crpCluster, clusterAdd);
      this.addCrpKeyOutputs(crpCluster, clusterAdd);
    } else {

      for (CrpClusterOfActivity clusterPrev : clusters) {
        clusterPrev.setDescription(crpCluster.getDescription());
        clusterPrev.setIdentifier(crpCluster.getIdentifier());
        clusterPrev = crpClusterOfActivityDAO.save(clusterPrev);
        this.updateClusterLeaders(clusterPrev, crpCluster);
        this.updateKeyOutputs(clusterPrev, crpCluster);
      }
    }

    if (phase.getNext() != null) {
      this.addCrpClusterPhase(phase.getNext(), crpProgramID, crpCluster);
    }

  }

  /**
   * clone the key ouputs
   * 
   * @param crpClusterOfActivity clusterActivity original
   * @param crpClusterOfActivityAdd clusterActivity new
   */

  private void addCrpKeyOutputs(CrpClusterOfActivity crpClusterOfActivity,
    CrpClusterOfActivity crpClusterOfActivityAdd) {

    if (crpClusterOfActivity.getKeyOutputs() != null) {
      for (CrpClusterKeyOutput crpClusteKeyOutput : crpClusterOfActivity.getKeyOutputs()) {
        CrpClusterKeyOutput crpClusteKeyOutputAdd = new CrpClusterKeyOutput();
        crpClusteKeyOutputAdd.setContribution(crpClusteKeyOutput.getContribution());
        crpClusteKeyOutputAdd.setKeyOutput(crpClusteKeyOutput.getKeyOutput());
        crpClusteKeyOutputAdd.setComposeID(crpClusteKeyOutput.getComposeID());
        crpClusteKeyOutputAdd.setCrpClusterOfActivity(crpClusterOfActivityAdd);
        crpClusterKeyOutputDAO.save(crpClusteKeyOutputAdd);
        if (crpClusteKeyOutput.getComposeID() == null) {
          crpClusteKeyOutput.setComposeID(crpClusterOfActivity.getIdentifier() + "-" + crpClusteKeyOutputAdd.getId());
          crpClusteKeyOutputAdd
            .setComposeID(crpClusterOfActivity.getIdentifier() + "-" + crpClusteKeyOutputAdd.getId());
          crpClusterKeyOutputDAO.save(crpClusteKeyOutputAdd);
        }
        if (crpClusteKeyOutput.getKeyOutputOutcomes() != null) {
          for (CrpClusterKeyOutputOutcome crpClusterKeyOutputOutcome : crpClusteKeyOutput.getKeyOutputOutcomes()) {
            CrpClusterKeyOutputOutcome crpClusterKeyOutputOutcomeAdd = new CrpClusterKeyOutputOutcome();
            crpClusterKeyOutputOutcomeAdd.setCrpClusterKeyOutput(crpClusteKeyOutputAdd);
            CrpProgramOutcome crpProgramOutcome =
              crpProgramOutcomeDAO.find(crpClusterKeyOutputOutcome.getCrpProgramOutcome().getId());
            crpClusterKeyOutputOutcomeAdd.setCrpProgramOutcome(crpProgramOutcomeDAO
              .getCrpProgramOutcome(crpProgramOutcome.getComposeID(), crpClusterOfActivityAdd.getPhase()));
            crpClusterKeyOutputOutcomeDAO.save(crpClusterKeyOutputOutcomeAdd);
          }
        }
      }
    }
  }

  /**
   * clone the cluster leaders
   * 
   * @param crpClusterOfActivity clusterActivity original
   * @param crpClusterOfActivityAdd clusterActivity new
   */

  private void addLeaders(CrpClusterOfActivity crpClusterOfActivity, CrpClusterOfActivity crpClusterOfActivityAdd) {

    if (crpClusterOfActivity.getLeaders() != null) {
      for (CrpClusterActivityLeader crpClusterActivityLeader : crpClusterOfActivity.getLeaders()) {
        CrpClusterActivityLeader crpClusterActivityLeaderAdd = new CrpClusterActivityLeader();
        crpClusterActivityLeaderAdd.setUser(crpClusterActivityLeader.getUser());
        crpClusterActivityLeaderAdd.setCrpClusterOfActivity(crpClusterOfActivityAdd);
        crpClusterActivityLeaderDAO.save(crpClusterActivityLeaderAdd);
      }
    }
  }

  @Override
  public void deleteCrpClusterOfActivity(long crpClusterOfActivityId) {

    crpClusterOfActivityDAO.deleteCrpClusterOfActivity(crpClusterOfActivityId);
  }

  @Override
  public boolean existCrpClusterOfActivity(long crpClusterOfActivityID) {

    return crpClusterOfActivityDAO.existCrpClusterOfActivity(crpClusterOfActivityID);
  }

  @Override
  public List<CrpClusterOfActivity> findAll() {

    return crpClusterOfActivityDAO.findAll();

  }

  @Override
  public List<CrpClusterOfActivity> findClusterProgramPhase(CrpProgram crpProgram, Phase phase) {
    return crpClusterOfActivityDAO.findClusterProgramPhase(crpProgram.getId(), phase.getId());
  }

  @Override
  public List<ImpactPathwaysClusterDTO> getAllClusterInfoFromPhase(Long phaseId) {
    return this.crpClusterOfActivityDAO.getAllClusterInfoFromPhase(phaseId.longValue());
  }

  @Override
  public CrpClusterOfActivity getCrpClusterOfActivityById(long crpClusterOfActivityID) {

    return crpClusterOfActivityDAO.find(crpClusterOfActivityID);
  }

  @Override
  public CrpClusterOfActivity getCrpClusterOfActivityByIdentifierFlagshipAndPhase(String crpClusterOfActivityIdentefier,
    Long fpId, Long phaseId) {
    return this.crpClusterOfActivityDAO.getCrpClusterOfActivityByIdentifierFlagshipAndPhase(
      crpClusterOfActivityIdentefier, fpId.longValue(), phaseId.longValue());
  }

  @Override
  public CrpClusterOfActivity saveCrpClusterOfActivity(CrpClusterOfActivity crpClusterOfActivity) {

    CrpClusterOfActivity resultCluster = crpClusterOfActivityDAO.save(crpClusterOfActivity);
    if (crpClusterOfActivity.getPhase().getNext() != null) {
      this.addCrpClusterPhase(crpClusterOfActivity.getPhase().getNext(), crpClusterOfActivity.getCrpProgram().getId(),
        crpClusterOfActivity);
    }

    return resultCluster;
  }

  /**
   * check the leaders and updated
   * 
   * @param crpClusterOfActivityPrev outcome to update
   * @param crpClusterOfActivity outcome modified
   */
  private void updateClusterLeaders(CrpClusterOfActivity crpClusterOfActivityPrev,
    CrpClusterOfActivity crpClusterOfActivity) {
    for (CrpClusterActivityLeader leader : crpClusterOfActivityPrev.getCrpClusterActivityLeaders().stream()
      .filter(c -> c.isActive()).collect(Collectors.toList())) {
      if (crpClusterOfActivity.getLeaders() == null || crpClusterOfActivity.getLeaders().stream()
        .filter(c -> c.getUser() != null && c.getUser().getId().equals(leader.getUser().getId()))
        .collect(Collectors.toList()).isEmpty()) {
        crpClusterActivityLeaderDAO.deleteCrpClusterActivityLeader(leader.getId());
      }
    }
    if (crpClusterOfActivity.getLeaders() != null) {
      for (CrpClusterActivityLeader leader : crpClusterOfActivity.getLeaders()) {
        if (crpClusterOfActivityPrev.getCrpClusterActivityLeaders().stream()
          .filter(c -> c.isActive() && c.getUser().getId().equals(leader.getUser().getId()))
          .collect(Collectors.toList()).isEmpty()) {
          CrpClusterActivityLeader leaderAdd = new CrpClusterActivityLeader();
          leaderAdd.setCrpClusterOfActivity(crpClusterOfActivityPrev);
          leaderAdd.setUser(leader.getUser());
          crpClusterActivityLeaderDAO.save(leaderAdd);

        }
      }
    }
  }

  /**
   * check the keyouputs and updated
   * 
   * @param crpClusterOfActivityPrev cluster to update
   * @param crpClusterOfActivity cluster modified
   */
  private void updateKeyOutputs(CrpClusterOfActivity crpClusterOfActivityPrev,
    CrpClusterOfActivity crpClusterOfActivity) {
    for (CrpClusterKeyOutput crpClusterKeyOutput : crpClusterOfActivityPrev.getCrpClusterKeyOutputs().stream()
      .filter(c -> c.isActive()).collect(Collectors.toList())) {
      if (crpClusterOfActivity.getKeyOutputs() == null || crpClusterOfActivity.getKeyOutputs().stream()
        .filter(c -> c.getComposeID() != null && c.getComposeID().equals(crpClusterKeyOutput.getComposeID()))
        .collect(Collectors.toList()).isEmpty()) {
        crpClusterKeyOutputDAO.deleteCrpClusterKeyOutput(crpClusterKeyOutput.getId());
      }
    }
    if (crpClusterOfActivity.getKeyOutputs() != null) {
      for (CrpClusterKeyOutput crpClusterKeyOutput : crpClusterOfActivity.getKeyOutputs()) {
        if (crpClusterOfActivityPrev.getCrpClusterKeyOutputs().stream()
          .filter(c -> c.isActive() && c.getComposeID().equals(crpClusterKeyOutput.getComposeID()))
          .collect(Collectors.toList()).isEmpty()) {
          CrpClusterKeyOutput crpClusterKeyOutputAdd = new CrpClusterKeyOutput();
          crpClusterKeyOutputAdd.setComposeID(crpClusterKeyOutput.getComposeID());
          crpClusterKeyOutputAdd.setContribution(crpClusterKeyOutput.getContribution());
          crpClusterKeyOutputAdd.setCrpClusterOfActivity(crpClusterOfActivityPrev);
          crpClusterKeyOutputAdd.setKeyOutput(crpClusterKeyOutput.getKeyOutput());
          crpClusterKeyOutputDAO.save(crpClusterKeyOutputAdd);
          if (crpClusterKeyOutput.getComposeID() == null) {
            crpClusterKeyOutput
              .setComposeID(crpClusterOfActivityPrev.getIdentifier() + "-" + crpClusterKeyOutputAdd.getId());
            crpClusterKeyOutputAdd.setComposeID(crpClusterKeyOutput.getComposeID());
            crpClusterKeyOutputDAO.save(crpClusterKeyOutputAdd);
          }

          if (crpClusterKeyOutput.getKeyOutputOutcomes() != null) {
            for (CrpClusterKeyOutputOutcome crpClusterKeyOutputOutcome : crpClusterKeyOutput.getKeyOutputOutcomes()) {
              CrpClusterKeyOutputOutcome crpClusterKeyOutputOutcomeAdd = new CrpClusterKeyOutputOutcome();
              crpClusterKeyOutputOutcomeAdd.setContribution(crpClusterKeyOutputOutcome.getContribution());
              crpClusterKeyOutputOutcomeAdd.setCrpClusterKeyOutput(crpClusterKeyOutputAdd);
              // get outcome of the current phase
              CrpProgramOutcome crpProgramOutcome =
                crpProgramOutcomeDAO.find(crpClusterKeyOutputOutcome.getCrpProgramOutcome().getId());
              crpClusterKeyOutputOutcomeAdd.setCrpProgramOutcome(crpProgramOutcomeDAO
                .getCrpProgramOutcome(crpProgramOutcome.getComposeID(), crpClusterOfActivityPrev.getPhase()));
              if (crpClusterKeyOutputOutcomeAdd.getCrpProgramOutcome() != null) {
                crpClusterKeyOutputOutcomeDAO.save(crpClusterKeyOutputOutcomeAdd);
              }
            }
          }

        } else {
          CrpClusterKeyOutput crpClusterKeyOutputtoUpdate = crpClusterOfActivityPrev.getCrpClusterKeyOutputs().stream()
            .filter(c -> c.isActive() && c.getComposeID().equals(crpClusterKeyOutput.getComposeID()))
            .collect(Collectors.toList()).get(0);
          crpClusterKeyOutputtoUpdate.setKeyOutput(crpClusterKeyOutput.getKeyOutput());
          crpClusterKeyOutputtoUpdate.setContribution(crpClusterKeyOutput.getContribution());
          crpClusterKeyOutputDAO.save(crpClusterKeyOutputtoUpdate);
          if (crpClusterKeyOutput.getKeyOutputOutcomes() == null) {
            crpClusterKeyOutput.setKeyOutputOutcomes(new ArrayList<CrpClusterKeyOutputOutcome>());
          }

          for (CrpClusterKeyOutputOutcome crpClusterKeyOutputOutcome : crpClusterKeyOutput.getKeyOutputOutcomes()) {
            if (crpClusterKeyOutputOutcome != null && crpClusterKeyOutputOutcome.getCrpProgramOutcome() != null
              && crpClusterKeyOutputOutcome.getCrpProgramOutcome().getId() != null) {
              CrpProgramOutcome crpProgramOutcome =
                crpProgramOutcomeDAO.find(crpClusterKeyOutputOutcome.getCrpProgramOutcome().getId());
              crpClusterKeyOutputOutcome.setCrpProgramOutcome(crpProgramOutcomeDAO
                .getCrpProgramOutcome(crpProgramOutcome.getComposeID(), crpClusterOfActivityPrev.getPhase()));
            } else {
              crpClusterKeyOutputOutcome.setCrpProgramOutcome(null);
            }

          }
          for (CrpClusterKeyOutputOutcome crpClusterKeyOutputOutcome : crpClusterKeyOutputtoUpdate
            .getCrpClusterKeyOutputOutcomes().stream().filter(c -> c.isActive()).collect(Collectors.toList())) {
            if (crpClusterKeyOutput.getKeyOutputOutcomes().stream()
              .filter(c -> c.getCrpProgramOutcome() != null && c.getCrpProgramOutcome().getComposeID() != null
                && c.getCrpProgramOutcome().getComposeID()
                  .equals(crpClusterKeyOutputOutcome.getCrpProgramOutcome().getComposeID()))
              .collect(Collectors.toList()).isEmpty()) {
              crpClusterKeyOutputOutcomeDAO.deleteCrpClusterKeyOutputOutcome(crpClusterKeyOutputOutcome.getId());
            }
          }
          for (CrpClusterKeyOutputOutcome crpClusterKeyOutputOutcome : crpClusterKeyOutput.getKeyOutputOutcomes()) {
            if (crpClusterKeyOutputOutcome != null) {
              if (crpClusterKeyOutputtoUpdate.getCrpClusterKeyOutputOutcomes().stream()
                .filter(c -> c.isActive() && crpClusterKeyOutputOutcome != null
                  && crpClusterKeyOutputOutcome.getCrpProgramOutcome() != null && c.getCrpProgramOutcome() != null
                  && c.getCrpProgramOutcome().getComposeID()
                    .equals(crpClusterKeyOutputOutcome.getCrpProgramOutcome().getComposeID()))
                .collect(Collectors.toList()).isEmpty()) {
                CrpClusterKeyOutputOutcome crpClusterKeyOutputOutcomeAdd = new CrpClusterKeyOutputOutcome();
                crpClusterKeyOutputOutcomeAdd.setContribution(crpClusterKeyOutputOutcome.getContribution());
                crpClusterKeyOutputOutcomeAdd.setCrpClusterKeyOutput(crpClusterKeyOutputtoUpdate);
                crpClusterKeyOutputOutcomeAdd.setCrpProgramOutcome(crpClusterKeyOutputOutcome.getCrpProgramOutcome());
                if (crpClusterKeyOutputOutcome.getCrpProgramOutcome() != null) {
                  crpClusterKeyOutputOutcomeDAO.save(crpClusterKeyOutputOutcomeAdd);
                }
              }
            }

          }
        }
      }
    }
  }
}
