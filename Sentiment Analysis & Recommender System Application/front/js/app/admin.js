console.log("App init!");

var InsiderApp = angular.module('Insider',[
    'ngMaterial',
    'ui.router',
    'ngResource',
    'ngAnimate'
]);

InsiderApp.constant('API_V1', 'http://localhost:3000/api/v1/');

InsiderApp.config(function($stateProvider, $urlRouterProvider){

    $urlRouterProvider.otherwise('/');
    $stateProvider
        .state('main', {
            url: '/',
            views: {
                'main': {
                    controller: 'adminCtrl'
                }
            },
            resolve: {
            }
        })
        .state('main.roster', {
            views: {
                'admin': {
                    templateUrl: 'js/app/templates/admin.html'
                }
            },
            resolve: {
            }
        });
});