/**
 * Logs the user out and redirects to the home screen
 */
function logout() { 
	
	$.removeCookie('utoken', { path: '/' });
	$.removeCookie('uadmin', { path: '/' });
	$.removeCookie('ufname', { path: '/' });
	//redirect user to the home page
	window.open("../index.html", "_self");
}