'use strict';

/* App Module */

var socialGMApp = angular.module('socialGMApp', [
  'ui.router',
  'animations',
  'controllers',
  'filters',
  'services',
  'directives'
]);

socialGMApp.config(function($stateProvider, $urlRouterProvider){
  
  $urlRouterProvider.otherwise("/query");
  // Now set up the states
  $stateProvider
    .state('user', {
      url: "",
      templateUrl: "partials/user-navbar.html"
    })
    .state('user.profile', {
      url: "/profile",
      templateUrl: "partials/profile.html",
      controller: "ProfileCtrl"
    })
    .state('user.compare',{
      url:"/compare",
      templateUrl: "partials/compare.html",
      controller: "CompareCtrl"
    })
    .state('user.query',{
      url:"/query",
      templateUrl: "partials/query.html",
      controller: "QueryCtrl"
    })
    .state('admin', {
      url: "/admin",
      templateUrl: "partials/admin-navbar.html"
    })
    .state('admin.manageUsers', {
      url: "/manageUsers",
      templateUrl: "partials/manage-users.html",
      controller: "ManageUsersCtrl"
    })
    .state('admin.manageData', {
      url: "/manageData",
      templateUrl: "partials/manage-data.html",
      controller: "AdminCtrl"
    })
    .state('admin.manageAnalytics', {
      url: "/manageAnalytics",
      templateUrl: "partials/manage-analytics.html",
      controller: "AdminCtrl"
    })
});