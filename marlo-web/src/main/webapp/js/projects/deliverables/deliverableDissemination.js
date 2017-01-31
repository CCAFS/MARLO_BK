$(document).ready(init);

function init() {
  $("a[href='#deliverable-mainInformation']").on('shown.bs.tab', function(e) {
    $("a[href='#deliverable-mainInformation']").removeClass("hideInfo");
// verifyMissingFields(".radio-block");
// $(".radio-block").each(function(i,e) {
//
// });
  });
  $("a[data-toggle='tab']").on('shown.bs.tab', function(e) {
    $(".radio-block").each(function(i,e) {
      showHiddenTags(e);
    });
  });
  $(".dateMetadata").attr("id", "deliverableMetadataDate");
  $(".restrictionDate").attr("id", "restrictionDate");
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
  /* Init Select2 plugin */
  $('.disseminationChannel').select2({
    width: '90%'
  });

  // Is this deliverable Open Access
  $(".accessible .button-label").on("click", function() {
    var valueSelected = $(this).hasClass('yes-button-label');
    var $input = $(this).parent().find('input');
    $input.val(valueSelected);
    $(this).parent().find("label").removeClass("radio-checked");
    $(this).addClass("radio-checked");

    if(!valueSelected) {
      $(".openAccessOptions").show("slow");
    } else {
      $(".openAccessOptions").hide("slow");
    }
    checkFAIRCompliant();
  });

  // Is this deliverable already disseminated
  $(".findable .button-label").on("click", function() {
    var valueSelected = $(this).hasClass('yes-button-label');
    var $input = $(this).parent().find('input');
    $input.val(valueSelected);
    $(this).parent().find("label").removeClass("radio-checked");
    $(this).addClass("radio-checked");

    if(!valueSelected) {
      $(".findableOptions").hide("slow");
      $(".dataSharing").show("slow");
    } else {
      $(".findableOptions").show("slow");
      $(".dataSharing").hide("slow");
    }
    checkFAIRCompliant();
  });

  // Does the publication acknowledge
  $(".acknowledge .button-label").on("click", function() {
    var valueSelected = $(this).hasClass('yes-button-label');
    var $input = $(this).parent().find('input');
    $input.val(valueSelected);
    $(this).parent().find("label").removeClass("radio-checked");
    $(this).addClass("radio-checked");
  });

  // Have you adopted a license
  $(".license .button-label").on("click", function() {
    var valueSelected = $(this).hasClass('yes-button-label');
    var $input = $(this).parent().find('input');
    $input.val(valueSelected);
    $(this).parent().find("label").removeClass("radio-checked");
    $(this).addClass("radio-checked");

    if(!valueSelected) {
      $(".licenseOptions").hide("slow");
    } else {
      $(".licenseOptions").show("slow");
    }
    checkFAIRCompliant();
  });

  // Does this license allow modifications?
  $(".licenceModifications .button-label").on("click", function() {
    var valueSelected = $(this).hasClass('yes-button-label');
    var $input = $(this).parent().find('input');
    $input.val(valueSelected);
    $(this).parent().find("label").removeClass("radio-checked");
    $(this).addClass("radio-checked");
    checkFAIRCompliant();
  });

  $("#deliverableMetadataDate").datepicker({
      dateFormat: "yy-mm-dd",
      minDate: '2015-01-01',
      maxDate: '2030-12-31',
      changeMonth: true,
      numberOfMonths: 1,
      changeYear: true,
      onChangeMonthYear: function(year,month,inst) {
        var selectedDate = new Date(inst.selectedYear, inst.selectedMonth, 1)
        $(this).datepicker('setDate', selectedDate);
      }
  });

  $(".addAuthor").on("click", addAuthor);

  // Remove a author
  $('.removeAuthor').on('click', removeAuthor);

  // Change dissemination channel
  $(".disseminationChannel").on('change', changeDisseminationChannel);

  $("#fillMetadata").on("click", loadAndFillMetadata);

  $("input[name='deliverable.dissemination.type']").on("change", openAccessRestriction);

  // Type a dissemination channel url
  $('input.deliverableDisseminationUrl, input.otherLicense').on("change", function() {
    checkFAIRCompliant();
  })

  // Check handle and doi urls
  $(".handleMetadata").on("change keyup", checkHandleUrl);
  $(".doiMetadata").on("change keyup", checkDoiUrl);

  // Ohter license type
  $("input[name='deliverable.license']").on("change", function() {
    if($(this).val() == "OTHER") {
      $(".licence-modifications").show("slow");
    } else {
      $(".licence-modifications").hide("slow");
    }
    checkFAIRCompliant();
  });

  // Add many flagships
  $(".flaghsipSelect").on("change", function() {
    var option = $(this).find("option:selected");
    if(option.val() != "" && option.val() != "-1") {
      if($(".flagshipList").find(".flagships input.idFlagship[value='" + option.val() + "']").exists()) {
      } else {
        var composedText = option.text().toUpperCase() + "-" + option.text();
        var v = composedText.length > 30 ? composedText.substr(0, 30) + ' ... ' : composedText;
        addFlagship(option.val(), v, composedText, "");
      }
    }
  });
  $(".crpSelect").on("change", function() {
    var option = $(this).find("option:selected");
    if(option.val() != "" && option.val() != "-1") {
      if($(".flagshipList").find(".flagships input.idCrp[value='" + option.val() + "']").exists()) {
      } else {
        var composedText = option.text().toUpperCase() + "-" + option.text();
        var v = composedText.length > 30 ? composedText.substr(0, 30) + ' ... ' : composedText;
        addCrp("", v, composedText, option.val());
      }
    }
  });

  // remove flagship
  $(".removeFlagship ").on("click", removeFlagship);

  if(editable) {
    $('.lastName').dblclick(function() {
      var spantext = $(this).text();
      $(this).empty().html('<input type="text" value="' + spantext + '">').find('input').focus();
    }).keypress(function(e) {
      if(e.keyCode == 13) {
        var text = $('input', this).val();
        if(text == "") {
          text = "Last Name";
        } else {
          $(this).parents(".author").find(".lastNameInput").val(text);
        }
        $(this).html(text);
      }
    });
    $('.firstName').dblclick(function() {
      var spantext = $(this).text();
      $(this).empty().html('<input type="text" value="' + spantext + '">').find('input').focus();
    }).keypress(function(e) {
      if(e.keyCode == 13) {
        var text = $('input', this).val();
        if(text == "") {
          text = "First Name";
        } else {
          $(this).parents(".author").find(".firstNameInput").val(text);
        }
        $(this).html(text);
      }
    });
    $('.orcidId').dblclick(function() {
      var spantext = $(this).text();
      $(this).empty().html('<input type="text" value="' + spantext + '">').find('input').focus();
    }).keypress(function(e) {
      if(e.keyCode == 13) {
        var text = $('input', this).val();
        if(text == "") {
          text = "orcid Id";
        } else {
          $(this).parents(".author").find(".orcidIdInput").val(text);
        }
        $(this).html(text);
      }
    });
  }
}

