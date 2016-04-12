InsiderApp.controller('adminCtrl',['$scope', '$state', 'AdminService', function( $scope, $state, AdminService){
    console.log('\nAdmin controller loads!');

    var username = prompt("Please enter your username", "Harry Potter");
    var password = prompt("Please enter your password", "Harry Potter");
    if (username == 'admin' && password == 'admin') {
        console.log('Entering into the system!');
        $state.go('main.roster');
    }
    else console.log('Username or Password is NOT correct!');

    $scope.sentiment = {};
    $scope.sentimentalAnalysis = function(){
        $scope.sentiment.response = 'Computing ...';
        AdminService.sentimentalAnalysis($scope.sentiment.hotelId).then(function(response) {
            $scope.sentiment.response = response;
            console.log('Done ...');
        },function(error) {
            $scope.sentiment.response = error;
            console.log(error);
        });
    };

    $scope.cf = {};
    $scope.collaborativeFiltering = function(){
        $scope.cf.response = 'Computing ...';
        AdminService.collaborativeFiltering($scope.cf.userId).then(function(response) {
            $scope.cf.response = response;
            console.log('Done ...');
            },function(error) {
            $scope.cf.response = error;
            console.log(error);
            });
    };
}]);