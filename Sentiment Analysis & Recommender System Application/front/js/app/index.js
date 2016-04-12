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
    $stateProvider.state('main',{
        url:'/',
        views:{
            'main':{
                templateUrl:'js/app/templates/search.html',
                controller:'searchCtrl'
            }
        },
        resolve:{
            searchTypes: function(SearchService){
                return SearchService.getAllTypes();
            }
        }
    })
    .state('main.roster',{
        views:{
            'items':{
                templateUrl:'js/app/templates/items.html',
                controller:'itemsCtrl'
            }
        },
        resolve:{
            itemsName: function(SearchService){
                return SearchService.getAllNames();
            }
        }
    });
});