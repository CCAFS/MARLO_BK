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


package org.cgiar.ccafs.marlo.action.json.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.FeedbackQACommentManager;
import org.cgiar.ccafs.marlo.data.manager.FeedbackQACommentableFieldsManager;
import org.cgiar.ccafs.marlo.data.manager.FeedbackQAReplyManager;
import org.cgiar.ccafs.marlo.data.manager.FeedbackStatusManager;
import org.cgiar.ccafs.marlo.data.manager.PhaseManager;
import org.cgiar.ccafs.marlo.data.manager.ProjectManager;
import org.cgiar.ccafs.marlo.data.manager.UserManager;
import org.cgiar.ccafs.marlo.data.model.FeedbackQAComment;
import org.cgiar.ccafs.marlo.data.model.FeedbackQACommentableFields;
import org.cgiar.ccafs.marlo.data.model.FeedbackQAReply;
import org.cgiar.ccafs.marlo.data.model.FeedbackStatus;
import org.cgiar.ccafs.marlo.data.model.FeedbackStatusEnum;
import org.cgiar.ccafs.marlo.data.model.Phase;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.utils.APConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveFeedbackCommentsAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = -4335064142194555431L;
  private final Logger logger = LoggerFactory.getLogger(SaveFeedbackCommentsAction.class);
  private Map<String, Object> save;
  private Long phaseId;
  private Long fieldId;
  private String comment;
  private String status;
  private Long replyId;
  private Long commentId;
  private Long objectId;
  private Long parentId;
  private String reply;
  private String parentFieldDescription;
  private String link;
  private String fieldDescription;
  private String fieldValue;
  private Long userId;
  private Long deliverableId;
  private Date date;
  private Long projectId;
  private FeedbackQACommentableFieldsManager feedbackQACommentableFieldsManager;
  private FeedbackStatusManager feedbackStatusManager;
  private ProjectManager projectManager;
  private FeedbackQACommentManager commentQAManager;
  private FeedbackQAReplyManager commentManager;
  private PhaseManager phaseManager;
  private UserManager userManager;
  private FeedbackQAComment qaComment;


  @Inject
  public SaveFeedbackCommentsAction(APConfig config,
    FeedbackQACommentableFieldsManager feedbackQACommentableFieldsManager, FeedbackQACommentManager commentQAManager,
    FeedbackQAReplyManager commentManager, PhaseManager phaseManager, UserManager userManager,
    FeedbackStatusManager feedbackStatusManager, ProjectManager projectManager) {
    super(config);
    this.feedbackQACommentableFieldsManager = feedbackQACommentableFieldsManager;
    this.commentQAManager = commentQAManager;
    this.commentManager = commentManager;
    this.phaseManager = phaseManager;
    this.userManager = userManager;
    this.projectManager = projectManager;
    this.feedbackStatusManager = feedbackStatusManager;
  }

  @Override
  public String execute() throws Exception {
    // @param = comment/parentID/phaseID/fieldID
    // @param (optional) = commentID

    save = new HashMap<String, Object>();
    if (fieldId != null) {

      // Create feedback Comment save object
      qaComment = new FeedbackQAComment();

      // get existing object from database
      try {
        if (commentId != null) {
          FeedbackQAComment qaCommentDB = commentQAManager.getFeedbackQACommentById(commentId);
          if (qaCommentDB != null && qaCommentDB.getId() != null) {
            qaComment = qaCommentDB;

            boolean isEdited = this.saveEditorInfo();
            if (!isEdited) {
              if (userId != null) {
                try {
                  User user = userManager.getUser(userId);
                  if (user != null) {
                    qaComment.setUser(this.getCurrentUser());
                  }
                } catch (Exception e) {
                  logger.error("unable to set User object", e);
                }
              }
              date = new Date();
              if (date != null) {
                qaComment.setCommentDate(date);
              }
            }
          }
        } else {
          if (userId != null) {
            try {
              User user = userManager.getUser(userId);
              if (user != null) {
                qaComment.setUser(this.getCurrentUser());
              }
            } catch (Exception e) {
              logger.error("unable to set User object", e);
            }
          }

          date = new Date();
          if (date != null) {
            qaComment.setCommentDate(date);
          }
        }
      } catch (Exception e) {
        logger.error("unable to get existing Feedback QA comment object from DB", e);
      }

      qaComment.setComment(comment);

      if (phaseId != null) {
        Phase phase = phaseManager.getPhaseById(phaseId);
        qaComment.setPhase(phase);
      }

      String statusText = null;
      if (status != null) {
        if (status.equals("0")) {
          statusText = "rejected";
        }
        if (status.equals(FeedbackStatusEnum.Approved.getStatusId())) {
          statusText = FeedbackStatusEnum.Approved.getStatus();
        }
        if (status.equals(FeedbackStatusEnum.ClarificatioNeeded.getStatusId())) {
          statusText = FeedbackStatusEnum.ClarificatioNeeded.getStatus();
        }
        if (status.equals(FeedbackStatusEnum.Pending.getStatusId())) {
          statusText = FeedbackStatusEnum.Pending.getStatus();
        }
        if (status.equals(FeedbackStatusEnum.Accepted.getStatusId())) {
          statusText = FeedbackStatusEnum.Accepted.getStatus();
        }
        if (status.equals(FeedbackStatusEnum.Rejected.getStatusId())) {
          statusText = FeedbackStatusEnum.Rejected.getStatus();
        }
        if (status.equals(FeedbackStatusEnum.NoAccepted.getStatusId())) {
          statusText = FeedbackStatusEnum.NoAccepted.getStatus();
        }
        if (status == null) {
          statusText = FeedbackStatusEnum.Pending.getStatus();
        }
      }

      if (status != null) {
        qaComment.setStatus(statusText);
        this.saveFeedbackStatus();
      }

      if (fieldId != null && phaseId != null && parentId != null) {
        FeedbackQACommentableFields field =
          feedbackQACommentableFieldsManager.getInternalQaCommentableFieldsById(fieldId);

        if (field.getSectionName() != null && parentId != null) {
          switch (field.getSectionName()) {
            case "projectContributionCrp":
              link = this.getBaseUrl() + "/clusters/" + this.getCurrentCrp().getAcronym() + "/contributionCrp.do?"
                + "projectOutcomeID=" + parentId + "&phaseID=" + phaseId + "&edit=true";
              break;
            case "deliverable":
              link = this.getBaseUrl() + "/clusters/" + this.getCurrentCrp().getAcronym() + "/deliverable.do?"
                + "deliverableID=" + parentId + "&phaseID=" + phaseId + "&edit=true";
              break;
            case "study":
              link = this.getBaseUrl() + "/clusters/" + this.getCurrentCrp().getAcronym() + "/study.do?" + "expectedID="
                + parentId + "&phaseID=" + phaseId + "&edit=true";
              break;
            case "innovation":
              link = this.getBaseUrl() + "/clusters/" + this.getCurrentCrp().getAcronym() + "/innovation.do?"
                + "innovationID=" + parentId + "&phaseID=" + phaseId + "&edit=true";
              break;
          }
        }
      }

      if (link != null) {
        try {
          link.replace("orgclusters", "org/clusters");
        } catch (Exception e) {
          logger.error("unable to format the url", e);
        }
        qaComment.setLink(link);
      }

      if (fieldDescription != null) {
        qaComment.setFieldDescription(fieldDescription);
      }
      if (parentFieldDescription != null) {
        qaComment.setParentFieldDescription(parentFieldDescription);
      }
      if (fieldValue != null) {
        qaComment.setFieldValue(fieldValue);
      }

      if (replyId != null) {
        FeedbackQAReply reply = commentManager.getFeedbackCommentById(replyId);
        qaComment.setReply(reply);
      }

      if (parentId != null) {
        qaComment.setParentId(parentId);
      }

      if (fieldId != null) {
        FeedbackQACommentableFields field =
          feedbackQACommentableFieldsManager.getInternalQaCommentableFieldsById(fieldId);
        qaComment.setField(field);
      }

      if (projectId != null) {
        try {
          Project project = new Project();
          project = projectManager.getProjectById(projectId);
          if (project != null) {
            qaComment.setProject(project);
          }
        } catch (Exception e) {
          logger.error("unable to set Project object", e);
        }
      }

      qaComment = commentQAManager.saveFeedbackQAComment(qaComment);

      if (qaComment.getId() != null) {
        save.put("save", true);
        save.put("id", qaComment.getId());
      } else {
        save.put("save", false);
      }
    } else {
      save.put("save", false);
    }
    return SUCCESS;
  }

  public Map<String, Object> getSave() {
    return save;
  }

  @Override
  public void prepare() throws Exception {
    Map<String, Parameter> parameters = this.getParameters();


    if (parameters.get(APConstants.COMMENT_REQUEST).isDefined()) {
      comment = StringUtils.trim(StringUtils.trim(parameters.get(APConstants.COMMENT_REQUEST).getMultipleValues()[0]));
    }
    if (parameters.get(APConstants.STATUS_REQUEST).isDefined()) {
      status = StringUtils.trim(StringUtils.trim(parameters.get(APConstants.STATUS_REQUEST).getMultipleValues()[0]));
    }
    try {
      if (parameters.get(APConstants.PHASE_ID).isDefined()) {
        phaseId = Long
          .parseLong(StringUtils.trim(StringUtils.trim(parameters.get(APConstants.PHASE_ID).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get phaseID", e);
    }
    try {
      if (parameters.get(APConstants.FIELD_REQUEST_ID).isDefined()) {
        fieldId = Long.parseLong(
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.FIELD_REQUEST_ID).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get fieldID", e);
    }
    try {
      if (parameters.get(APConstants.REPLY_ID_REQUEST).isDefined()) {
        replyId = Long.parseLong(
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.REPLY_ID_REQUEST).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get replyID", e);
    }
    try {
      if (parameters.get(APConstants.COMMENT_REQUEST_ID).isDefined()) {
        commentId = Long.parseLong(
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.COMMENT_REQUEST_ID).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get commentID", e);
    }
    try {
      if (parameters.get(APConstants.OBJECT_REQUEST_ID).isDefined()) {
        objectId = Long.parseLong(
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.OBJECT_REQUEST_ID).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get replyID", e);
    }
    try {
      if (parameters.get(APConstants.PARENT_REQUEST_ID).isDefined()) {
        parentId = Long.parseLong(
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.PARENT_REQUEST_ID).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get parentID", e);
    }
    try {
      if (parameters.get(APConstants.COMMENT_REPLY).isDefined()) {
        reply = StringUtils.trim(StringUtils.trim(parameters.get(APConstants.COMMENT_REPLY).getMultipleValues()[0]));
      }
    } catch (Exception e) {
      logger.error("unable to get comment", e);
    }
    try {
      if (parameters.get(APConstants.COMMENT_USER_ID).isDefined()) {
        userId = Long.parseLong(
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.COMMENT_USER_ID).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get user", e);
    }
    try {
      if (parameters.get(APConstants.COMMENT_STATUS_REQUEST).isDefined()) {
        status =
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.COMMENT_STATUS_REQUEST).getMultipleValues()[0]));
      }
    } catch (Exception e) {
      logger.error("unable to get user", e);
    }
    try {
      if (parameters.get(APConstants.PROJECT_ID).isDefined()) {
        projectId = Long
          .parseLong(StringUtils.trim(StringUtils.trim(parameters.get(APConstants.PROJECT_ID).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get user", e);
    }
    try {
      if (parameters.get(APConstants.LINK).isDefined()) {
        link = StringUtils.trim(StringUtils.trim(parameters.get(APConstants.LINK).getMultipleValues()[0]));
      }
    } catch (Exception e) {
      logger.error("unable to get url", e);
    }
    try {
      if (parameters.get(APConstants.FIELD_DESCRIPTION).isDefined()) {
        fieldDescription =
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.FIELD_DESCRIPTION).getMultipleValues()[0]));
      }
    } catch (Exception e) {
      logger.error("unable to get field Description", e);
    }
    try {
      if (parameters.get(APConstants.FIELD_VALUE).isDefined()) {
        fieldValue = StringUtils.trim(StringUtils.trim(parameters.get(APConstants.FIELD_VALUE).getMultipleValues()[0]));
      }
    } catch (Exception e) {
      logger.error("unable to get field Description", e);
    }
    try {
      if (parameters.get(APConstants.DELIVERABLE_ID).isDefined()) {
        deliverableId = Long.parseLong(
          StringUtils.trim(StringUtils.trim(parameters.get(APConstants.DELIVERABLE_ID).getMultipleValues()[0])));
      }
    } catch (Exception e) {
      logger.error("unable to get deliverable ID value", e);
    }
    try {
      if (parameters.get(APConstants.PARENT_FIELD_DESCRIPTION).isDefined()) {
        parentFieldDescription = StringUtils
          .trim(StringUtils.trim(parameters.get(APConstants.PARENT_FIELD_DESCRIPTION).getMultipleValues()[0]));
      }
    } catch (Exception e) {
      logger.error("unable to get deliverable ID value", e);
    }
  }

  /**
   * Validate if the comment is being edited and add the editor information
   * 
   * @return true if the comment is being edited
   */
  public boolean saveEditorInfo() {
    if (qaComment.getId() != null && qaComment.getUserEditor() == null && qaComment.getEditionDate() == null
      && qaComment.getUser() != null && qaComment.getCommentDate() != null) {
      qaComment.setUserEditor(this.getCurrentUser());
      date = new Date();
      qaComment.setEditionDate(date);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Save feedback status id relation with feedback status table
   */
  public void saveFeedbackStatus() {
    if (status != null) {
      long idStatus;
      try {
        if (status.equals("0")) {
          idStatus = 5;
        } else {
          idStatus = Long.valueOf(status);
        }
        FeedbackStatus feedbackStatus = feedbackStatusManager.getFeedbackStatusById(idStatus);
        qaComment.setFeedbackStatus(feedbackStatus);
        qaComment = commentQAManager.saveFeedbackQAComment(qaComment);
      } catch (Exception e) {
        logger.error("unable to get feedback status id", e);
      }

    }
  }

  public void setSave(Map<String, Object> save) {
    this.save = save;
  }

}
