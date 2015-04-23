'use strict';

var counter = 0;
var savedMake = "All Makes";
var savedModel = "All Models";
var savedYear = "All Years";
var savedLocation = "aa";
var savedStartDate = "undefined";
var savedEndDate = "undefined";

angular.module('controllers').controller('CompareCtrl',['$scope', '$http', '$filter', 
                                                        function($scope, $http, $filter){


      $http({
            method: 'post',
            url: '../api/savedsearches/getSavedSearches',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: "user="+_uToken
      }).then(function(response) {
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

var grade;