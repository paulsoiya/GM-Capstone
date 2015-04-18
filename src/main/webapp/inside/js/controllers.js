'use strict';

/* Controllers */

var controllers = angular.module('controllers', []);


//this is the user id of the current logged in user
var _uToken = $.cookie("utoken");
console.log(_uToken);


controllers.controller('AdminCtrl', ['$scope',
  function ($scope) {
    $scope.isAdmin = true;
  }]);

controllers.controller('ManageAnalyticsCtrl', ['$scope', '$http', function($scope, $http) {
  
  $scope.getExplicit = function() {
    $http.get('../api/explicit-content').success(function(data) {
      $scope.explicitContents = data;
    });
  }
  $scope.getCommon = function() {
    $http.get('../api/common-content').success(function(data) {
      $scope.commonContents = data;
    });
  }
  
  $scope.getExplicit();
  $scope.getCommon();
  
  $scope.newExplicit = '';
  $scope.removeExplicit = '';
  $scope.addExplicit = function(){
	  var postFields = {explicitWords: $scope.newExplicit};
	  $http({
		    method: 'POST',
		    url: '../api/explicit-content',
		    data: $.param(postFields),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	  }).success(function(data, status, headers, config) {
			  console.log(data);
			  $scope.getExplicit();
              $scope.newExplicit = '';
	  }).error(function(data, status, config) {
			console.log("Something went wrong");
            $scope.newExplicit = '';
	  });
  }
  $scope.deleteExplicit = function(){
	  $http.delete('../api/explicit-content/' + $scope.removeExplicit).success(function (data, status) {
          $scope.getExplicit();
          $scope.removeExplicit = '';
      });
  }
  $scope.newCommon = '';
  $scope.removeCommon = '';
  $scope.addCommon = function(){
	  var postFields = {commonWords: $scope.newCommon};
	  $http({
		    method: 'POST',
		    url: '../api/common-content',
		    data: $.param(postFields),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	  }).success(function(data, status, headers, config) {
			  $scope.getCommon();
              $scope.newCommon = '';
	  }).error(function(data, status, config) {
			console.log("Something went wrong");
            $scope.newCommon = '';
	  });
  }
  $scope.deleteCommon = function(){
	  $http.delete('../api/common-content/' + $scope.removeCommon).success(function (data, status) {
          $scope.getCommon();
          $scope.removeCommon = '';
      });
  }
}]);

controllers.controller('ManageUsersCtrl', ['$scope', '$http', function ($scope, $http){


  $scope.getPendingUsers = function () {
	  $http.get('../api/pending-users').success(function(data) {
		  $scope.pusers = data;
	  });
  }
    
  $scope.getUsers = function () {
	  $http.get('../api/users').success(function(data) {
		  //change boolean value for admin to 
		  //textual representation of user role
		  for(var i = 0; i < data.length; i++){
			  if(data[i].admin)
				  data[i].admin = "Admin";
			  else
				  data[i].admin = "User";
		  }
		  $scope.users = data;
	  });
  }
  
  $scope.getPendingUsers();
  $scope.getUsers();
  
  $scope.changeRole = function(id, admin){
	  var uid = $scope.users[id].id;
	  var payload;
	  
	  if ( admin ) { 
		  payload = {role: true};
	  } else {
		  payload = {role: false};
	  }
	  
	  
	  $http({
		    method: 'PUT',
		    url: "../api/users/"+ uid + "/role",
		    data: $.param(payload),
		    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	  }).success(function(data, status, headers, config) {
		  console.log(data);
          $scope.getUsers();
	  });
	 
  }
  
  /**
   * Deletes a registered user
   */
  $scope.removeAccess = function(id){
	  var uid = $scope.users[id].id;
	  $http.delete('../api/users/' + uid).success(function (data, status) {
          console.log(data);
          $scope.getUsers();
      });
  }
  
  /**
   * Converts a pending user into a user
   */
  $scope.grantAccess = function(id){
	
	  var postFields = {email: $scope.pusers[id].email, 
			  			password: $scope.pusers[id].password, 
			  			first_name: $scope.pusers[id].firstName, 
			  			last_name: $scope.pusers[id].lastName,
			  			puser_id: $scope.pusers[id].id
			  			};
	  $http({
		    method: 'POST',
		    url: '../api/users',
		    data: $.param(postFields),
		    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	  }).success(function(data, status, headers, config) {
			  console.log(data);
			  $scope.getPendingUsers();
			  $scope.getUsers();
	  }).error(function(data, status, headers, config) {
			console.log("Something went wrong");
	  });
  }
  
  /**
   * Deletes a pending user from the database
   */
  $scope.deleteRequest = function(id){
	  var uid = $scope.pusers[id].id;
	  $http.delete('../api/pending-users/' + uid).success(function (data, status) {
          console.log(data);
          console.log("DELETED");
          $scope.getPendingUsers();
      });
  }


}]);




