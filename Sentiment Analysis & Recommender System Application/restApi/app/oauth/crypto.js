var bCrypt = require("bcrypt-nodejs");
module.exports = {
    isValidPassword: function(user, password) {
        return bCrypt.compareSync(password, user.password);
    },
    createHash: function(password) {
        return bCrypt.hashSync(password, bCrypt.genSaltSync(10), null);
    }

};