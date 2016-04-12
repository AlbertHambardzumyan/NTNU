console.log("App init!");

var ItemApp = angular.module('itemApp', [
    'ngMaterial',
    'ui.router',
    'ngResource',
    'ngAnimate'
]);

ItemApp.constant('API_V1', 'http://localhost:3000/api/v1/');

ItemApp.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/');
    $stateProvider
        .state('main', {
            url: '/',
            views: {
                'main': {
                    templateUrl: 'js/app/templates/item.html',
                    controller: 'itemCtrl'
                }
            },
            resolve: {
            }
        })
        .state('main.roster', {
            url: 'hotel/:id',
            views: {
                'rate': {
                    templateUrl: 'js/app/templates/rate.html',
                    controller: 'rateCtrl'
                },
                'map': {
                    templateUrl: 'js/app/templates/map.html',
                    controller: 'mapCtrl'
                },
                'rank': {
                    templateUrl: 'js/app/templates/rank.html',
                    controller: 'rankCtrl'
                },
                'details': {
                    templateUrl: 'js/app/templates/details.html',
                    controller: 'detailsCtrl'
                },
                'recommendClosest': {
                    templateUrl: 'js/app/templates/recommendClosest.html',
                    controller: 'recommenderClosestCtrl'
                },
                'recommendCF': {
                    templateUrl: 'js/app/templates/recommendCF.html',
                    controller: 'recommendCFCtrl'
                }
            },
            resolve: {
                item: function (HotelService, $stateParams) {
                    return HotelService.findItemById($stateParams.id);
                },
                recommendsClosest: function (RecommenderClosestService, $stateParams) {
                    return RecommenderClosestService.recommendClosest($stateParams.id);
                },
                recommendsCF: function (RecommenderCFService, $localstorage) {
                    return RecommenderCFService.recommendCF($localstorage.get('userId'));
                },
                rate: function (HotelService, $localstorage, $stateParams) {
                    return HotelService.getRate($stateParams.id, $localstorage.get('userId'));
                }
            }
        });
});