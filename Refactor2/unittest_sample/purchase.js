/**
 * 行省
 */
class Province {
	constructor(doc) {
		this._name = doc.name; //名称
		this._producers = []; //生产商
		this._totalProduction = 0; //全部产品
		this._demand = doc.demand; //需求量
		this._price = doc.price; //价格
		doc.producers.forEach((element) => this.addProducer(new Producer(this, element)));
	}

	addProducer(arg) {
		this._producers.push(arg);
		this._totalProduction += arg.production;
	}

	//短缺
	get shortfall() {
		return this._demand - this.totalProduction;
	}

	//满足的需求
	get satisfiedDemand() {
		return Math.min(this._demand, this.totalProduction);
	}

	//付出的费用
	get demandValue() {
		this.satisfiedDemand * this.price;
	}

	get demandCost() {
		let remainingDemand = this.demand;
		let result = 0;
		this.producers.sort((a, b) => a.cost - b.cost);
	}

	//getter setter
	get name() {
		return this._name;
	}

	get producers() {
		return this._producers.slice();
	}

	get totalProduction() {
		return this._totalProduction;
	}

	set totalProduction(arg) {
		this._totalProduction = arg;
	}

	get demand() {
		return this._demand;
	}

	set demand(arg) {
		this._demand = parseInt(arg);
	}

	get price() {
		return this._price;
	}

	set price(arg) {
		this._price = parseInt(arg);
	}
}

/**
 * 
 */
class Producer {
	constructor(aProvince, data) {
		this._province = aProvince;
		this._cost = data.cost;
		this._name = data.name;
		this._production = data.production || 0;
	}

	get name() {
		return this._name;
	}
	get cost() {
		return this._cost;
	}
	set cost(arg) {
		this._cost = parseInt(arg);
	}

	get production() {
		return this._production;
	}

	//修改产品数 备注：本对象产品数修改以及关联省份的总产品修改
	set production(amountStr) {
		const amount = parseInt(amountStr);
		const newProduction = Number.isNaN(amount) ? 0 : amount;
		this._province.totalProduction += newProduction - this._production;
		this._production = newProduction;
	}
}

/**
 * 固定的实例用行省数据
 * @param {*} params 
 */
function sampleProvinceData(params) {
	return {
		name: 'Asia',
		producers: [
			{ name: 'Byzan', cost: 10, production: 9 },
			{ name: 'Atta', cost: 12, production: 10 },
			{ name: 'Sinope', cost: 10, production: 6 }
		],
		demand: 30,
		price: 20
	};
}
