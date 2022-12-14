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


package org.cgiar.ccafs.marlo.rest.services.deliverables;

import org.cgiar.ccafs.marlo.rest.services.deliverables.model.Author;
import org.cgiar.ccafs.marlo.rest.services.deliverables.model.MetadataModel;
import org.cgiar.ccafs.marlo.utils.DateTypeAdapter;
import org.cgiar.ccafs.marlo.utils.RestConnectionUtil;
import org.cgiar.ccafs.marlo.utils.doi.DOIService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.icu.util.Calendar;
import org.dom4j.Element;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CIMMYTDspaceClientAPI:Get metadata from CIMMYT Dspace according to an URL.
 * 
 * @author avalencia - CCAFS
 * @date Nov 2, 2017
 * @time 1:59:29 PM: Create class
 * @date Nov 3, 2017
 * @time 2:38:03 PM: Add metadata and get handle
 */
public class CIMMYTDspaceClientAPI extends MetadataClientApi {

  private static final Logger LOG = LoggerFactory.getLogger(CIMMYTDspaceClientAPI.class);

  private final String Dataverse_OAI_PMH_HANDLE =
    "http://repository.cimmyt.org/oai/request?verb=GetRecord&identifier=oai:repository.cimmyt.org:{0}&metadataPrefix=oai_dc";

  private final String CYMMYT_DSPACE_URL_REGEX =
    "((http?|https)://)?((W|w){3}.)?(repository.cimmyt.org/)(xmlui/)?(handle/)";

  private final Pattern CYMMYT_DSPACE_URL_PATTERN = Pattern.compile(CYMMYT_DSPACE_URL_REGEX);

  private RestConnectionUtil xmlReaderConnectionUtil;

  private Map<String, String> coverterAtrributes;

  public CIMMYTDspaceClientAPI() {
    xmlReaderConnectionUtil = new RestConnectionUtil();
    coverterAtrributes = new HashMap<String, String>();
    coverterAtrributes.put("subject", "keywords");
  }

