$("#loginForm").submit(function(event) {
    event.preventDefault();
    //hide any previous errors
    $("#failure").hide();
    
    var email = $("#login_email").val();
    var password = $("#login_password").val();

    var postFields = {
        email: email,
        password: password,
    };

    $.ajax({
        type: "POST",
        data: postFields,
        cache: false,
        url: "api/users/authenticate",
        success: function(data) {
                    
            if (data.result === "true") {
                console.log("success");
                if(data.admin === "false"){
                 //redirect to admin page
                 
                }else{
                 //redirect to user page   
                }
            } else {
                console.log("failure");
                //display error
                $("#failure-message").html("The username/password you entered is incorrect");
                $("#failure").show();
                
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
             $("#failure-message").html("There was a problem attempting to log you in. Please try again.");
             $("#failure").show();
        }
    });

    return false;
});

