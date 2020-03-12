$(document).ready(init);
$( document ).ready(function() {
  validateSubCategorySelector();
});

function init() {

  // Setting ID to Date-picker input
  $(".dateMetadata").attr("id", "deliverableMetadataDate");
  $(".restrictionDate").attr("id", "restrictionDate");

  // Set Date-picker widget
  $("#deliverableMetadataDate, #restrictionDate").datepicker({
      dateFormat: "yy-mm-dd",
      minDate: '2012-01-01',
      maxDate: '2030-12-31',
      changeMonth: true,
      numberOfMonths: 1,
      changeYear: true,
      onChangeMonthYear: function(year,month,inst) {
        var selectedDate = new Date(inst.selectedYear, inst.selectedMonth, 1)
        $(this).datepicker('setDate', selectedDate);
      }
  });

  addDisseminationEvents();
}

function addDisseminationEvents() {

  // Update indexTab input
  $("a[data-toggle='tab']").on('shown.bs.tab', function(e) {
    $("#indexTab").val($(this).attr("index"));
    $(".radio-block").each(function(i,e) {
      showHiddenTags(e);
    });
  });

  // YES/NO Event for deliverables
  $(".button-label").on("click", function() {
    var valueSelected = $(this).hasClass('yes-button-label');
    var type = $(this).parent().parent().classParam('type');
    var inverted = $(this).parent().parent().classParam('inverted') === "true";

    // Set value
    // $(this).parent().find('input').val(valueSelected);
    $(this).parent().find("label").removeClass("radio-checked");
    $(this).addClass("radio-checked");
    // Show block if exist
    if(inverted) {
      valueSelected = !valueSelected;
    }
    if(valueSelected) {
      $('.block-' + type).slideDown();
    } else {
      $('.block-' + type).slideUp();
    }
    // Check FAIR Principles
    checkFAIRCompliant();
  });

  // Is this deliverable already disseminated
  $(".type-findable .button-label").on("click", function() {
    var valueSelected = $(this).hasClass('yes-button-label');

    if(!valueSelected) {
      $(".dataSharing").show("slow");
      $(".block-notFindable").slideDown();
      unSyncDeliverable();
      setOpenAccess("false");
      $('input[value="notDisseminated"]').prop('checked', true);
    } else {
      $(".dataSharing").hide("slow");
      $(".block-notFindable").slideUp();
    }
  });

  $('input.radioType-confidential').on("click", function() {
    if(this.value == "true") {
      $(".confidentialBlock-true").slideDown();
      $(".confidentialBlock-false").slideUp();
    } else {
      $(".confidentialBlock-false").slideDown();
      $(".confidentialBlock-true").slideUp();
    }
  });

  // Add Author
  $(".addAuthor").on("click", addAuthorElement);

  // Remove a author
  $('.removeAuthor').on('click', removeAuthor);

  // Change dissemination channel
  $(".disseminationChannel").on('change', changeDisseminationChannel);

  // Harvest metadata from URL
  $("#fillMetadata .checkButton, #fillMetadata .updateButton").on("click", syncMetadata);

  // Unsync metadata
  $("#fillMetadata .uncheckButton").on("click", unSyncDeliverable);

  $("input[name='deliverable.dissemination.type']").on("change", openAccessRestriction);

  // Type a dissemination channel url
  $('input.deliverableDisseminationUrl, input.otherLicense').on("change", function() {
    checkFAIRCompliant();
  })

  // Check handle and doi urls
  $(".handleMetadata").on("change keyup", checkHandleUrl);
  $(".doiMetadata").on("change keyup", checkDoiUrl);

  // Other license type
  $('.licenseOptions input[type="radio"].licenceOption').on("change", function() {
    if($(this).val() == "OTHER") {
      $(".licence-modifications").show("slow");
    } else {
      $(".licence-modifications").hide("slow");
    }
    checkFAIRCompliant();
  });

  // Add many flagships
  $(".flaghsipSelect").on("change", function() {
    var CRPProgram = $(this).find("option:selected");
    if(CRPProgram.val() != "" && CRPProgram.val() != "-1") {
      if($(".flagshipList").find(".flagships input.idCRPProgram[value='" + CRPProgram.val() + "']").exists()) {
      } else {
        addFlagship(CRPProgram.val(), CRPProgram.text());
      }
    }
  });
  $(".crpSelect").on("change", function() {
    var globalUnit = $(this).find("option:selected");
    if(globalUnit.val() != "" && globalUnit.val() != "-1") {
      if(!($(".flagshipList").find(".flagships input.idGlobalUnit[value='" + globalUnit.val() + "']").exists())) {
        addCrp(globalUnit.val(), globalUnit.text());
      }
    }
  });

  // Remove flagship
  $(".removeFlagship ").on("click", removeFlagship);

  // Edit an Author
  if(editable) {
    // EVENT FIRST NAME
    $('.lastName').on("click", function() {
      var spantext = $(this).text();
      $(this).empty().html('<input type="text" value="' + spantext + '">').find('input').focus();
    }).on("focusout", function(e) {
      var $author = $(this).parents(".author");
      var defaultText = "LastName";
      var text = $('input', this).val() || defaultText;
      if(text != defaultText) {
        $author.find(".lastNameInput").val(text);
      } else {
        $author.find(".lastNameInput").val("");
      }
      $(this).html(text);
    }).on('keypress', function(e) {
      if(e.which == 13 || e.keyCode === 13) {
        e.preventDefault();
      }
    });
    // EVENT FIRST NAME
    $('.firstName').on("click", function() {
      var spantext = $(this).text();
      $(this).empty().html('<input type="text" value="' + spantext + '">').find('input').focus();
    }).on("focusout", function(e) {
      var $author = $(this).parents(".author");
      var defaultText = "FirstName";
      var text = $('input', this).val() || defaultText;
      if(text != defaultText) {
        $author.find(".firstNameInput").val(text);
      } else {
        $author.find(".firstNameInput").val("");
      }
      $(this).html(text);
    }).on('keypress', function(e) {
      if(e.which == 13 || e.keyCode === 13) {
        e.preventDefault();
      }
    });
    // EVENT ORCID
    $('.orcidId').on("click", function() {
      var spantext = $(this).text();
      $(this).empty().html('<input type="text" value="' + spantext + '">').find('input').focus();
    }).on("focusout", function(e) {
      var $author = $(this).parents(".author");
      var defaultText = "No ORCID";
      var text = $('input', this).val() || defaultText;
      if(text != defaultText) {
        $author.find(".orcidIdInput").val(text);
      } else {
        $author.find(".orcidIdInput").val("");
      }
      $(this).html(text);
    }).on('keypress', function(e) {
      if(e.which == 13 || e.keyCode === 13) {
        e.preventDefault();
      }
    });
  }

  //
  $('input.iaType').on('change', function() {
    if(this.value == 1) {
      // Patent
      $('.block-patent').slideDown();
      $('.block-pvp').slideUp();
    } else {
      // PVP
      $('.block-pvp').slideDown();
      $('.block-patent').slideUp();
    }
  });

  $('.block-intellectualAsset .datePicker').pickadate({
      format: "mmm d, yyyy",
      formatSubmit: "yyyy-mm-dd",
      hiddenName: true,
      selectYears: true,
      selectMonths: true
  });

  // Does this deliverable involve Participants and Trainees?
  $('#estimateFemales').on('change', function() {
    $('#dontKnowFemale').prop('checked', false);
    $(this).parents('.femaleNumbers').find('input[type="text"]').prop('disabled', false);
  });
  $('#dontKnowFemale').on('change', function() {
    $('#estimateFemales').prop('checked', false);
    $(this).parents('.femaleNumbers').find('input[type="text"]').prop('disabled', $(this).is(':checked'));
  });

  $('.trainingType').on('change', function() {
    var id = this.value;
    if(id == 1) {
      $('.block-academicDegree').show();
    } else {
      $('.block-academicDegree').hide();
    }

    if((id == 1) || (id == 3) || (id == 2) || (id == 4)) {
      $('.block-periodTime').show();
    } else {
      $('.block-periodTime').hide();
    }
  });

  // Setting Numeric Inputs
  $('form input.numericInput').numericInput();

  // Set countries flag
  $('.nationalBlock').find("select").select2({
      maximumSelectionLength: 0,
      placeholder: "Select a country(ies)",
      templateResult: formatStateCountries,
      templateSelection: formatStateCountries,
      width: '100%'
  });

//Display Other Url option for DOI
  $('input.isOtherUrl').on('change', function() {
    var selected = $('input.isOtherUrl').is(":checked");

    if(selected == true) {
      $('.conditionalRequire .requiredTag').slideUp();
      $('.other-url').css("display","block");
    } else {
      $('.conditionalRequire .requiredTag').slideDown();
      $('.other-url').css("display","none");
    }
  });
  var crp = $('form').attr("name").split('_');
  $('#'+crp[0]+'_deliverable_deliverable_deliverableInfo_deliverableType_id').on('change', function() {
    var doiField = $('.metadataElement-doi').find('div.input ').children()[3];

    if(this.value == '63'){
      $('.acknowledge'+crp[0]+' .requiredTag').slideDown();
      $('.metadataElement-volume .requiredTag').slideDown();
      $('.metadataElement-issue .requiredTag').slideDown();
      $('.metadataElement-pages .requiredTag').slideDown();
      $('.isIsiJournal .requiredTag').slideDown();
      if(doiField.value ==''){
        displayExtraFieldUrl(true,true);
      }else{
          displayExtraFieldUrl(false,true);
      }
    }else{
      $('.acknowledge'+crp[0]+' .requiredTag').slideUp();
      $('.metadataElement-volume .requiredTag').slideUp();
      $('.metadataElement-issue .requiredTag').slideUp();
      $('.metadataElement-pages .requiredTag').slideUp();
      $('.isIsiJournal .requiredTag').slideUp();
      displayExtraFieldUrl(false,false);
    }
  });

}