function addFlagship(id,text,title,crpId) {
  var $list = $('.flagshipList');
  var $item = $('#flagship-template').clone(true).removeAttr("id");
  $item.find(".name").text(text);
  $item.find(".name").attr("title", title);
  $item.find(".idElemento").val("-1");
  $item.find(".idCrp").val(crpId);
  $item.find(".idFlagship").val(id);
  $list.append($item);
  $item.show('slow');
  checkNextFlagshipItems($list);
  updateFlagship();
}

function addCrp(id,text,title,crpId) {
  var $list = $('.flagshipList');
  var $item = $('#flagship-template').clone(true).removeAttr("id");
  $item.find(".name").text(text);
  $item.find(".name").attr("title", title);
  $item.find(".idElemento").val("-1");
  $item.find(".idCrp").val(crpId);
  $item.find(".idFlagship").val(id);
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

function flagshipService() {
  $(".flaghsipSelect").empty();
  $.ajax({
      'url': baseURL + '/flaghshipsByCrpAction.do',
      'type': "GET",
      'data': {
        crpID: $(this).find("option:selected").val()
      },
      'dataType': "json",
      beforeSend: function() {
      },
      success: function(m) {
        // console.log(m.flagships);
        $(".flaghsipSelect").addOption("-1", "Select an otpion...");
        $.each(m.flagships, function(i,e) {
          $(".flaghsipSelect").addOption(e.id, e.description);
        });

        $(".flaghsipSelect").trigger("change.select2");
      },
      complete: function() {
      },
      error: function() {
      }
  });
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
    $(".restrictionDate-block").find("label").text("Restricted access until");
    $("#restrictionDate").attr("name", "deliverable.dissemination.restrictedAccessUntil");
    $(".restrictionDate-block").show("slow");
  } else if($(this).val() == "effectiveDateRestriction") {
    $(".restrictionDate-block").find("label").text("Restricted embargoed date");
    $("#restrictionDate").attr("name", "deliverable.dissemination.restrictedEmbargoed");
    $(".restrictionDate-block").show("slow");
  } else {
    $(".restrictionDate-block").hide("slow");
  }
}