  @Override
  public MetadataModel getMetadata(String link) {
    MetadataModel metadataModel = null;
    JSONObject jo = new JSONObject();
    this.setDefaultEmptyValues(jo);
    try {
      Element metadata = xmlReaderConnectionUtil.getXmlRestClient(link);
      List<Author> authors = new ArrayList<Author>();
      List<Element> elements = metadata.elements();
      for (Element element : elements) {
        // get recordOAI
        Element recordOAI = element.element("record");

        if (recordOAI != null) {
          // get metadataOAI
          Element metadataOAI = recordOAI.element("metadata");
          if (metadataOAI != null) {
            // get oai_dc:dc
            List<Element> elements2 = metadataOAI.elements();
            for (Element oai_dc : elements2) {
              // get title
              if (oai_dc.element("title") != null) {
                Element titleElement = oai_dc.element("title");
                String titleValue = titleElement.getStringValue();
                jo.put(titleElement.getName(), titleValue);
              }
              // get authors
              if (oai_dc.elements("creator") != null) {
                List<Element> authorsElements = oai_dc.elements("creator");
                if (authorsElements != null && authorsElements.size() > 0) {
                  for (Element value : authorsElements) {
                    Author author = new Author(value.getStringValue().replaceAll(",", ""));
                    String names[] = author.getFirstName().split(" ");
                    // Name validations
                    if (names.length > 0) {
                      if (names.length == 1) {
                        author.setFirstName(names[0]);
                        authors.add(author);
                      } else if (names.length == 2) {
                        author.setFirstName(names[0]);
                        author.setLastName(names[1]);
                        authors.add(author);
                      } else if (names.length == 3) {
                        if (names[1].contains(".")) {
                          author.setFirstName(names[0] + " " + names[1]);
                          author.setLastName(names[2]);
                          authors.add(author);
                        } else {
                          author.setFirstName(names[0]);
                          author.setLastName(names[1] + " " + names[2]);
                          authors.add(author);
                        }
                      } else {
                        author.setFirstName(names[0] + " " + names[1]);
                        String lastName = "";
                        for (int i = 2; i < names.length; i++) {
                          if (i + 1 == names.length) {
                            lastName += names[i];
                          } else {
                            lastName += names[i] + " ";
                          }
                        }
                        author.setLastName(lastName);
                        authors.add(author);
                      }
                    }
                  }
                }
              }
              // get keywords
              if (oai_dc.elements("subject") != null) {
                List<Element> subjectsElements = oai_dc.elements("subject");
                if (subjectsElements != null && subjectsElements.size() > 0) {
                  String keyWords = "";
                  for (Element subjectElement : subjectsElements) {
                    if (keyWords.isEmpty()) {
                      keyWords += subjectElement.getStringValue();
                    } else {
                      keyWords += " " + subjectElement.getStringValue();
                    }
                  }
                  jo.put(subjectsElements.get(0).getName(), keyWords);
                }
              }
              // get description
              if (oai_dc.element("description") != null) {
                Element descriptionElement = oai_dc.element("description");
                String descriptionValue = descriptionElement.getStringValue();
                jo.put(descriptionElement.getName(), descriptionValue);
              }

              // get date
              if (oai_dc.element("date") != null) {
                List<Element> dateElements = oai_dc.elements("date");
                if (dateElements != null && dateElements.size() > 0) {
                  dateElements = dateElements.stream()
                    .sorted((e1, e2) -> Integer.compare(e1.getStringValue().length(), e2.getStringValue().length()))
                    .collect(Collectors.toList());
                  Element dateElement = dateElements.get(0);
                  Calendar cal = Calendar.getInstance();
                  cal.set(Integer.parseInt(dateElement.getStringValue()), 0, 1, 0, 0, 0);
                  Date date = cal.getTime();
                  jo.put("publicationDate", date);
                }
              }
              // get rights
              if (oai_dc.element("rights") != null) {
                Element rightsElement = oai_dc.element("rights");
                String rightsValue = rightsElement.getStringValue();
                jo.put(rightsElement.getName(), rightsValue);
              }
              // get handle and doi
              if (oai_dc.elements("identifier") != null && !oai_dc.elements("identifier").isEmpty()) {
                List<Element> identifiers = oai_dc.elements("identifier");
                for (Element identifier : identifiers) {
                  String identifierString = identifier.getStringValue();
                  Matcher doiMatcher = DOIService.REGEXP_PLAINDOI.matcher(identifierString);
                  if (doiMatcher.lookingAt()) {
                    // a doi was found. save it.
                    jo.put("doi", doiMatcher.group());
                  } else {
                    // we do not know what this is. we will just assume is a handle and move on
                    jo.put("handle", identifierString);
                  }
                }
                // jo.put("handle", this.getId());
              }
              // get language
              if (oai_dc.element("language") != null) {
                Element languageElement = oai_dc.element("language");
                String languageValue = languageElement.getStringValue();
                jo.put(languageElement.getName(), languageValue);
              }
            }
          }
        }
      }
      this.setDoi(jo);
      GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
      Gson gson = gsonBuilder.create();
      String data = jo.toString();
      for (String key : coverterAtrributes.keySet()) {
        data = data.replace(key, coverterAtrributes.get(key));
      }
      metadataModel = gson.fromJson(data, MetadataModel.class);
      Author[] authorsArr = new Author[authors.size()];
      authorsArr = authors.toArray(authorsArr);
      metadataModel.setAuthors(authorsArr);

    } catch (Exception e) {
      e.printStackTrace();
      LOG.error(e.getLocalizedMessage());
    }
    return metadataModel;

  }

  /**
   * with the link get the id and make a connection to get the Metadata id connnection and format into the rest url
   * 
   * @return the link to get the metadata
   */
  @Override
  public String parseLink(String link) {
    Matcher matcher = CYMMYT_DSPACE_URL_PATTERN.matcher(link);
    // if the link contains the pattern, we remove the matched region from the link
    if (matcher.lookingAt()) {
      this.setId(link.replace(matcher.group(0), ""));
    }

    String linkRest = (Dataverse_OAI_PMH_HANDLE.replace("{0}", this.getId()));
    return linkRest;
  }

}

