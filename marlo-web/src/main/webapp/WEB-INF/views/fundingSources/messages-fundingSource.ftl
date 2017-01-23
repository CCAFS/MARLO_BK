[#ftl]
[#-- Projects data information --]
[#include "/WEB-INF/views/fundingSources/dataInfo-funding.ftl" /]

[#-- History Message --]
[#if transaction??]
  <div class="history-mode text-center animated flipInX">
    [#if transaction == "-1"]
      <p>[@s.text name="fundingSource.message.historyNotFound" /]</p>
    [#else]
      <p>[@s.text name="fundingSource.message.historyVersion" ]  
          [@s.param]<span>${fundingSource.modifiedBy.composedName?html}</span>[/@s.param]
          [@s.param]<span>${fundingSource.activeSince?datetime}</span>[/@s.param]
          [@s.param]<a href="[@s.url][@s.param name="projectID" value=fundingSourceID /][@s.param name="edit" value="true"/][/@s.url]">here</a>[/@s.param]
         [/@s.text]
      </p>
    [/#if]
  </div>
[/#if]


[#-- Privileges Message --]
[#if (!canEdit && !(transaction??) && !(submission?has_content)) || crpClosed]
  [#if crpClosed]
    <p class="readPrivileges">MARLO is closed.</p>
  [#else]
    <p class="readPrivileges">[@s.text name="saving.read.privileges.section" /]</p>
  [/#if]
[/#if]


[#-- Concurrence Message --]
<div id="concurrenceMessage" class="text-center" style="display:none">
  <p><span class="glyphicon glyphicon-flash"></span> 
  [@s.text name="fundingSource.message.sectionSaved"]
    [@s.param]<span class="person"></span>[/@s.param]
    [@s.param] <a href="#" onclick="location.reload()">click here</a> [/@s.param]
  [/@s.text]
  </p>
</div>


[#-- Concurrence Hidden Block --]
<div id="concurrenceBlock" class="text-center" style="display:none">
  <div class="layer"></div>
  <div class="content">
    <span class="glyphicon glyphicon-lock"></span>
    <p>[@s.text name="fundingSource.message.concurrence" /] [@s.text name="fundingSource.message.concurrenceNotEditing"][@s.param] <a href="[@s.url][@s.param name="fundingSourceID" value=fundingSourceID /][/@s.url]">click here</a> [/@s.param][/@s.text]</p>
  </div>
</div>
