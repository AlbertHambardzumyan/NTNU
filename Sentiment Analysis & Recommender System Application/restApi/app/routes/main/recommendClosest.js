"use strict";

var router = require('express').Router();
var hotels_model = require('../../models/hotels');
var config = require('../../config/config');
var help = require('../../helper/helpers');

router.get('/:id', function (req, res) {
    console.log("\nServer call for - Route: 'recommendClosest'. Method: 'get Closer & Highest rank recommendations");
    console.log("Initial parameters - Hotel Id: "+req.params.id);
    hotels_model.getCoordinates(req.params.id, function (dataCoordinates) {

        hotels_model.getAll(function (dataHotels) {
            var result = [];
            for(var i = 4; i >= -1; i--){ //?. distance rather random slice
                for (var j = 0; j < dataHotels.length; j++) {
                    var km = help.getDistanceFromLatLonInKm(dataCoordinates[0].lat, dataCoordinates[0].long, dataHotels[j].lat, dataHotels[j].long);
                    //console.log(km);
                    if( (km > 0 && km < config.recommendClosest.distance) && (dataHotels[j].rate >= i && dataHotels[j].rate <= i+1) ){
                        dataHotels[j].km = km;
                        result.push(dataHotels[j]);
                    }
                }
                if (result.length >= config.recommendClosest.maxRecommends) break;
            }

            var random = Math.floor((Math.random() * result.length) + 1);
            if(random < config.recommendClosest.maxRecommends){
                result.sort(function(a, b){return a['rate']-b['rate']});
                result = result.slice(-config.recommendClosest.maxRecommends);
            }
            else result = result.slice(random-config.recommendClosest.maxRecommends,random);

            console.log('Done...');
            res.json(result);
        }, function (err) {
            console.log('Error while resolving the method...');
            res.json(err);
        });
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});

module.exports = router;