
/**
 * 打印账单详情
 * @param {账单} invoice 
 * @param {剧目} play 
 */
function statement(invoice, plays) {
    let totalAmount = 0;
    let volumnCredits = 0;

    let result = `statement for ${invoice.customer}\n`;//账单客户
    const format = new Intl.NumberFormat( //打印格式
        'zh-CN',
        {
            style: 'currency',
            currency: 'USD',
            minimunFractionDigits: 2
        }).format;

    //遍历账单
    for (let perf of invoice.performances) {
        const play = plays[perf.playID];
        let thisAmount = 0;
        //基本费用
        switch (play.type) {
            case "tragedy"://根据剧本类型计算总价格
                thisAmount = 40000;
                if (perf.audience > 30) {
                    thisAmount += 1000 * (perf.audience - 30);
                }
                break;
            case "comedy":
                thisAmount = 30000;
                if (perf.audience > 20) {
                    thisAmount += 10000 + 500 * (perf.audience - 20);
                }
                thisAmount += 300 * perf.audience;
                break;
            default:
                throw new Error(`unknow type:${play.type}`);
        }
        volumnCredits += Math.max(perf.audience - 30, 0);//基本积分
        if ('comedy' == play.type) {//喜剧额外积分
            volumnCredits += Math.floor(perf.audience / 5);
        }

        result += `${play.name}:${format(thisAmount / 100)} (${perf.audience} seats)\n`;
        totalAmount += thisAmount;
    }
    result += `Amount owned is ${format(totalAmount / 100)}\n`
    result += `you earned ${volumnCredits} credits\n`;
    return result;
}

let flag = false;
function hello() {
    if (flag) {
        console.log('hello');
        flag = false;
    } else {
        console.log('world');
        flag = true;
    }

}

hello();
hello();

let isDoing = true;
let count = 0;
while (isDoing) {
    hello();
    count++;
    if (count === 100) {
        console.log(`count=${count}`);
        process.exit(1);//编程界面退出程序
    }
}