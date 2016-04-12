"use strict";

var router = require('express').Router();
var hotels_model = require('../../models/hotels');
var python_model = require('../../python_model/python');
var help = require('../../helper/helpers');

router.get('/', function (req, res) {
    hotels_model.getAllNames(function (hotelNamesData) {

        for (var i = 0; i < 5; i++) {
            var id = hotelNamesData[i].id;
            var result = sentimentAll(id);
        }
        res.json(result);
    }, function (err) {
        res.json(err);
    });
});

router.get('/:id', function (req, res) {
    console.log("\nServer call for - Route: 'sentiment'. Method: 'do sentimental analysis and store in db");
    console.log("Initial parameters - Hotel Id: "+req.params.id);
    hotels_model.getAllReviews(req.params.id, function (data) {

        python_model.compute(data, function (sentimentData) {
            var newRank = help.computeRank(sentimentData);
            hotels_model.updateRank(req.params.id, newRank, function (data) {
                console.log('Done...');
                res.json(newRank);
            }, function (err) {
                console.log('Error while resolving the method...');
                res.json(err);
            });
        }, function (err) {
            console.log('Error while resolving the method...');
            res.json(err);
        });
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});

function sentimentAll(id){
    hotels_model.getAllReviews(id, function (reviewsData) {

        python_model.compute(reviewsData, function (sentimentData) {
            var newRank = help.computeRank(sentimentData);
            hotels_model.updateRank(id, newRank, function (updateRankData) {
                return "Done ...";
            }, function (err) {
                //res.json(err);
            });
        }, function (err) {
           // res.json(err);
        });
    }, function (err) {
        //res.json(err);
    });
}

module.exports = router;