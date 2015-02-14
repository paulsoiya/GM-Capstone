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


function add(){
    
    //do another ajax call here with the form data you want posted
}


// call the initial request
request();