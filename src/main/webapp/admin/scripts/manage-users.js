GMAdminApp.controller('ManageUsersController',
                     ['$scope','$http', function($scope, $http) {
 

    //fill the pending users table
    $http.get('../api/pending-users').success(function(data) {
        $scope.pusers = data.pendingUser;
    });
    
    //fill the users table
    $http.get('../api/users').success(function(data) {
        $scope.cusers = data.user;
    });

 
}]);