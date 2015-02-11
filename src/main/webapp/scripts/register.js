$( "#registerForm" ).submit(function( event ) {
  event.preventDefault();
  

  
  var postFields = {first_name: $("#first_name").val(),
                    last_name: $("#last_name").val(),
                    email: $("#email").val(),
                    confirm_email: $("#confirm_email").val(),
                    password: $("#password").val(),
                    password_confirm: $("#password_confirm").val(),
                    position: $("#position").val(),
                    reason: $("#reason").val()};

   $.ajax({   
            type: "POST",
            data: postFields,
            cache: false,  
            url: "api/pending-users",   
            success: function(data){
                
                console.log(data);
                if(data.result === "success"){
                    $("#success-message").html("Your request was submitted successfully.");
                    $("#failure").hide();
                    $("#success").show();
                }else{
                    $("#failure-message").html("There was a problem submitting your request. Please Try again.");
                    $("#failure").show();
                }
            }   
        });   
        
    return false;
});