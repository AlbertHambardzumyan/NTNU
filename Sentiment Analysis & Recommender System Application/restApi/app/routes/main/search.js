"use strict";

var router = require('express').Router();
var search_model = require('../../models/search');

router.get('/', function (req, res) {
    console.log("\nServer call for - Route: 'search'. Method: 'get types of search items");
    search_model.getTypes(function (data) {
        console.log('Done...');
        res.json(data);
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});


module.exports = router;