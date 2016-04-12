"use strict";

module.exports = {
    setCrossOriginHeaders: function (app) {
        // Open Cross Origin Access fos all domains
        app.use(function (req, res, next) {
            res.header('Access-Control-Allow-Origin', '*');
            res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
            res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, Cache-Control");
            if (req.method == 'OPTIONS') {
                res.send(200);
            } else {
                next();
            }
        });
    }
};