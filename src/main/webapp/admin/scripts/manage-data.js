function request() {

    $.ajax({
        type: "GET",
        cache: false,
        url: "car-details.php",
        success: function (data) {
            console.log("SUCCESS");

            $("#cars").html(data.cars);
        }
    });

}


function add() {

    //do another ajax call here with the form data you want posted

    $.ajax({
        type: "GET",
        cache: false,
        data: $("#modelForm").serialize(),
        url: "add-car.php",
        success: function (data) {
            console.log("SUCCESS");

            $("#models").html(data.cars);
            $("#alt").html(data.alt);
        }
    });



}

$("#modelForm").submit(function (event) {
    add();
});

// call the initial request
request();