function displayExtraFieldUrl(display,showRequiredTag){
  if(display){
    //$('.conditionalRequire .requiredTag').slideDown();
    $('.isOtherUrlContentBox').css("display","block");
  }else{
    $('.isOtherUrlContentBox').css("display","none");
    //$('.conditionalRequire .requiredTag').slideUp();
    //console.log($('.conditionalRequire .requiredTag'));
  }

  if(showRequiredTag){
    $('.conditionalRequire .requiredTag').slideDown();
    // $('.isOtherUrlContentBox').css("display","block");
  }else{
    //$('.isOtherUrlContentBox').css("display","none");
    $('.conditionalRequire .requiredTag').slideUp();
    //console.log($('.conditionalRequire .requiredTag'));
  }
}

function addFlagship(idCRPProgram,text) {
  var $list = $('.flagshipList');
  var $item = $('#flagship-template').clone(true).removeAttr("id");
  var tooltip = text.length > 60 ? text.substr(0, 60) + ' ... ' : text;
  $item.find(".name").text(text);
  $item.find(".name").attr("title", tooltip);
  $item.find(".idElemento").val("-1");
  $item.find(".idCRPProgram").val(idCRPProgram);
  $list.append($item);
  $item.show('slow');
  checkNextFlagshipItems($list);
  updateFlagship();
}

