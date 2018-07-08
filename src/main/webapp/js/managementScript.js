
$(function(){
    var $curParent, Content;
    $(document).delegate("span","click", function(){
        if($(this).closest("s").length) {
            Content = $(this).parent("s").html();
            $curParent = $(this).closest("s");
            $(Content).insertAfter($curParent);
            $(this).closest("s").remove();
        }
        else {
            $(this).wrapAll("<s />");
        }
    });
});

// When the user clicks on div, open the popup
function toggleText() {
    var popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
}

function addElement() {
    var input = document.getElementById("new-word").value;
    var ul = document.getElementById("wordList");
    var li = document.createElement("li");
    li.appendChild(document.createTextNode(input));
    ul.appendChild(li);
    $("#new-word").val('');
}

function removeElement() {
    var x = document.getElementById("wordList");

    if(x.selectedIndex != NULL) {

        x.remove(x.selectedIndex);

    }
}

$( function() {
    $( "#selectable" ).selectable();
} );

