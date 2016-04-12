"use strict";

var db = require('../db/db');
var PythonShell = require('python-shell');
var config = require('../config/config');
var pathRoot = config.project.path;
var options = {
    scriptPath: pathRoot+"/WebIntelligence/api/app/python_model"
};

module.exports = {
    compute: function (data, successCallBack, errorCallBack) {
        var pyshell = new PythonShell('sentiment.py', options);

        var pos = 0, neg = 0, net = 0;
        var halt = 0;
        for(var i = 0; i < data.length; i++){
            console.log("Feedback: "+data[i].text);
            pyshell.send(data[i].text); // Send input to sentiment analyzer here
        }

        pyshell.on('message', function (message) {
            halt++;
            // received a message sent from the Python script (a simple "print" statement)
            if(message.toString().trim() === "Positive") pos++;
            else if(message.toString().trim() === "Negative") neg++;
            else if(message.toString().trim() === "Neutral") net++;

            if (halt == data.length) {
                var result = {
                    positive: pos,
                    negative: neg,
                    neutral: net
                };
                console.log('Result: '+JSON.stringify(result));
            successCallBack(result);
            }
        });

        // end the input stream and allow the process to exit
        pyshell.end(function (err) {
            if (err) {
                console.log(err);
                errorCallBack(err);
            }
        });
    }
};
