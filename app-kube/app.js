const http = require('http');
const os = require('os');

console.log('kubia server starting...');
var handler = function(req, res) {
	console.log('recieved request from ' + req.connection.remoteAddress);
	res.writeHead(200);
	res.end("you've hit" + os.hostname() + '\n');
};
var www = http.createServer(handler);
www.listen(8034);
