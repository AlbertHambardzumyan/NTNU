var db = require('../db/db');
var Promise = require('bluebird');
var _ = require('lodash-node');
var crypto_model = require('./crypto');

module.exports = function() {

    this.getAccessToken = function(bearerToken, callback) {
        var query = "SELECT * FROM oauth_access_tokens WHERE access_token='" + bearerToken + "'";

        db.getConnection().query(query, function(err, result, fields) {
            if (err) {
                return callback(true);
            }

            if (!result.length) {
                return callback(false, false);
            }
            callback(false, result[0]);
        });
    };

    this.getRefreshToken = function(bearerToken, callback) {
        var query = "SELECT * FROM oauth_refresh_tokens WHERE refresh_token='" + bearerToken + "'";

        db.getConnection().query(query, function(err, result, fields) {
            console.log("getREFRESHTOKEN **********************************")
            if (err) {
                return callback(true);
            }
            if (!result.length) {
                return callback(false, false);
            }

            result[0].clientId = result[0].client_id;
            result[0].userId = result[0].user_id;
            callback(false, result[0]);
        });
    };

    this.getClient = function(clientId, clientSecret, callback) {
        var query = "SELECT * FROM oauth_clients WHERE client_id='" + clientId + "' AND client_secret='" + clientSecret + "'";

        db.getConnection().query(query, function(err, result, fields) {
            if (err) {
                return callback(true);
            }
            if (!result.length) {
                return callback(false, false);
            }
            result[0].clientId = result[0].client_id;
            result[0].userId = result[0].user_id;
            callback(false, result[0]);
        });
    };

    this.grantTypeAllowed = function(clientId, grantType, callback) {

        var allowedGrantTypes = {
            refresh_token: true,
            password: true
        };
        callback(false, allowedGrantTypes[grantType]);
    };

    this.saveAccessToken = function(accessToken, clientId, expires, userId, callback) {
        var expires = expires.toISOString().slice(0, 19).replace('T', ' ');

        var query = "INSERT INTO oauth_access_tokens (access_token, client_id, user_id, expires) VALUES ('" + accessToken + "','" + clientId + "','" + userId["id"] + "','" + expires + "')";
        db.getConnection().query(query, function(err, result, fields) {
            if (err) {
                return callback(true);
            }
            callback(false);
        });
    };

    this.saveRefreshToken = function(refreshToken, clientId, expires, userId, callback) {
        var expires = expires.toISOString().slice(0, 19).replace('T', ' ');
        var query = "INSERT INTO oauth_refresh_tokens (refresh_token, client_id, user_id, expires) VALUES ('" + refreshToken + "','" + clientId + "','" + userId["id"] + "','" + expires + "')";

        db.getConnection().query(query, function(err, result, fields) {
            if (err) {
                return callback(true);
            }
            callback(false);
        });
    };

    this.getUser = function(username, password, callback) {
        var query = "SELECT user.*, admin.status FROM user LEFT JOIN admin ON admin.id=user.id WHERE user.username='" + username + "'";

        db.getConnection().query(query, function(err, result, fields) {
            if (err) {
                return callback(true);
            }
            var user = _.first(result);
            if (!result.length) {
                return callback(false, false);
            }

            if (!crypto_model.isValidPassword(user, password)) {
                return callback(false, false);
            }


            if ('ADMIN' === user.type) {
                if (user.status !== 'ENABLED') {
                    return callback(false, false);
                }
            }

            callback(false, user); //success
        });
    };
};