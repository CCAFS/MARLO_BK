var $tableViewMore;
var tableDatatableViewmore, tableDataProgressTableViewmore;
var pageName;
var googleChartsLoaded = false;
$(document).ready(function() {
  pageName = actionName.replace(/[^a-z0-9+]+/gi, '_');

  // Set data tables
  if($.fn.DataTable) {
    $tableViewMore = $('.viewMoreSyntesis-block table');
    tableDatatableViewmore = $tableViewMore.DataTable({
        "paging": false,
        "searching": false,
        "info": false,
        "scrollY": "320px",
        "scrollCollapse": true,
        aoColumnDefs: [
          {
              sType: "natural",
              aTargets: [
                0
              ]
          }
        ]
    });

    $progressTableViewMore = $('.viewMoreSyntesisTable-block table');
    tableDataProgressTableViewmore = $progressTableViewMore.DataTable({
        "paging": true,
        "searching": true,
        "info": true,
        aoColumnDefs: [
          {
              sType: "natural",
              aTargets: [
                0
              ]
          }
        ]
    });

    tableDataExport = $('.dataTableExport table').DataTable({
        "paging": false,
        "searching": false,
        "info": true,
        dom: 'Bfrtip',
        buttons: [
          {
              text: '<i class="fas fa-download"></i> Export CSV Data',
              extend: 'csv',
              title: 'Data_export_' + currentSectionString + '_' + getDateString(),
              autoFilter: true,
              bom: true, // UTF-8
              className: 'exportCSV'
          }
        ]
    });
  }

  $('.urlify').each(function(i,urlifyText) {
    var text = $(urlifyText).html();

    if($(urlifyText).find('a').length > 0) {
      // Short URLs text
      $(urlifyText).find('a').each(function(iAnchor,anchor) {
        var anchorText = $(anchor).text();
        $(anchor).text(truncate(anchorText, 45));
      });
    } else {
      // URLfy links (Works only for plain text)
      $(urlifyText).html(urlifyComplete(text));
    }

    $(urlifyText).find('a').addClass('dont-break-out');

  });

  // Load indexTab
  loadTab();

  // Save Local storage indexTab
  $('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
    window.localStorage.setItem(pageName, JSON.stringify({
      "indexTab": $(this).attr("index")
    }));
  });

  // checkbox disables field
  console.log("init press");
  setStatusByBack();
  $('.checkboxDiTeArClick').on('click',setCheckboxValueTohide);

});
function setStatusByBack() {
  $(".sloTargetsList")
    .find(".sloTarget")
    .each(function (i, field) {
      // console.log($(field).find(".checkboxDiTeArClick").val());

      let checkbox = $(field).find(".checkboxDiTeArClick");

      // console.log("init value: "+$(this).val());
      if ($(checkbox).val() == "true") {
        $(checkbox).val("false");
        // console.log("now is: "+$(this).val());
      } else {
        $(checkbox).val("true");
        // console.log("now is: "+$(this).val());
      }
      if ($(checkbox).val() == "true") {
        $(checkbox).parents(".sloTarget").addClass("disabled");
        let $currrentSlo = $(checkbox).parents(".sloTarget");
        $($currrentSlo)
          .find(".trumbowyg-box")
          .each(function (i, field) {
            $(field).find(".trumbowyg-button-pane").hide();
            $(field).find(".trumbowyg-editor").attr("contenteditable", "false");
          });
      } else {
        $(this).parents(".sloTarget").removeClass("disabled");
        let $currrentSlo = $(checkbox).parents(".sloTarget");
        $($currrentSlo)
          .find(".trumbowyg-box")
          .each(function (i, field) {
            $(field).find(".trumbowyg-button-pane").show();
            $(field).find(".trumbowyg-editor").attr("contenteditable", "true");
          });
      }
      // $('.editor').trumbowyg('disable');
    });
}

function setCheckboxValueTohide() {
  // console.log("init value: "+$(this).val());
  if ($(this).val() == "true") {
    $(this).val("false");
    // console.log("now is: "+$(this).val());
  } else {
    $(this).val("true");
    // console.log("now is: "+$(this).val());
  }

  if ($(this).val() == "true") {
    $(this).parents(".sloTarget").addClass("disabled");
    let $currrentSlo = $(this).parents(".sloTarget");
    $($currrentSlo)
      .find(".trumbowyg-box")
      .each(function (i, field) {
        $(field).find(".trumbowyg-button-pane").hide();
        $(field).find(".trumbowyg-editor").attr("contenteditable", "false");
      });
  } else {
    $(this).parents(".sloTarget").removeClass("disabled");
    let $currrentSlo = $(this).parents(".sloTarget");
    $($currrentSlo)
      .find(".trumbowyg-box")
      .each(function (i, field) {
        $(field).find(".trumbowyg-button-pane").show();
        $(field).find(".trumbowyg-editor").attr("contenteditable", "true");
      });
  }
  // $('.editor').trumbowyg('disable');
}


function loadTab() {
  var ls = JSON.parse((window.localStorage.getItem(pageName)));
  if((ls != null)) {
    $('.bootstrapTabs li:eq(' + ls.indexTab + ') a').tab('show');
  }

}

/**
 * Get chart data in Array
 *
 * @param chart
 * @returns
 */
function getChartDataArray(chart) {
  var dataArray = [];
  $(chart).find('.chartData li').each(function(i,e) {
    dataArray.push($(e).find('span').map(function() {
      var text = $(this).text();
      if($(this).hasClass('number')) {
        return parseFloat(text);
      } else if($(this).hasClass('json')) {
        return JSON.parse(text);
      } else {
        return text;
      }
    }).toArray());
  });
  return dataArray;
}

function createGooglePieChart(chartID,options) {
  createGoogleChart(chartID, "Pie", options);
}

function createGoogleBarChart(chartID,options) {
  createGoogleChart(chartID, "Bar", options);
}

function createGoogleChart(chartID, type, options) {
  if (!googleChartsLoaded) {
    google.charts.load("current", {
      packages: ["corechart", "bar"],
    });
    googleChartsLoaded = true;
  }

  var $chart = $(chartID);
  if ($chart.exists()) {
    google.charts.setOnLoadCallback(function () {
      $chart.addClass("loaded");
      var data = new google.visualization.arrayToDataTable(
        getChartDataArray($chart)
      );
      console.log(data);
      if (!data) {
        $chart.append(
          '<p  class="text-center"> ' + options.title + " <br>  No data </p>"
        );
      } else {
        if (type == "Bar") {
          var view = new google.visualization.DataView(data);
          var chart = new google.visualization.BarChart(
            document.getElementById($chart[0].id)
          );
          chart.draw(view, google.charts.Bar.convertOptions(options));
        } else if (type == "Pie") {
          var chart = new google.visualization.PieChart(
            document.getElementById($chart[0].id)
          );
          chart.draw(data, options);
        }
      }
      console.log(type + ": " + chartID, options.title);
    });
  }
}
