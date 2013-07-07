var bookEditableTpl;

$(document).ready(
    function () {
        refresh();

        // Chargement des template au debut
        // FIXME: use ICANHAZ.JS qui fait ça
        // Ex: http://ohmydev.wordpress.com/2012/12/11/mustache-js-et-icanhaz-js-au-secours-de-jquery/
        bookEditableTpl = $('#bookEditableTpl').html();

        $('#comboboxAuthor').select2({
            placeholder: "Sélectionner un auteur",
            minimumInputLength: 2
        });

        $('#authorAdd').on('click', function () {
            var firstname = $('#firstname').val(),
                lastname = $('#lastname').val();

            $.post('/resource/author/add', {firstname: firstname, lastname: lastname}, function (data) {
                console.log(data);

                refresh();
            });
        });

        /***********************************
         END: BUTTON INITIALISATION
         ***********************************
         */
    }
);

function refresh() {
    $.ajax({
        url: "/resource/book/all",
        data: {},
        success: function (data) {
            var list = "";
            var lineTemplate = $('#bookTpl').html();

            $.each(data, function () {
                list += Mustache.render(lineTemplate, this);
            });

            $("#book tbody").html(list);
        }
    });

    $.ajax({
        url: "/resource/author/all",
        data: {},
        success: function (data) {
            var template = "{{firstname}} {{lastname}}";

            $.each(data, function() {
                $('#comboboxAuthor')
                    .append($("<option></option>")
                        .attr("value",this.id)
                        .text(Mustache.render(template, this)));
            });
        }
    });

    /***********************************
     BEGIN: BUTTON INITIALISATION
     ***********************************
     */
    $('#bookAdd').on('click', function () {
        console.log('INFO: Save button click');

        var title = $('#title').val(),
            collection = $('#collection').val(),
            shelf = $('#shelf').val(),
            authorId = $('#comboboxAuthor').val();

        $.post('/resource/book/add', {title: title, collection: collection, shelf: shelf, authorId: authorId}, function (data) {
            console.log(data);
//                    var user = JSON.stringify(data);
//                    $.cookie(COOKIE_NAME, user, COOKIE_OPTIONS);
//                    configureLoginButton(data);
            refresh();
        });
    });

}

/**
 *
 * @param title
 */
function makeBookEditable(bookId) {
    var book = {
        id: bookId,
        author: $('#book' + bookId).find('td[name=author]').text(),
        title: $('#book' + bookId).find('td[name=title]').text(),
        collection: $('#book' + bookId).find('td[name=collection]').text(),
        shelf: $('#book' + bookId).find('td[name=shelf]').text()
    }

    var result = Mustache.render(bookEditableTpl, book);

    $('#book' + bookId).after(result);

    $('#bookEditableButton' + bookId).removeAttr('onclick').click(function(){});
    $('#bookEditableButton' + bookId).unbind('click');
}

function stopBookEdition(bookId) {
    $('#bookEditable' + bookId).remove();

    $('#bookEditableButton' + bookId).removeAttr('onclick').click(function() {makeBookEditable(bookId)});
}

function saveModifiedBook(bookId) {
    var id = bookId,
        title = $('#title' + bookId).val(),
        collection = $('#collection' + bookId).val(),
        shelf = $('#shelf' + bookId).val();

    console.log("title: " + title + ", collection: " + collection + ", shelf: " + shelf);

    $.post('/resource/book/update', {id: id, title: title, collection: collection, shelf: shelf, authorId: 2}, function () {
        stopBookEdition(bookId);

        $("#book" + bookId).find('td[name=title]').html(title);
        $("#book" + bookId).find('td[name=collection]').html(collection);
        $("#book" + bookId).find('td[name=shelf]').html(shelf);
    });
}