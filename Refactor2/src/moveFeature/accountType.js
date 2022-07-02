class AccountType {
    isPremium;

    constructor(isPremium) {
        this.isPremium = isPremium;
    }

    overdraftCharge(daysOverdrawn) {
        if (this.isPremium) {
            const baseCharge = 10;
            if (daysOverdrawn <= 7)
                return baseCharge;
            else
                return baseCharge + (daysOverdrawn - 7) * 0.85;
        } else
            return daysOverdrawn * 1.75;
    }
}

module.exports = AccountType