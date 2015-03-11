'use strict';

/* Directives */
var directives = angular.module('directives', []);

directives.directive('gmHeader', function(){
    return{
      restrict: 'E',
      templateUrl: 'partials/header.html',
      controller: 'NavbarCtrl'
    };
});