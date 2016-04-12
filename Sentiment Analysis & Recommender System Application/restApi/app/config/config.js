"use strict";

var env = process.env.env || 'prod';
//var env = process.env.env || 'dev';

console.log("****************************");
console.log("    Config env is: " + process.env.env);
console.log("****************************");

var config = {
    dev: {
        port: '3000',
        host: 'http://localhost',

        api_version: 'v1',
        rest_url: function () {
            return '/api/' + this.api_version;
        },

        db: {
            dialect: 'mysql',
            multipleStatements: true,
            host: '127.0.0.1',
            user: 'root',
            password: 'sesame',
            database: 'intelligence'
        },
        recommendClosest: {
            distance: 1,
            maxRecommends: 7
        },
        recommendCF: {
            prepareMax: 7
        },
        project: {
            path: 'D:/Additional to C disk/Phpstorm Projects'
        }

    },

    prod: {
        port: '3000',

        api_version: 'v1',
        rest_url: function () {
            return '/api/' + this.api_version;
        },

        db: {
            dialect: 'mysql',
            multipleStatements: true,
            host: '',
            user: '',
            password: '',
            database: ''
        }
    }
};

module.exports = config[env];