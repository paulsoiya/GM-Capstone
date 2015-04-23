'use strict';

angular.module('controllers').controller('ManageUsersCtrl', ['$scope', '$http', 
                                                             function ($scope, $http){

      
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