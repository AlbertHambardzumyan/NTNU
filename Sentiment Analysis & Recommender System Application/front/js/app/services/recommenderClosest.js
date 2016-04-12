ItemApp.factory('RecommenderClosestService', ['$http','$q','API_V1', function ($http, $q, API_V1) {
    return {
        recommendClosest:function(id){
            console.log("Request for Recommends Closest ones with Highest Rank!");
            var def = $q.defer();
            $http.get(API_V1+'recommendClosest/'+id).then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        }
    };
}]);