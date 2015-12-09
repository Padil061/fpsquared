$( document ).ready(function() {
    $('#teamForm').validate({
        rules: {
            teamName: {
                required: true
            }
        }
    });

    var lastPlace;
});

// Event listeners
$('#createTeam').click(function(e) {
        $('#teamForm').submit();
});

$('#joinTeam').click(function(e) {
        $('#joinForm').submit();
});

$('#createSprint').click(function(e) {
        $('#sprintForm').submit();
});

$('#closeSprint').click(function(e) {
        $('#closeSprintForm').submit();
});

$('#createStory').click(function(e) {
        $('#storyForm').submit();
});

$('#createTask').click(function(e) {
        $('#taskForm').submit();
});

$('#createComment').click(function(e) {
        $('#commentForm').submit();
});

$('#menuBar').click(function(e) {
        $('.demo.sidebar')
          .sidebar('toggle');
});


$('#show-sidebar').click(function(e) {
      $('#show-sidebar').hide();
      $('.menu.sidebar').sidebar('toggle');
});

$('#hide-sidebar').click(function(e) {
      $('#show-sidebar').show();
      $('.menu.sidebar').sidebar('toggle');
});

/* Dragging tasks */
/*
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("src", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var src = document.getElementById(ev.dataTransfer.getData("src"));
    var srcParent = src.parentNode;
    var tgt = ev.currentTarget.firstElementChild;

    ev.currentTarget.replaceChild(src, tgt);
    srcParent.appendChild(tgt);
}
*/

   $(".taskStatus li").draggable({
        revert: true,
        zIndex: 10,
        snap: ".taskStatus li",
        snapMode: "inner",
        snapTolerance: 40,
        start: function (event, ui) {
            lastPlace = $(this).parent();
        }
    });

    $(".taskStatus li").droppable({
        drop: function (event, ui) {
            var droppedDiv = ui.draggable;
            var droppedOn = this;

            var dropped = droppedDiv[0];

       //     if ($(droppedOn).children().length > 0) {
                $(droppedOn).detach().appendTo($(lastPlace));
                $(droppedDiv).remove();
                $(dropped).detach().appendTo($(droppedOn));

       //     }
/*
            $(dropped).detach().css({
                top: 0,
                left: 0
            }).prependTo($(droppedOn)); */
        }
    });