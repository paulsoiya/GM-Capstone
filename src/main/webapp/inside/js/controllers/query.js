'use strict';

var notLoaded = true;




var makes;
var models;
var years;

var changeMake = function(){
  $("#selectModel").empty();
  $("#selectYear").empty();

  $("#selectYear").attr('disabled', 'disabled');
  $("#selectYear").append($('<option>', {value: -1, text: "All Years"}));
  $("#selectYear").val(-1);

  $("#selectModel").append($('<option>', {value: -1, text: "All Models"}));
  if($("#selectMake").val() === -1){
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

var changeModel = function(){
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

angular.module('controllers').controller('QueryCtrl',['$scope', '$http', '$filter', 
                                                      function($scope, $http, $filter){
  // Loading stuff
  var throbber = document.getElementById('throbber');
  var wordCloud = document.getElementById('wordCloud_canvas');
  var wCtx=wordCloud.getContext("2d");
  wCtx.font="20px Calibri";
  wCtx.fillText("Loading...",10,50);
  var pieChart = document.getElementById('pieGraph_canvas');
  var pCtx=pieChart.getContext("2d");
  pCtx.font="20px Calibri";
  pCtx.fillText("Loading...",10,50);
  var barGraph = document.getElementById('barGraph_canvas');
  var bCtx=barGraph.getContext("2d");
  bCtx.font="20px Calibri";
  bCtx.fillText("Loading...",10,50);
  
  // Get data from SQL and set up stuff
  $scope.getMakes = function() {
    $http.get('../api/makes').success(function(data) {
        makes = data;
        var allMakes = {makeId: -1, makeName: "All Makes"};
        makes.unshift(allMakes);

        for(var i = 0; i < makes.length; ++i){
          $("#selectMake").append($('<option>', {
            value: makes[i].makeId,
            text: makes[i].makeName
          }));
        }

        $scope.getModels();
    });
  }
  $scope.getModels = function() {
    $http.get('../api/models').success(function(data) {
      models = data;
      var allModels = {makeId: -1, modelId: -1, modelName: "All Models"};
      models.unshift(allModels); 

      $("#selectModel").append($('<option>', {value: -1, text: "All Models"}));
      $("#selectModel").val(-1);
      $scope.getYears();
    });
  }
  $scope.getYears = function() {
    $http.get('../api/model-years').success(function(data) {
      years = data;
      var allYears = {modelId: -1, yearId: -1, yearName: "All Years"};
      years.unshift(allYears); 

      $("#selectYear").append($('<option>', {value: -1, text: "All Years"}));
      $("#selectYear").val(-1);
    });   
  }
  
  // Check to make sure setting up everything only happens once
  if (notLoaded) {
    $scope.getMakes();
    $scope.selectMake = -1;
    $scope.selectModel = -1;
    $scope.selectYear = -1;
    notLoaded = false;
  }                  
  
  // On change should reset other fields
  $scope.makesChange = function(){
      $scope.selectModel = -1;
      $scope.selectYear = -1;
      if ($scope.selectMake < 0) {
        document.getElementById("selectModel").disabled = true;
        document.getElementById("selectYear").disabled = true;
      } else {
        document.getElementById("selectModel").disabled = false;
      }
  }
  $scope.modelsChange = function(){
      $scope.selectYear = -1;
      if ($scope.selectModel < 0) {
        document.getElementById("selectYear").disabled = true;
      } else {
        document.getElementById("selectYear").disabled = false;
      }
  }
  
  // Datepickers
  $scope.open = function($event, dateType) {
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


  //POST, query response
  $scope.queryPost = function() {
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
    if($scope.selectMake === "undefined"){
      $scope.selectMake.makeName === "undefined";
    }
    if($scope.selectMake === "undefined"){
      $scope.selectMake.makeName === "undefined";
      }
      $http({
            method: 'post',
            url: '../api/query',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: "endDate="+$scope.endDate+"&"+
            "startDate="+$scope.startDate+"&"+
            "make="+$scope.selectMake.makeName+"&"+
            "model="+$scope.selectModel.modelName+"&"+
            "year="+$scope.selectYear.yearName
      }).then(function(response) {
      
            savedMake = $scope.selectMake.makeName+"";
            savedModel = $scope.selectModel.modelName+"";
            savedYear = $scope.selectYear.yearName+"";
            savedStartDate = $scope.startDate+"";
            savedEndDate = $scope.endDate+"";
            
            var wordCloud = document.getElementById('wordCloud_canvas');
            var pieChart = document.getElementById('pieGraph_canvas');
            var barGraph = document.getElementById('barGraph_canvas');
            drawQuery(response, wordCloud, pieChart, barGraph);
                  
    });
  };
  


  $scope.saveQuery = function() {

    $http({
            method: 'post',
            url: '../api/savedsearches',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: "endDate="+savedEndDate+"&"+
            "startDate="+savedStartDate+"&"+
            "make="+savedMake+"&"+
            "model="+savedModel+"&"+
            "user="+_uToken+"&"+
            "searchName="+$scope.searchName+"&"+
            "year="+savedYear
    }).then(function(response) {
      $("#savedMessage").removeClass('hidden');
      $scope.searchName = "";
      setTimeout(function(){
            $("#savedMessage").addClass('hidden');
      }, 2500)
            console.log("response");
            console.log(response);
      });
  };


      if(counter === 1){
            $http({
                  method: 'post',
                  url: '../api/query',
                  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                  data: "endDate="+$scope.endDate+"&"+
                  "startDate="+$scope.startDate+"&"+
                  "make="+"undefined"+"&"+
                  "model="+"undefined"+"&"+
                  "year="+"undefined"
            }).then(function(response) {
                  var wordCloud = document.getElementById('wordCloud_canvas');
                  var pieChart = document.getElementById('pieGraph_canvas');
                  var barGraph = document.getElementById('barGraph_canvas');
                  drawQuery(response, wordCloud, pieChart, barGraph);   
            $scope.grade = grade;
            });
      }


}]);


