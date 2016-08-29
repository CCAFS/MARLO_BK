[#ftl]
[#assign title = "Project Locations" /]
[#assign currentSectionString = "project-${actionName?replace('/','-')}-${projectID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/projects/projectsLocation.js", "${baseUrl}/js/global/autoSave.js"] /] [#-- "${baseUrl}/js/global/autoSave.js" --]
[#assign customCSS = ["${baseUrl}/css/projects/projectLocations.css" ] /]
[#assign currentSection = "projects" /]
[#assign currentStage = "locations" /]

[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/projects", "action":"${(crpSession)!}/projectsList"},
  {"label":"projectLocations", "nameSpace":"/projects", "action":""}
] /]

[#assign locationLevelName = "project.locationsData"/]
[#assign locationName = "locElements"/]
[#assign countID = 0/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

<div class="container">
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p> [@s.text name="projectLocations.help" /] </p></div> 
</div>
    
<section class="container">
    <div class="row">
      [#-- Project Menu --]
      <div class="col-md-3">
        [#include "/WEB-INF/views/projects/menu-projects.ftl" /]
      </div>
      [#-- Project Section Content --]
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/projects/messages-projects.ftl" /]
      
        [@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
           
          <h3 class="headTitle">[@s.text name="projectLocations.title" /]</h3>  
          <div id="" class="borderBox">
            [#-- Content--]
            <div id="content" class="col-md-12">
              <div class="text-center col-md-12  alert alert-info"><span> Select the points where the focus activity is being carried out </span></div>
              <div id="selectsContent" class="col-md-6">
                [#-- Content collapsible--]
                <div class="selectWrapper row">
                [#if project.locationsData?has_content]
                  [#list project.locationsData as locationLevels]
                    [@locationLevel element=locationLevels name="${locationLevelName}" index=locationLevels_index list=locationLevels.list?? && locationLevels.list /]
                  [/#list]
                [/#if]
                
                </div>
              </div>
              
              <div  class="col-md-6 map">
                <div id="map" class="col-md-12"></div>
              </div>
                
            </div>
            [#if editable]
            [#-- locations level Select --]
            <select name="" id="" class="selectLocationLevel select " >
            <option value="-1" >Select an option...</option>
              [#list locationsLevels as locLevels]
                [#list locLevels.locations as locations]
                  <option value="${locations.id}-${locations.list?string}-${locations.name}" >${locations.name}</option>
                [/#list]
              [/#list]
            </select>
            [/#if]
          </div> 
          
          [#include "/WEB-INF/views/projects/buttons-projects.ftl" /]
             
         
          [/@s.form] 
      </div>
    </div>  
</section>

[#--<script src="https://maps.googleapis.com/maps/api/js?key=${config.googleApiKey}&callback=initMap"></script>--]

[#-- Section hidden inputs--]
[@locationLevel element={} name="" index=0 template=true /]

[@locationMacro element={} name="" index=0 template=true /]

<input type="hidden" id="locationLevelName" value="${locationLevelName}" />
<input type="hidden" id="locationName" value="${locationName}" />
  
[#include "/WEB-INF/global/pages/footer.ftl"]

[#macro locationLevel element  name index template=false list=false]
  [#local customName = "${name}[${index}]" /]
  [#-- Content collapsible--]
  <div id="locationLevel-${template?string('template',index)}" class="locationLevel col-md-12" style="display:${template?string('none','block')}">
    [#-- header element --]
    <div class="col-md-12 locationName-content borderBox closed">
      <div class="glyphicon glyphicon-chevron-up collapsible" ></div>
      <div class="locationLevel-option">${(element.name)!}</div>
      [#if editable]
      <div class="removeLocationLevel removeElement" title="Remove Location level"></div>
      [/#if]
    </div>
    <div class="col-md-12 locationLevel-optionContent ">
      [#if editable]
      <div class="col-md-12 checkBox">
        <span class="col-md-10">Are you working in all countries on this region?</span>
        <input name="${locationLevelName}[${index}].allCountries" class="col-md-1 allCountries" type="checkbox" value="" />
      </div>
      [/#if]
      [#-- Content of locations--]
      <div class="optionSelect-content row">
        [#if element.locElements?has_content]
          [#list element.locElements as location]
            [@locationMacro element=location name="${customName}.${locationName}" index=location_index isList=list/]
          [/#list]
        [/#if]
      </div>
      [#if editable]
      <select style="display:${(element.list?? && element.list)?string('block','none')}"  class="form-control selectLocation col-md-12" placeholder="select an option...">
        <option selected="selected" value="-1" >Select a location</option>
        [#if element.allElements?has_content]
        [#list element.allElements as locElements]
            <option value="${locElements.id}" >${locElements.name}</option>
        [/#list]
        [/#if]
      </select>
        <div class="col-md-12 coordinates-inputs" style="display:${(element.list?? && !element.list)?string('block','none')}">
          <div class="latitudeWrapper"><input placeholder="Latitude" class="latitude form-control" type="text" /></div>
          <div class="longitudeWrapper"><input placeholder="Longitude" class="longitude form-control " type="text" /></div>
          <div class="nameWrapper"><input placeholder="name" class="name form-control" type="text" /></div>
          <span class="addLocation glyphicon glyphicon-plus-sign button-green"></span>
        </div>
        [/#if]
    </div>
    <input class="locationLevelId" type="hidden" name="${locationLevelName}[${index}].id" value="${(element.id)!}"/>
    <input class="locationLevelName" type="hidden" name="${locationLevelName}[${index}].name" value="${(element.name)!}"/>
    <input type="hidden" class="isList" name="${customName}.isList"  value="${(list)?string}"/>
  </div>
[/#macro]

[#macro locationMacro element  name index template=false isList=false ]
  [#local customName = "${name}[${index}]" /]
  [#assign countID = countID+1/]
  [#-- Content collapsible--]
  <div id="location-${template?string('template',countID)}" class="col-md-12 locElement" style="display:${template?string('none','block')}">
    <div class="locations col-md-12">
      <div class="locationName"><span class="lName">${(element.name)!}</span> [#if element.locGeoposition?? && element.locGeoposition.latitude?? && element.locGeoposition.longitude?? && element.locGeoposition.latitude!=0 && element.locGeoposition.longitude!=0] <span class="lPos">[#if isList!=true](${(element.locGeoposition.latitude)!}, ${(element.locGeoposition.longitude)!})[/#if]</span> [/#if] </div>
      [#if editable]
      <div class="removeLocation removeIcon" title="Remove Location"></div>
      [/#if]
    </div>
    [#-- Hidden inputs --]
    <input type="hidden" class="locElementId" name="${customName}.id" value="${(element.id)!}"/>
    <input type="hidden" class="locElementName" name="${customName}.name" value="${(element.name)!}" />
    <input type="hidden" class="locElementCountry" name="${customName}.locElement.isoAlpha2" value="${(element.isoAlpha2)!}" />
    <input type="hidden" class="geoId" name="${customName}.locGeoposition.id"  value="${(element.locGeoposition.id)!}" />
    
    <input type="hidden" class="geoLatitude" name="${customName}.locGeoposition.latitude"  value="${(element.locGeoposition?? && element.locGeoposition.latitude?? && element.locGeoposition.latitude!=0)?string((element.locGeoposition.latitude)!,'')}" /> 
    <input type="hidden" class="geoLongitude" name="${customName}.locGeoposition.longitude"  value="${(element.locGeoposition?? && element.locGeoposition.longitude?? && element.locGeoposition.longitude!=0)?string((element.locGeoposition.longitude)!,'')}" />
  </div>
[/#macro]
