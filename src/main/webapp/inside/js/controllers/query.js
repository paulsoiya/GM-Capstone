/* 
 * The controller for the query page. 
 *
 * Uses HTTP to GET makes, models, and years from MySQL,
 *           to POST a query to CouchDB and thus create a view,
 *           and to POST a saved query to CouchDB.
 *
 * NOTE: Uses jQuery rather than Angular to populate the HTML selects.
 *       Authors were unable to use Angular to filter selects correctly.
 */

'use strict';

var notLoaded = true;

var makes;
var models;
var years;

angular.module('controllers').controller('QueryCtrl', ['$scope', '$http', '$filter',
                                                      function ($scope, $http, $filter) {
  
  // GET data from MySQL and set up make selects using jQuery
  $scope.getMakes = function() {
    $http.get('../api/makes').success(function (data) {
      makes = data;
      var allMakes = {makeId: -1, makeName: "All Makes"};
      makes.unshift(allMakes);
      for (var i = 0; i < makes.length; ++i){
        $("#selectMake").append($('<option>', {
          value: makes[i].makeId,
          text: makes[i].makeName
        }));
      }
      $scope.getModels();
    });
  }
  // GET data from MySQL and set up model selects using jQuery
  $scope.getModels = function () {
    $http.get('../api/models').success(function(data) {
      models = data;
      var allModels = {makeId: -1, modelId: -1, modelName: "All Models"};
      models.unshift(allModels); 
      $("#selectModel").append($('<option>', {
        value: -1, 
        text: "All Models"
      }));
      $("#selectModel").val(-1);
      $scope.getYears();
    });
  }
  // GET data from MySQL and set up year selects using jQuery
  $scope.getYears = function() {
    $http.get('../api/model-years').success(function(data) {
      years = data;
      var allYears = {modelId: -1, yearId: -1, yearName: "All Years"};
      years.unshift(allYears); 
      $("#selectYear").append($('<option>', {
        value: -1, 
        text: "All Years"
      }));
      $("#selectYear").val(-1);
      $scope.queryPost();
    });   
  }
  
  
  // Datepickers
  $scope.open = function ($event, dateType) {
    $event.preventDefault();
    $event.stopPropagation();
    if (dateType === "start") {
      $scope.startOpened = true;
    } else if (dateType === "end") {
      $scope.endOpened = true;
    }
  };
  // TODO: Would like to add a day to today because of rounding but it messes up format
  $scope.today = $filter('date')(new Date(), 'yyyy-MM-dd');
  $scope.endDate = $scope.today;
  $scope.aMonthAgo = $filter('date')(new Date() - 2592000000, 'yyyy-MM-dd');
  $scope.startDate = $scope.aMonthAgo;


  // POST query to CouchDb, creating a view, and receiving the data in a response
  $scope.queryPost = function() {
    var pieChart = document.getElementById('pieGraph_canvas');
    var wordCloud = document.getElementById('wordCloud_canvas');
    var barGraph = document.getElementById('barGraph_canvas');
    
    var start = new Date();
    var pieThrob = drawThrobber(pieChart,start);
    var barThrob = drawThrobber(barGraph,start);
    var cloudThrob = drawThrobber(wordCloud,start);
    
    var payload = { endDate: $scope.endDate,
      startDate: $scope.startDate,
      make: $("#selectMake option:selected").text(),
      model: $("#selectModel option:selected").text(),
      year: $("#selectYear option:selected").text()
    }
    $http({
      method: 'post',
      url: '../api/query',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: $.param(payload)
    }).then(function(response) {
      clearInterval(pieThrob);
      clearInterval(barThrob);
      clearInterval(cloudThrob);
      $scope.grade = "blank.png";
      if (response.data.wordCount.length < 15) {
        $scope.errorMessage = "Sorry, data for that request is not available right now. Performing default query.";
        $("#selectMake").val(-1);
        $("#selectModel").val(-1);
        $("#selectYear").val(-1);
        $scope.queryPost();
      } else {
        $scope.grade = drawQuery(response, wordCloud, "pieCanvasArea", "pieGraph_canvas", "barCanvasArea", "barGraph_canvas"); 
        $scope.errorMessage = "Success!";
      }
    });
  };
  
  // POST saved query to CouchDB
  $scope.saveQuery = function () {
    var payload = { endDate: $scope.endDate,
      startDate: $scope.startDate,
      make: $("#selectMake option:selected").text(),
      model: $("#selectModel option:selected").text(),
      year: $("#selectYear option:selected").text(),
      user: _uToken,
      searchName: $scope.searchName
    }
    $http({
      method: 'post',
      url: '../api/savedsearches',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: $.param(payload)
    }).then(function(response) {
      $("#savedMessage").removeClass('hidden');
      $scope.searchName = "";
      setTimeout(function(){
        $("#savedMessage").addClass('hidden');
      }, 2500)
    });
  };

  if (notLoaded) {
    $scope.getMakes();
    notLoaded = false;
    $scope.errorMessage = "";
  }

}]);

// Use jQuery to properly filter models based on make selected
var changeMake = function (){
  $("#selectModel").empty();
  $("#selectYear").empty();

  $("#selectYear").attr('disabled', 'disabled');
  $("#selectYear").append($('<option>', {value: -1, text: "All Years"}));
  $("#selectYear").val(-1);

  $("#selectModel").append($('<option>', {value: -1, text: "All Models"}));
  if($("#selectMake").val() == -1){
    $("#selectModel").attr('disabled', 'disabled');
    $("#selectModel").val(-1);
  }
  else{
    for(var i = 0; i < models.length; ++i){
      if(models[i].makeId == $("#selectMake").val()){
        $("#selectModel").append($('<option>', {value: models[i].modelId, text: models[i].modelName}));
      }
    }
    $("#selectModel").removeAttr('disabled');
  }
}
// User jquery to properly filter years based on model selected
var changeModel = function (){
  $("#selectYear").empty();

  $("#selectYear").append($('<option>', {value: -1, text: "All Years"}));
  if($("#selectModel").val() == -1){
    $("#selectYear").attr('disabled', 'disabled');
    $("#selectYear").val(-1);
  }
  else{
    for(var i = 0; i < years.length; ++i){
      if(years[i].modelId == $("#selectModel").val()){
        $("#selectYear").append($('<option>', {value: years[i].yearId, text: years[i].yearName}));
      }
    }
    $("#selectYear").removeAttr('disabled');
  }
}