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
    

    $scope.isAdminState = function(){
      return $state.includes("admin");
    }
  }]);