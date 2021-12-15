const fs = require('fs');
const http = require('http');
const port = 3050;

const invoice = JSON.parse(fs.readFileSync('./invoices.json'))[0];
const plays = JSON.parse(fs.readFileSync('./plays.json'));
const s = plays['hamlet'].type;
// console.log(s);
const result = statement(invoice, plays);
console.log(result);

const server = http.createServer((req, res) => {
	console.log(`url:${req.url}`);
	let data = '';
	if (req.url.includes('txt')) {
		res.statusCode = 200;
		res.setHeader('Content-Type', 'text/plain;charset=utf-8');
		res.setHeader('Access-Control-Allow-Origin', '*');
		res.end(`${result}`);
	} else if (req.url.includes('htm')) {
		res.statusCode = 200;
		res.setHeader('Content-Type', 'text/html;charset=utf-8');
		res.setHeader('Access-Control-Allow-Origin', '*');
		const im = 'https://img.zcool.cn/community/0106a25a06fcb1a801204a0e25ce4b.jpg@1280w_1l_2o_100sh.jpg';
		const out = "<h1 style='color:red'>你好世界</h1>" +
		 `<img src='${im}' width='200px'/>` + 
		 '<br/>' +
		 `<p style='color:purple'>&emsp;&emsp;&emsp;鸡公煲</p>`;
		res.end(`${out}`);
	} else {
		res.statusCode = 200;
		res.setHeader('Content-Type', 'text/plain;charset=utf-8');
		res.setHeader('Access-Control-Allow-Origin', '*');
		res.end(`你好世界\n`);
	}
});

server.listen(port, () => {
	console.log(`服务器运行在http://127.0.0.1:${port}/`);
});

/**
 * 打印账单详情:
 * - 计算价格
 * - 计算积分
 * @param {账单} invoice
 * @param {剧目} play
 */
function statement(invoice, plays) {
	const statementData = {}; //中间层数据，相关的计算都移到中间层，renderPlainText只做文本渲染功能
	statementData.customer = invoice.customer;
	statementData.performances = invoice.performances.map(enrichPerformance);
	statementData.totalAmount = totalAmount(statementData);
	statementData.totalVolumnCredits = totalVolumnCredits(statementData);
	return renderPlainText(statementData);

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
