'use strict';

angular.module('controllers').controller('ManageDataCtrl', ['$scope', '$http', 
                                                            function($scope, $http) {
  
    // WARNING: Angular's filter function works by searching for how something begins.
    // When you add enough makes / models, the models / years won't filter correctly.
    
    $scope.getMakes = function() {
    $http.get('../api/makes').success(function(data) {
      $scope.makes = data;
    });
    }
    $scope.getModels = function() {
      $http.get('../api/models').success(function(data) {
        $scope.models = data;
      });
    }
    $scope.getYears = function() {
      $http.get('../api/model-years').success(function(data) {
        $scope.years = data;
      });
    }
    $scope.getMakeAlternates = function() {
      $http.get('../api/make-alternates').success(function(data) {
        $scope.makeAlternates = data;
      });
    }
    $scope.getModelAlternates = function() {
      $http.get('../api/model-alternates').success(function(data) {
        $scope.modelAlternates = data;
      });
    }
  
    $scope.getMakes();
    $scope.getModels();
    $scope.getYears();
    $scope.getMakeAlternates();
    $scope.getModelAlternates();
  
    $scope.selectMake = 'None';
    $scope.newMake = '';
    $scope.selectModel = 'None';
    $scope.newModel = '';
    $scope.selectYear = 'None';
    $scope.newYear = '';
    $scope.selectMakeAlternate = 'None';
    $scope.newMakeAlternate = '';
    $scope.selectModelAlternate = 'None';
    $scope.newModelAlternate = '';
  
    $scope.deleteMake = function() {
      $http.delete('../api/makes/' + $scope.selectMake).success(function (data, status) {
          $scope.getMakes();
          $scope.selectMake = 'None';
          $scope.selectModel = 'None';
          $scope.selectYear = 'None';
          $scope.selectMakeAlternate = 'None';
          $scope.selectModelAlternate = 'None';
      });
    }
    
    $scope.addMake = function() {
      var postFields = {makeName: $scope.newMake};
        $http({
                method: 'POST',
                url: '../api/makes',
                data: $.param(postFields),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data, status, headers, config) {
                  console.log(data);
                  $scope.getMakes();
            $scope.newMake = '';
        }).error(function(data, status, config) {
                  console.log("Something went wrong");
            $scope.newMake = '';
        });
    }
    
    $scope.deleteModel = function() {
      $http.delete('../api/models/' + $scope.selectModel).success(function (data, status) {
          $scope.getModels();
          $scope.selectModel = 'None';
          $scope.selectYear = 'None';
          $scope.selectModelAlternate = 'None';
      });
    }
    
    $scope.addModel = function() {
      var postFields = {makeId: $scope.selectMake,
                        modelName: $scope.newModel};
        $http({
                method: 'POST',
                url: '../api/models',
                data: $.param(postFields),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data, status, headers, config) {
                  console.log(data);
                  $scope.getModels();
            $scope.newModel = '';
        }).error(function(data, status, config) {
                  console.log("Something went wrong");
            $scope.newModel = '';
        });
    }    
    
    $scope.deleteYear = function() {
      $http.delete('../api/model-years/' + $scope.selectYear).success(function (data, status) {
          $scope.getYears();
          $scope.selectYear = 'None';
      });
    }
    
    $scope.addYear = function() {
      var postFields = {modelId: $scope.selectModel,
                        yearName: $scope.newYear};
        $http({
                method: 'POST',
                url: '../api/model-years',
                data: $.param(postFields),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data, status, headers, config) {
                  console.log(data);
                  $scope.getYears();
            $scope.newYear = '';
        }).error(function(data, status, config) {
                  console.log("Something went wrong");
            $scope.newYear = '';
        });
    }
    
    $scope.deleteMakeAlternate = function() {
      $http.delete('../api/make-alternates/' + $scope.selectMakeAlternate).success(function (data, status) {
          $scope.getMakeAlternates();
          $scope.selectMakeAlternate = 'None';
      });
    }
    
    $scope.addMakeAlternate = function() {
      var postFields = {makeId: $scope.selectMake,
                        makeAlternateName: $scope.newMakeAlternate};
        $http({
                method: 'POST',
                url: '../api/make-alternates',
                data: $.param(postFields),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data, status, headers, config) {
                  console.log(data);
                  $scope.getMakeAlternates();
            $scope.newMakeAlternate = '';
        }).error(function(data, status, config) {
                  console.log("Something went wrong");
            $scope.newMakeAlternate = '';
        });
    }
    
    $scope.deleteModelAlternate = function() {
      $http.delete('../api/model-alternates/' + $scope.selectModelAlternate).success(function (data, status) {
          $scope.getModelAlternates();
          $scope.selectModelAlternate = 'None';
      });
    }
    
    $scope.addModelAlternate = function() {
      var postFields = {modelId: $scope.selectModel,
                        modelAlternateName: $scope.newModelAlternate};
        $http({
                method: 'POST',
                url: '../api/model-alternates',
                data: $.param(postFields),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data, status, headers, config) {
                  console.log(data);
                  $scope.getModelAlternates();
            $scope.newModelAlternate = '';
        }).error(function(data, status, config) {
                  console.log("Something went wrong");
            $scope.newModelAlternate = '';
        });
    }
  
}]);