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

/**************
 * @author Diego Perez - CIAT/CCAFS
 **************/

package org.cgiar.ccafs.marlo.rest.mappers;

import org.cgiar.ccafs.marlo.rest.dto.PublicationsWOSDTO;
import org.cgiar.ccafs.marlo.rest.services.deliverables.model.PublicationWOS;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "jsr330", uses = {PublicationAuthorWOSMapper.class, PublicationInstitutionWOSMapper.class,
  PublicationAltmetricsWOSMapper.class, PublicationGardianWOSMapper.class})
public interface PublicationWOSMapper {

  @Mappings({@Mapping(source = "publication_type", target = "publicationType"),
    @Mapping(source = "authors", target = "authors"), @Mapping(source = "publication_year", target = "publicationYear"),
    @Mapping(source = "journal_name", target = "journalName"), @Mapping(source = "start_end_pages", target = "pages"),
    @Mapping(source = "is_isi", target = "is_isi"), @Mapping(source = "is_oa", target = "is_oa")})
  public abstract PublicationsWOSDTO publicationWOSToPublicationWOSDTO(PublicationWOS publicationWOS);
}
