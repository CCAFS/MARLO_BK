var $globalUnitSelect, $phasesSelect, $deliverablesList;
$(document).ready(init);

function init() {
  $entitySelect = $('#entityID');
  $globalUnitSelect = $('#globalUnitID');
  $phasesSelect = $('#phaseID');
  $deliverablesList = $('#deliverables-checkbox table tbody');
  $deliverablesSelect = $('#deliverableID');
  // Events
  attachEvents();
  /* Init Select2 plugin */
  $('form select').select2();
}

function attachEvents() {
  $entitySelect.on('change', updateEntity);
  $globalUnitSelect.on('change', updatePhases);
  $phasesSelect.on('change', updateDeliverables);

  $deliverablesSelect.on('change', updateCurrentTexts);
  // Expand alls Outcomes
  $("#anotherPhase").on('click', changeValue);
  $("#anotherProject").on('click', changeValue);

  $('#toggleSelectAll').on('change', function() {
    $deliverablesList.find('tr:visible').find('.deliverableCheck').prop("checked", this.checked);
    updateCheckedCount();
  });

  $('.deliverableCheck').on('change', function() {
    updateCheckedCount();
  });

  $('#filterButton').on('click', function() {
    var fiterText = $.trim($('#filterText').val());
    if(fiterText) {
      $deliverablesList.find('tr').hide();
      $('.deliverableCheck').prop("checked", false);
      $.each(fiterText.split(','), function(i,id) {
        var id = $.trim(id);
        if(id) {
          $('tr#' + id).show();
          $('tr#' + id).find('.deliverableCheck').prop("checked", true);
        }
      });
    } else {
      $deliverablesList.find('tr').show();
      $('.deliverableCheck').prop("checked", true);
    }
    updateCheckedCount();
  });
}

function updateCurrentTexts(){
  getproject(parseInt($( "#deliverableID" ).val()));
}
function getproject(Id){

  $.ajax({
    url: baseURL + '/getProjectByDeliverable.do',
    data: {
      deliverableId: Id
        // phaseID: phaseID,
        // financeCode: financeCode,
        // institutionLead: leadPartnerID
    },
    beforeSend: function() {
      // console.log("before");
    },
    success: function(r) {
    // console.log("succes");
    setCurrentProject(r);
    },
    error: function(e) {
    },
    complete: function() {
      // console.log("complete");
    }
});

}


function setCurrentProject(project){
  auxProject = project.projects[0];
  // console.log(auxProject);
  $("#currentProject").html("P"+auxProject.projectID+ " - " + auxProject.title); 
}

function changeValue(){
  //  console.log($(this).val());
   if($(this).val()=="project"){
    $("#anotherPhaseContent").hide();
    $("#anotherProjectContent").show();
   }else{
    $("#anotherProjectContent").hide();
    $("#anotherPhaseContent").show();
   }
  //  console.log($("#anotherPhase").val(),$("#anotherProject").val());

}

function updateEntity() {
  var entityName = $(this).find(":selected").text();
  var entityAction = $(this).find(":selected").classParam('action');
  $('th.entityName').text(entityName);
  $("#bulkReplicationForm").attr("action", "./" + entityAction + ".do");

  $phasesSelect.empty();
  $deliverablesList.empty();
  $('.count').text(0);
}

function updatePhases() {
  var globalUnitID = this.value;

  if(globalUnitID == "-1") {
    return;
  }

  $.ajax({
      url: baseURL + "/getPhasesByGlobalUnit.do",
      data: {
        selectedGlobalUnitID: globalUnitID
      },
      beforeSend: function() {
        $('.loading').fadeIn();
        $phasesSelect.empty();
        $deliverablesList.empty();
        $('.count').text(0);
      },
      success: function(data) {
        $phasesSelect.addOption('-1', 'Select an option...');
        $.each(data.phasesbyGlobalUnit, function(i,e) {
          $phasesSelect.addOption(e.id, e.name + ' ' + e.year);
        });
      },
      complete: function() {
        $('.loading').fadeOut();
        updateCheckedCount();
      }
  });
}

function updateDeliverables() {
  var phaseID = this.value;
  if(phaseID == "-1") {
    return;
  }

  $.ajax({
      url: baseURL + "/" + $('select#entityID').val() + ".do",
      data: {
        selectedPhaseID: phaseID
      },
      beforeSend: function() {
        $('.loading').fadeIn();
        $deliverablesList.empty();

      },
      success: function(data) {
        var dataArray = (data.entityByPhaseList);

        $.each(dataArray, function(i,e) {
          var $checkmarkRow = $('.check-template tr').clone(true).removeAttr('id');
          $checkmarkRow.attr('id', e.id);
          $checkmarkRow.find('input').addClass('deliverableCheckAdded');
          $checkmarkRow.find('input').val(e.id);
          $checkmarkRow.find('.id').text(e.id);
          $checkmarkRow.find('.labelText').text(e.composedName);

          // console.log($checkmarkRow);
          $deliverablesList.append($checkmarkRow);

        });
      },
      complete: function() {
        $('.loading').fadeOut();
        updateCheckedCount();
      }
  });
}

function updateCheckedCount() {
  var checkedCounted = $('.deliverableCheckAdded:checked').length;
  $('.count').text(checkedCounted);

  if(checkedCounted) {
    // $('.controls-block').fadeIn();
  } else {
    // $('.controls-block').fadeOut();
  }
}
