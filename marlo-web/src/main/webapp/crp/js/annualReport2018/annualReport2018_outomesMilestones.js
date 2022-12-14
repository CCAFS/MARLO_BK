$(document).ready(init);

var markers, inputMilestoneStatus, isOICR, multiInputOutcomesMilestones;
var oicrsAjaxURL = '/qaAssessmentStatus.do?year=2021&indicatorTypeID=4&crpID=';
var oicrsArrName = 'fullItemsAssessmentStatus';
var milestoneAjaxURL = '/qaAssessmentStatus.do?year=2021&indicatorTypeID=7&crpID=';
var milestoneArrName = 'fullItemsAssessmentStatus';

function init() {

  // Add Select2
  // $('form select').select2({
  //   width: '100%'
  // });
  isOICR = $('#isOICR').html();
  // if ($('#actualPhaseAR2021').html() == 'true' ) {
  //   $('textarea[name*="evidenceLink"]').prop('disabled', true);
  // }
  multiInputOutcomesMilestones = $('.multiInput').find('span input');
  inputMilestoneStatus = $('input.milestoneStatus');
  loadInputMilestoneStatus();
  inputMilestoneStatus.on('change', function () {
    var evidenceTag = $(this).parents('.synthesisMilestone').find('.milestoneEvidence').find('.requiredTag');
    var warningTag = $(this).parents('.synthesisMilestone').find('.linksToEvidence').find('#warningEmptyLinksTag');
    var tag = $(warningTag).next().find('.requiredTag');
    disableEnableWarningTag(this.value, evidenceTag, warningTag, tag);
  });
  attachEvents();

  // Set google charts
  setGoogleCharts();
  disabledUncheckedCheckmarkColor();
  markers = $('select.marker');
  loadMarkers();
  markers.on('change', function () {
    var tag = $(this).parent().parent().parent().next('.conditionalRequire').find('.requiredTag');
    disableEnableRequiredTag(this.value, tag);
  });
}

function loadInputMilestoneStatus() {
  inputMilestoneStatus.each((index, item) => {
    var evidenceTag = $(this).parents('.synthesisMilestone').find('.milestoneEvidence').find('.requiredTag');
    var warningTag = $(item).parents('.synthesisMilestone').find('.linksToEvidence').find('#warningEmptyLinksTag');
    var tag = $(warningTag).next().find('.requiredTag');
    if (item.checked) {
      disableEnableWarningTag(item.value, evidenceTag, warningTag, tag);
    }
  });
}

function disableEnableWarningTag(optionSelected, evidenceTag, warningTag, tag) {
  switch (optionSelected) {
    case '3':
      $(evidenceTag).hide();
      // $(warningTag).show();
      $(tag).show();
      break;
    case '5':
      $(evidenceTag).show();
      // $(warningTag).hide();
      $(tag).hide();
      break;
    case '6':
      $(evidenceTag).show();
      // $(warningTag).show();
      $(tag).show();
      break;
    case '7':
      $(evidenceTag).show();
      // $(warningTag).hide();
      $(tag).hide();
      break;

    default:
      break;
  }
}

function loadMarkers() {
  markers.each((index, item) => {
    var tag = $(item).parent().parent().parent().next('.conditionalRequire').find('.requiredTag');
    disableEnableRequiredTag(item.value, tag);
  });
}

function disableEnableRequiredTag(key, tag) {
  switch (key) {
    case '-1':
      $(tag).hide();
      break;

    case '1':
      $(tag).hide();
      break;

    case '2':
      $(tag).show();
      break;

    case '3':
      $(tag).show();
      break;

    case '4':
      $(tag).hide();
      break;

    default:
      break;
  }
}

function disabledUncheckedCheckmarkColor() {
  $('input[id^="disabled-"]').each((index, item) => {
    if ($(item).prop('checked') == false) {
      $(item).prop('disabled', false);
      $(item).closest('.inputContainer').find('.checkmark').css('border', '2px solid #ff0000');
    }
  });
}

