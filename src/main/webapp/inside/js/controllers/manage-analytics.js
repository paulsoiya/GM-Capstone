'use strict';

angular.module('controllers').controller('ManageAnalyticsCtrl', ['$scope', '$http', 
                                                                 function($scope, $http) {

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