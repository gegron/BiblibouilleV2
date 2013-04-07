$(document).ready(
    function() {
        refreshBook();

        // Initialisation du bouton d'ajout des livres
        $('#bookAdd').click(function () {
                var $title = $('#title').val(),
                    $collection = $('#collection').val(),
                    $etage = $('#etage').val();

                $.post('/resource/book/add',  {title:$title, collection:$collection, etage:$etage}, function (data) {
                    console.log(data);
//                    var user = JSON.stringify(data);
//                    $.cookie(COOKIE_NAME, user, COOKIE_OPTIONS);
//                    configureLoginButton(data);
                    refreshBook();
                });
        });

    }
);

function refreshBook() {
    $.ajax({
        url: "/resource/book/all",
        data: {},
        success:
            function( data ) {
                var liste = "";

                $.each(data, function() {
                    liste += "<tr><td>" + this.id + "</td><td>" + this.titre + "</td><td>" + this.collection + "</td><td>" + this.etage + "</td></tr>"
                });

                $("#book tbody").html(liste);
            }
    });
}
