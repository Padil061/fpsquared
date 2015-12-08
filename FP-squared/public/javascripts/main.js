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

$('#menuBar').click(function(e) {
        $('.demo.sidebar')
          .sidebar('toggle');
});