function attachEvents() {
  if ($('#actualPhase').html() == 'true' && $('#isSubmitted').html() == 'true') {
    if (isOICR == 'true') {
      loadQualityAssessmentStatus(oicrsAjaxURL, oicrsArrName);
    } else {
      loadQualityAssessmentStatus(milestoneAjaxURL, milestoneArrName);
    }
  }

  // Links Component
  (function () {
    // Events
    $('.addButtonLink').on('click', addItem);
    $('.removeLink.links').on('click', removeItem);
    $('.multiInput').find('span input').on('input', function () {
      validateURL(this);
    });
    validateEmptyLinks();

    multiInputOutcomesMilestones.each((index, item) => {
      validateURL(item);
    });

    // Functions
    function addItem() {
      var $list = $(this).parent('.linksBlock').find('.linksList');
      var $element = $(this).parent('.linksBlock').parent().find('#multiInput-links-template').clone(true).removeAttr("id");
      // var $listLength = $list.children().length;
      // if ($listLength <= 9) {
      // Remove template tag
      $element.find('input, textarea').each(function (i, e) {
        e.name = (e.name).replace("_TEMPLATE_", "");
        e.id = (e.id).replace("_TEMPLATE_", "");
      });
      // Show the element
      $element.appendTo($list).hide().show(350);
      // Update indexes
      updateIndexes(this);
      // }
    }
    function removeItem() {
      var $parent = $(this).parent('.multiInput.links');
      var $addBtn = $(this).parent().parent().parent().find('.addButtonLink');
      $parent.hide(500, function () {
        // Remove DOM element
        $parent.remove();
        // Update indexes
        updateIndexes($addBtn);
      });
    }
    function updateIndexes(list) {
      var linksList = $(list).parent('.linksBlock').find('.linksList');
      var warningTag = $(linksList).parent().parent().find('#warningEmptyLinksTag');
      linksList.find('.multiInput').each(function (i, element) {
        $(element).find('.indexTag').text(i + 1);
        $(element).setNameIndexes(3, i);
      });

      if (linksList.children().length != 0) {
        warningTag.hide();
        validateEmptyLinks();
      } else {
        warningTag.show();
      }
    }
    function validateURL(item) {
      var url = item.value;
      var expression = /((([A-Za-z]{3,9}:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)/;
      var regex = new RegExp(expression);
      var res = "";
      var warningTag = $(item).parent().parent().parent().parent().parent().parent().find('#warningEmptyLinksTag');

      if (url.match(regex)) {
        res = "Valid URL";
        $(item).css('border', 'none');
        warningTag.hide();
      } else {
        res = "Invalid URL";
        $(item).css('border', '1px solid red');
        warningTag.show();
      }
    }
    function validateEmptyLinks() {
      $('.linksList').find('.multiInput span input').map((index, item) => {
        var warningTag = $(item).parent().parent().parent().parent().parent().parent().find('#warningEmptyLinksTag');
        if (item.value != '') {
          warningTag.hide();
        } else {
          warningTag.show();
        }
      });
    }

  })();

  // Change main reason
  $('select.milestoneMainReasonSelect').on('change', function () {
    var optionSelected = this.value;
    var $block = $(this).parents('.milestonesEvidence').find('.otherBlock');

    if (optionSelected == 7) {
      $block.slideDown();
    } else {
      $block.slideUp();
    }
  });

  inputMilestoneStatus.on('change', function () {
    var optionSelected = this.value;

    // Milestone Evidence
    var $block = $(this).parents('.synthesisMilestone').find('.milestonesEvidence');
    if (optionSelected == 4 || optionSelected == 5 || optionSelected == 6 || optionSelected == 7) {
      $block.slideDown();
    } else {
      $block.slideUp();
    }

    // Extended year
    var $yearBlock = $(this).parents('.synthesisMilestone').find('.extendedYearBlock');
    if (optionSelected == 4) {
      $yearBlock.slideDown();
    } else {
      $yearBlock.slideUp();
    }
  });
}

function loadQualityAssessmentStatus(ajaxURL, arrName) {
  var currentCrpID = $('#actualCrpID').html();

  if (currentCrpID != '-1') {
    var finalAjaxURL = ajaxURL + currentCrpID;

    $.ajax({
      url: baseURL + finalAjaxURL,
      async: false,
      success: function (data) {
        if (data && Object.keys(data).length != 0) {
          var newData = data[arrName].map(function (x) {
            var arr = [];

            arr.push(x.id);
            arr.push(x.assessmentStatus);
            arr.push(x.updatedAt);

            return arr;
          });

          if (isOICR == 'true') {
            updateQualityAssessmentStatusData(newData);
          } else {
            if ($('#isPMU').html() == 'true') {
              updateQualityAssessmentStatusData(newData);
            } else {
              qualityAssessmentStatusMessage(newData);
            }
          }
        }
      }
    });
  }
}

function updateQualityAssessmentStatusData(data) {
  data.map(function (x) {
    var isCheckedAR = $(`#isCheckedAR-${x[0]}`).html();
    var element = document.getElementById(`QAStatusIcon-${x[0]}`);
    var status, iconSrc;

    if (element && isCheckedAR == '1') {
      switch (x[1]) {
        case 'pending':
          status = 'Pending assessment';
          iconSrc = baseURL + '/global/images/pending-icon.svg';
          break;
        case 'pending_crp':
          status = 'Pending CRP response';
          iconSrc = baseURL + '/global/images/pending-icon.svg';
          break;
        case 'in_progress':
          status = 'Quality Assessed (Requires 2nd assessment)';
          iconSrc = baseURL + '/global/images/quality-assessed-icon.svg';
          break;
        case 'quality_assessed':
          status = 'Quality Assessed';
          iconSrc = baseURL + '/global/images/quality-assessed-icon.svg';
          if (isOICR == 'true') {
            $(`<input name='reportSynthesis.reportSynthesisFlagshipProgress.studiesValue' type='hidden' value='${x[0]}'/>`).insertAfter(`#study-${x[0]}`);
            $(`#study-${x[0]}`).prop('disabled', true);
            $(`#study-${x[0]}`).next('span').attr('title', 'This item cannot be unchecked because it has been already Quality Assessed');
          } else {
            var milestoneID = $(`#milestoneID-${x[0]}`).html();
            $(`<input name='reportSynthesis.reportSynthesisFlagshipProgress.milestonesValue' type='hidden' value='${milestoneID}'/>`).insertAfter(`#milestone-${x[0]}`);
            $(`#milestone-${x[0]}`).prop('disabled', true);
            $(`#milestone-${x[0]}`).next('span').attr('title', 'This item cannot be unchecked because it has been already Quality Assessed');
          }
          break;

        default:
          break;
      }

      var imgTag = document.createElement('img');
      var br = document.createElement('br');
      var spanTag = document.createElement('span');
      var text = document.createTextNode(status);

      element.innerHTML = '';
      imgTag.style.width = '25px';
      imgTag.src = iconSrc;
      element.appendChild(imgTag);
      element.appendChild(br);
      spanTag.appendChild(text);
      element.appendChild(spanTag);
    }
  });
}

function qualityAssessmentStatusMessage(data) {
  data.map(function (x) {
    var mileID = $(`#mileID-${x[0]}`).html();
    if (mileID && (x[0] == mileID)) {
      var container = document.getElementById(`containerTitleElementsProject-${x[0]}`);
      var element = document.getElementById(`qualityAssessedIcon-${x[0]}`);
      var date, status, statusClass;

      if (element) {
        switch (x[1]) {
          case 'pending':
            status = 'Pending assessment';
            statusClass = 'pending-mode';
            break;
          case 'pending_crp':
            status = 'Pending CRP response';
            statusClass = 'pending-mode';
            break;
          case 'in_progress':
            status = 'Quality Assessed (Requires 2nd assessment)';
            statusClass = 'qualityAssessed-mode';
            break;
          case 'quality_assessed':
            date = new Date((x[2].split('T')[0])).toDateString();
            status = 'Milestone was Quality Assessed on ' + date;
            statusClass = 'qualityAssessed-mode';
            break;

          default:
            break;
        }

        var pTag = document.createElement('p');
        var text = document.createTextNode(status);

        element.innerHTML = '';
        element.classList.remove('pendingForReview-mode');
        element.classList.add(statusClass);
        pTag.style.margin = '0';
        pTag.appendChild(text);
        element.appendChild(pTag);

        if (x[1] == 'quality_assessed' || x[1] == 'pending') {
          var pMessageTag = document.createElement('p');
          if (x[1] == 'quality_assessed') {
            var textMessage = document.createTextNode('As this item has already been Quality Assessed, no changes are recommended');
            element.style.backgroundPosition = '594px';
          } else {
            var textMessage = document.createTextNode('As this item is being assessed by the SMO, no changes are recommended');
          }

          pMessageTag.classList.add('messageQAInfo');
          pMessageTag.appendChild(textMessage);
          container.appendChild(pMessageTag);
        }
      }
    }
  });
}

function setGoogleCharts() {
  // Chart #14  - OICRs Stage of maturity
  createGoogleBarChart('#chart14', {
    title: 'OICRs Stage of Maturity',
    titleTextStyle: {
      color: '#5f5e5e',
      fontName: 'Roboto',
      fontSize: 16,
      bold: false
    },
    orientation: 'horizontal',
    hAxis: {
      baseline: 'none',
      textPosition: 'none',
      gridlines: {
        count: 0
      }
    },
    vAxis: {
      baseline: 'none',
      textPosition: 'none',
      gridlines: {
        count: 0
      }
    },
    //pieHole: 0.4,
    chartArea: {
      top: 45,
      width: "80%",
      heigth: "100%"
    },
    colors: [
      '#1773b8', '#e43a74', '#00a0b0', '#f3bd1e', '#373a3b'
    ],
    bar: { groupWidth: '100%' },
    legend: {
      position: "bottom",
      //alignment: 'center',
    },
  });

  // Chart #15 - Policies by Type
  createGoogleBarChart("#chart15", {
    title: "OICRs by Flagship/Module",
    titleTextStyle: {
      color: '#5f5e5e',
      fontName: 'Roboto',
      fontSize: 16,
      bold: false
    },
    chartArea: {
      top: 65,
      left: 55,
      width: '80%',
      heigth: "100%"
    },
    hAxis: {
      baseline: 'none',
      //viewWindowMode: 'pretty',
      //slantedText: true,
      textPosition: 'none',
      gridlines: {
        count: 0
      },
      title: '*Note: Please note that an OICR can contribute to more than one Flagship/Module.'
    },
    vAxis: {
      textStyle: {
        color: '#5f5e5e',
        fontName: 'Roboto'
      }
    },
    legend: {
      position: "none"
    },
    bars: 'horizontal' // Required for Material Bar Charts.
  });
}