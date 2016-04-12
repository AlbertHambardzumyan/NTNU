ItemApp.controller('rateCtrl',  function ($scope, HotelService, $localstorage, rate) {
    console.log('\nRate controller loads!');


    if(rate.length != 0){
        console.log('Rate given by you previously: '+rate[0].rating);
        $scope.rating = rate[0].rating;
    }
    else {
        console.log("You haven't rated this item previously");
        $scope.rating = -1;
    }

    $scope.rateFunction = function (rating) {
        HotelService.rate($localstorage.get('itemId'), $localstorage.get('userId'), rating).then(function(response) {
            console.log(response);
        },function(error) {
            console.log(error);
        });
    };
})
    .directive('starRating',
    function () {
        return {
            restrict: 'A',
            template: '<ul class="rating">'
                + '	<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">'
                + '\u2605'
                + '</li>'
                + '</ul>',
            scope: {
                ratingValue: '=',
                max: '=',
                onRatingSelected: '&'
            },
            link: function (scope, elem, attrs) {
                var updateStars = function () {
                    scope.stars = [];
                    for (var i = 0; i < scope.max; i++) {
                        scope.stars.push({
                            filled: i < scope.ratingValue
                        });
                    }
                };

                scope.toggle = function (index) {
                    scope.ratingValue = index + 1;
                    scope.onRatingSelected({
                        rating: index + 1
                    });
                };

                scope.$watch('ratingValue',
                    function (oldVal, newVal) {
                        if (newVal) {
                            updateStars();
                        }
                    }
                );
            }
        };
    }
);