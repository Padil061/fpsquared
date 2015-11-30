$( document ).ready(function() {
    $('#teamForm').validate({
        rules: {
            teamName: {
                required: true
            }
        }
    });
});

$('.sprint').click(function() {
    alert('You will now be taken to a new view which will have one to many stories, filled with one to many tasks');
});

$('.task').click(function() {
    alert('From here, you should be able to comment on a task / assign a user to a task / delete a task');
});

$('#createTeam').click(function(e) {
    if($('#teamName').val().length > 0)
        $('#teamForm').submit();

    return false;
});

