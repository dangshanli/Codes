/**
 * 渲染数据
 */
const createData = require('./createStatementData');
function htmlStatement(invoice, plays) {
	return renderHtml(createData.createStatementData(invoice, plays));
}

function renderHtml(data) {
	let result = `<h1 style='color:red'>Statement for ${data.customer}</h1>\n`;
	result += '<table/>\n';
	result += '<tr style="color:blue"><th>play</th><th>seats</th><th>cost</th></tr>';
	for (let perf of data.performances) {
		result += `<tr><td>${perf.play.name}</td>`;
		result += `<td>${perf.audience}</td>`;
		result += `<td>${usd(perf.amount)}</td></tr>\n`;
	}
	result += '</table>\n';
	result += `<p>Amount owned is <em>${usd(data.totalAmount)}</em></p>\n`;
	result += `<p>You earned <em>${data.totalVolumnCredits}</em> credits</p>\n`;
	return result;
}

/**
 * 打印账单详情:
 * - 计算价格
 * - 计算积分
 * @param {账单} invoice
 * @param {剧目} play
 */
function statement(invoice, plays) {
	return renderPlainText(createData.createStatementData(invoice, plays));
}

function renderPlainText(data) {
	let result = `statement for ${data.customer}\n`; //账单客户

	//遍历账单 循环一次
	for (let perf of data.performances) {
		result += `${perf.play.name}:${usd(perf.amount)} (${perf.audience} seats)\n`;
	}

	//refactor:将积分计算拆出来 经过refactor 一个循环拆成了3次
	result += `Amount owned is ${usd(data.totalAmount)}\n`; //内联变量 全部金额 循环一次
	result += `you earned ${data.totalVolumnCredits} credits\n`; //内联变量 全部积分 循环一次
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

exports.statement = statement;
exports.htmlStatement = htmlStatement;
