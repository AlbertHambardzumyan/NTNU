"use strict";

var db = require('../db/db');

module.exports = {
    getAll: function (successCallBack, errorCallBack) {
        db.getConnection().query('SELECT * FROM hotels;', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            successCallBack(rows, fields);
        });
    },

    getAllNames: function (successCallBack, errorCallBack) {
        db.getConnection().query('SELECT id, name FROM hotels;', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            successCallBack(rows, fields);
        });
    },

    findById: function (id, successCallBack, errorCallBack) {
        db.getConnection().query('SELECT * FROM hotels WHERE id="' + id + '";', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            successCallBack(rows, fields);
        });
    },

    getCoordinates: function (id, successCallBack, errorCallBack) {
        db.getConnection().query('SELECT `long`, lat FROM hotels WHERE id="' + id + '";', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            successCallBack(rows, fields);
        });
    },

    getAllReviews: function (id, successCallBack, errorCallBack) {
        db.getConnection().query('SELECT * FROM reviews WHERE hotel="' + id + '";', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            successCallBack(rows, fields);
        });
    },
    updateRank: function (id, newRank, successCallBack, errorCallBack) {
        db.getConnection().query('UPDATE hotels SET rate=' + newRank + ' WHERE id="' + id + '";', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            successCallBack(rows, fields);
        });
    },
    rate: function (hotelId, rate, userId, successCallBack, errorCallBack) {
        db.getConnection().query('SELECT * FROM ratings as rec JOIN hotels ON rec.hotelID=hotels.numID WHERE userId=' + userId + ' And id="' + hotelId + '";', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            db.getConnection().query('Select numID from hotels where id="' + hotelId + '";', function (err, rows2, fields) {
                if (err) {
                    errorCallBack(err);
                    return;
                }
                var numID = rows2[0].numID;
                console.log("Hotel Numeric ID: " + numID);

                if (rows.length == 0) {
                    console.log("Inserting a new rating record! User ID: " + userId + ", Hotel ID: " + numID + ", Rate: " + rate);
                    db.getConnection().query('insert into ratings (userID, hotelID, rating) values (' + userId + ', ' + numID + ', ' + rate + ');', function (err, rows, fields) {
                        if (err) {
                            errorCallBack(err);
                            return;
                        }
                        successCallBack(rows, fields);
                    });
                }
                else {
                    console.log("Updating the existing rate! User ID: " + userId + ", Hotel ID: " + numID + ", Rate: " + rate);
                    db.getConnection().query('update ratings set rating=' + rate + ' where hotelID=' + numID + ' AND userId=' + userId + ';', function (err, rows, fields) {
                        if (err) {
                            errorCallBack(err);
                            return;
                        }
                        successCallBack(rows, fields);
                    });
                }
            });
        });
    },
    getRate: function (hotelId, userId, successCallBack, errorCallBack) {
        db.getConnection().query('Select numID from hotels where id="' + hotelId + '";', function (err, rows, fields) {
            if (err) {
                errorCallBack(err);
                return;
            }
            var numID = rows[0].numID;
            console.log("Hotel Numeric ID: " + numID);

                db.getConnection().query('Select rating from ratings where userID='+userId+' AND hotelID='+numID+';', function (err, rows, fields) {
                    if (err) {
                        errorCallBack(err);
                        return;
                    }
                    successCallBack(rows, fields);
                });
        });
    }
};