function addCrp(idGlobalUnit,text) {
  var $list = $('.flagshipList');
  var $item = $('#flagship-template').clone(true).removeAttr("id");
  var tooltip = text.length > 60 ? text.substr(0, 60) + ' ... ' : text;
  $item.find(".name").text(text);
  $item.find(".name").attr("title", tooltip);
  $item.find(".idElemento").val("-1");
  $item.find(".idGlobalUnit").val(idGlobalUnit);
  $list.append($item);
  $item.show('slow');
  checkNextFlagshipItems($list);
  updateFlagship();
}

function removeFlagship() {
  var $list = $(this).parents('.flagshipList');
  var $item = $(this).parents('.flagships');
  $item.hide(function() {
    $item.remove();
    checkNextFlagshipItems($list);
    updateFlagship();
  });
}

function updateFlagship() {
  $(".flagshipList").find('.flagships').each(function(i,e) {
    // Set activity indexes
    $(e).setNameIndexes(1, i);
  });
}

function checkNextFlagshipItems(block) {
  var items = $(block).find('.flagships ').length;
  if(items == 0) {
    $(block).parent().find('p.emptyText').fadeIn();
  } else {
    $(block).parent().find('p.emptyText').fadeOut();
  }
}

function checkHandleUrl() {
  var input = $(this);
  $(input).removeClass("fieldError");
  var inputData = $.trim(input.val());
  if(inputData != "") {
    if(inputData.indexOf("handle") == -1) {
      $(input).addClass("fieldError");
    } else {
      $(input).removeClass("fieldError");
    }
  }
}

