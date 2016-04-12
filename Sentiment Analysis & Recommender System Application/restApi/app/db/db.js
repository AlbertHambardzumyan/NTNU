"use strict";

var mysql = require('mysql');
var config = require('../config/config');
var connection;

module.exports = {
    init: function () {
        connection = mysql.createConnection(config.db);
        connection.connect(function (msg) {
            console.log("Mysql connection established: ", msg);
        });
    },

    destroy: function () {
        connection.end();
    },

    getConnection: function () {
        return connection;
    }

};