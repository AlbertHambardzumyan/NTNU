"use strict";

var router = require('express').Router();
var hotels_model = require('../../models/hotels');

router.get('/', function (req, res) {
    console.log("\nServer call for - Route: 'hotels'. Method: 'get names of all hotels");
    hotels_model.getAllNames(function (data) {
        console.log('Done...');
        res.json(data);
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});

router.get('/:id', function (req, res) {
    console.log("\nServer call for - Route: 'hotels'. Method: 'get hotel by Id");
    console.log("Initial parameters - Hotel Id: "+req.params.id);
    hotels_model.findById(req.params.id, function (data) {
        console.log('Done...');
        res.json(data);
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});

router.get('/:id/rate/:rate/users/:user', function (req, res) {
    console.log("\nServer call for - Route: 'hotels'. Method: 'post a rate for particular item from particular user");
    console.log("Initial parameters - Hotel Id: "+req.params.id+", Rate: "+ req.params.rate+ ", User Id: "+req.params.user);
    hotels_model.rate(req.params.id, req.params.rate, req.params.user, function (data) {
        console.log('Done...');
        res.json(data);
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});

router.get('/:id/users/:user', function (req, res) {
    console.log("\nServer call for - Route: 'hotels'. Method: 'get the rate for particular item from particular user");
    console.log("Initial parameters - Hotel Id: "+req.params.id+ ", User Id: "+req.params.user);
    hotels_model.getRate(req.params.id, req.params.user, function (data) {
        console.log('Done...');
        res.json(data);
    }, function (err) {
        console.log('Error while resolving the method...');
        res.json(err);
    });
});
module.exports = router;