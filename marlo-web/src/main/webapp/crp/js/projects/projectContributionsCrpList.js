var $addOutcome, $addOutcomeSelect;
var OUTCOME_ID_PARAMETER = 'outcomeId'
$(document).ready(function() {

  $addOutcome = $('.addOutcomeBlock a');
  $addOutcomeSelect = $('.outcomesListBlock select');

  $('select').select2({
      templateResult: formatState,
      templateSelection: formatStateSelection,
      width: '100%'
  });

  var outcomesSelectedIds = ($('#outcomesSelectedIds').text()).split(',');
  $addOutcomeSelect.clearOptions(outcomesSelectedIds);

  $addOutcomeSelect.on('change', function(e) {
    var url = $addOutcome.attr('href');
    var uri = new Uri(url);
    var projectOutcomeID = $(this).val();
    uri.replaceQueryParam(OUTCOME_ID_PARAMETER, projectOutcomeID)
    $addOutcome.attr('href', uri);
  });

  $addOutcome.on('click', function(e) {
    e.preventDefault();
    var url = $(this).attr('href');
    var projectOutcomeID = new Uri(url).getQueryParamValue(OUTCOME_ID_PARAMETER);

    if(projectOutcomeID == "-1") {
      var notyOptions = jQuery.extend({}, notyDefaultOptions);
      notyOptions.text = 'You must select an outcome for adding.';
      noty(notyOptions);
    } else {
      window.location.href = url;

      addNewOutcome();
    }

  });

  /* Reporting events */
  attachReportingEvents();

  lp6menu();
});

function addNewOutcome() {
  var value = $addOutcomeSelect.find('option:selected').val();
  var name = $addOutcomeSelect.find('option:selected').text();
  console.log(value)
  console.log(name)
}

function formatStateSelection(state) {
  if(state.id != "-1") {
    var text = $("span.outcomeID-" + state.id).html();
    var $state = $(text);
    $state.find('.indicatorText').remove();
    return $state;
  } else {
    var $state = $("<span>" + state.text + "</span>");
    return $state;
  }
}

function formatState(state) {
  if(state.id != "-1") {
    var text = $("span.outcomeID-" + state.id).html();
    return $(text);
  } else {
    var $state = $("<span>" + state.text + "</span>");
    return $state;
  }
}

/** Reporting functions * */

function attachReportingEvents() {

  $('.addOtherContribution').on('click', addOtherContribution);

  $('.removeElement').on('click', removeOtherContribution);

  $('input[name="lp6Contribution"]').on('change', contributionToLP6);

}

function addOtherContribution() {
  var $item = $('#otherContribution-template').clone(true).removeAttr('id');
  var $list = $(this).parents('.tab-pane').find('.otherContributionsBlock');
  $list.append($item);
  $item.show('slow');

}

function removeOtherContribution() {
  var $parent = $(this).parent();
  $parent.hide('slow', function() {

    $parent.remove();

  });
}

function contributionToLP6() {

  var projectID = $(this).parents('.borderBox').classParam('project');
  var phaseID = $(this).parents('.borderBox').classParam('phase');
  var contributionValue = this.value;

  $.ajax({
      url: baseURL + '/projectCollaborationValue.do',
      data: {
          projectID: projectID,
          phaseID: phaseID,
          "crp_lp6_contribution_value": contributionValue
      },
      success: function(data) {
        window.location.href = window.location.href;
      }
  });

};

function lp6menu() {
  var $menu = $("#menu-contributionsLP6");
  if($menu.hasClass('toSubmit')) {
    // $menu.animateCss('rubberBand');
  }
}
