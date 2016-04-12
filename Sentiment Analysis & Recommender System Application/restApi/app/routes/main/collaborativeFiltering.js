"use strict";

var router = require('express').Router();
var recommendCf = require('../../recommender_cf/collabRec');
var config = require('../../config/config');

router.get('/:id', function (req, res) {
    console.log("\nServer call for. Route: 'collaborative filtering'. Method: 'make recommendations");
    console.log("Initial parameters - User Id: "+req.params.id);
    recommendCf.makeRecommendations(req.params.id, config.recommendCF.prepareMax, function (data) {
        console.log('Done...');
        res.json(data);
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});

module.exports = router;