var yesnoEvent;
var notyDefaultOptions = {
    text: '',
    layout: 'bottomRight',
    type: 'error',
    theme: 'relax',
    timeout: 5000,
    animation: {
        open: 'animated bounceInRight',
        close: 'animated bounceOutRight'
    },
    closeWith: [
      'click'
    ]
};

// Global javascript must be here.
$(document).ready(function() {
  showNotificationMessages();
  showHelpText();
  applyWordCounter($("#justification"), justificationLimitWords);

  // Yes / No Event
  $('input.onoffswitch-radio').on('change', function(e) {
    yesnoEvent($(this));
  });

  // hash url animation
  if(window.location.hash) {
    if($(window.location.hash).exists && hashScroll) {
      $('html, body').animate({
        scrollTop: $(window.location.hash).offset().top - 110
      }, 1500);
    }
  }

  if($('#mainMenu').exists()) {
    var mainMenuPosition = $('#mainMenu').position().top + 20;
    $(window).scroll(function() {
      if($(window).scrollTop() >= mainMenuPosition) {
        $('#mainMenu .menuContent').addClass('positionFixedTop');
      } else {
        $('#mainMenu .menuContent').removeClass('positionFixedTop');
      }
    });
  }

  // Function that set the interface buttons always visible
  var $buttons = $('.buttons');
  if($buttons.exists()) {
    var menuOffset = function() {
      return $(document).height() - ($buttons.offset().top + $buttons.height());
    }
    $buttons.find('.buttons-content').css({
      right: $(document).width() - ($buttons.offset().left + $buttons.width())
    });

    setFixedElement($(window).scrollBottom() >= menuOffset());
    $(window).scroll(function() {
      setFixedElement($(window).scrollBottom() >= menuOffset());
    });
  }

  function setFixedElement(isFixed) {
    if(isFixed) {
      $buttons.find('.buttons-content').addClass('positionFixedBot animated flipInX');
    } else {
      $buttons.find('.buttons-content').removeClass('positionFixedBot animated flipInX');
    }
  }

  function showHelpText() {
    $('.helpMessage').addClass('animated flipInX');
  }

  function showNotificationMessages() {
    $('#generalMessages .messages').children("li").each(function(index) {
      // Validate if the notification is a warning checking if the text contains --warn--
      var message = $(this).html();
      var messageType;
      if(message.lastIndexOf("--warn--", 0) === 0) {
        message = message.replace("--warn--", " ");
        messageType = 'warning';
      } else {
        messageType = $(this).attr("class");
      }

      $('#generalMessages').noty({
          theme: 'relax',
          layout: 'top',
          theme: 'relax', // or 'relax'
          type: messageType,
          text: message, // can be html or string
          dismissQueue: true, // If you want to use queue feature set this true
          animation: {
              open: 'animated flipInX', // Animate.css class names
              close: 'animated flipInX' // Animate.css class names
          },
          timeout: 10000, // delay for closing event. Set false for sticky notifications
          force: false, // adds notification to the beginning of queue when set to true
          modal: false,
          maxVisible: 5, // you can set max visible notification for dismissQueue true option,
          killer: false, // for close all notifications before show
          closeWith: [
            'click'
          ]

      });
    });
  }

  /* Tooltips with JQuery UI */
  $(this).tooltip({
      track: true,
      content: function() {
        return $(this).attr('title');
      }
  });

  /* Event triggers */
  $(document).on('addComponent', function() {
    console.log('Add Component');
  });

  $(document).on('removeComponent', function() {
    console.log('Remove Component');
  });

  yesnoEvent = function(target) {
    // var isChecked = $(this).is(':checked');
    $t = $(target);
    var isChecked = ($t.val() === "true");
    $t.siblings().removeClass('radio-checked');
    $t.next().addClass('radio-checked');
    var array = $t.attr('name').split('.');
    var $aditional = $('#aditional-' + array[array.length - 1]);
    if($t.hasClass('inverse')) {
      isChecked = !isChecked;
    }
    if(isChecked) {
      $aditional.slideDown("slow");
    } else {
      $aditional.slideUp("slow");
      $aditional.find('input:text,textarea').val('');
    }
  }

  /**
   * Tick Box functions
   */
  var $tickBoxWp = $('.tickBox-wrapper input[type=checkbox]');

  $tickBoxWp.on('change', toggleInputs);
  $tickBoxWp.each(function() {
    var $toggle = $(this).closest('.tickBox-wrapper').find('.tickBox-toggle');
    if($(this).is(':checked')) {
      $toggle.show();
    } else {
      $toggle.hide();
    }
  });

  function toggleInputs(e) {
    $(this).parent().parent().parent().find('.tickBox-toggle').slideToggle($(e.target).is(':checked'));
  }

  // History log popup
  $('.button-history').on('click', function() {
    $('#log-history').dialog({
        modal: true,
        maxWidth: '500px',
        width: '70%',
        buttons: {
          Cancel: function() {
            $(this).dialog("close");
          }
        }
    });
  });

  $("textarea[id!='justification']").autoGrow();

  // Generating hash from form information
  setFormHash();

});

function isReportingCycle() {
  var url = window.location.href;
  return(url.indexOf("/reporting") > -1)
}

function isPlanningCycle() {
  var url = window.location.href;
  return(url.indexOf("/planning") > -1)
}

/**
 * Validate fields length when click to any button
 */