controllers.controller('NavbarCtrl', ['$scope', '$state',
  function ($scope, $state){
    
    $scope.userIsAdmin = true;
    
    $scope.isAdminState = function(){
      return $state.includes("admin"); 
    }
  }]);

controllers.controller('ProfileCtrl',['$scope','$http', function($scope, $http){
	
	$scope.getUserDetail = function() {
		
		 $http.get('../api/users/' + _uToken).success(function(data) {
			  $scope.userData = data;
		  });
	}
	
	$scope.getUserDetail();
	
	
	$scope.save = function()
	  {
		$("#success").hide();
		$("#failure").hide();
		
	   var postFields = {	first_name:$("#FirstName").val(),
				  			last_name:$("#LastName").val(),
				  			email:$("#inputEmail").val(),
				  			password:$("#inputPassword").val()
				  		};

	   if ($("#inputPassword").val() != $("#inputPasswordConfirm").val()) {
		   $("#failure-message").html("The passwords you entered do not match");
		   $("#failure").show();
		   return;
	   }
	   
	   
		  $http({
			    method: 'PUT',
			    url: 'http://localhost:7001/GMProject/api/users/' + _uToken,
			    data: $.param(postFields),
			    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		  }).success(function(data, status, headers, config) {
		      console.log(data);
		      $("#success").hide();
		      $("#failure").hide();
		      
				  if(data.result === "success"){
				      $("#success-message").html("Your changes have been saved");
				      $("#success").show();
				  }
				  else{
				      $("#failure-message").html("There was a problem saving your changes");
				      $("#failure").show();
				  }
				  
		  }).error(function(data, status, headers, config) {
		      $("#success-message").hide();
		      $("#failure-message").hide();

		      $("#failure-message").html("There was a problem saving your changes");
		      $("#failure").show();
				
		  });
	  }
}]);



var counter = 0;
var savedMake = "aa";
var savedModel = "aa";
var savedYear = "aa";
var savedLocation = "aa";
var savedStartDate = "aa";
var savedEndDate = "aa";

controllers.controller('QueryCtrl',['$scope', '$http', '$filter', function($scope, $http, $filter){

	counter += 1;
	//console.log(counter);

  
  // Location dropdown
  $scope.locations = ['All Locations', 'Alabama', 'Alaska', 'Arizona', 'Arkansas', 
                      'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 
                      'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 
                      'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 
                      'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 
                      'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 
                      'New Jersey', 'New Mexico', 'New York', 'North Carolina', 
                      'North Dakota', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 
                      'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 
                      'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 
                      'West Virginia', 'Wisconsin', 'Wyoming'];
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
		var allMakes = {makeId: -1, makeName: "All Makes"};
		$scope.makes.unshift(allMakes);
		if(counter == 1)console.log($scope.makes);
  	});
  
	$http.get('../api/models').success(function(data) {
		$scope.models = data;
		var allModels = {modelId: -1, modelName: "All Models", makeId: -1};
		$scope.models.unshift(allModels);
		if(counter == 1)console.log($scope.models);
	});

	$http.get('../api/model-years').success(function(data) {
		$scope.years = data;
		var allYears = {yearName: "All Years", modelId: -1};
		$scope.years.unshift(allYears);
		if(counter == 1)console.log($scope.years);
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
		data: "location="+$scope.selectLocation+"&"+
		"endDate="+$scope.endDate+"&"+
		"startDate="+$scope.startDate+"&"+
		"make="+$scope.selectMake.makeName+"&"+
		"model="+$scope.selectModel.modelName+"&"+
		"year="+$scope.selectYear.yearName
	}).then(function(response) {
	
		savedMake = $scope.selectMake.makeName+"";
		savedModel = $scope.selectModel.modelName+"";
		savedYear = $scope.selectYear.yearName+"";
		savedLocation = $scope.selectLocation+"";
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
		data: "location="+savedLocation+"&"+
		"endDate="+savedEndDate+"&"+
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

}]);



controllers.controller('CompareCtrl',['$scope', '$http', '$filter', function($scope, $http, $filter){


	$http({
		method: 'post',
		url: '../api/savedsearches/getSavedSearches',
		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		data: "user="+_uToken
	}).then(function(response) {
	  //$scope.searches = response.data;
        console.log(response);
        var data = response.data;
        var indexId = data.indexOf("_id");
        var indexRev = data.indexOf("_rev");
        if (indexId > -1) {
            data.splice(indexId, 1);
        }
        if (indexId > -1) {
            data.splice(indexRev, 1);
        }
        $scope.searches = data;
	  //console.log($scope.searches);
	});
	
	
	$scope.comparePost1 = function(){
		$(".comparesearch1").removeClass('hidden');

		$http({
			method: 'post',
			url: '../api/savedsearches/getSavedSearch',
			headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			data: "user="+_uToken+"&"+
			"searchName="+$scope.searchName1
		}).then(function(response) {
		  	//$scope.searches = response.data;
		  	console.log(response);

		  	var wordCloud = document.getElementById('first_cloud_canvas');
			var pieChart = document.getElementById('first_pie_canvas');
			var barGraph = document.getElementById('first_bar_canvas');
		  	drawQuery(response, wordCloud, pieChart, barGraph);
		});
	}

	$scope.comparePost2 = function(){
		$(".comparesearch2").removeClass('hidden');

		$http({
			method: 'post',
			url: '../api/savedsearches/getSavedSearch',
			headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			data: "user="+_uToken+"&"+
			"searchName="+$scope.searchName2
		}).then(function(response) {
		  	//$scope.searches = response.data;
		    console.log(response);

		    var wordCloud = document.getElementById('second_cloud_canvas');
			var pieChart = document.getElementById('second_pie_canvas');
			var barGraph = document.getElementById('second_bar_canvas');
			drawQuery(response, wordCloud, pieChart, barGraph);
		});
	}

}]);