function setMetadata(data) {
  // $("a[href='#deliverable-mainInformation']").addClass("hideInfo");
  if($(".citationMetadata").val() == "") {
    $(".citationMetadata").val(data.citation).autoGrow();
  }
  if($("#deliverableMetadataDate").val() == "") {
    $("#deliverableMetadataDate").datepicker('setDate', data.publicationDate);
  }
  if($(".languageMetadata").val() == "") {
    $(".languageMetadata").val(data.languaje);
  }
  if($(".titleMetadata").val() == "") {
    $(".titleMetadata").val(data.title);
  }

  if($(".descriptionMetadata").val() == "") {
    $(".descriptionMetadata").val(data.description).autoGrow();
  }

  if($(".HandleMetadata").val() == "") {
    $(".HandleMetadata").val(data.handle);
  }
  if($(".DOIMetadata").val() == "") {
    $(".DOIMetadata").val(data.doi);
  }
  if($(".countryMetadata").val() == "") {
    $(".countryMetadata").val(data.country);
  }

  if($(".keywordsMetadata ").val() == "") {
    $(".keywordsMetadata ").val(data.keywords);
  }

}

function changeDisseminationChannel() {
  var channel = $(".disseminationChannel").val();
  $('#disseminationUrl').find("input").val("");
  $("#metadata-output").empty();
  $(".exampleUrl-block").hide();
  if(channel != "-1") {
    // CGSpace or Dataverse
    if((channel == "cgspace") || channel == "dataverse") {
      $("#fillMetadata").slideDown("slow");
      $(".exampleUrl-block.channel-" + channel).slideDown("slow");
    } else {
      $("#fillMetadata").slideUp("slow");
    }
    $('#disseminationUrl').slideDown("slow");
  } else {
    $('#disseminationUrl').slideUp("slow");
  }

  checkFAIRCompliant();
}

