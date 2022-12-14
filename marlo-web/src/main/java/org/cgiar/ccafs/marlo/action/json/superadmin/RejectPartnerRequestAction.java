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

package org.cgiar.ccafs.marlo.action.json.superadmin;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConstants;
import org.cgiar.ccafs.marlo.data.manager.GlobalUnitManager;
import org.cgiar.ccafs.marlo.data.manager.PartnerRequestManager;
import org.cgiar.ccafs.marlo.data.model.GlobalUnit;
import org.cgiar.ccafs.marlo.data.model.PartnerRequest;
import org.cgiar.ccafs.marlo.utils.APConfig;
import org.cgiar.ccafs.marlo.utils.SendMailS;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.Parameter;

/**
 * RejectPartnerRequestAction:
 * 
 * @author avalencia - CCAFS
 * @date Oct 31, 2017
 * @time 10:52:21 AM: Add sendEmail boolean parameter
 * @date Nov 10, 2017
 * @time 10:09:18 AM:Inactive parent partner request
 */
public class RejectPartnerRequestAction extends BaseAction {


  /**
   * 
   */
  private static final long serialVersionUID = 821788435993637711L;


  // Managers
  private final PartnerRequestManager partnerRequestManager;
  private final GlobalUnitManager globalUnitManager;

  // Variables
  private String requestID;
  private String justification;
  private boolean success;
  private final SendMailS sendMail;
  private boolean sendNotification;

  @Inject
  public RejectPartnerRequestAction(APConfig config, PartnerRequestManager partnerRequestManager,
    GlobalUnitManager globalUnitManager, SendMailS sendMail) {
    super(config);
    this.partnerRequestManager = partnerRequestManager;
    this.sendMail = sendMail;
    this.globalUnitManager = globalUnitManager;
  }

  @Override
  public String execute() throws Exception {
    try {
      PartnerRequest partnerRequest = this.partnerRequestManager.getPartnerRequestById(Long.parseLong(this.requestID));
      partnerRequest.setAcepted(new Boolean(false));
      partnerRequest.setRejectJustification(this.justification);
      partnerRequest.setRejectedBy(this.getCurrentUser());
      partnerRequest.setRejectedDate(new Date());
      partnerRequest.setActive(false);
      partnerRequest = this.partnerRequestManager.savePartnerRequest(partnerRequest);
      // inactive the parent partnerRequest
      PartnerRequest partnerRequestParent =
        this.partnerRequestManager.getPartnerRequestById(partnerRequest.getPartnerRequest().getId());
      partnerRequestParent.setActive(false);
      this.partnerRequestManager.savePartnerRequest(partnerRequestParent);
      // Send notification email
      if (this.sendNotification) {
        this.sendRejectedNotficationEmail(partnerRequest);
      }
    } catch (Exception e) {
      this.success = false;
    }

    return SUCCESS;

  }

  @Override
  public String getJustification() {
    return this.justification;
  }

  public String getRequestID() {
    return this.requestID;
  }

  public boolean isSuccess() {
    return this.success;
  }

  @Override
  public void prepare() throws Exception {
    this.success = true;
    try {
      Map<String, Parameter> parameters = this.getParameters();
      this.justification = StringUtils.trim(parameters.get(APConstants.JUSTIFICATION_REQUEST).getMultipleValues()[0]);
      this.requestID = StringUtils.trim(parameters.get(APConstants.PARTNER_REQUEST_ID).getMultipleValues()[0]);
      this.sendNotification = Boolean.valueOf(
        StringUtils.trim(parameters.get(APConstants.PARTNER_REQUEST_SEND_NOTIFICATION).getMultipleValues()[0]));
    } catch (Exception e) {
      this.success = false;
    }
  }

  private void sendRejectedNotficationEmail(PartnerRequest partnerRequest) {
    String toEmail = "";
    // CC Email: User who rejected the request
    String ccEmail = this.getCurrentUser().getEmail();

    // ToEmail: User who requested the partner or the mail received by CLARISA
    if (partnerRequest.getExternalUserMail() != null) {
      toEmail = partnerRequest.getExternalUserMail();
      ccEmail = ccEmail + ", " + partnerRequest.getCreatedBy().getEmail();
    } else {
      toEmail = partnerRequest.getCreatedBy().getEmail();
    }
    // BBC: Our gmail notification email.
    String bbcEmails = this.config.getEmailNotification();
    // subject
    String subject =
      this.getText("marloRequestInstitution.reject.email.subject", new String[] {partnerRequest.getPartnerName()});
    // Building the email message
    StringBuilder message = new StringBuilder();
    String userName = partnerRequest.getExternalUserName() == null ? partnerRequest.getCreatedBy().getFirstName()
      : partnerRequest.getExternalUserName();
    message.append(this.getText("email.dear", new String[] {userName}));
    message.append(this.getText("marloRequestInstitution.reject.email",
      new String[] {partnerRequest.getPartnerInfo(), this.justification}));
    message.append(this.getText("email.support.noCrpAdmins"));
    message.append(this.getText("email.bye"));
    GlobalUnit globalUnit = globalUnitManager.getGlobalUnitById(partnerRequest.getCrp().getId());
    if (this.validateEmailNotification(globalUnit)) {
      this.sendMail.send(toEmail, ccEmail, bbcEmails, subject, message.toString(), null, null, null, true);
    }
  }


  @Override
  public void setJustification(String justification) {
    this.justification = justification;
  }


  public void setRequestID(String requestID) {
    this.requestID = requestID;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  private boolean validateEmailNotification(GlobalUnit globalUnit) {
    Boolean crpNotification = globalUnit.getCustomParameters().stream()
      .filter(c -> c.getParameter().getKey().equalsIgnoreCase(APConstants.CRP_EMAIL_NOTIFICATIONS))
      .allMatch(t -> (t.getValue() == null) ? true : t.getValue().equalsIgnoreCase("true"));
    return crpNotification;
  }
}
