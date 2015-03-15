'use strict';

/* Controllers */

var controllers = angular.module('controllers', []);

/**
controllers.controller('PhoneListCtrl', ['$scope', 'Phone',
  function ($scope, Phone) {
    
    $scope.phones = Phone.query();
    $scope.orderProp = 'age';
    $scope.isAdmin = true;
  }]);

controllers.controller('PhoneDetailCtrl', ['$scope', '$routeParams', 'Phone',
  function ($scope, $routeParams, Phone) {
    $scope.phone = Phone.get({phoneId: $routeParams.phoneId}, function(phone) {
      $scope.mainImageUrl = phone.images[0];
    });

    $scope.setImage = function(imageUrl) {
      $scope.mainImageUrl = imageUrl;
    }
  }]);
**/

controllers.controller('AdminCtrl', ['$scope',
  function ($scope) {
    $scope.isAdmin = true;
  }]);

controllers.controller('ManageUsersCtrl', ['$scope', '$http', function ($scope, $http){

  $http.get('http://localhost:7001/GMProject/api/pending-users').success(function(data) {
        $scope.pusers = data.pendingUser;
    });
    
    //fill the users table
    $http.get('http://localhost:7001/GMProject/api/users').success(function(data) {
        
        //change boolean value for admin to 
        //textual representation of user role
        for(var i = 0; i < data.user.length; i++){
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
  
}]);

controllers.controller('QueryCtrl',['$scope', '$filter', function($scope, $filter){
  
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
  
   $scope.open = function($event, dateType) {
    $event.preventDefault();
    $event.stopPropagation();
    if(dateType === "start"){
      $scope.startOpened = true;
    }else if(dateType === "end"){
      $scope.endOpened = true;
    }
  };
  
  var testWords = [['foo', 30], ['bar', 6], ['cat', 1], ['rat', 9], ['poo', 5], ['pop', 4],
                   ['car', 11], ['app', 10], ['ban', 2], ['tan', 10], ['fuck', 10], ['slit', 3],
                   ['tap', 13], ['him', 9], ['can', 3], ['que', 7], ['duck', 7], ['crap', 1],
                   ['top', 14], ['she', 8], ['man', 4], ['pee', 5], ['shit', 8], ['slap', 2],
                  ];
  WordCloud(document.getElementById('wordCloud_canvas'), 
            { list: testWords, 
             color: 'random-dark',
             shape: 'square',
             rotateRatio: 0.0,
             weightFactor: 2
            });
  
  var testPieData = [
    {
        value: 300,
        color:"#F7464A",
        highlight: "#FF5A5E",
        label: "Red"
    },
    {
        value: 50,
        color: "#46BFBD",
        highlight: "#5AD3D1",
        label: "Green"
    },
    {
        value: 100,
        color: "#FDB45C",
        highlight: "#FFC870",
        label: "Yellow"
    }
  ]
  
  var testBarData = {
    labels: ["January", "February", "March", "April", "May", "June", "July"],
    datasets: [
        {
            label: "My First dataset",
            fillColor: "rgba(220,220,220,0.5)",
            strokeColor: "rgba(220,220,220,0.8)",
            highlightFill: "rgba(220,220,220,0.75)",
            highlightStroke: "rgba(220,220,220,1)",
            data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
            label: "My Second dataset",
            fillColor: "rgba(151,187,205,0.5)",
            strokeColor: "rgba(151,187,205,0.8)",
            highlightFill: "rgba(151,187,205,0.75)",
            highlightStroke: "rgba(151,187,205,1)",
            data: [28, 48, 40, 19, 86, 27, 90]
        }
    ]
  };
  
  var pieCtx = document.getElementById("pieGraph_canvas").getContext("2d");
  var pieChart = new Chart(pieCtx).Pie(testPieData);
  
  var barCtx = document.getElementById("barGraph_canvas").getContext("2d");
  var barChart = new Chart(barCtx).Bar(testBarData);
  
  // TODO: Would like to add a day to today because of rounding but it messes up format
  $scope.today = $filter('date')(new Date(), 'yyyy-MM-dd');
  $scope.endDate = $scope.today;
  $scope.aMonthAgo = $filter('date')(new Date() - 2592000000, 'yyyy-MM-dd');
  $scope.startDate = $scope.aMonthAgo;

}]);

controllers.controller('CompareCtrl',['$scope', function($scope){
  
}]);