function drawQuery(response, wordCloudCanvas, pieGraphCanvas, barGraphCanvas){
	var responseJSON = response.data;

	var wordCount = JSON.parse(responseJSON.wordCount);
	var sentiment = JSON.parse(responseJSON.sentiment);
	
	var wordCountData = [];
	
	for(var i = 0; i < wordCount.rows.length; ++i){
		if(wordCount.rows[i].key !== "_id" &&
		wordCount.rows[i].key !== "_rev" &&
		wordCount.rows[i].key !== "tweetsentiment" &&
		wordCount.rows[i].key !== "tweettext" &&
		wordCount.rows[i].key !== "tweettime"){
			wordCountData.push([wordCount.rows[i].key, wordCount.rows[i].value]);
		}
	}
	
	wordCountData.sort(function(current, next) {
		return ((current[1] > next[1]) ? -1 : ((current[1] === next[1]) ? 0 : 1));
	});
			
	if(wordCountData.length > 30){
		var tempData = [];
		for(var i = 0; i < 30; ++i){
			tempData[i] = wordCountData[i];
		}
		wordCountData = tempData;
	}
	
	//Word Cloud

	WordCloud(wordCloudCanvas, 
		{ 
			list: wordCountData, 
			color: 'random-dark',
			shape: 'square',
			rotateRatio: 0.0,
			weightFactor: 2
		}
	);
	
	//console.log(sentiment.rows[0].value[0]);
	//console.log(Math.abs(sentiment.rows[0].value[0] + 1));
	//console.log(Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100));
	
	//console.log(Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100) / 100);
	//console.log(Math.round(Math.abs(sentiment.rows[0].value[0] - 1) * 100) / 100);
	var pieData = [
		{
			value: (Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100) / 100) / 2 * 100,
			color:"#000080",
			highlight: "#00004c",
			label: "Positive %"
		},
		{
			value: (Math.round(Math.abs(sentiment.rows[0].value[0] - 1) * 100) / 100) / 2 * 100,
			color: "#7f7fff",
			highlight: "#4c4cff",
			label: "Negative %"
		}
	  ]

//TODO maybe?: chart isn't updating, it's just redrawing, might need to fix			  
/*
	pieChart[0].value = Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100) / 100;
	pieChart[1].value = Math.round(Math.abs(sentiment.rows[0].value[0] - 1) * 100) / 100;

	barData.labels[0] = wordCountData[0][0];
	barData.labels[1] = wordCountData[1][0];
	barData.labels[2] = wordCountData[2][0];
	barData.labels[3] = wordCountData[3][0];

	barChart.datasets[0].data[0] = wordCountData[0][1];
	barChart.datasets[0].data[1] = wordCountData[1][1];
	barChart.datasets[0].data[2] = wordCountData[2][1];
	barChart.datasets[0].data[3] = wordCountData[3][1];
*/
	var barData = {
		labels: [wordCountData[0][0], 
			wordCountData[1][0], 
			wordCountData[2][0], 
			wordCountData[3][0]
		],
		datasets: [
			{
				label: "My First dataset",
				fillColor: "rgba(220,220,220,0.5)",
				strokeColor: "rgba(220,220,220,0.8)",
				highlightFill: "rgba(220,220,220,0.75)",
				highlightStroke: "rgba(220,220,220,1)",
				data: [wordCountData[0][1], 
				wordCountData[1][1], 
				wordCountData[2][1], 
				wordCountData[3][1] 
				]
			}
		]
	  };

	// Pie Graph
	var pieCtx = pieGraphCanvas.getContext("2d");
	pieCtx.clearRect(0, 0, pieGraphCanvas.width, pieGraphCanvas.height);
	var pieChart = new Chart(pieCtx).Pie(pieData);

	// Bar Graph
	var barCtx = barGraphCanvas.getContext("2d");
	barCtx.clearRect(0, 0, barGraphCanvas.width, barGraphCanvas.height);
	var barChart = new Chart(barCtx).Bar(barData);
}
