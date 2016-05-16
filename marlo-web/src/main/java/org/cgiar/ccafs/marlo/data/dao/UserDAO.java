package org.cgiar.ccafs.marlo.data.dao;

import org.cgiar.ccafs.marlo.data.model.User;

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


/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public interface UserDAO {

  /**
   * Get the user's email that relates with the given user name.
   * 
   * @param username is the user nickname.
   * @return a String with the user's email, or null if nothing was found.
   */
  public String getEmailByUsername(String username);

  /**
   * This method gets the data of a User identified with a given id.
   * 
   * @param userId is the id of the User.
   * @return a Map with the user data.
   */
  public User getUser(int id);

  /**
   * Get a Users object with the given email.
   * 
   * @param email is the user email
   * @return a Users object or null if the User not exist.
   */
  public User getUser(String email);

}
