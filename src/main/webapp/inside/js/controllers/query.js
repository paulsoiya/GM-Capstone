'use strict';

angular.module('controllers').controller('QueryCtrl',['$scope', '$http', '$filter', 
                                                      function($scope, $http, $filter){
      
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
  
  $scope.changeMake = function(){
        console.log($scope.selectMake.makeName);
        for(var i = 0; $scope.models > i; ++i ){
    		if($scope.selectMake.makeId === $scope.models[i].makeId){
    			$scope.selectModel = $scope.models[i];
    			i = $scope.models;
    		}
    	}
        for(var i = 0; $scope.years > i; ++i ){
    		if($scope.selectModel.modelId === $scope.years[i].modelId){
    			$scope.selectYear = $scope.years[i];
    			i = $scope.years;
    		}
        }

        if($scope.selectMake.makeName === "All Makes"){
            document.getElementById("selectModel").disabled = true;
            document.getElementById("selectYear").disabled = true;
        }
        else{
            document.getElementById("selectModel").disabled = false;
        }
    }
    $scope.changeModel = function(){
    	for(var i = 0; $scope.years > i; ++i ){
    		if($scope.selectModel.modelId === $scope.years[i].modelId){
    			$scope.selectYear = $scope.years[i];
    			i = $scope.years;
    		}
        }
        if($scope.selectModel.modelName === "All Models"){
            document.getElementById("selectYear").disabled = true;
        }
        else{
            document.getElementById("selectYear").disabled = false;
            console.log("aaaaaaaaaaa");
        }
    }

      counter += 1;
      //console.log(counter);

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

  // Make, model, year
  // makes

  	$http.get('../api/makes').success(function(data) {
		$scope.makes = data;
/*		var allMakes = {makeId: -1, makeName: "All Makes"};
		$scope.makes.unshift(allMakes);
		$scope.selectMake = $scope.makes[0];
		console.log($scope.makes);*/
  	});
  
	$http.get('../api/models').success(function(data) {
		$scope.models = data;
/*		for(var i = 0; $scope.makes.length > i; ++i){
			$scope.models.unshift({modelName: "All Years", modelId: -1, makeId: $scope.makes[i]});
			console.log($scope.models[i].modelId);
		}
		var allModels = {modelId: -1, modelName: "All Models", makeId: -1};
		$scope.models.unshift(allModels);
		$scope.selectModel = $scope.models[0];
		console.log($scope.models);*/
	});

	$http.get('../api/model-years').success(function(data) {
		$scope.years = data;
/*		//var allYears = {yearName: "All Years", modelId: $scope.models.modelId};
		for(var i = 0; $scope.models.length > i; ++i){
			$scope.years.unshift({yearName: "All Years", modelId: $scope.models[i].modelId});
			console.log($scope.models[i].modelId);
		}
		$scope.selectYears = $scope.years[0];
		console.log("$scope.years");
		console.log($scope.years);*/
	});


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


