$( document ).ready(function() {
    $('#teamForm').validate({
        rules: {
            teamName: {
                required: true
            }
        }
    });

    // Sidebar for the dashboard menu (reference : https://github.com/jillix/jQuery-sidebar)
    $(".sidebar.right").sidebar({speed: 400}).trigger("sidebar:open");
});

$('.sprint').click(function() {
    alert('You will now be taken to a new view which will have one to many stories, filled with one to many tasks');
});

$('.task').click(function() {
    alert('From here, you should be able to comment on a task / assign a user to a task / delete a task');
});

$('#createTeam').click(function(e) {
        $('#teamForm').submit();
});

$('#joinTeam').click(function(e) {
        $('#joinForm').submit();
});

$('#menuBar').click(function(e) {
       // $(".sidebar.right").trigger("sidebar:toggle");
});