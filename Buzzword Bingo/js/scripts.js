 <!-- https://www.youtube.com/watch?v=wSnELgwFFiQ-->
 
      
    function addWord(){
        var text = $("#new-text").val();
        $("#buzzwordbingo").append('<td><input type = "checkbox" class = "done" /> '+text+' <button class = "delete">Delete</button></td>');
        $("new-text").val('');
    }
    function deleteWord(){
        $(this).parent().remove();
    }
    function finishWord(){
        if ($(this).parent().css('textDecoration')== 'line-through'){
            $(this).parent().css('textDecoration', 'none');
        }else{
            $(this).parent().css('textDecoration', 'line-through');
        }
    }
    $(function(){
        $("#add").on('click', addWord);
        $(document).on('click', '.delete', deleteWord);
        $(document).on('click', '.done', finishWord);
    });
