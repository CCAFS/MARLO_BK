[#ftl]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
[#macro projectsList projects={} owned=true canValidate=false canEdit=false isPlanning=false namespace="/" defaultAction="description"]
  <table class="projectsList" id="projects">
    <thead>
      <tr class="header">
        <th colspan="5">General Information</th>
        <th colspan="2">[@s.text name="projectsList.projectBudget" /] ${(reportingCycle?string(currentReportingYear,currentPlanningYear))!}</th> 
        <th colspan="3">Actions</th> 
      </tr>
      <tr class="subHeader">
        <th id="ids">[@s.text name="projectsList.projectids" /]</th>
        <th id="projectTitles" >[@s.text name="projectsList.projectTitles" /]</th>
        <th id="projectLeader" >[@s.text name="projectsList.projectLeader" /]</th>
        <th id="projectType">[@s.text name="projectsList.projectType" /]</th>
        <th id="projectFlagships">[@s.text name="projectsList.projectFlagships" /]</th>
        <th id="projectBudget">[@s.text name="projectsList.W1W2projectBudget" /]</th>
        <th id="projectBudget">[@s.text name="projectsList.W3BILATERALprojectBudget" /]</th>
        <th id="projectActionStatus">[@s.text name="projectsList.projectActionStatus" /]</th>
        <th id="projectDownload">[@s.text name="projectsList.download" /]</th>
        <th id="projectDelete">[@s.text name="projectsList.delete" /]</th>
        [#if isPlanning]
          <th id="projectBudget">[@s.text name="planning.projects.completion" /]</th>
        [/#if]
      </tr>
    </thead>
    <tbody>
    [#if projects?has_content]
      [#list projects as project]
        <tr>
        [#-- ID --]
        <td class="projectId">
          <a href="[@s.url namespace=namespace action=defaultAction][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> P${project.id}</a>
        </td>
          [#-- Project Title --]
          <td class="left"> 
            [#if project.title?has_content]
              <a href="[@s.url namespace=namespace action=defaultAction] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]" title="${project.title}">
              [#if project.title?length < 120] ${project.title}</a> [#else] [@utilities.wordCutter string=project.title maxPos=120 /]...</a> [/#if]
            [#else]
              <a href="[@s.url namespace=namespace action=defaultAction includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url] ">
                [@s.text name="projectsList.title.none" /]
              </a>
            [/#if]
          </td>
          [#-- Project Leader --]
          <td class=""> 
            [#if project.leader?has_content]${(project.leader.institution.acronym)!project.leader.institution.name}[#else][@s.text name="projectsList.title.none" /][/#if]
          </td>
          [#-- Project Type --]
          <td>
            [@s.text name="project.type.${(project.type?lower_case)!'none'}" /]
          </td>
          [#-- Flagship / Regions --]
          <td>
            [#if project.flagships?has_content]
              [#list project.flagships as element]<span class="programTag" style="background-color:${(element.color)!'#fff'}">${element.acronym}</span>[/#list]
            [#else]
              [@s.text name="projectsList.none" /]
            [/#if]
          </td>
          [#-- Budget W1/W2 --]
          <td class="budget"> 
            [#if project.totalBudget?has_content]
              <p id="">US$ <span id="">${((project.totalCcafsBudget)!0)?string(",##0.00")}</span></p> 
            [#else]
              [@s.text name="projectsList.none" /]
            [/#if]
          </td>
          [#-- Budget W3/ Bilateral --]
          <td class="budget"> 
            [#if project.totalBudget?has_content]
              <p id="">US$ <span id="">${((project.totalBilateralBudget)!0)?string(",##0.00")}</span></p> 
            [#else]
              [@s.text name="projectsList.none" /]
            [/#if]
          </td>
          [#-- Project Action Status --]
          <td>
            {TODO}
          </td>
          [#-- Track completition of entry --]
          [#if isPlanning]
          <td> <a href="#">Complete / Incomplete</a></td>
          [/#if]
          [#-- Summary PDF download --]
          <td>
            [#if true]
            <a href="[@s.url namespace="/summaries" action='project'][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='cycle']{TODO}[/@s.param][/@s.url]" target="__BLANK">
              <img src="${baseUrl}/images/global/download-summary.png" height="25" title="[@s.text name="summaries.project.download" /]" />
            </a>
            [#else]
              <img src="${baseUrl}/images/global/download-summary-disabled.png" height="25" title="[@s.text name="menu.link.disabled" /]" />
            [/#if]
          </td>
          [#-- Delete Project--]
          <td>
            [#--if (action.hasProjectPermission("deleteProject", project.id, "manage") && project.isNew(currentPlanningStartDate)) --]
            [#if true]
              <a id="removeProject-${project.id}" class="removeProject" href="#" title="">
                <img src="${baseUrl}/images/global/trash.png" title="[@s.text name="projectsList.deleteProject" /]" /> 
              </a>
            [#else]
              <img src="${baseUrl}/images/global/trash_disable.png" title="[@s.text name="projectsList.cantDeleteProject" /]" />
            [/#if]
          </td>
        </tr>  
      [/#list]
    [/#if]
    </tbody>
  </table>
[/#macro]

[#macro evaluationProjects projects={} owned=true canValidate=false canEdit=false isPlanning=false namespace="/" defaultAction="evaluation"]
  <table class="evaluationProjects" id="projects">
    <thead> 
      <tr class="subHeader">
        <th class="idsCol">[@s.text name="projectsList.projectids" /]</th>
        <th class="projectTitlesCol" >[@s.text name="projectsList.projectTitles" /]</th>
        <th class="leaderCol">Leader</th>
        <th class="focusCol">Region / Flagship</th>
        <th class="yearCol">Year</th>
        <th class="statusCol">Status</th>
        <th class="totalScoreCol">Total Score</th>
      </tr>
    </thead>
    <tbody>
    [#if projects?has_content]
      [#list projects as project]
        [#assign projectUrl][@s.url namespace=namespace action=defaultAction][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url][/#assign]
        <tr>
          [#-- ID --]
          <td class="projectId">
            <a href="${projectUrl}"> P${project.id}</a>
          </td>
          [#-- Project Title --]
          <td class="left"> 
            [#if project.title?has_content]
              <a href="${projectUrl}" title="${project.title}">[@utilities.wordCutter string=project.title maxPos=120 /]</a>  
            [#else]
              <a href="${projectUrl}">[@s.text name="projectsList.title.none" /]</a>
            [/#if]
          </td>
          [#-- Leader --]
          <td>[#if project.leadInstitutionAcronym?has_content]${project.leadInstitutionAcronym}[#else][@s.text name="projectsList.title.none" /][/#if]</td>
          [#-- Region / Flagship --]
          <td>
            [#if project.flagships?has_content][#list project.flagships as element]<p class="focus region">${(element.acronym)!}</p>[/#list][/#if]
            [#if project.regions?has_content][#list project.regions as element]<p class="focus flagship">${(element.acronym)!}</p>[/#list][/#if]
          </td>
          [#-- Year --]
          <td><p class="center">${project.yearEvaluation}</p></td>
          [#-- Status --]
          <td><p class="center">${project.statusEvaluation}</p></td>
          [#-- Total Score --]
          <td><p class="totalScore">${project.totalScoreEvaluation}</p></td>
        </tr>  
      [/#list]
    [/#if]
    </tbody>
  </table>
[/#macro]

[#macro dashboardProjectsList projects={} owned=true canValidate=false canEdit=false isPlanning=false namespace="/" defaultAction="description"]
  <table class="projectsList" id="projects">
    <thead>
      <tr class="subHeader">
        <th id="ids">[@s.text name="projectsList.projectids" /]</th>
        <th id="projectTitles" >[@s.text name="projectsList.projectTitles" /]</th>
        <th id="projectType">[@s.text name="projectsList.projectType" /]</th>
        [#if isPlanning]
          <th id="projectBudget">[@s.text name="planning.projects.completion" /]</th>
        [/#if]
      </tr>
    </thead>
    <tbody>
    [#if projects?has_content]
      [#list projects as project]
        <tr>
        [#-- ID --]
        <td class="projectId">
          <a href="[@s.url namespace=namespace action=defaultAction][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> P${project.id}</a>
        </td>
          [#-- Project Title --]
          <td class="left"> 
            [#if project.title?has_content]
              <a href="[@s.url namespace=namespace action=defaultAction] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]" title="${project.title}">
              [#if project.title?length < 120] ${project.title}</a> [#else] [@utilities.wordCutter string=project.title maxPos=120 /]...</a> [/#if]
            [#else]
              <a href="[@s.url namespace=namespace action=defaultAction includeParams='get'] [@s.param name='projectID']${project.id?c}[/@s.param][/@s.url] ">
                [@s.text name="projectsList.title.none" /]
              </a>
            [/#if]
          </td>
          [#-- Project Type --]
          <td>
            [@s.text name="project.type.${(project.type?lower_case)!'none'}" /]
          </td>
        </tr>  
      [/#list]
    [/#if]
    </tbody>
  </table>
[/#macro]