ItemApp.factory('RecommenderCFService', ['$http','$q','API_V1', function ($http, $q, API_V1) {
    return {
        recommendCF:function(id){
            console.log("Request for Collaborative Filtering Recommends!");
            var def = $q.defer();
            $http.get(API_V1+'recommendCf/'+id).then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        }
    };
}]);