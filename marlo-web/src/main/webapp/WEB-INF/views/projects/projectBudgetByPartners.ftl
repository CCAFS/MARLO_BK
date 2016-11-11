[#ftl]
[#assign title = "Project Budget By Partners" /]
[#assign currentSectionString = "project-${actionName?replace('/','-')}-${projectID}" /]
[#assign pageLibs = ["select2"] /]
[#assign customJS = ["${baseUrl}/js/projects/projectBudgetByPartners.js", "${baseUrl}/js/global/autoSave.js","${baseUrl}/js/global/fieldsValidation.js"] /]
[#assign customCSS = ["${baseUrl}/css/projects/projectBudgetByPartners.css"] /]
[#assign currentSection = "projects" /]
[#assign currentStage = "budgetByPartners" /]

[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/projects", "action":"${(crpSession)!}/projectsList"},
  {"label":"projectBudgetByPartners", "nameSpace":"/projects", "action":""}
] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]

<div class="container">
  <div class="helpMessage"><img src="${baseUrl}/images/global/icon-help.png" /><p> [@s.text name="projectBudgetByPartners.help" /]    ${(project.bilateralProject?string)}</p></div> 
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
          
          [#-- Section Title --]
          <h3 class="headTitle">[@s.text name="projectBudgetByPartners.title" /]</h3>
          
          [#if project.startDate?? && project.endDate??]
          
            [#assign startYear = (project.startDate?string.yyyy)?number /]
            [#assign endYear = (project.endDate?string.yyyy)?number /]
            [#if currentCycleYear gt endYear][#assign selectedYear = endYear /][#else][#assign selectedYear = currentCycleYear /][/#if]
            [#assign type = { 
              'w1w2':         1,
              'w3':           2,
              'bilateral':    3,
              'centerFunds':  4
            } /]
            
            [#-- Year Tabs --]
            <ul class="nav nav-tabs budget-tabs" role="tablist">
              [#list startYear .. endYear as year]
                <li class="[#if year == selectedYear]active[/#if]"><a href="#year-${year}" role="tab" data-toggle="tab">${year} [@customForm.req required=isYearRequired(year) && editable /] </a></li>
              [/#list]
            </ul>
            
            [#-- Years Content --]
            <div class="tab-content budget-content">
              [#list startYear .. endYear as year]
                <div role="tabpanel" class="tab-pane [#if year == selectedYear]active[/#if]" id="year-${year}">
                
                  [#-- Budgest cannot be editable message --]
                  [#if !isYearEditable(year) && editable]<div class="note">Budgets for ${year} cannot be editable.</div>[/#if]
                  
                  <div class="overallYearBudget fieldset clearfix">
                    <h5 class="title">Overall ${year} budget</h5>
                    <div class="row">
                      [#-- W1/W2 --]
                      <div class="col-md-3"><h5 class="subTitle">W1/W2 <small>US$ <span class="totalByYear-${type.w1w2}">${action.getTotalYear(year,1)?number?string(",##0.00")}</span></small></h5></div>
                      [#-- W3 --]
                      <div class="col-md-3"><h5 class="subTitle">W3 <small>US$ <span class="totalByYear-${type.w3}">${action.getTotalYear(year,2)?number?string(",##0.00")}</span></small></h5></div>
                      [#-- Bilateral  --]
                      <div class="col-md-3"><h5 class="subTitle">Bilateral <small>US$ <span class="totalByYear-${type.bilateral}">${action.getTotalYear(year,3)?number?string(",##0.00")}</span></small></h5></div>
                      [#-- Center Funds --]
                      <div class="col-md-3"><h5 class="subTitle">Center Funds <small>US$ <span class="totalByYear-${type.centerFunds}">${action.getTotalYear(year,4)?number?string(",##0.00")}</span></small></h5></div>
                    </div>
                  </div>
                
                  [#if projectPPAPartners?has_content]
                    [#list projectPPAPartners as projectPartner]
                      [@projectPartnerMacro element=projectPartner name="project.partners[${projectPartner_index}]" index=projectPartner_index selectedYear=year/]
                    [/#list]
                  [#else]
                    <div class="simpleBox emptyMessage text-center">Before entering budget information, you need to add project partner in <a href="[@s.url action="${crpSession}/partners"][@s.param name="projectID" value=projectID /][@s.param name="edit" value=true /][/@s.url]">partners section</a></div>
                  [/#if]
                </div>
              [/#list]  
            </div>
            
            [#-- Section Buttons & hidden inputs--]
            [#include "/WEB-INF/views/projects/buttons-projects.ftl" /]
            
          [#else]
            <div class="simpleBox emptyMessage text-center">Before entering this section, please fill project start & end date <a href="[@s.url action="${crpSession}/description"][@s.param name="projectID" value=projectID /][@s.param name="edit" value=true /][/@s.url]">description section </a>and click <span class="label label-success">save</span></div>  
          [/#if]
         
        [/@s.form] 
      </div>
    </div>  
</section>

<span id="budgetIndex" style="display:none">${budgetIndex+1}</span>

[#-- Bilateral Co-Funded Project Popup --]
[#include "/WEB-INF/global/macros/fundingSourcesPopup.ftl"]

[#-- W3/bilaterl Fund Template --]
[@fundingSourceMacro element={} name="project.budgets" selectedYear=-1 index=-1  isTemplate=true /]


[#include "/WEB-INF/global/pages/footer.ftl"]


[#macro projectPartnerMacro element name index=-1 selectedYear=0 isTemplate=false]
  [#local isLeader = (element.leader)!false/]
  [#local isCoordinator = (element.coordinator)!false/]
  [#local isPPA = (action.isPPA(element.institution))!false /]
  
  <div id="projectPartner-${isTemplate?string('template',(element.id)!)}" class="projectPartner expandableBlock borderBox ${(isLeader?string('leader',''))!} ${(isCoordinator?string('coordinator',''))!}" style="display:${isTemplate?string('none','block')}">
    [#-- Partner Title --]
    <div class="blockTitle opened">
      [#-- Title --] 
      <span class="partnerTitle">${(element.institution.composedName)!''}</span>
      <span class="partnerInstitutionId" style="display:none">${(element.institution.id)!}</span>
      [#-- Tags --]
      <div class="partnerTags pull-right">
        <span class="label label-success type-leader" style="display:${(isLeader?string('inline','none'))!'none'}">Leader</span>
        <span class="label label-default type-coordinator" style="display:${(isCoordinator?string('inline','none'))!'none'}">Coordinator</span>
        <span class="index ${isPPA?string('ppa','')}">${isPPA?string('Managing / PPA Partner','Partner')}</span>
      </div>
      <div class="clearfix"></div>
    </div>
    
    <div class="blockContent" style="display:block">
      <hr />
      <table class="table">
        <thead>
          <tr>
            <th class="amountType"> </th>
            [#-- W1/W2 --]
            <th class="text-center">W1/W2</th>
            [#-- W3 --]
            <th class="text-center">W3</td>
            [#-- Bilateral  --]
            <th class="text-center">Bilateral</th>
            [#-- Center Funds --]
            <th class="text-center">Center Funds</th>
          </tr>
        </thead>
        <tbody>
          [#-- Budget Amount --]
          <tr>
            <td class="amountType"> Budget:</td>
            [#-- W1/W2 --]
            <td class="budgetColumn">
              <div class="input"><p>US$ <span class="currencyInput totalByPartner-${type.w1w2}">${((action.getTotalAmount(element.institution.id, selectedYear, type.w1w2))!0)?number?string(",##0.00")}</span></p></div>
            </td>
            [#-- W3 --]
            <td class="budgetColumn">
              <div class="input"><p>US$ <span class="currencyInput totalByPartner-${type.w3}">${((action.getTotalAmount(element.institution.id, selectedYear, type.w3))!0)?number?string(",##0.00")}</span></p></div>
            </td>
            [#-- Bilateral  --]
            <td class="budgetColumn">
              <div class="input"><p>US$ <span class="currencyInput totalByPartner-${type.bilateral}">${((action.getTotalAmount(element.institution.id, selectedYear, type.bilateral))!0)?number?string(",##0.00")}</span></p></div>
            </td>
            [#-- Center Funds --]
            <td class="budgetColumn">
              <div class="input"><p>US$ <span class="currencyInput totalByPartner-${type.centerFunds}">${((action.getTotalAmount(element.institution.id, selectedYear, type.centerFunds))!0)?number?string(",##0.00")}</span></p></div>
            </td>
            
          </tr>
          [#-- Budget Percentage --]
          [#if project.projectEditLeader]
          <tr>
            <td class="amountType"> Gender %:</td> 
            [#-- W1/W2 --]
            <td class="budgetColumn">
              <div class="input"><p><span class="percentageLabel type-${type.w1w2}">${((action.getTotalGenderPer(element.institution.id, selectedYear, type.w1w2))!0)}%</span></p></div>
              <div class="row percentageAmount type-${type.w1w2} text-center">
                <small>US$ <span>${((action.getTotalGender(element.institution.id, selectedYear, type.w1w2))!0)?number?string(",##0.00")}</span></small>
              </div>
            </td>
            [#-- W3 --]
            <td class="budgetColumn">
              <div class="input"><p><span class="percentageLabel type-${type.w3}">${((action.getTotalGenderPer(element.institution.id, selectedYear, type.w3))!0)}%</span></p></div>
              <div class="row percentageAmount type-${type.w3} text-center">
                <small>US$ <span>${((action.getTotalGender(element.institution.id, selectedYear, type.w3))!0)?number?string(",##0.00")}</span></small>
              </div>
            </td>
            [#-- Bilateral  --]
            <td class="budgetColumn">
              <div class="input"><p><span class="percentageLabel type-${type.bilateral}">${((action.getTotalGenderPer(element.institution.id, selectedYear, type.bilateral))!0)}%</span></p></div>
              <div class="row percentageAmount type-${type.bilateral} text-center">
                <small>US$ <span>${((action.getTotalGender(element.institution.id, selectedYear, type.bilateral))!0)?number?string(",##0.00")}</span></small>
              </div>
            </td>
            [#-- Center Funds --]
            <td class="budgetColumn">
              <div class="input"><p><span class="percentageLabel type-${type.centerFunds}">${((action.getTotalGenderPer(element.institution.id, selectedYear, type.centerFunds))!0)}%</span></p></div>
              <div class="row percentageAmount type-${type.centerFunds} text-center">
                <small>US$ <span>${((action.getTotalGender(element.institution.id, selectedYear, type.centerFunds))!0)?number?string(",##0.00")}</span></small>
              </div>
            </td>
          </tr>
          [/#if]
        </tbody>
      </table>
      
      <h5 class="sectionSubTitle">Funding Sources:</h5>
      <div class="projectW3bilateralFund-block">
        [#-- Funding sources --]
        [#assign fundingSources = 0 /]
        <div class="projectW3bilateralFund-list simpleBox">
          [#attempt]
            [#list action.getBudgetsByPartner(element.institution.id,selectedYear) as budget ]
                [#assign fundingSources++ /]
                [#local indexBudgetfundingSource=action.getIndexBudget(element.institution.id,selectedYear,budget.fundingSource.budgetType.id,budget.fundingSource.id) ]
                [@fundingSourceMacro element=budget name="project.budgets" selectedYear=selectedYear  index=indexBudgetfundingSource /]
            [/#list]
          [#recover]
            ERROR LOADING FUNDING SOURCES
          [/#attempt]
          
          [#if fundingSources == 0]
            [#if editable && isYearEditable(selectedYear) && action.canSearchFunding(element.institution.id)]
              <p class="emptyMessage text-center">Add a new one clicking on "[@s.text name="form.buttons.selectProject" /]" button below.</p>
            [#else]
              <p class="emptyMessage text-center">Theres is not a funding source added.</p>
            [/#if]
          [/#if]
        </div>
        [#-- Search project bilateral co-funded --]
        [#if editable  && isYearEditable(selectedYear) && action.canSearchFunding(element.institution.id)]
        <div class="text-right">
          <div class="searchProject button-blue ${action.canAddFunding(element.institution.id)?string('canAddFunding','')}"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> [@s.text name="form.buttons.selectProject" /]</div>
        </div>
        [/#if]
      </div>
      
    </div>
  </div>
[/#macro]

[#macro fundingSourceMacro element name selectedYear index=-1  isTemplate=false]
  <div id="projectW3bilateralFund-${isTemplate?string('template', index )}" class="projectW3bilateralFund expandableBlock grayBox" style="display:${isTemplate?string('none','block')}">
    [#local customName = "${name}[${index}]" /]
    [#-- Remove --]
    [#if (editable && isYearEditable(selectedYear)) || isTemplate]<div class="removeIcon removeW3bilateralFund" title="Remove"></div>[/#if]
    [#-- Project Title --]
    
    <p class="checked">
      <small>Funding source #<span class="titleId">${(element.fundingSource.id)!}</span></small> -
      <small class="grayLabel"> (Remaining budget US$ <span class="projectAmount">${((element.fundingSource.getRemaining(selectedYear))!0)?number?string(",##0.00")}</span>) </small>
    </p> 
    
    <a href="[@s.url namespace="/fundingSources" action="${crpSession}/fundingSource"][@s.param name="fundingSourceID" value="${(element.fundingSource.id)!}" /][/@s.url]" class="" target="_BLANK"> 
       
      <p> <span class="title">${(element.fundingSource.title)!}</span> </p>
    </a>

    <input type="hidden" class="id " name="${customName}.id" value="${(element.id)!}"/>
    <input type="hidden" class="institutionId" name="${customName}.institution.id" value="${(element.institution.id)!}"/>
    <input type="hidden" class="selectedYear" name="${customName}.year" value="${(selectedYear)!}"/>
    <input type="hidden" class="projectId institution-${(element.institution.id)!} year-${(selectedYear)!}" name="${customName}.fundingSource.id" value="${(element.fundingSource.id)!}"/>
    
    [#-- Project Fund --]
    <div class="row w3bilateralFund">
      <div class="col-md-4">
        <div class="row col-md-6"> <strong>Type:</strong> </div>
        <div class="row col-md-8">
          <span class="budgetTypeName">${(element.fundingSource.budgetType.name)!}</span> 
          <input type="hidden" class="budgetTypeId" name="${customName}.budgetType.id" value="${(element.fundingSource.budgetType.id)!}" />
        </div>
      </div>
      <div class="col-md-4">
        <div class="row col-md-5">
          <div class="row"><strong>Amount:</strong></div>
        </div>
        <div class="row col-md-9">
        [#if (editable && isYearEditable(selectedYear)) || isTemplate]
          [@customForm.input name="${customName}.amount" i18nkey="budget.amount" showTitle=false className="currencyInput fundInput type-${(element.fundingSource.budgetType.id)!'none'}" required=true /]
        [#else]
          <div class="input"><p>US$ <span>${((element.amount)!0)?number?string(",##0.00")}</span></p></div>
          <input type="hidden" name="${customName}.amount" value="${(element.amount)!0}" />
        [/#if]
        </div>
      </div>
      <div class="col-md-4">
        [#if project.projectEditLeader]
          <div class="row col-md-6"> <div class="row"><strong>Gender %:</strong></div> </div>
          <div class="row col-md-7">
          [#if (editable && isYearEditable(selectedYear)) || isTemplate]
            [@customForm.input name="${customName}.genderPercentage" i18nkey="budget.genderPercentage" showTitle=false className="percentageInput type-${(element.fundingSource.budgetType.id)!'none'}" required=true   /]
          [#else]  
            <div class="input"><p><span>${((element.genderPercentage)!0)}%</span></p></div>
            <input type="hidden" name="${customName}.genderPercentage" value="${(element.genderPercentage)!0}" />
          [/#if]
          </div>
        [/#if]
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

[#-- Get if the year is editable--]
[#function isYearEditable year]
  [#if project.endDate??]
    [#assign endDate = (project.endDate?string.yyyy)?number]
    [#if reportingActive]
      [#return  (year gte currentCycleYear) ]
    [#else]
      [#return  (year gte currentCycleYear) ]
    [/#if]
  [#else]
    [#return false]
  [/#if]
[/#function]