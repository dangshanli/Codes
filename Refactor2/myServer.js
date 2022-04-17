/**
 * 服务器
 */
const http = require('http');
const render = require('./statement');
const readFileSync = require('./utils').readJsonFile;
const port = 3050;

const invoice = readFileSync('./invoices.json')[0];
const plays = readFileSync('./plays.json');
const server = http.createServer((req, res) => {
	console.log(`url:${req.url}`);
	if (req.url.includes('renderText')) {
		res.statusCode = 200;
		res.setHeader('Content-Type', 'text/plain;charset=utf-8');
		res.setHeader('Access-Control-Allow-Origin', '*');
		res.end(`${render.statement(invoice, plays)}`);
	} else if (req.url.includes('renderHtml')) {
		res.statusCode = 200;
		res.setHeader('Content-Type', 'text/html;charset=utf-8');
		res.setHeader('Access-Control-Allow-Origin', '*');
		res.end(`${render.htmlStatement(invoice, plays)}`);
	} else {
		res.statusCode = 200;
		res.setHeader('Content-Type', 'text/html;charset=utf-8');
		res.setHeader('Access-Control-Allow-Origin', '*');
		const im = 'https://img.zcool.cn/community/0106a25a06fcb1a801204a0e25ce4b.jpg@1280w_1l_2o_100sh.jpg';
		const out =
			"<h1 style='color:red'>你好世界</h1>" +
			`<img src='${im}' width='200px'/>` +
			'<br/>' +
			`<p style='color:purple'>&emsp;&emsp;&emsp;鸡公煲</p>`;
		res.end(`${out}`);
	}
});
const os = require('os');
server.listen(port, () => {
	console.log(`当前进程:${process.pid}`);
	console.log(`服务器运行在127.0.0.1:${port}/`);
});
