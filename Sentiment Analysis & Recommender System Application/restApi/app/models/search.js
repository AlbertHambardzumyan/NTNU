"use strict";

var db = require('../db/db');

module.exports = {
    getTypes: function(successCallBack, errorCallBack) {
        db.getConnection().query('SELECT * FROM spottypes;', function(err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            successCallBack(rows, fields);
        });
    }
};