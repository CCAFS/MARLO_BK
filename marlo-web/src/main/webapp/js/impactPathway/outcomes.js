$(document).ready(init);

function init() {

  /* Declaring Events */
  attachEvents();

  /* Init Select2 plugin */
  $('outcomes-list select').select2();

  /* Numeric Inputs */
  $('input.targetValue , input.targetYear').numericInput();

  /* Percentage Inputs */
  $('.outcomes-list input.contribution').percentageInput();

}

function attachEvents() {

  // Add an Outcome
  $('.addOutcome').on('click', addOutcome);
  // Remove an Outcome
  $('.removeOutcome').on('click', removeOutcome);

  // Add a Milestone
  $('.addMilestone').on('click', addMilestone);
  // Remove a Milestone
  $('.removeMilestone').on('click', removeMilestone);

  // Add a Sub IDO
  $('.addSubIdo').on('click', addSubIdo);
  // Remove a Sub IDO
  $('.removeSubIdo').on('click', removeSubIdo);

  // Select an Ido
  $('select.idoId').on('change', function() {
    var idoId = $(this).val();
    var $subIdosSelect = $(this).parents('div.subIdo').find('select.subIdoId');
    loadSubIdosByIdoId(idoId, $subIdosSelect);
  });

  // Add an assumption
  $('.addAssumption').on('click', addAssumption);
  // Remove assumption
  $('.removeAssumption').on('click', removeAssumption);

}

/**
 * Outcome Functions
 */

function addOutcome() {
  var $list = $('.outcomes-list');
  var $item = $('#outcome-template').clone(true).removeAttr("id");
  $item.find('select').select2({
    width: '100%'
  });
  $list.append($item);
  // updateOutcomesIndexes($list, "outcome");
  updateAllIndexes();
  $item.show('slow');
}

function removeOutcome() {
  var $list = $(this).parents('.outcomes-list');
  var $item = $(this).parents('.outcome');
  $item.hide(function() {
    $item.remove();
    // updateOutcomesIndexes($list, "outcome");
    updateAllIndexes();
  });
}

/**
 * Milestone Functions
 */

function addMilestone() {
  var $list = $(this).parents('.outcome').find('.milestones-list');
  var $item = $('#milestone-template').clone(true).removeAttr("id");
  $item.find('select').select2({
    width: '100%'
  });
  $list.append($item);
  // updateMilestonesIndexes($list, "outcome[0].milestones");
  updateAllIndexes();
  $item.show('slow');
  // Hide empty message
  $(this).parents('.outcome').find('.milestones-list p.message').hide();
}

function removeMilestone() {
  var $list = $(this).parents('.outcome').find('.milestones-list');
  var $item = $(this).parents('.milestone');
  $item.hide(function() {
    $item.remove();
    updateAllIndexes();
  });
}

/**
 * SUB-IDOs Functions
 */

function verifyPercentage($subIdosList) {
  $inputsPercentage = $($subIdosList).find(".contribution");
  $inputsPercentage.each(function() {
    console.log($(this).val());
  });
}

function addSubIdo() {
  var $list = $(this).parents('.outcome').find('.subIdos-list');
  var $item = $('#subIdo-template').clone(true).removeAttr("id");
  $item.find('select').select2({
    width: '100%'
  });
  $item.find('input.contribution').percentageInput();
  $list.append($item);
  updateAllIndexes();
  $item.show('slow');
  // Hide empty message
  $(this).parents('.outcome').find('.subIdos-list p.message').hide();
  verifyPercentage($list);
}

function removeSubIdo() {
  var $list = $(this).parents('.outcome').find('.subIdos-list');
  var $item = $(this).parents('.subIdo');
  $item.hide(function() {
    $item.remove();
    updateAllIndexes();
  });
}