function addAuthor() {
  if($(".lName").val() != "" && $(".fName").val() != "") {
    $(".lName").removeClass("fieldError");
    $(".fName").removeClass("fieldError");
    $(".oId").removeClass("fieldError");
    var $list = $('.authorsList');
    var $item = $('#author-template').clone(true).removeAttr("id");
    $item.find(".lastName").html($(".lName").val() + ", ");
    $item.find(".firstName").html($(".fName").val());
    if($(".oId").val() == "") {
      $item.find(".orcidId").html("<b>orcid id:</b> not filled</small>");
      $item.find(".orcidIdInput").val("");
    } else {
      $item.find(".orcidId").html($(".oId").val());
      $item.find(".orcidIdInput").val($(".oId").val());
    }

    $item.find(".lastNameInput").val($(".lName").val());
    $item.find(".firstNameInput").val($(".fName").val());
    $list.append($item);
    $item.show('slow');
    updateAuthor();
    checkNextAuthorItems($list);

    $(".lName").val("");
    $(".fName").val("");
    $(".oId").val("");
  } else {
    $(".lName").addClass("fieldError");
    $(".fName").addClass("fieldError");
    $(".oId").addClass("fieldError");
  }
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
    // Set activity indexes
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

/* Load Metadata and fill fields */
function loadAndFillMetadata() {
  var channel = $(".disseminationChannel").val();
  var url = $.trim($(".deliverableDisseminationUrl").val());
  // jsUri Library (https://github.com/derek-watson/jsUri)
  var uri = new Uri(url);

  // Validate URL
  if(url == "") {
    return;
  }

  if(channel == "cgspace") {
    // Get CGSpace Metadata from MARLO server
    getCGSpaceMetadata(channel, url, uri);
  } else if(channel == "dataverse") {
    // Get Dataverse Metadata from native API
    getDataverseMetadata(channel, url, uri);
  }

}

function getCGSpaceMetadata(channel,url,uri) {
  var data = {
    pageID: "cgspace"
  }

  if(uri.host() == "hdl.handle.net") {
    console.log(uri.path());
    data.metadataID = "oai:cgspace.cgiar.org:" + uri.path().slice(1, uri.path().length);
  } else {
    data.metadataID = "oai:" + uri.host() + ":" + uri.path().slice(8, uri.path().length);
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
      success: function(m) {

        if(m.errorMessage) {
          $('#metadata-output').html(data.errorMessage);
        } else {
          m.metadata = JSON.parse(m.metadata);
          if(jQuery.isEmptyObject(m.metadata)) {
            $('#metadata-output').html("Metadata empty");
          } else {
            var fields = [];
            $.each(m.metadata, function(key,value) {
              console.log(key + "-" + value);
              fields.push(key.charAt(0).toUpperCase() + key.slice(1));
            });

            var sendDataJson = {};
            sendDataJson.title = m.metadata['title'];
            sendDataJson.citation = m.metadata['identifier.citation'];
            var date = m.metadata['date.available'].split("T");
            sendDataJson.publicationDate = date[0];
            sendDataJson.languaje = m.metadata['language.iso'];
            sendDataJson.description = m.metadata['description.abstract'];
            sendDataJson.handle = m.metadata['identifier.uri'];
            sendDataJson.doi = m.metadata['identifier.doi'];
            sendDataJson.country = m.metadata['coverage.country'];
            sendDataJson.keywords = m.metadata['subject'];
            setMetadata(sendDataJson);

            authorsByService([]);

            var $input = $(".accessible ").parent().find('input');
            if(m.metadata['identifier.status'] == "Open Access") {
              $input.val(true);
              $(".accessible ").parent().find("label").removeClass("radio-checked");
              $(".openAccessOptions").hide("slow");
              $(".yes-button-label ").addClass("radio-checked");
            } else {
              $input.val(false);
              $(".accessible ").parent().find("label").removeClass("radio-checked");
              $(".openAccessOptions").show("slow");
              $(".no-button-label ").addClass("radio-checked");
            }

            $('#metadata-output').empty().append("Found metadata for " + data.metadataID);
            // " <br /> " + fields.reverse().join(', '));
          }
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

function getDataverseMetadata(channel,url,uri) {
  /**
   * Dataverse metadata is harvest using swagger https://services.dataverse.harvard.edu/static/swagger-ui/
   */

  var data = {
      key: 'c1580888-185f-4250-8f44-b98ca5e7b01b',
      persistentId: uri.getQueryParamValue('persistentId')
  }

  $.ajax({
      // url: 'https://dataverse.harvard.edu/api/datasets/:persistentId/',
      url: 'https://services.dataverse.harvard.edu/miniverse/metrics/v1/datasets/by-persistent-id',
      data: data,
      beforeSend: function() {
        $(".deliverableDisseminationUrl").addClass('input-loading');
        $('#metadata-output').html("Searching ... " + data.persistentId);
      },
      success: function(m) {
        console.log("success");
        if(m.status == "OK") {

          console.log(m.data);

          // Setting Metadata
          setMetadata({
              title: m.data.title,
              citation: '',
              publicationDate: m.data.timestamps.publicationdate,
              languaje: '',
              description: function() {
                var output = "";
                $.each(m.data.metadata_blocks.citation.dsDescription, function(i,element) {
                  output += element.dsDescriptionValue;
                })
                return output;
              },
              keywords: function() {
                var output = [];
                $.each(m.data.metadata_blocks.citation.keyword, function(i,element) {
                  output.push(element.keywordValue);
                })
                return output.join(', ');
              },
              handle: '',
              doi: data.persistentId
          });

          // Getting authors
          var authors = [];
          $.each(m.data.metadata_blocks.citation.author, function(i,element) {
            authors.push({
                lastName: (element.authorName).split(',')[0],
                firstName: (element.authorName).split(',')[1],
                orcidId: element.authorIdentifier
            });
          });

          // Set Authors
          authorsByService(authors);

        } else {
          $('#metadata-output').empty().append("Invalid URL for searching metadata");
        }

      },
      complete: function() {
        $(".deliverableDisseminationUrl").removeClass('input-loading');
      },
      error: function() {
        $('#metadata-output').empty().append("Invalid URL for searching metadata");
      }
  });

}

function authorsByService(authors) {
  var $list = $('.authorsList');
  for(var i = 0; i < authors.length; i++) {
    var validation = validateAuthors(authors[i].lastName, authors[i].firstName);
    if(validation == false) {
      var $item = $('#author-template').clone(true).removeAttr("id");
      $($item).find(".lastName").text(authors[i].lastName + ", ");
      $($item).find(".firstName").text(authors[i].firstName);
      $($item).find(".orcidId").text(authors[i].orcidId);
      $($item).find(".lastNameInput").val(authors[i].lastName + ", ");
      $($item).find(".firstNameInput").val(authors[i].firstName);
      $($item).find(".orcidIdInput").val(authors[i].orcidId);
      $list.append($item);
      $item.show('slow');
      updateAuthor();
      checkNextAuthorItems($list);
    }
  }
}

function validateAuthors(lastName,firstName) {
  if($(".authorsList").find(".author input.lastNameInput[value='" + lastName + "']").exists()
      || $(".authorsList").find(".author input.firstNameInput[value='" + firstName + "']").exists()) {
    return true;
  } else {
    return false;
  }

}
