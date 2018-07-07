jQuery.noConflict();
function addListItem() {
    'use strict';
    var text = $("#new-text").val();
    $("#kategori").append('<p><input type="checkbox" class="done" /> ' + text + '  <button class="delete">Delete</button></p>');
    $("#new-text").val('');
}

function deleteItem() {
    'use strict';
    $(this).parent().remove();
}

function finishItem() {
    'use strict';
    if ($(this).parent.css('textDecoration') === 'line-through') {
        $(this).parent().css('textDecoration', 'none');
        } else {
            $(this).parent().css('textDecoration', 'line-through');
        }
}

$ function () {
    'use strict';
    $("#add").on('click', addListItem);
    $(document).on('click', '.delete', deleteItem);
    $(document).on('click', '.done', finishItem);
});

$('#myTable tr').click(function(){
    $(this).remove();
    return false;
});

