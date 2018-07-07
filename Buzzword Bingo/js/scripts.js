 <!-- https://www.youtube.com/watch?v=wSnELgwFFiQ-->
 
      
   $(document).ready (function addWord(){
        var text = $("#new-text").val();
        $("#buzzwordbingo").append('<td><input type = "checkbox" class = "done" /> '+text+' <button class = "delete">Delete</button></td>');
        $("new-text").val('');
    });
   $(document).ready (function deleteWord(){
        $(this).parent().remove();
    });
   $(document). function finishWord(){
        if ($(this).parent().css('textDecoration')== 'line-through'){
            $(this).parent().css('textDecoration', 'none');
        }else{
            $(this).parent().css('textDecoration', 'line-through');
        }
    }
   $(document). $(function(){
        $("#add").on('click', addWord);
        $(document).on('click', '.delete', deleteWord);
        $(document).on('click', '.done', finishWord);
    });
    $('#myTable tr').click(function(){
    $(this).remove();
    return false;
    });

    $(".delete").live('click', function(event) {
    $(this).parent().parent().remove();
    });


    $($(this).closest("tr")).remove()