$(document).ready(init);

function init() {
  // Add User - Override function from userManagement.js
  addUser = addUserItem;

// Numeric inputs
  $('input.keyOutputContribution').numericInput();
  $('input.outcomeContribution').numericInput();

  $('form select').select2({
    width: '100%'
  });

// Add a new cluster
  $('.addCluster').on('click', addCluster);
// Remove cluster
  $('.removeCluster').on('click', removeCluster);
// Remove person
  $('.remove-userItem').on('click', removePerson);
// Add a new Key Output
  $('.addKeyOutput').on('click', addKeyOutput);
// Remove key output
  $('.removeKeyOutput').on('click', removeKeyOutput);
// Remove outcome
  $('.removeOutcome').on('click', removeOutcome);

  updateClustersIndex();

  $(".keyOutputInput").on("change", changeTitle);
  $(".keyOutputInput").on("keyup", changeTitle);

  $(".keyOutputContribution").on("change", changeKOcontribution);
  $(".keyOutputContribution").on("keyup", changeKOcontribution);

  // Select outcomes
  $(".outcomeList").on("change", function() {
    var option = $(this).find("option:selected");
    if(option.val != "-1") {
      addOutcome(option);
    }
  });

  $('.blockTitle').on('click', function() {
    if($(this).hasClass('closed')) {
      $(this).parent().find('.blockTitle').removeClass('opened').addClass('closed');
      $(this).removeClass('closed').addClass('opened');
    } else {
      $(this).removeClass('opened').addClass('closed');
    }
    $(this).next().slideToggle('slow', function() {
      $(this).find('textarea').autoGrow();
    });
  });
}

// CLUSTERS

function addCluster() {
  var $list = $('.clusterList');
  var $item = $('#cluster-template').clone(true).removeAttr("id");
  $list.append($item);
  $item.show('slow');
  updateClustersIndex();
}

function removeCluster() {
  var $list = $(this).parents('.clusterList');
  var $item = $(this).parents('.cluster');
  $item.hide(1000, function() {
    $item.remove();
    updateClustersIndex();
  });
}

function updateClustersIndex() {
  var name = $("#clusterName").val();
  $(".clusterList").find('.cluster').each(function(i,item) {

    var customName = name + '[' + i + ']';
    $(item).find('span.index').html(i + 1);
    $(item).find('.outcome-statement').attr('name', customName + '.description');
    $(item).find('.cluterId').attr('name', customName + '.id');

    updateUsersIndex(item, customName);
    updateKeyOtuputsIndex(item, customName);
  });
}

// USERS-LEADERS
function removePerson() {
  var $item = $(this).parents('li');
  var $parent = $item.parent();
  $item.hide(function() {
    $item.remove();
    checkItems($parent);
    updateClustersIndex();
  });
}

function addUserItem(composedName,userId) {
  $usersList = $elementSelected.parent().parent().find(".leaders");
  var $li = $("#user-template").clone(true).removeAttr("id");
  var item = {
      name: escapeHtml(composedName),
      id: userId
  }
  if($usersList.find('input[value=' + item.id + ']').exists()) {
  } else {
    $li.find('.name').html(item.name);
    $li.find('.user').val(item.id);
    $usersList.append($li);
    $li.show('slow');
  }
  checkItems($usersList);
  dialog.dialog("close");
  updateClustersIndex();
}

function updateUsersIndex(item,clustersName) {
  var name = $("#leaderName").val();
  $(item).find('li').each(function(indexUser,userItem) {
    var customName = clustersName + '.' + name + '[' + indexUser + ']';
    $(userItem).find('.user').attr('name', customName + '.user.id');
    $(userItem).find('.role').attr('name', customName + '.role.id');
    $(userItem).find('.id').attr('name', customName + '.id');
  });

  // Update component event
  $(document).trigger('updateComponent');
}

// KEY OUTPUTS

