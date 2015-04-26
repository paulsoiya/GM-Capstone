/* 
 * The controller for the compare page. 
 *
 * Uses HTTP to POST a request to CouchDB for the saved searches of the particular user
 *           and to POST a request for the saved search chosen by the user.
 */

'use strict';

var notLoaded = true;

var counter = 0;
var savedMake = "All Makes";
var savedModel = "All Models";
var savedYear = "All Years";
var savedLocation = "aa";
var savedStartDate = "undefined";
var savedEndDate = "undefined";

angular.module('controllers').controller('CompareCtrl', ['$scope', '$http', '$filter', 
                                                        function($scope, $http, $filter) {

  // POST request to CouchDB for saved searches
  $scope.getSearches = function() {
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
  }

  // POST a request for the first saved search
  $scope.comparePost1 = function(){
    var pieChart = document.getElementById('first_pie_canvas');
    var wordCloud = document.getElementById('first_cloud_canvas');
    var barGraph = document.getElementById('first_bar_canvas');
    
    var start = new Date();
    var pieThrob = drawThrobber(pieChart,start);
    var barThrob = drawThrobber(barGraph,start);
    var cloudThrob = drawThrobber(wordCloud,start);
    
    $(".comparesearch1").removeClass('hidden');
    
    $http({
      method: 'post',
      url: '../api/savedsearches/getSavedSearch',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: "user="+_uToken+"&"+
      "searchName="+$scope.searchName1
    }).then(function(response) {
      clearInterval(pieThrob);
      clearInterval(barThrob);
      clearInterval(cloudThrob);
      $scope.firstGrade = drawQuery(response, wordCloud, pieChart, barGraph);
    });
  }

  // POST a request for the second saved search
  $scope.comparePost2 = function(){
    var pieChart = document.getElementById('second_pie_canvas');
    var wordCloud = document.getElementById('second_cloud_canvas');
    var barGraph = document.getElementById('second_bar_canvas');
    
    var start = new Date();
    var pieThrob = drawThrobber(pieChart,start);
    var barThrob = drawThrobber(barGraph,start);
    var cloudThrob = drawThrobber(wordCloud,start);
    $(".comparesearch2").removeClass('hidden');
    $http({
      method: 'post',
      url: '../api/savedsearches/getSavedSearch',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      data: "user="+_uToken+"&"+
      "searchName="+$scope.searchName2
    }).then(function(response) {
      clearInterval(pieThrob);
      clearInterval(barThrob);
      clearInterval(cloudThrob);
      $scope.secondGrade = drawQuery(response, wordCloud, pieChart, barGraph);          
    });
  }
  
  if (notLoaded) {
    $scope.getSearches();
    notLoaded = false;
  }
  
}]);