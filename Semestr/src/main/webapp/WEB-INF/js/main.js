function subscribe(id) {
    $.ajax({
        url: "subscriptions/subscribe",
        type: "GET",
        data: {
            idUser: id
        },
        success: function (response) {
            document.getElementById('btn_subscribe').hidden = true;
            document.getElementById('btn_unsubscribe').hidden = false;
        }
    });
}

function unsubscribe(id) {
    $.ajax({
        url: "subscriptions/unsubscribe",
        type: "GET",
        data: {
            idUser: id
        },
        success: function (response) {
            document.getElementById('btn_subscribe').hidden = false;
            document.getElementById('btn_unsubscribe').hidden = true;
        }
    });
}