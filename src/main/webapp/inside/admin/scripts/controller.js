function populateExisting() {
  $.get('../../api/analytics').success(function(data) {
        var container = document.getElementById('removeForm');
        // container.innerHTML=;
        var htmlString = "<select id=\"removeSelector\" name=\"removeSelector\" multiple class=\"form-control\">"
        
        if(data != null) {
        	var setting = data.analyticSetting;
        	for(var i = 0; i < setting.length; i++) {
	        	console.log(setting[i].explicitWords + "");
	        	htmlString += "<option id=\"" + setting[i].id + "\">" + setting[i].explicitWords + "</option>";
	        }
        }
        
        htmlString += "</select>";
        htmlString += "<div class=\"form-group formPadding\"><button type=\"submit\"  id=\"submitRemoveExplicit\" class=\"btn btn-primary\">Remove Word</button></div>";
        // htmlString += "</form>";
        container.innerHTML = htmlString;
    });
}