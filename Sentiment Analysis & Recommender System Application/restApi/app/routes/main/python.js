"use strict";

var router = require('express').Router();
var python_model = require('../../python_model/python');

router.get('/', function (req, res) {
    console.log("\nServer call for. Route: 'python'. Method: 'compute test");
    python_model.compute([{text: 'Test'}], function (data) {
        console.log('Done...');
        res.json(data);
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});


module.exports = router;