
$( "#explicitForm" ).submit(function( event ) {
  event.preventDefault();
  
  var explicitWords = $("#explicitWordsArea").val();
  console.log(explicitWords + " +++");
 //validate form
 if(!isValidLength(explicitWords, 3) ){
     $("#failure-message").html("You cannot update the dictionary with any words shorter than 3 letters");
     $("#failure").show();
    return;
 }

  var postFields = {explicit_words: explicitWords};


   $.ajax({   
            type: "POST",
            data: postFields,
            cache: false,  
            url: "../../api/analytics",   
            success: function(data){
                
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

function isValidLength(val, len){
    return (val.length > len ? true : false);
}