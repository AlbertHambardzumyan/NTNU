InsiderApp.factory('AdminService', ['$http','$q','API_V1', function ($http, $q, API_V1) {
    return {
        collaborativeFiltering:function(id){
            console.log("\nRequest for Collaborative Filtering!");
            console.log('Initial parameters - User Id: '+id);
            var def = $q.defer();
            $http.get(API_V1+'collaborativeFiltering/'+id).then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        },
        sentimentalAnalysis:function(id){
            console.log("\nRequest for Sentimental  Analysis!");
            console.log('Initial parameters - Hotel Id: ' +id);
            var def = $q.defer();
            $http.get(API_V1+'sentiment/'+id).then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        }
    };
}]);