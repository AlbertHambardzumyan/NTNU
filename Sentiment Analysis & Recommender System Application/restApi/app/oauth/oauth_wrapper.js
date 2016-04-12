var oauthserver = require('oauth2-server');
var Model = require('./model');

var server = oauthserver({
    model: new Model(),
    grants: ['password', 'refresh_token'],
    accessTokenLifetime: 86400,
    debug: process.env.NODE_ENV !== 'production'
});

module.exports = {
    grant: function(req, res, next) {
        var grant = server.grant();
        grant(req, res, next);
    },
    authorise: function(req, res, next) {
        var authorise = server.authorise();
        authorise(req, res, next);
    },
    errorHandler: function(err, req, res, next) {
        var errorHandler = server.errorHandler();
        errorHandler(err, req, res, next);
    }
};