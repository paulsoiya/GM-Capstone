


function isValidEmail(email){
  
    var atpos = email.indexOf("@");
    var dotpos = email.lastIndexOf(".");
    if (atpos< 1 || dotpos<atpos+2 || dotpos+2>=x.length) {
        return false;
    }else{
        return true;
    }
}

/**
 * Validates that a string is greater then a certain amount
 * @param  val  The string to be checked
 * @param len the length value should be greater than
 * @returns {Boolean}
 */
function isValidLength(val, len){
    return (val.length > len ? true : false);
}



function isMatching(val1, val2){
    return (val1 == val2 ? true : false)
}