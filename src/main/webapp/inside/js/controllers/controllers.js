'use strict';
//this is the user id of the current logged in user
var _uToken = $.cookie("utoken");
var _uAdmin = $.cookie("uadmin");
var _uFname = $.cookie("ufname");


/* Controllers */

var controllers = angular.module('controllers', []);

controllers.controller('AdminCtrl', ['$scope',
  function ($scope) {
    $scope.isAdmin = _uAdmin;
  }]);


controllers.controller('NavbarCtrl', ['$scope', '$state',
  function ($scope, $state){
    
      if ( _uAdmin == "false") {
            $("#switchView").hide();
      }
      
      $("#ufname").html(_uFname);
      
    $scope.userIsAdmin = _uAdmin;
    

    $scope.isAdminState = function() {
      return $state.includes("admin");
    }
  }]);

controllers.controller('UserCtrl', ['$scope', '$state', '$location',
  function ($scope, $state, $location) {
    $scope.queryLoad = function(){
      $state.go("user.query");
      $location.url('/query');
      setTimeout(function(){
        location.reload();
      },50);
      
    }
    $scope.compareLoad = function(){
      $state.go("user.compare");
      $location.url('/compare');
      setTimeout(function(){
        location.reload();
      },50);
    }
    $scope.profileLoad = function(){
      $state.go("user.profile");
      $location.url('/profile');
      setTimeout(function(){
        location.reload();
      },50);
    }
  }]);