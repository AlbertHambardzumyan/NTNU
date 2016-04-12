InsiderApp.controller('searchCtrl', [ '$scope', '$state', 'searchTypes', '$localstorage',  function ( $scope, $state, searchTypes, $localstorage) {
    console.log('Search controller loads!');

    var userId = prompt("Please enter your user id", "13");
    $localstorage.set('userId', userId);
    console.log("Provided user id : " + userId);

    $scope.searchTypes   = loadAll(searchTypes);
    $scope.selectedType  = null;
    $scope.searchTypeText    = null;
    $scope.queryTypeSearch   = queryTypeSearch;
    $scope.selectedTypeChange = selectedItemChange;

    function queryTypeSearch (query) {
        return query ? $scope.searchTypes.filter( createFilterFor(query) ) : [];
    }
    function selectedItemChange(item) {
        if(item) $state.go('main.roster',{type: $scope.selectedType.value});
    }
    function createFilterFor(query) {
        var lowercaseQuery = angular.lowercase(query);
        return function filterFn(state) {
            return (state.value.indexOf(lowercaseQuery) === 0);
        };
    }
    function loadAll(input) {
        var result = [];
        for (var i = 0; i < input.length; i++) {
            var item = {
                value: input[i].name.toLowerCase(),
                display: input[i].name
            };
            result.push(item);
        }
        return result;
    }
}]);