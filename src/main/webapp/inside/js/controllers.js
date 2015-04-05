'use strict';

/* Controllers */

var controllers = angular.module('controllers', []);

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
	  $http.get('http://localhost:7001/GMProject/api/pending-users').success(function(data) {
		  $scope.pusers = data;
	  });
  }

  $scope.getUsers = function () {
	  $http.get('http://localhost:7001/GMProject/api/users').success(function(data) {
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

  $scope.grantAdminAccess = function(id){
	  var uid = $scope.users[id].id;
	  $http.put('http://localhost:7001/GMProject/api/users/' + uid + '/makeadmin').success(function (data, status) {
          console.log(data);
          $scope.getUsers();
      });
  }

  /**
   * Deletes a registered user
   */
  $scope.removeAccess = function(id){
	  var uid = $scope.users[id].id;
	  $http.delete('http://localhost:7001/GMProject/api/users/' + uid).success(function (data, status) {
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
		    url: 'http://localhost:7001/GMProject/api/users',
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
	  $http.delete('http://localhost:7001/GMProject/api/pending-users/' + uid).success(function (data, status) {
          console.log(data);
          console.log("DELETED");
          $scope.getPendingUsers();
      });
  }

  $http.get('http://localhost:7001/GMProject/api/pending-users').success(function(data) {
        $scope.pusers = data;
  });

  //fill the users table
  $http.get('http://localhost:7001/GMProject/api/users').success(function(data) {

      //change boolean value for admin to
      //textual representation of user role
      for(var i = 0; i < data.length; i++){
          if(data.user[i].admin == "true")
              data.user[i].admin = "Admin";
          else
              data.user[i].admin = "User";
      }
      $scope.users = data.user;
  });


}]);

controllers.controller('NavbarCtrl', ['$scope', '$state',
  function ($scope, $state){

    $scope.userIsAdmin = true;

    $scope.isAdminState = function(){
      return $state.includes("admin");
    }
  }]);

controllers.controller('ProfileCtrl',['$scope', function($scope){

  $scope.save = function()
  {
   var postFields = {first_name:$("#FirstName").val(),
			  			last_name:$("#LastName").val(),
			  			email:$("#inputEmail").val(),
			  			password:$("#inputPassword").val()
			  			};

	  $http({
		    method: 'PUT',
		    url: 'http://localhost:7001/GMProject/api/users/' + _uToken,
		    data: $.param(postFields),
		    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	  }).success(function(data, status, headers, config) {
			  console.log(data);
			  $scope.getPendingUsers();
			  if(data.success == "success"){
			      $("success-message").html("");
			      $("success").show();
			  }
			  else{
                
			  }
	  }).error(function(data, status, headers, config) {
	      $("#success-message").hide();

			console.log("Something went wrong");
	  });
  }
//$("#id").val(),
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
      $scope.makes = data.makes;
  });
  // models
  $http.get('http://localhost:7001/GMProject/api/models').success(function(data) {
      $scope.models = data.models;
  });
  // years
  $http.get('http://localhost:7001/GMProject/api/model-years').success(function(data) {
      $scope.years = data.modelYears;
  });

  // Pie Graph
  var pieCtx = document.getElementById("pieGraph_canvas").getContext("2d");
  var pieChart = new Chart(pieCtx).Pie(testPieData);

  // Bar Graph
  var barCtx = document.getElementById("barGraph_canvas").getContext("2d");
  var barChart = new Chart(barCtx).Bar(testBarData);

  // Word Cloud
  WordCloud(document.getElementById('wordCloud_canvas'),
          {
              list: testWords,
              color: 'random-dark',
              shape: 'square',
              rotateRatio: 0.0,
              weightFactor: 2
          }
  );

}]);

controllers.controller('CompareCtrl',['$scope', function($scope){

  // First Pie Graph
  var pieCtx = document.getElementById("first_pie_canvas").getContext("2d");
  var pieChart = new Chart(pieCtx).Pie(testPieData);

  // First Bar Graph
  var barCtx = document.getElementById("first_bar_canvas").getContext("2d");
  var barChart = new Chart(barCtx).Bar(testBarData);

  // First Word Cloud
  WordCloud(document.getElementById('first_cloud_canvas'),
          {
              list: testWords,
              color: 'random-dark',
              shape: 'square',
              rotateRatio: 0.0,
              weightFactor: 2
          }
  );

  // Second Pie Graph
  var pieCtx = document.getElementById("second_pie_canvas").getContext("2d");
  var pieChart = new Chart(pieCtx).Pie(testPieData);

  // Second Bar Graph
  var barCtx = document.getElementById("second_bar_canvas").getContext("2d");
  var barChart = new Chart(barCtx).Bar(testBarData);

  // Second Word Cloud
  WordCloud(document.getElementById('second_cloud_canvas'),
          {
              list: testWords,
              color: 'random-dark',
              shape: 'square',
              rotateRatio: 0.0,
              weightFactor: 2
          }
  );


}]);
