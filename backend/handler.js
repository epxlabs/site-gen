"use strict";

var AWS = require("aws-sdk");
var ses = new AWS.SES();
var RECEIVER = "hello@epxlabs.com";
var SENDER = "hello@epxlabs.com";

function sendEmail(event, done) {
    var params = {
        Destination: {
            ToAddresses: [
                RECEIVER
            ]
        },
        Message: {
            Body: {
                Text: {
                    Data: "Name: " + event.name + "\nEmail: " + event.email + "\nMessage: " + event.message,
                    Charset: "UTF-8"
                }
            },
            Subject: {
                Data: "Website Referral Form: " + event.name,
                Charset: "UTF-8"
            }
        },
        Source: SENDER
    };
    ses.sendEmail(params, done);
};

module.exports.submit = function (event, context) {
    sendEmail(event, function (err) {
        context.done(err, null);
    });
};