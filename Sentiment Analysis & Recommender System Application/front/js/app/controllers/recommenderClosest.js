ItemApp.controller('recommenderClosestCtrl', function($window,$localstorage, $scope, recommendsClosest){
    console.log('\nRecommends Closest controller loads!');

    $scope.recommendsClosest = recommendsClosest;

    $scope.redirect = function(id){
        console.log('\nStoring hotel ID in Local Storage!');
        console.log('Redirecting to item.html!');

        $localstorage.set('itemId', id);
        $window.open( 'item.html');
    };
});