InsiderApp.controller('itemsCtrl',function($localstorage,$window, $scope, itemsName){
    console.log('Items controller loads!');

    $scope.items = itemsName;
    $scope.item = null;

    $scope.redirect = function(id){
        console.log('\nStoring hotel ID in Local Storage!');
        console.log('Redirecting to item.html!');

        $localstorage.set('itemId', id);
        $window.open( 'item.html');
    };
});
