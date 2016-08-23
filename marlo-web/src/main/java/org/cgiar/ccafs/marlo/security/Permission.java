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

package org.cgiar.ccafs.marlo.security;


/**
 * This class have the statics texts of the names and
 * paths of the privileges that have a user.
 * 
 * @author Hernán David Carvajal
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class Permission {

  public static final String FULL_PRIVILEGES = "*"; // path full privileges
  public static final String CRP_ADMIN_VISIBLE_PRIVILEGES = "crp:{0}:admin:canAcess"; // path full privileges
  public static final String CRP_ADMIN_EDIT_PRIVILEGES = "crp:{0}:admin:*"; // path full privileges
  public static final String CRP_ADMIN_BASE_PERMISSION = "crp:{0}:admin";
  public static final String IMPACT_PATHWAY_BASE_PERMISSION = "crp:{0}:impactPathway:{1}";
  public static final String IMPACT_PATHWAY_EDIT_PRIVILEGES = "crp:{0}:impactPathway:{1}:*";
  public static final String IMPACT_PATHWAY_VISIBLE_PRIVILEGES = "crp:{0}:impactPathway:canAcess";
  public static final String PROJECT_DESCRIPTION_BASE_PERMISSION = "crp:{0}:project:{1}:description";
  public static final String PROJECT_CONTRIBRUTIONCRP_BASE_PERMISSION = "crp:{0}:project:{1}:contributionCrp";
  public static final String PROJECT_DESCRIPTION_PERMISSION = "crp:{0}:project:{1}:description:*";
  public static final String PROJECT_PARTNER_BASE_PERMISSION = "crp:{0}:project:{1}:partners";
  public static final String PROJECT_LOCATION_BASE_PERMISSION = "crp:{0}:project:{1}:location";
  public static final String PROJECT__PERMISSION = "crp:{0}:project:{1}:{2}:canEdit";
  public static final String PROJECT__SWITCH = "crp:{0}:project:{1}:projectSwitch";
  public static final String PROJECT_CONTRIBRUTIONCRP_EDIT_PERMISSION = "crp:{0}:project:{1}:contributionCrp:edit";


}
