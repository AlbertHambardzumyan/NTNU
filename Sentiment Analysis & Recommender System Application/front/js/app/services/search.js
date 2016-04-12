InsiderApp.factory('SearchService', ['$http','$q','API_V1', function ($http, $q, API_V1) {
    return {
        getAllTypes:function(){
            console.log("Request for All Search Types!");
            var def = $q.defer();
            $http.get(API_V1+'search/').then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        },
        getAllNames:function(){
            console.log("Request for Searched Items!");
            var def = $q.defer();
            $http.get(API_V1+'hotels/').then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        }
    };
}]);