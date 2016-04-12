"use strict";

var router = require('express').Router();
var recommendCf = require('../../models/recommendCf');

router.get('/:id', function (req, res) {
    console.log("\nServer call for - Route: 'recommendCf'. Method: 'get CF recommendations");
    console.log("Initial parameters - User Id: "+req.params.id);
    recommendCf.fetchCFRecommendations(req.params.id, function (data) {
        console.log('Done...');
        res.json(data);
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});

module.exports = router;