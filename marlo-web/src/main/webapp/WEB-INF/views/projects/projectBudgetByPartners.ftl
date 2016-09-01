[#ftl]
[#assign title = "Project Budget By Partners" /]
[#assign currentSectionString = "project-${actionName?replace('/','-')}-${projectID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/projects/projectBudgetByPartners.js", "${baseUrl}/js/global/autoSave.js"] /]
[#assign customCSS = ["${baseUrl}/css/projects/projectBudgetByPartners.css"] /]
[#assign currentSection = "projects" /]
[#assign currentStage = "budgetByPartners" /]

[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/projects", "action":"${(crpSession)!}/projectsList"},
  {"label":"projectBudgetByPartners", "nameSpace":"/projects", "action":""}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

[#assign startYear = (project.startDate?string.yyyy)?number /]
[#assign endYear = (project.endDate?string.yyyy)?number /]

<div class="container">
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p> [@s.text name="projectBudgetByPartners.help" /] </p></div> 
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
          
          <h3 class="headTitle">[@s.text name="projectBudgetByPartners.title" /]</h3>  
          
          <ul class="nav nav-tabs budget-tabs" role="tablist">
            [#list startYear .. endYear as year]
              <li class="[#if year == currentCycleYear]active[/#if]"><a href="#year-${year}" role="tab" data-toggle="tab">${year} [@customForm.req required=isYearRequired(year) /] </a></li>
            [/#list]
          </ul>
          <div class="tab-content budget-content">
            [#list startYear .. endYear as year]
              <div role="tabpanel" class="tab-pane [#if year == currentCycleYear]active[/#if]" id="year-${year}">
                
                [#if project.partners?has_content]
                  [#list project.partners as projectPartner]
                    [#if action.isPPA(projectPartner.institution)]
                      [@projectPartnerMacro element=projectPartner name="project.partners[${projectPartner_index}]" index=projectPartner_index /]
                    [/#if]
                  [/#list]
                [/#if] 
                  
              </div>
            [/#list]
          </div>
          
          [#-- Section Buttons & hidden inputs--]
          [#include "/WEB-INF/views/projects/buttons-projects.ftl" /]
          
         
        [/@s.form] 
      </div>
    </div>  
</section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]


[#macro projectPartnerMacro element name index=-1 isTemplate=false]
  [#local isLeader = (element.leader)!false/]
  [#local isCoordinator = (element.coordinator)!false/]
  [#local isPPA = (action.isPPA(element.institution))!false /]
  
  <div id="projectPartner-${isTemplate?string('template',(projectPartner.id)!)}" class="projectPartner expandableBlock borderBox ${(isLeader?string('leader',''))!} ${(isCoordinator?string('coordinator',''))!}" style="display:${isTemplate?string('none','block')}">
    [#-- Partner Title --]
    <div class="blockTitle closed">
      [#-- Title --]
      <span><span class="partnerTitle"></span>${(element.institution.composedName)!'New Project Partner'}</span>

      [#-- Tags --]
      <div class="partnerTags pull-right">
        <span class="label label-success type-leader" style="display:${(isLeader?string('inline','none'))!'none'}">Leader</span>
        <span class="label label-default type-coordinator" style="display:${(isCoordinator?string('inline','none'))!'none'}">Coordinator</span>
        <span class="index ${isPPA?string('ppa','')}">${isPPA?string('PPA Partner','Partner')}</span>
      </div>
      
    </div>
    
    <div class="blockContent" style="display:block">
      <hr />
      
      <table class="table">
        <thead>
          <tr>
            <th class="amountType"> </th>
            <th class="text-center">W1/W2</th>
            <th class="text-center">W3</td>
            <th class="text-center">Bilateral</th>
            <th class="text-center">Center Funds</th>
            </tr>
        </thead>
        <tbody>
          [#-- Budget Amount --]
          <tr>
            <td class="amountType"> Budget: </td>
            <td>[@customForm.input name="" showTitle=false required=true editable=editable /]</td>
            <td>[@customForm.input name="" showTitle=false required=true editable=editable /]</td>
            <td>[@customForm.input name="" showTitle=false required=true editable=editable /]</td>
            <td>[@customForm.input name="" showTitle=false required=true editable=editable /]</td>
          </th>
          [#-- Budget Percentage --]
          <tr>
            <td class="amountType"> Gender %:</td>
            <td>[@customForm.input name="" showTitle=false required=true editable=editable /]</td>
            <td></td>
            <td></td>
            <td>[@customForm.input name="" showTitle=false required=true editable=editable /]</td>
          </th>
        </thead>
      </table>
      
      <h5 class="sectionSubTitle">W3 Funds & Bilateral:</h5>
      <div class="simpleBox">
        [#list 1..2 as funds]
          <div class="grayBox">
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ipsum dolores fugiat velit odit atque neque maiores nemo saepe quidem reiciendis corporis</p>
            <div class="row w3bilateralFund">
              <div class="col-md-4">
                <div class="row">
                  <div class="col-md-4"><strong>Type:</strong></div>
                  <div class="col-md-8">[@customForm.select name="" showTitle=false  disabled=!editable  listName="" keyFieldName=""  displayFieldName="" required=true editable=editable /]</div>
                </div>
              </div>
              <div class="col-md-4">
                <div class="row">
                  <div class="col-md-4"><strong>Amount:</strong></div>
                  <div class="col-md-8">[@customForm.input name="" showTitle=false required=true editable=editable /]</div>
                </div>
              </div>
              <div class="col-md-4">
                <div class="row">
                  <div class="col-md-5"><strong>Gender %:</strong></div>
                  <div class="col-md-7">[@customForm.input name="" showTitle=false required=true editable=editable /]</div>
                </div>
              </div>
            </div>
          </div>
        [/#list]
      </div>
      
    </div>
  
  </div>
[/#macro]


[#-- Get if the year is required--]
[#function isYearRequired year]
  [#if project.endDate??]
    [#assign endDate = (project.endDate?string.yyyy)?number]
    [#if reportingActive]
      [#return  (year == currentCycleYear)  && (endDate gte year) ]
    [#else]
      [#return  (year == currentCycleYear) && (endDate gte year) ]
    [/#if]
  [#else]
    [#return false]
  [/#if]
[/#function]