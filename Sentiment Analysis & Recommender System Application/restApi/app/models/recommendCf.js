"use strict";

var db = require('../db/db');

module.exports = {
    fetchCFRecommendations: function (userId, successCallBack, errorCallBack) {
        db.getConnection().query('SELECT * FROM recommendations as rec ' +
            'JOIN hotels ON rec.hotelID=hotels.numID ' +
            'WHERE userId=' + userId + ' ORDER BY recommendationValue DESC;', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            successCallBack(rows, fields);
        });
    }
};

