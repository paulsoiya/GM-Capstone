'use strict';

angular.module('controllers').controller('ProfileCtrl',['$scope','$http', '$location',
                                                        function($scope, $http, $location){

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


      $scope.savedSearch = function() {
          $location.path('query');

            $http({
                  method: 'post',
                  url: '../api/savedsearches/getSavedSearch',
                  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                  data: "user="+_uToken+"&"+
                  "searchName="+$scope.searchName
            }).then(function(response) {
                  //$scope.searches = response.data;
                  console.log(response);

                  var wordCloud = document.getElementById('first_cloud_canvas');
                  var pieChart = document.getElementById('first_pie_canvas');
                  var barGraph = document.getElementById('first_bar_canvas');
                  drawQuery(response, wordCloud, pieChart, barGraph);
            });
      }

      $scope.getUserDetail = function() {

             $http.get('../api/users/' + _uToken).success(function(data) {
                    $scope.userData = data;
              });
      }
      
      
      
      

      $scope.getUserDetail();


      $scope.save = function()
        {
            $("#success").hide();
            $("#failure").hide();

         var postFields = {   first_name:$("#FirstName").val(),
                                          last_name:$("#LastName").val(),
                                          email:$("#inputEmail").val(),
                                          password:$("#inputPassword").val()
                                    };

         if ($("#inputPassword").val() != $("#inputPasswordConfirm").val()) {
               $("#failure-message").html("The passwords you entered do not match");
               $("#failure").show();
               return;
         }


              $http({
                      method: 'PUT',
                      url: 'http://localhost:7001/GMProject/api/users/' + _uToken,
                      data: $.param(postFields),
                      headers: {'Content-Type': 'application/x-www-form-urlencoded'}
              }).success(function(data, status, headers, config) {
                  console.log(data);
                  $("#success").hide();
                  $("#failure").hide();

                          if(data.result === "success"){
                              $("#success-message").html("Your changes have been saved");
                              $("#success").show();
                          }
                          else{
                              $("#failure-message").html("There was a problem saving your changes");
                              $("#failure").show();
                          }

              }).error(function(data, status, headers, config) {
                  $("#success-message").hide();
                  $("#failure-message").hide();

                  $("#failure-message").html("There was a problem saving your changes");
                  $("#failure").show();

              });
        }
}]);