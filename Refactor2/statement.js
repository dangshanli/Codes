const fs = require('fs');

const invoice = JSON.parse(fs.readFileSync('./invoices.json'))[0];
const plays = JSON.parse(fs.readFileSync('./plays.json'));
const s = plays['hamlet'].type;
// console.log(s);
const result = statement(invoice, plays);
console.log(result);

/**
 * 打印账单详情:
 * - 计算价格
 * - 计算积分
 * @param {账单} invoice
 * @param {剧目} play
 */
function statement(invoice, plays) {
	const statementData = {};
	statementData.customer = invoice.customer;
	statementData.performances = invoice.performances;
	return renderPlainText(statementData, plays);
}

function renderPlainText(data, plays) {
	let result = `statement for ${data.customer}\n`; //账单客户

	//遍历账单 循环一次
	for (let perf of data.performances) {
		result += `${playFor(perf).name}:${usd(amountFor(perf))} (${perf.audience} seats)\n`;
	}

	//refactor:将积分计算拆出来 经过refactor 一个循环拆成了3次
	result += `Amount owned is ${usd(totalAmount())}\n`; //内联变量 全部金额 循环一次
	result += `you earned ${totalVolumnCredits()} credits\n`; //内联变量 全部积分 循环一次
	return result;

	/**
   * 计算总价格
   * @returns
   */
	function totalAmount() {
		let result = 0;
		for (let perf of data.performances) {
			result += amountFor(perf);
		}
		return result;
	}

	/**
   * 积分计算总数 分离出主函数
   * @returns
   */
	function totalVolumnCredits() {
		let volumnCredits = 0;
		for (let perf of invoice.performances) {
			volumnCredits += volumnCreditsFor(perf);
		}
		return volumnCredits;
	}

	/**
   * 计算积分
   * @param {*} aPerformance
   * @returns
   */
	function volumnCreditsFor(aPerformance) {
		let result = 0;
		result += Math.max(aPerformance.audience - 30, 0); //基本积分
		if ('comedy' === playFor(aPerformance).type) {
			result += Math.floor(aPerformance.audience / 5);
		}
		return result;
	}

	/**
   * 内联函数
   * @param {*} aPerformance
   * @returns
   */
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
		switch (playFor(aPerformance).type) {
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
				throw new Error(`unknow type:${playFor(aPerformance).type}`);
		}
		return result;
	}

	/**
   * 格式化输出现金数值
   * @param {number 美分} aNumber
   * @returns
   */
	function usd(aNumber) {
		return new Intl.NumberFormat('en-US', {
			style: 'currency',
			currency: 'USD',
			minimunFractionDigits: 2
		}).format(aNumber / 100);
	}
}
