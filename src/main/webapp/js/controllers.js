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

controllers.controller('QueryCtrl',['$scope', function($scope){
  
}]);

controllers.controller('CompareCtrl',['$scope', function($scope){
  
}]);