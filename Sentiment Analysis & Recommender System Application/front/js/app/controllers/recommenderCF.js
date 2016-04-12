ItemApp.controller('recommendCFCtrl', function($window,$localstorage, $scope, recommendsCF){
    console.log('\nRecommends Collaborative Filtering controller loads!');

    $scope.recommendsCF = recommendsCF;

    $scope.redirect = function(id){
        console.log('\nStoring hotel ID in Local Storage!');
        console.log('Redirecting to item.html!');

        $localstorage.set('itemId', id);
        $window.open( 'item.html');
    };
});