// change title
function changeTitle() {
  var $blockTitle = $(this).parents(".keyOutputItem ").find(".koTitle");
  $blockTitle.html($(this).val());
  if($blockTitle.html() == "" || $blockTitle.html() == " ") {
    $blockTitle.html("New Key OutPut");
  }
}

// change key output contribution on title box
function changeKOcontribution() {
  var $blockTitle = $(this).parents(".keyOutputItem ").find(".koContribution-percentage");
  if($(this).val() == "" || $(this).val() == " ") {
    $blockTitle.html("0%");
  } else {
    $blockTitle.html($(this).val() + "%");
  }
}

function addKeyOutput() {
  console.log(this);
  var $list = $(this).parent().parent().find('.keyOutputsItems-list');
  var $item = $('#keyOutput-template').clone(true).removeAttr("id");
  $item.find(".keyOutputContribution").val(verifyKoContribution($list));
  $item.find("span .koContribution-percentage").html(verifyKoContribution($list) + '%');
  $list.append($item);
  $item.find("select").select2({
    width: "100%"
  });
  $item.show('slow');
  updateClustersIndex();
}

function removeKeyOutput() {
  var $item = $(this).parents('.keyOutputItem');
  var $parent = $item.parent();
  $item.hide(function() {
    $item.remove();
    updateClustersIndex();
  });
}

function updateKeyOtuputsIndex(item,clustersName) {
  var name = $("#keyOutputName").val();
  $(item).find('.keyOutputsItems-list .keyOutputItem').each(function(indexKeyOuput,keyOutputItem) {
    var customName = clustersName + '.' + name + '[' + indexKeyOuput + ']';
    $(keyOutputItem).find('.keyOutputInput').attr('name', customName + '.keyOutput');
    $(keyOutputItem).find('.id').attr('name', customName + '.id');
    $(keyOutputItem).find('.keyOutputContribution').attr('name', customName + '.contribution');

    updateOutcomesIndex(keyOutputItem, customName);
  });

  // Update component event
  $(document).trigger('updateComponent');
}

function verifyKoContribution(list) {
  var contribution = 0;
  var val = 0;
  list.find(".keyOutputItem ").each(function(i,e) {
    if($(e).find(".id ").val()) {
      // Existente
    } else {
      // nuevo
      if($(e).find(".keyOutputContribution ").val()) {
        val = parseInt($(e).find(".keyOutputContribution ").val());
        contribution = contribution + val;
      } else {
        val = 0;
        contribution = contribution + val;
      }
    }
  });
  var newContribution = 100 - contribution;
  return newContribution;
}

// OUTCOMES BY CoA

function addOutcome(option) {
  var $list = $(option).parents('.blockContent').find(".outcomesWrapper");
  var $item = $('#outcomeByCluster-template').clone(true).removeAttr("id");
  $item.find(".outcomeStatement").html(option.html());
  $item.find(".outcomeId").val(option.val());
  $list.append($item);
  $item.show('slow');
  updateClustersIndex();

}

function removeOutcome() {
  console.log("holiClose");
  var $item = $(this).parents('.outcomeByClusterItem');
  var $parent = $item.parent();
  $item.hide(function() {
    $item.remove();
    updateClustersIndex();
  });
}

function updateOutcomesIndex(item,keyOutputName) {
  var name = $("#outcomesName").val();
  $(item).find('.outcomesWrapper .outcomeByClusterItem').each(function(indexOutcome,outcomeItem) {
    var customName = keyOutputName + '.' + name + '[' + indexOutcome + ']';
    $(outcomeItem).find('.outcomeContribution ').attr('name', customName + '.contribution');
    $(outcomeItem).find('.outcomeId').attr('name', customName + '.crpProgramOutcome.id');
  });

  // Update component event
  $(document).trigger('updateComponent');
}

function checkItems(block) {
  console.log(block);
  var items = $(block).find('li').length;
  if(items == 0) {
    $(block).parent().find('p.inf').fadeIn();
  } else {
    $(block).parent().find('p.inf').fadeOut();
  }
}