$(document).ready(
    function () {
        refresh();

        $('#comboboxAuthor').select2({
            placeholder: "Sélectionner un auteur"
        });

        // Initialisation du bouton d'ajout des livres
        $('#bookAdd').on('click', function () {
            var title = $('#title').val(),
                collection = $('#collection').val(),
                shelf = $('#shelf').val(),
                authorId = $('#comboboxAuthor').val();

            $.post('/gerome/resource/book/add', {title: title, collection: collection, shelf: shelf, authorId: authorId}, function (data) {
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

            $.post('/gerome/resource/author/add', {firstname: firstname, lastname: lastname}, function (data) {
                console.log(data);

                refresh();
            });
        });

    }
);

function refresh() {
    $.ajax({
        url: "/gerome/resource/book/all",
        data: {},
        success: function (data) {
            var liste = "";

            $.each(data, function () {
                var firstname = "",
                    lastname = "";

                if (this.author != null) {
                    firstname = this.author.firstname,
                        lastname = this.author.lastname;
                }


                liste += "<tr><td>" + this.id + "</td><td>" + firstname + " " + lastname + "</td><td>" + this.title + "</td><td>" + this.collection + "</td><td>" + this.shelf + "</td></tr>"
            });

            $("#book tbody").html(liste);
        }
    });

    $.ajax({
        url: "/gerome/resource/author/all",
        data: {},
        success: function (data) {
            var liste = "";

            $.each(data, function () {
                liste += "<tr><td>" + this.id + "</td><td>" + this.firstname + "</td><td>" + this.lastname + "</td></tr>"
            });

            $("#author tbody").html(liste);

            $.each(data, function() {
                $('#comboboxAuthor')
                    .append($("<option></option>")
                        .attr("value",this.id)
                        .text(this.firstname + " " + this.lastname));
            });
        }
    });


}
