$(document).ready(
    function () {
        refresh();

        $('#comboboxAuthor').select2({
            placeholder: "Sélectionner un auteur",
            minimumInputLength: 4
        });

        /***********************************
            BEGIN: BUTTON INITIALISATION
         ***********************************
         */
        $('#bookAdd').on('click', function () {
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
            var lineTemplate = "<tr><td>{{id}}</td><td>{{author.firstname}} {{author.lastname}}</td><td>{{title}}</td><td>{{collection}}</td><td>{{shelf}}</td></tr>";

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

}
