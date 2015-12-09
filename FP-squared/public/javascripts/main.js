$( document ).ready(function() {
    $('#teamForm').validate({
        rules: {
            teamName: {
                required: true
            }
        }
    });
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


$('#show-sidebar').click(function() {
      $('#show-sidebar').hide();
      $('.menu.sidebar').sidebar('toggle');
});

$('#hide-sidebar').click(function() {
      $('#show-sidebar').show();
      $('.menu.sidebar').sidebar('toggle');
});