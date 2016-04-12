ItemApp.controller('itemCtrl', function ($localstorage, $scope, $state) {
    console.log('\nItem controller loads!');

    var itemId = $localstorage.get('itemId');
    var userId = $localstorage.get('userId');
    console.log('Initial parameters - User Id: ' + userId);
    console.log('Initial parameters - Item Id: ' + itemId);

    $state.go('main.roster', {id: itemId});
});