function checkDoiUrl() {
  var input = $(this);
  $(input).removeClass("fieldError");
  var inputData = $.trim(input.val());
  if(inputData != "") {
    if(inputData.indexOf("doi") == -1) {
      $(input).addClass("fieldError");
    } else {
      $(input).removeClass("fieldError");
    }
  }
}

function openAccessRestriction() {
  if($(this).val() == "restrictedUseAgreement") {
    $(".restrictionDate-block").find("label").text("Restricted access until:*");
    $("#restrictionDate").attr("name", "deliverable.dissemination.restrictedAccessUntil");
    $(".restrictionDate-block").show("slow");
  } else if($(this).val() == "effectiveDateRestriction") {
    $(".restrictionDate-block").find("label").text("Restricted embargoed date:*");
    $("#restrictionDate").attr("name", "deliverable.dissemination.restrictedEmbargoed");
    $(".restrictionDate-block").show("slow");
  } else {
    $(".restrictionDate-block").hide("slow");
    $(".restrictionDate-block input.restrictionDate").val("");
  }
}

function changeDisseminationChannel() {
  var channel = $(".disseminationChannel").val();
  $('#disseminationUrl').find("input").val("");
  $("#metadata-output").empty();
  $(".exampleUrl-block").hide();

  // Find the list in deliverablesMacros.ftl
  var channelsList = jQuery.map($('ul#channelsList li'), function(e) {
    return $(e).find('span.id').text();
  });

  if(channel != "-1") {
    $('#disseminationUrl').slideDown("slow");
    if(channelsList.indexOf(channel) != -1) {
      $("#fillMetadata").slideDown("slow");
      $(".exampleUrl-block.channel-" + channel).slideDown("slow");
    } else {
      $("#fillMetadata").slideUp("slow");
    }
  } else {
    $('#disseminationUrl').slideUp("slow");
  }
  checkFAIRCompliant();
}

function addAuthorElement() {
  var firstName = $(".fName").val();
  var lastName = $(".lName").val();
  var orcidId = $(".oId").val();

  // Check if inputs are filled out
  if(firstName && lastName) {
    $(".lName, .fName, .oId").removeClass("fieldError");

    // Add a new author
    addAuthor({
        lastName: lastName,
        firstName: firstName,
        orcidId: orcidId
    });

    // Clean add inputs
    $(".lName, .fName, .oId").val("");
  } else {
    $(".lName, .fName, .oId").addClass("fieldError");
  }
}

