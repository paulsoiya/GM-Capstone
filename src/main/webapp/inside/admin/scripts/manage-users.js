GMAdminApp.controller('ManageUsersController',
                     ['$scope','$http', function($scope, $http) {
 

    //fill the pending users table
    $http.get('../api/pending-users').success(function(data) {
        $scope.pusers = data.pendingUser;
    });
    
    //fill the users table
    $http.get('../api/users').success(function(data) {
        
        //change boolean value for admin to 
        //textual representation of user role
        for(i = 0; i < data.user.length; i++){
            if(data.user[i].admin == "true")
                data.user[i].admin = "Admin";
            else
                data.user[i].admin = "User";
        }
        $scope.users = data.user;
    });

 
}]);