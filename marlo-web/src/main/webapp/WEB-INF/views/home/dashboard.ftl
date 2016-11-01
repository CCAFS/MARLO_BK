[#ftl]
[#assign title = "Welcome to MARLO" /]
[#assign currentSectionString = "${actionName?replace('/','-')}" /]
[#assign pageLibs = ["jQuery-Timelinr","cytoscape","cytoscape-panzoom","cytoscape-qtip","qtip2","datatables.net", "datatables.net-bs"] /]
[#assign customJS = ["${baseUrl}/js/home/dashboard.js","${baseUrl}/js/global/impactGraphic.js"] /]
[#assign customCSS = ["${baseUrl}/css/home/dashboard.css","${baseUrl}/css/global/customDataTable.css","${baseUrl}/css/global/impactGraphic.css"] /]
[#assign currentSection = "home" /]
[#assign breadCrumb = [
  {"label":"home", "nameSpace":"", "action":""}
]/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/projectsListTemplate.ftl" as projectList /]

[#assign timeline = [
  {"id":"23-11-2016", "startDate":"Nov 23th", "endDate":"Nov 25th","what":"MARLO opens for impact pathway and project pre-setting (Mgmt Liaisons and CPs)","who":"MARLO Team, Finance, MLs & CPs"},
  {"id":"28-11-2016", "startDate":"Nov 28th", "endDate":"Dec 9th","what":"MARLO opens for planning (Project Leaders)","who":"Project Leaders"},
  {"id":"12-12-2016", "startDate":"Dec 12th ", "endDate":"Dec 14th","what":"Management liaison to review the plan, liaise with the PL and approve/make recommendations for project submission","who":"MLs"},
  {"id":"15-12-2016", "startDate":"Dec 15th", "endDate":"Dec 21nd","what":"PLs to make changes accordingly and resubmit the project","who":"PLs"},
  {"id":"22-12-2016", "startDate":"Dec 22nd", "endDate":"","what":"MARLO closes planning stage","who":"KDS Team"}
]/]



<section class="marlo-content">
  <div class="container">
    [#-- What do you want to do --]
    <div class="homeTitle"><b>[@s.text name="dashboard.decisionTree.title" /]</b></div>
    <div id="decisionTree" class="borderBox">
      <a href="[@s.url namespace="/projects" action='${crpSession}/addNewCoreProject'/]"><div id="newProject" class="option"><p>[@s.text name="dashboard.decisionTree.newProject" /]</p></div></a>
      <div id="updatePlanning" class="option disabled" title="This link is disabled"><p>[@s.text name="dashboard.decisionTree.updateProject" /]</p></div>
      <div id="reportProject" class="option disabled" title="This link is disabled"><p>[@s.text name="dashboard.decisionTree.evaluateProject" /]</p></div>
      <div class="clearfix"></div>
    </div>
    
    [#-- Shorcuts --]    
    <div id="shorcuts" class="col-md-5">
      <div class="homeTitle"><strong>Timeline</strong></div>   
      <div class="borderBox col-md-12">
        <div id="timeline">
        <span class="timelineControl leftControl glyphicon glyphicon-chevron-left"></span>
        <span class="timelineControl rigthControl control glyphicon glyphicon-chevron-right"></span>
          <ul id="dates">
          [#list timeline as time]
            <li><a href="#${time.id}">${time.startDate}</a></li>
          [/#list]
          </ul>
          
          <div class="borderBox">
            <ul id="issues">
            [#list timeline as time]
              <li id="${time.id}">
                <h1>${time.startDate} - ${time.endDate}</h1>
                <hr />
                <label for="">What?</label>
                <p> ${time.what}</p>
                <hr />
                <label for="">Who?</label>
                <p>${time.who}</p>
              </li>
            [/#list]
            </ul> 
          </div>
        </div>
      </div>
    </div>     
    
    [#-- Dashboard --]   
    <div id="dashboardContent" class="col-md-7">
      <div class="homeTitle col-md-12"><strong>Dashboard</strong></div>
      <div class=" col-md-12">
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active"><a  id="projects" href="#myProjects" aria-controls="myProjects" role="tab" data-toggle="tab">My projects</a></li>
          <li role="presentation"><a id="impact" href="#impactP" aria-controls="impactP" role="tab" data-toggle="tab">Impact pathway</a></li>
        </ul>
        
        <div class="tab-content">
          <div role="tabpanel" class="tab-pane fade in active" id="myProjects">
            [@projectList.dashboardProjectsList projects=myProjects canValidate=true canEdit=true namespace="/projects" defaultAction="${(crpSession)!}/description" /]
          </div>
          
          <div role="tabpanel" class="tab-pane fade" id="impactP">
            <div id="infoRelations" class="panel panel-default">
              <div class="panel-heading"><strong>Relations</strong></div>
              <div id="infoContent" class="panel-body">
               <ul></ul>
              </div>
            </div>
            <div id="contentGraph">
              [#-- Download button--]
             [#--  <span id="buttonShowAll"><span class="glyphicon glyphicon-download-alt"></span></span>--]
              <div id="impactGraphic" ></div>
              [#-- Download button--]
              <span title="View full graph" id="fullscreen" class="glyphicon glyphicon-fullscreen"></span>
            </div>
          </div>
        </div>  
      </div>    
    </div>
    
    
    <div id="impactGraphic-content"  style="display:none;" >
  
  [#-- Information panel --]
  <div id="infoRelation" class="panel panel-default">
    <div class="panel-heading"><strong>Relations</strong></div>
    <div id="infoContent" class="panel-body">
     <ul></ul>
    </div>
  </div>
  
  [#-- Controls --]
  <div id="controls" class="">
    <span id="zoomIn" class="glyphicon glyphicon-zoom-in tool"></span>
    <span id="zoomOut" class="glyphicon glyphicon-zoom-out tool "></span>
    <span id="panRight" class="glyphicon glyphicon-arrow-right tool "></span>
    <span id="panDown" class="glyphicon glyphicon-arrow-down tool "></span>
    <span id="panLeft" class="glyphicon glyphicon-arrow-left tool "></span>
    <span id="panUp" class="glyphicon glyphicon-arrow-up tool "></span>
    <span id="resize" class="glyphicon glyphicon-resize-full  tool"></span>
  </div>
  
  [#-- Download button--]
  <a class="download" href=""><span title="download" id="buttonDownload"><span class="glyphicon glyphicon-download-alt"></span></span></a>
  
  <div id="impactGraphic-fullscreen"></div>
</div>
  </div>
 
 
</section>

[#include "/WEB-INF/global/pages/footer.ftl" /]