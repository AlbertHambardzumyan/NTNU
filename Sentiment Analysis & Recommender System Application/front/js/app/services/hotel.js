ItemApp.factory('HotelService', ['$http','$q','API_V1', function ($http, $q, API_V1) {
    return {
        findItemById:function(id){
            console.log("Request for the Item by Id!");
            var def = $q.defer();
            $http.get(API_V1+'hotels/'+id).then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        },
        rate:function(itemId, userId, rate){
            console.log("Request for Rating this Item by this User!");
            var def = $q.defer();
            $http.get(API_V1+'hotels/'+itemId+'/rate/'+rate+'/users/'+ userId).then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        },
        getRate:function(itemId, userId){
            console.log("Request for the Rate given by this User!");
            var def = $q.defer();
            $http.get(API_V1+'hotels/'+itemId+'/users/'+ userId).then(function(resp){
                def.resolve(resp.data);
            },function(err){
                def.reject(err);
            });
            return def.promise;
        }
    };
}]);