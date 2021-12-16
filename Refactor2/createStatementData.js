class PerformanceCalculator {
	constructor(aPerformance, aPlay) {
		this.performance = aPerformance;
		this.play = aPlay;
	}

	get amount() {
		throw new Error(`unknow type:${this.play.type}`);
	}

	get volumnCredits() {
		return Math.max(this.performance.audience - 30, 0); //基本积分
	}
}

class TragedyCalculator extends PerformanceCalculator {
	get amount() {
		let result = 40000;
		if (this.performance.audience > 30) {
			result += 1000 * (this.performance.audience - 30);
		}
		return result;
	}
}

class ComedyCalculator extends PerformanceCalculator {
	get amount() {
		let result = 30000;
		if (this.performance.audience > 20) {
			result += 10000 + 500 * (this.performance.audience - 20);
		}
		result += 300 * this.performance.audience;
		return result;
	}

	get volumnCredits() {
		return super.volumnCredits + Math.floor(this.performance.audience / 5);
	}
}

/**
 * 工厂函数 创建PerformanceCalculator类型的各种子类
 * @param {*} aPerformance 
 * @param {*} aPlay 
 * @returns 
 */
function createPerformanceCalculator(aPerformance, aPlay) {
	switch (aPlay.type) {
		case 'tragedy':
			return new TragedyCalculator(aPerformance, aPlay);
		case 'comedy':
			return new ComedyCalculator(aPerformance, aPlay);
		default:
			throw new Error(`unknown type:${aPlay.type}`);
	}
}

/**
 * 数据计算的主要逻辑在这，渲染和计算分离
 * @param {生产数据} invoice 
 * @param {*} plays 
 * @returns 
 */
function createStatementData(invoice, plays) {
	const statementData = {}; //中间层数据，相关的计算都移到中间层，renderPlainText只做文本渲染功能
	statementData.customer = invoice.customer;
	statementData.performances = invoice.performances.map(enrichPerformance);
	statementData.totalAmount = totalAmount(statementData);
	statementData.totalVolumnCredits = totalVolumnCredits(statementData);
	return statementData;

	function enrichPerformance(aPerformance) {
		const calculator = createPerformanceCalculator(aPerformance, playFor(aPerformance));
		const result = Object.assign({}, aPerformance);
		result.play = calculator.play;
		result.amount = calculator.amount;
		result.volumnCredits = calculator.volumnCredits;
		return result;
	}

	/**
   * 计算总价格
   * @returns
   */
	function totalAmount(data) {
		return data.performances.reduce((total, p) => total + p.amount, 0);
	}

	/**
   * 积分计算总数 分离出主函数
   * @returns
   */
	function totalVolumnCredits(data) {
		return data.performances.reduce((total, p) => total + p.volumnCredits, 0);
	}

	function playFor(aPerformance) {
		return plays[aPerformance.playID];
	}

	/**
   * refactor：extract function
   * 计算单个剧本演出的费用
   * @param {剧本} play
   * @param {表演秀：包含多个剧本} perf
   * @returns   表演秀中某个剧本的费用
   */
	function amountFor(aPerformance) {
		//refactor:rename param and func_name
		//refactor:条件选择转多态
		return new PerformanceCalculator(aPerformance, playFor(aPerformance)).amount;
	}

	/**
   * 计算积分
   * @param {*} aPerformance
   * @returns
   */
	function volumnCreditsFor(aPerformance) {
		let result = 0;
		result += Math.max(aPerformance.audience - 30, 0); //基本积分
		//戏剧特别加分
		if ('comedy' === aPerformance.play.type) {
			result += Math.floor(aPerformance.audience / 5);
		}
		return result;
	}
}

exports.createStatementData = createStatementData;
