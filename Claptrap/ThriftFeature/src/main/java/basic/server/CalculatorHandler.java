package basic.server;

import basic.gen.Calculator;
import basic.gen.InvalidOperation;
import basic.gen.SharedStruct;
import basic.gen.Work;
import org.apache.thrift.TException;

import java.util.HashMap;
import java.util.Map;

public class CalculatorHandler implements Calculator.Iface {
    Map<Integer, SharedStruct> log;

    public CalculatorHandler() {
        log = new HashMap<>();
    }

    @Override
    public void ping() throws TException {
        System.out.println("ping()");
    }

    @Override
    public int add(int num1, int num2) throws TException {
        System.out.printf("add(%s+%s)\n", num1, num2);
        return num1 + num2;
    }

    @Override
    public int calculate(int logid, Work w) throws InvalidOperation, TException {
        System.out.printf("calculate(%s,{%s,%s,%s})\n", logid, w.op, w.num1, w.num2);
        int val = 0;
        switch (w.getOp()) {
            case ADD:
                val = w.num1 + w.num2;
                break;
            case SUBTRACT:
                val = w.num1 - w.num2;
                break;
            case MULTIPLY:
                val = w.num1 * w.num2;
                break;
            case DIVIDE:
                if (w.num2 == 0) {
                    InvalidOperation io = new InvalidOperation();
                    io.whatOp = w.op.getValue();
                    io.why = "can't divide by 0";
                    throw io;
                }
                val = w.num1 / w.num2;
                break;
            default:
                throw new InvalidOperation(w.op.getValue(), "未知操作");
        }

        SharedStruct entry = new SharedStruct();
        entry.key = logid;
        entry.value = Integer.toBinaryString(val);
        log.put(logid,entry);

        return val;
    }

    @Override
    public void zip() throws TException {
        System.out.println("zip()");
    }

    @Override
    public SharedStruct getStruct(int key) throws TException {
        System.out.printf("getStruct( %s )\n",key);
        return log.get(key);
    }
}
