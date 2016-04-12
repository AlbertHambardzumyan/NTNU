ItemApp.controller('detailsCtrl',['$scope', 'item', function( $scope, item){
    console.log('\nDetails controller loads!');

    $scope.address = item[0].street;
}]);