const AccountType = require("./accountType");
// const AccountType = tt.AccountType;

class Account {
    _daysOverdrawn;
    type;

    constructor(daysOverdrawn, type) {
        this._daysOverdrawn = daysOverdrawn;
        this.type = type;
    }

    get daysOverdrawn() {
        return this._daysOverdrawn;
    }

    get bankCharge() {
        let result = 4.5;
        if (this._daysOverdrawn > 0) {
            result += this.overdraftCharge;
        }
        return result;
    }

    get overdraftCharge() {
        return this.type.overdraftCharge(this.daysOverdrawn);
    }
}

console.log('Account type is ')

const account = new Account(10, new AccountType(true));
console.log(account);
console.log(account.type)
console.log(account.bankCharge)
