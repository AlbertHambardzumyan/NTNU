ItemApp.controller('rankCtrl',['$scope', 'item', function( $scope, item){
    console.log('\nRank controller loads!');
    console.log('Rank: '+item[0].rate);

    $scope.rank = item[0].rate;
}]);