function validateEvent(fields) {
  var $justification = $('#justification');
  var $parent = $justification.parent().parent();
  var errorClass = 'fieldError';
  $parent.prepend('<div class="loading" style="display:none"></div>');
  $('[name=save], [name=next]').on('click', function(e) {
    $parent.find('.loading').fadeIn();
    var isNext = (e.target.name == 'next');
    $justification.removeClass(errorClass);
    /*
     * var fieldErrors = $(document).find('input.fieldError, textarea.fieldError').length; if(fieldErrors != 0) {
     * e.preventDefault(); $parent.find('.loading').fadeOut(500); var notyOptions = jQuery.extend({},
     * notyDefaultOptions); $('html, body').animate({ scrollTop: $('.fieldError').offset().top - 80 }, 700);
     * notyOptions.text = 'Something is wrong in this section, please fix it then save'; noty(notyOptions); } else {
     */
    if(!isChanged() && !forceChange && !isNext) {
      // If there isn't any changes
      e.preventDefault();
      $parent.find('.loading').fadeOut(500);
      var notyOptions = jQuery.extend({}, notyDefaultOptions);
      notyOptions.text = 'Nothing has changed';
      notyOptions.type = 'alert';
      noty(notyOptions);
    } else {
      if(errorMessages.length != 0) {
        // If there is an error message
        e.preventDefault();
        $parent.find('.loading').fadeOut(500);
        var notyOptions = jQuery.extend({}, notyDefaultOptions);
        notyOptions.text = errorMessages.join();
        noty(notyOptions);
      } else if(!validateField($('#justification')) && (isChanged() || forceChange)) {
        // If field is not valid
        e.preventDefault();
        $parent.find('.loading').fadeOut(500);
        $justification.addClass(errorClass);
        var notyOptions = jQuery.extend({}, notyDefaultOptions);
        notyOptions.text = 'The justification field needs to be filled';
        noty(notyOptions);
      }
    }
    // }

  });

  // Force change when an file input is changed
  $("input:file").on('change', function() {
    forceChange = true;
  });
}

function isChanged() {
  return(formBefore != getFormHash());
}

function setFormHash() {
  formBefore = getFormHash();
}

function getFormHash() {
  return getHash($('form [id!="justification"]').serialize());
}

function getHash(str) {
  var hash = 0, i, chr, len;
  if(str.length == 0) {
    return hash;
  }
  for(i = 0, len = str.length; i < len; i++) {
    chr = str.charCodeAt(i);
    hash = ((hash << 5) - hash) + chr;
    hash |= 0; // Convert to 32bit integer
  }
  return hash;
}

setWordCounterToInputs('limitWords');
/* Set word counter to inputs list where cssName could be limitWords */
function setWordCounterToInputs(cssName) {
  // Attribute contains certain value somewhere -> [class*="limitWords"]
  var check = cssName + "-";
  $('input[type=text][class*="' + cssName + '"], textarea[class*="' + cssName + '"]').each(function(i,input) {
    var className = $(input).attr('class') || '';
    var cls = $.map(className.split(' '), function(val,i) {
      if(val.indexOf(check) > -1) {
        return val.slice(check.length, val.length);
      }
    });
    applyWordCounter($(input), (cls.join(' ')) || 100);
  });
}

/**
 * * MARLO Messages Widget
 */
if(getCookie('messageDelivered') == ""){
  document.cookie = 'messageDelivered=false';  
}
if(getCookie('messageMin') == ""){
  document.cookie = 'messageMin=false';  
}
(function worker() {
  $.ajax({
      url: baseURL + '/systemMessages.json',
      cache: false,
      success: function(data) {
        if(data.active && !(getCookie('messageDelivered') === 'true')) {
          showSystemResetMessage(data);
        } else if(!data.active) {
          $.noty.closeAll();
          document.cookie = 'messageDelivered=false';
        }
      },
      complete: function() {
        // Schedule the next request when the current one's complete
        setTimeout(worker, 1000*5);
      }
  });
})();

function showSystemResetMessage(data) {
  var currentTime = new Date(data.serverCurrentTime);
  var deadlineTime = new Date(data.messageDeadline);
  var diff = deadlineTime - currentTime
  if(diff <= 0){
    return
  }
  var $timer = $('#timer-content').clone(true).removeAttr('id');
  $timer.find('.message').html(data.message);
  $timer.find('.countdown').countdown({
      date: +(new Date) + (diff),
      render: function(data) {
        $(this.el).text(this.leadingZeros(data.min, 2) + " min " + this.leadingZeros(data.sec, 2) + " sec");
        if(this.leadingZeros(data.min, 1) == 0) {
          $(this.el).addClass('ended animated infinite flash');
        } else {
          $(this.el).removeClass('ended animated infinite flash');
        }
      }
  });
  if((getCookie('messageMin') === 'true')){
    showMinNotification($timer);
  }else{
    showFullNotification($timer);
  }
  document.cookie = 'messageDelivered=true';

}

function showFullNotification(timer) {
  noty({
      text: $(timer),
      type: 'alert',
      dismissQueue: true,
      layout: 'center',
      theme: 'relax',
      modal: true,
      buttons: [
        {
            addClass: 'btn btn-primary',
            text: 'Roger that',
            onClick: function($noty) {
              $noty.close();
            }
        }
      ],
      callback: {
        afterClose: function() {
          document.cookie = 'messageMin=true';
          showMinNotification($(timer));
        },
      },
  });
}

function showMinNotification(timer) {
  $(timer).find('.countdown').addClass('small');
  $(timer).find('.message').hide();
  noty({
      text: $(timer),
      theme: 'relax',
      layout: 'topCenter',
      animation: {
          open: 'animated fadeInUp',
          close: 'animated fadeOutUp'
      }
  });
}


/** secondaryMenu * */

$('.selectedProgram, selectedProject').on('click', function(){
  $(this).parent().next().slideToggle('slow');
});