"use strict";

var db = require('../db/db');
var mysql = require('mysql');
var config = require('../config/config');

var pathRoot = config.project.path;
var dbHost = config.db.host;
var dbUser = config.db.user;
var dbName = config.db.database;
var dbPassword = config.db.password;

module.exports = {
    makeRecommendations: function (userId,maxNumberOfRecommendations, successCallBack, errorCallBack) {
        var exec = require('child_process').exec;
        var cmd = 'java -jar "'+pathRoot+'/WebIntelligence/api/app/recommender_cf/recommenderLast.jar" '+dbHost+' '+dbUser+' '+dbName+
            ' '+dbPassword+' '+userId+' '+maxNumberOfRecommendations;
        
        exec(cmd, function(error, stdout, stderr){
            if(stdout.lastIndexOf("ERROR",0) === 0){
                errorCallBack("Do some error handling");
            }
            else if(stdout.lastIndexOf("SUCCESS",0) === 0){
                successCallBack("Done ...");
            }
            else{
                console.log(error);
                errorCallBack("This should not happen");
            }         
        });

    }
};

