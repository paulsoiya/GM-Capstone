'use strict';

/* Controllers */

var controllers = angular.module('controllers', []);



//this is the user id of the current logged in user
var _uToken = $.cookie("utoken");




// Test Data
var testPieData = [
    {
        value: 300,
        color:"#000080",
        highlight: "#00004c",
        label: "Positive"
    },
    {
        value: 50,
        color: "#7f7fff",
        highlight: "#4c4cff",
        label: "Negative"
    }
  ]
var testBarData = {
    labels: ["Chevy", "Chevrolet", "Cadillac", "Caddy"],
    datasets: [
        {
            label: "My First dataset",
            fillColor: "rgba(220,220,220,0.5)",
            strokeColor: "rgba(220,220,220,0.8)",
            highlightFill: "rgba(220,220,220,0.75)",
            highlightStroke: "rgba(220,220,220,1)",
            data: [10, 8, 5, 3]
        }
    ]
  };
var testWords = [['Chevy', 30], ['Cadillac', 6], ['nice', 1], ['good', 9], ['car', 5], ['new', 4],
                   ['love', 11], ['like', 10], ['awesome', 2], ['cool', 10], ['Caddy', 10], ['Chevrolet', 3],
                   ['wow', 13], ['fantastic', 9], ['yowza', 3], ['the', 7], ['true', 7], ['stuff', 1],
                   ['can', 14], ['say', 8], ['shiny', 4], ['red', 5], ['truck', 8], ['man', 2],
                  ];

// Default data





controllers.controller('AdminCtrl', ['$scope',
  function ($scope) {
    $scope.isAdmin = true;
  }]);

controllers.controller('ManageAnalyticsCtrl', ['$scope', '$http', function($scope, $http) {
  $scope.explicitWords = [];
  $scope.newWord = '';
  $scope.removeSelected = '';
  $scope.add = function() {
    if ($scope.newWord) {
      $scope.explicitWords.push(this.newWord);
      $scope.newWord = '';
    }
  };
  $scope.delete = function() {
    var index = $scope.explicitWords.indexOf($scope.removeSelected);
    $scope.explicitWords.splice(index, 1);
  };
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

controllers.controller('ProfileCtrl',['$scope', function($scope){

}]);




controllers.controller('QueryCtrl',['$scope', '$http', '$filter', function($scope, $http, $filter){


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
  $http.get('http://localhost:7001/GMProject/api/makes').success(function(data) {
      $scope.makes = data;
	  console.log(data);
  });
  // models
  $http.get('http://localhost:7001/GMProject/api/models').success(function(data) {
	$scope.models = data;
	console.log(data);
  });
  // years
  $http.get('http://localhost:7001/GMProject/api/model-years').success(function(data) {
      $scope.years = data;
	  console.log(data);
  });


  //POST, query response
  $scope.queryPost = function() {
    $http.post('http://localhost:7001/GMProject/api/query',
	    {
			msg: "location="+$scope.selectLocation+"&"+
			"endDate="+$scope.endDate+"&"+
			"startDate="+$scope.startDate+"&"+
			"make="+$scope.selectMake.makeName+"&"+
			"model="+$scope.selectModel.modelName+"&"+
			"year="+$scope.selectYear.yearName
		},
		{headers: {'Content-Type': 'application/x-www-form-urlencoded'} }
	    ).success(function(data){
			console.log(JSON.parse(data.result));
			var responseJSON = JSON.parse(data.result);
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

			console.log("wordCountData");
			console.log(wordCountData);

			if(wordCountData.length > 30){
				var tempData = [];
				for(var i = 0; i < 30; ++i){
					tempData[i] = wordCountData[i];
				}
				wordCountData = tempData;
			}

			//Word Cloud
			WordCloud(document.getElementById('wordCloud_canvas'),
				{
					list: wordCountData,
					color: 'random-dark',
					shape: 'square',
					rotateRatio: 0.0,
					weightFactor: 2
				}
			);

			console.log(sentiment.rows[0].value[0]);
			console.log(Math.abs(sentiment.rows[0].value[0] + 1));
			console.log(Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100));

			console.log(Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100) / 100);
			console.log(Math.round(Math.abs(sentiment.rows[0].value[0] - 1) * 100) / 100);
			var pieData = [
				{
					value: Math.round(Math.abs(sentiment.rows[0].value[0] + 1) * 100) / 100,
					color:"#000080",
					highlight: "#00004c",
					label: "Positive"
				},
				{
					value: Math.round(Math.abs(sentiment.rows[0].value[0] - 1) * 100) / 100,
					color: "#7f7fff",
					highlight: "#4c4cff",
					label: "Negative"
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
			barChart.datasets[0].data[3] = wordCountData[3][1];*/
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
			var pieCtx = document.getElementById("pieGraph_canvas").getContext("2d");
			pieCtx.clearRect(0, 0, document.getElementById("pieGraph_canvas").width, document.getElementById("pieGraph_canvas").height);
			var pieChart = new Chart(pieCtx).Pie(pieData);

			// Bar Graph
			var barCtx = document.getElementById("barGraph_canvas").getContext("2d");
			barCtx.clearRect(0, 0, document.getElementById("pieGraph_canvas").width, document.getElementById("pieGraph_canvas").height);
			var barChart = new Chart(barCtx).Bar(barData);

			//barChart.update();
			//pieChart.update();



		});

  }

  			$scope.heatmap = function(){


  			    var myLatlng = new google.maps.LatLng(25.6586, -80.3568);
  			    // map options,
  			    var myOptions = {
  			        zoom: 3,
  			        center: myLatlng
  			    }
  			    var map = new google.maps.Map(document.getElementById("Map"), myOptions);




			}

$scope.heatmap()

}]);

controllers.controller('CompareCtrl',['$scope', function($scope){

  // First Pie Graph
  //var pieCtx = document.getElementById("first_pie_canvas").getContext("2d");
  //var pieChart = new Chart(pieCtx).Pie(testPieData);

  // First Bar Graph
  //var barCtx = document.getElementById("first_bar_canvas").getContext("2d");
  //var barChart = new Chart(barCtx).Bar(testBarData);

  // First Word Cloud
/*
  WordCloud(document.getElementById('first_cloud_canvas'),
          {
              list: testWords,
              color: 'random-dark',
              shape: 'square',
              rotateRatio: 0.0,
              weightFactor: 2
          }
  );
  */
  // Second Pie Graph
  //var pieCtx = document.getElementById("second_pie_canvas").getContext("2d");
  //var pieChart = new Chart(pieCtx).Pie(testPieData);

  // Second Bar Graph
  //var barCtx = document.getElementById("second_bar_canvas").getContext("2d");
  //var barChart = new Chart(barCtx).Bar(testBarData);

  // Second Word Cloud
/*  WordCloud(document.getElementById('second_cloud_canvas'),
          {
              list: testWords,
              color: 'random-dark',
              shape: 'square',
              rotateRatio: 0.0,
              weightFactor: 2
          }
  );

  */
}]);
