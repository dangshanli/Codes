const fs = require('fs');
/**
 * 读取JSON格式的文件
 * 具体读出来的JSON是怎样格式 取决于源文件
 * 非JSON文件会报错
 * @param {*} path 
 * @returns 
 */
function readJsonFile(path) {
	return JSON.parse(fs.readFileSync(path));
}

exports.readJsonFile = readJsonFile;