function loadSubIdosByIdoId(idoId,select) {
  // console.log(idoId);
  // console.log($(select));
  $loader = $(select).parents('div.subIdo').find('.loading');
  if(idoId == "-1") {
    $(select).attr('disabled', true);
  } else {
    $.ajax({
        'url': baseURL + '/SubIDObyIDO.do',
        'type': "GET",
        'data': {
          idoID: idoId
        },
        'dataType': "json",
        beforeSend: function() {
          $loader.fadeIn('slow');
        },
        success: function(data) {
          // First delete all the options already present in the subtype select
          $(select).empty();
          $(select).append(setOption('-1', 'Select an option'));
          $.each(data.subIdos, function(index,item) {
            var isSelected = (($(select).val() == item.id) ? "selected" : "");
            $(select).append("<option value='" + item.id + "' " + isSelected + ">" + item.description + "</option>");
          });
          // Select enabled
          $(select).attr('disabled', false);
          // Refresh the plugin in order to show the changes
          $(select).select2();
        },
        complete: function() {
          $loader.fadeOut();
        },
        error: function() {
        }
    });
  }

}

/**
 * Assumptions Functions
 */

function addAssumption() {
  var $assumptionsList = $(this).parents('.subIdo').find('.assumptions-list');
  var $item = $('#assumption-template').clone(true).removeAttr("id");
  $assumptionsList.append($item);
  updateAllIndexes();
  $item.show('slow');
}

function removeAssumption() {
  var $assumptionsList = $(this).parents('.subIdo').find('.assumptions-list');
  var $item = $(this).parents('.assumption');
  $item.hide(function() {
    $item.remove();
    updateAllIndexes();
  });
}

/**
 * General Function
 */

function updateAllIndexes() {
  // All Outcomes List
  $('.outcomes-list').find('.outcome').each(function(i,outcome) {
    var outcomesName = 'outcomes' + '[' + i + '].';
    $(outcome).find('span.index').html(i + 1);
    $(outcome).find('.outcome-statement').attr('name', outcomesName + 'description');
    $(outcome).find('.targetValue').attr('name', outcomesName + 'value');
    $(outcome).find('.targetYear').attr('name', outcomesName + 'year');
    $(outcome).find('.targetUnit').attr('name', outcomesName + 'srfTargetUnit.id');
    $(outcome).find('.outcomeId').attr('name', outcomesName + 'id');

    // Update Milestones
    $(outcome).find('.milestone').each(function(i,milestone) {
      var milestoneName = outcomesName + 'milestones' + '[' + i + '].';
      $(milestone).find('span.index').text(i + 1);
      $(milestone).find('.milestone-statement').attr('name', milestoneName + 'title');
      $(milestone).find('.targetValue').attr('name', milestoneName + 'value');
      $(milestone).find('.targetYear').attr('name', milestoneName + 'year');
      $(milestone).find('.targetUnit').attr('name', milestoneName + 'srfTargetUnit.id');
      $(milestone).find('.mileStoneId').attr('name', milestoneName + 'id');
    });

    // Update SubIdos
    $(outcome).find('.subIdo').each(function(i,subIdo) {
      var subIdoName = outcomesName + 'subIdos' + '[' + i + '].';
      $(subIdo).find('span.index').text(i + 1);
      $(subIdo).find('.subIdoId').attr('name', subIdoName + 'srfSubIdo.id');
      $(subIdo).find('.idoId').attr('name', subIdoName + 'srfSubIdo.srfIdo.id');
      $(subIdo).find('.contribution').attr('name', subIdoName + 'contribution');
      $(subIdo).find('.programSubIDOId').attr('name', subIdoName + 'id');

      // Update Assumptions
      $(subIdo).find('.assumption').each(function(i,assumption) {
        var assumptionName = subIdoName + 'assumptions' + '[' + i + '].';
        $(assumption).find('.assumptionId').attr('name', assumptionName + 'id');
        $(assumption).find('.statement').attr('placeholder', 'Assumption statement #' + (i + 1));
        $(assumption).find('.statement').attr('name', assumptionName + 'description');
      });
    });
  });
}