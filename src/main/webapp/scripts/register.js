function clearFields(){
	$("#first_name").val("");
	$("#last_name").val("");
	$("#email").val("");
	$("#confirm_email").val("");
	$("#password").val("");
	$("#confirm_password").val("");
	$("#position").val("");
	$("#reason").val("");
}


$( "#registerForm" ).submit(function( event ) {
  event.preventDefault();
  
  var firstName = $("#first_name").val();
  var lastName = $("#last_name").val();
  var email = $("#email").val();
  var confirmEmail = $("#confirm_email").val();
  var password = $("#password").val();
  var confirmPassword = $("#confirm_password").val();
  var position = $("#position").val();
  var reason = $("#reason").val();
  
 //validate form
 if(!isValidLength(firstName, 1) ){
     $("#failure-message").html("You must enter your first name.");
     $("#failure").show();
    return;
 }else if(!isValidLength(lastName, 1) ){
     $("#failure-message").html("You must enter your last name.");
     $("#failure").show();
    return;
 }else if(!isValidEmail(email)){
     $("#failure-message").html("You must enter a valid email address.");
     $("#failure").show();
    return;
 }else if(!isMatching(email, confirmEmail)){
     $("#failure-message").html("The emails you entered do not match.");
     $("#failure").show();
     return;
 }else if(!isValidLength(password, 5)){
     $("#failure-message").html("Your password must be at least 6 characters.");
     $("#failure").show();
     return;
 }else if(!isMatching(password, confirmPassword)){
     $("#failure-message").html("The passwords you entered do not match.");
     $("#failure").show();
     return;
 }


  
  var postFields = {first_name: firstName,
                    last_name: lastName,
                    email: email,
                    confirm_email: confirmEmail,
                    password: password,
                    password_confirm: confirmPassword,
                    position: position,
                    reason: reason};


   $.ajax({   
            type: "POST",
            data: postFields,
            cache: false,  
            url: "api/pending-users",   
            success: function(data){
                
                if(data.result === "success"){
                    $("#success-message").html("Your request was submitted successfully.");
                    $("#failure").hide();
                    $("#success").show();
                    
                    //clear the input fields since submission is complete
                    clearFields();
                }else{
                    $("#failure-message").html("There was a problem submitting your request. Please Try again.");
                    $("#failure").show();
                }
            },
           error: function(){
        	   $("#failure").hide();
        	   $("#success").hide();
        	   $("#failure-message").html("There was a problem submitting your request. Please Try again.");
               $("#failure").show();
           }
            
        });   
        
    return false;
});