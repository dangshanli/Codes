const CustomerContract = require("./customerContract");

class Customer {
    constructor(name, discountRate) {//名字 折扣率 合同
        this._name = name;
        this._discountRate = discountRate;
        this._contract = new CustomerContract(dateToday());
    }

    get discountRate() {
        return this._discountRate;
    }

    becomePreferred(){
        this._discountRate += 0.03;
    }

    applyDiscount(amount){
        return amount.subtract(amount.multiply(this._discountRate))
    }
}

console.log('')