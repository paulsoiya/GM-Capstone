function populateExisting() {
  $.get('../../api/analytics').success(function(data) {
        var container = document.getElementById('removeForm');
        // container.innerHTML=;
        var htmlString = "<select id=\"removeSelector\" name=\"removeSelector\" multiple class=\"form-control\">"
        
        console.log(data);
        if(data !== undefined) {
        	// var setting = data[0].analyticSetting;
        	for(var i = 0; i < data.length; i++) {
	        	console.log(data[i].explicitWords + "");
	        	htmlString += "<option id=\"" + data[i].id + "\">" + data[i].explicitWords + "</option>";
	        }
        }
        
        htmlString += "</select>";
        htmlString += "<div class=\"form-group formPadding\"><button type=\"submit\"  id=\"submitRemoveExplicit\" class=\"btn btn-primary\">Remove Word</button></div>";
        // htmlString += "</form>";
        container.innerHTML = htmlString;
    });
}