function addAuthor(author) {

  var $list = $('.authorsList');
  var $item = $('#author-template').clone(true).removeAttr("id");

  // Last Name
  $item.find(".lastName").html(author.lastName);
  $item.find(".lastNameInput").val(author.lastName);

  // First name
  $item.find(".firstName").html(author.firstName);
  $item.find(".firstNameInput").val(author.firstName);

  // ORCID
  if(author.orcidId) {
    author.orcidId = (author.orcidId).replace(/^https?\:\/\//i, "");
    $item.find(".orcidId strong").html(author.orcidId);
    $item.find(".orcidIdInput").val(author.orcidId);
  }

  $list.append($item);
  $item.show('slow');
  updateAuthor();
  checkNextAuthorItems($list);

}

function removeAuthor() {
  var $list = $(this).parents('.authorsList');
  var $item = $(this).parents('.author');
  $item.hide(function() {
    $item.remove();
    checkNextAuthorItems($list);
    updateAuthor();
  });
}

function updateAuthor() {
  $(".authorsList").find('.author').each(function(i,e) {
    // Set indexes
    $(e).setNameIndexes(1, i);
  });
}

function checkNextAuthorItems(block) {
  var items = $(block).find('.author ').length;
  if(items == 0) {
    $(block).parent().find('p.emptyText').fadeIn();
  } else {
    $(block).parent().find('p.emptyText').fadeOut();
  }
}

/**
 * Set the metadata in the interface
 *
 * @param {Object} data
 * @returns
 */
function setMetadata(data) {
  console.log(data);
  // Text area & Inputs fields
  $.each(data, function(key,value) {
    var $parent = $('.metadataElement-' + key);
    var lockInput = !($parent.hasClass('no-lock'));
    var $input = $parent.find(".metadataValue");
    var $text = $parent.find(".metadataText");
    var $hide = $parent.find('.hide');


    if(value) {
      $input.val(value);
      $text.text(value).parent().show();
      $parent.find('textarea').autoGrow();
      if(lockInput) {
        $input.attr('readOnly', true);
      }
      $hide.val("true");

      if(key == 'authors') {
        var authorsNameArray = jQuery.map(value, function(a,i) {
          return a.lastName + ", " + a.firstName;
        });
        $input.val(authorsNameArray.join('; '));
      }
      if(key == 'doi') {
        var pos = value.indexOf("/", 8);
        var formattedDoiUrl = value.substring(pos+1);
        $input.val(formattedDoiUrl);
      }
    } else {
      $input.attr('readOnly', false);
      $hide.val("false");
    }
  });

  // Set Authors
  if(typeof (data.authors) != 'undefined') {
    if(data.authors.length > 0) {
      // Clear Authors
      $('.authorsList').empty();
      // Add Authors from data source
      $.each(data.authors, function(i,author) {
        addAuthor(author);
      });
      // Hide authors
      $('.author').addClass('hideAuthor');
      $('.authorVisibles').hide();
      $('.metadataElement-authors .hide').val("true");
    } else {
      // Show authors
      $('.author').removeClass('hideAuthor');
      $('.authorVisibles').show();
      $('.metadataElement-authors .hide').val("false");
    }
  }

  // Open Access Validation
  setOpenAccess(data.openAccess);

  // Set License
  setLicense(data.rights);

  // Set ISI
  setISI(data.ISI);

  // Sync Deliverable
  syncDeliverable();

  var doiField = $('.metadataElement-doi').find('div.input ').children()[3];
  var selector = $('select[name="deliverable.deliverableInfo.deliverableType.id"]');
  if(selector.val() == '63' && (doiField.value =='')){
    displayExtraFieldUrl(true,true);
  }else{
    if(selector.val() == '63'){
      displayExtraFieldUrl(false,true);
    }
  }
}

function setOpenAccess(openAccess) {
  var $input = $(".type-accessible ").parent();
  $(".type-accessible ").parent().find("label").removeClass("radio-checked");

  if(openAccess === "true") {
    $input.find('input.yesInput').prop("checked", true);
    $(".block-accessible").hide("slow");
    $(".type-accessible .yes-button-label ").addClass("radio-checked");
  }
  if(openAccess === "false") {
    $input.find('input.noInput').prop("checked", true);
    $(".block-accessible").show("slow");
    $(".type-accessible .no-button-label ").addClass("radio-checked");
  }
  if(openAccess === "") {
    $input.find('input.yesInput').prop("checked", true);
    $(".block-accessible").hide("slow");
  }
  // Check FAIR Principles
  checkFAIRCompliant();
}

function setISI(isi) {
  if(isi === "true") {
    $('input#optionISI-yes').prop('checked', true);
  }
  if(isi === "false") {
    $('input#optionISI-no').prop('checked', true);
  }
  if(isi === "") {
    $('input#optionISI-yes').prop('checked', false);
    $('input#optionISI-no').prop('checked', false);
  }

}

function setLicense(license) {
  var $input = $(".type-license ").parent();
  if(license) {
    $input.find('input.yesInput').prop("checked", true);
    $(".type-license ").parent().find("label").removeClass("radio-checked");
    $(".block-license").show("slow");
    $(".type-license .yes-button-label ").addClass("radio-checked");

    // Set license as Other
    // $('input.licenceOption[value="OTHER"]').prop("checked", true);
    // $('input.otherLicense').val(license);

  } else {
    $input.find('input.noInput').prop("checked", true);
    $(".type-license ").parent().find("label").removeClass("radio-checked");
    $(".block-license").hide("slow");
    $(".type-license .no-button-label ").addClass("radio-checked");
  }
  // Check FAIR Principles
  checkFAIRCompliant();
}

/**
 * Sync the deliverable in the interface and set as synced
 */
function syncDeliverable() {
  // Hide Sync Button & dissemination channel
  $('#fillMetadata .checkButton, .disseminationChannelBlock').hide('slow');
  // Show UnSync & Update Button
  $('#fillMetadata .unSyncBlock').show();
  // Set hidden inputs
  $('#fillMetadata input:hidden').val(true);
  // Dissemination URL
  $('.deliverableDisseminationUrl').attr('readOnly', true);
  // Check Fair
  checkFAIRCompliant();
  // Update component
  $(document).trigger('updateComponent');
}

/**
 * UnSync the deliverable in the interface
 */
function unSyncDeliverable() {
  // Show metadata
  $('.metadataElement').each(function(i,e) {
    var $parent = $(e);
    var $input = $parent.find('.metadataValue');
    var $text = $parent.find(".metadataText");
    var $hide = $parent.find('.hide');
    $input.attr('readOnly', false);
    $input.val("");
    $text.text("").parent().hide();
    $hide.val("false");
  });

  var doiField = $('.metadataElement-doi').find('div.input ').children()[3];
  var selector = $('select[name="deliverable.deliverableInfo.deliverableType.id"]');
  if(selector.val() == '63' && (doiField.value =='')){
    displayExtraFieldUrl(true,true);
  }

  // Show authors
  $('.author').removeClass('hideAuthor');
  $('.authorVisibles').show();
  $('.metadataElement-authors .hide').val("false");
  $('.authorsList').empty();

  // Show Sync Button & dissemination channel
  $('#fillMetadata .checkButton, .disseminationChannelBlock').show('slow');
  // Hide UnSync & Update Button
  $('#fillMetadata .unSyncBlock').hide();
  // Set hidden inputs
  $('#fillMetadata input:hidden').val(false);
  // Dissemination URL
  $('.deliverableDisseminationUrl').attr('readOnly', false);
  // Check Fair
  checkFAIRCompliant();
  // Update component
  $(document).trigger('updateComponent');
}

/**
 * Load Metadata and fill fields
 */
function syncMetadata() {
  var channel = $(".disseminationChannel").val();
  var url = $.trim($(".deliverableDisseminationUrl").val());

  // Validate URL
  if(url == "") {
    return;
  }

  // Get metadata
  getMetadata(channel, url);

}

/**
 * Get Deliverable metadata from different repositories using ajax
 *
 * @param {string} channel - Repository whrere the metadata is hosted (e.g. CGSpace, Dataverse etc.)
 * @param {string} url - Repositori URL (e.g. https://cgspace.cgiar.org/handle/10568/79435)
 * @returns the ajax return a metadata object
 */
function getMetadata(channel,url) {
  var data = {
      pageID: channel,
      metadataID: url,
      phaseID: phaseID
  }

  // get data from url
  // Ajax to service
  $.ajax({
      'url': baseURL + '/metadataByLink.do',
      'type': "GET",
      'data': data,
      'dataType': "json",
      beforeSend: function() {
        $(".deliverableDisseminationUrl").addClass('input-loading');
        $('#metadata-output').html("Searching ... " + data.metadataID);
      },
      success: function(metadata) {
        metadata = metadata.metadata;
        if(jQuery.isEmptyObject(metadata)) {
          $('#metadata-output').html("Metadata empty");
        } else {
          // Setting Metadata
          setMetadata(metadata);

          // Show a message indicating the medatada harves was successfully
          $('#metadata-output').empty().append("Found metadata successfully in " + channel);
        }
      },
      complete: function() {
        $(".deliverableDisseminationUrl").removeClass('input-loading');
      },
      error: function() {
        console.log("error");
        $('#metadata-output').empty().append("Invalid URL for searching metadata");
      }
  });
}

/**
 * Validate duplicated authors
 *
 * @param {string} lastName
 * @param {string} firstName
 * @returns {boolean} True if is duplicated.
 */
function validateAuthors(lastName,firstName) {
  if($(".authorsList").find('.author input.lastNameInput[value="' + lastName + '"]').exists()
      || $(".authorsList").find(".author input.firstNameInput[value='" + firstName + "']").exists()) {
    return true;
  } else {
    return false;
  }
}

/**
 * Format select2: Add Countries flags
 *
 * @param state
 * @returns
 */
function formatStateCountries(state) {
  if(!state.id) {
    return state.text;
  }
  var flag = '<i class="flag-icon flag-icon-' + state.element.value.toLowerCase() + '"></i> ';
  var $state;
  if(state.id != -1) {
    $state = $('<span>' + flag + state.text + '</span>');
  } else {
    $state = $('<span>' + state.text + '</span>');
  }
  return $state;
};

/**
 * Search sub category deliverable and display extra url field
 *
 */
function validateSubCategorySelector() {
  var crp = $('form').attr("name").split('_');
  var selector = $('#'+crp[0]+'_deliverable_deliverable_deliverableInfo_deliverableType_id');
  var doiField = $('.metadataElement-doi').find('div.input ').children()[3];

  if(selector.val() == '63'){
    $('.acknowledge'+crp[0]+' .requiredTag').slideDown();
    $('.metadataElement-volume .requiredTag').slideDown();
    $('.metadataElement-issue .requiredTag').slideDown();
    $('.metadataElement-pages .requiredTag').slideDown();
    $('.isIsiJournal .requiredTag').slideDown();
    if(doiField.value ==''){
      displayExtraFieldUrl(true,true);
    }else{
        displayExtraFieldUrl(false,true);
    }
  }else{
    $('.acknowledge'+crp[0]+' .requiredTag').slideUp();
    $('.metadataElement-volume .requiredTag').slideUp();
    $('.metadataElement-issue .requiredTag').slideUp();
    $('.metadataElement-pages .requiredTag').slideUp();
    $('.isIsiJournal .requiredTag').slideUp();
    displayExtraFieldUrl(false,false);
  }
};