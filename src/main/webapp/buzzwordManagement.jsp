<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Buzzword Bingo</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
    <%@include file="common/imports.jsp"%>
    <script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
    <script type="text/javascript" src="js/scripts.js"></script>


    <script type="text/javascript">
        $(function () {

            $("#selectable").selectable({
                stop: function () {
                    var result = $("#select-result").empty();
                    $(".ui-selected", this).each(function () {
                        result.append(" " + ($(this).text()));
                    });
                }

            });

            $("#delete").click(function () {
                $('#selectable .ui-selected').hide();
                $("#select-result").empty();
            });

            $("#new").click(function () {

                if ($("li").length < 24 ) {

                    var name = $("#text").val();

                    $("#selectable").append("<li class='ui-widget-content' title='" + name + "'>" + name + "</li>");

                    $("li[title='" + name + "']").addClass("ui-selecting");

                    $("#text").val('');

                } else {

                    alert ("Sie haben die Maximalanzahl an hinzufügbaren Wörtern erreicht. " +
                        "Löschen Sie Wört oder übermitteln sie diese Liste an den Server.")

                }

            });

            $("#submitList").click( function () {

                if ($("li").length === 24 ) {

                    var arr = $("li").map(function() { return $(this).text() }).get();

                    //gameSocket.send(arr);

                } else {

                    alert ("Bitte geben Sie genau 24 Wörter an, Sie Pleb!");

                }


            });

        });
    </script>
    <link rel="stylesheet" href="css/stylesheet.css"/>

</head>

<body>

<script>connect()</script>

<header>
    <h1 class="h1">Buzzword Bingo</h1>
</header>

<table class="container">
    <tr>
        <td class="middle">
            <input id="text" type="text">
            <br><br>
            <button id="new">Add new Item</button>
            <button id="delete">Delete Selected Items</button>
            </div>
            <p id="feedback">
                <span>Selected Items:&nbsp;</span>
                <span id="select-result"></span>
            </p>

            <ol id="selectable">
                <li class="ui-widget-content">Item 1</li>
                <li class="ui-widget-content">Item 2</li>
                <li class="ui-widget-content">Item 3</li>
                <li class="ui-widget-content">Item 4</li>
                <li class="ui-widget-content">Item 5</li>
                <li class="ui-widget-content">Item 6</li>
            </ol>
            <button id="submitList" type="submit"></button>

        </td>
    </tr>
</table>
</body>
</html>
