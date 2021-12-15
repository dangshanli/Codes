/**
 * 
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
		const result = Object.assign({}, aPerformance);
		result.play = playFor(result);
		result.amount = amountFor(result);
		result.volumnCredits = volumnCreditsFor(result);
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
		let result = 0;
		switch (aPerformance.play.type) {
			case 'tragedy': //根据剧本类型计算总价格
				result = 40000;
				if (aPerformance.audience > 30) {
					result += 1000 * (aPerformance.audience - 30);
				}
				break;
			case 'comedy':
				result = 30000;
				if (aPerformance.audience > 20) {
					result += 10000 + 500 * (aPerformance.audience - 20);
				}
				result += 300 * aPerformance.audience;
				break;
			default:
				throw new Error(`unknow type:${aPerformance.play.type}`);
		}
		return result;
	}

	/**
   * 计算积分
   * @param {*} aPerformance
   * @returns
   */
	function volumnCreditsFor(aPerformance) {
		let result = 0;
		result += Math.max(aPerformance.audience - 30, 0); //基本积分
		if ('comedy' === aPerformance.play.type) {
			result += Math.floor(aPerformance.audience / 5);
		}
		return result;
	}
}

exports.createStatementData = createStatementData;
