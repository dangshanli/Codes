package basic.client;

import basic.gen.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.*;
import org.apache.thrift.transport.layered.TFramedTransport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;

public class JavaClient {
    public static void main(String[] args) {
        TTransport transport = null;//定义传输层
        try {
            // 包装顺序 传输层->协议层->Client
//            TSSLTransportFactory.TSSLTransportParameters params = new TSSLTransportFactory.TSSLTransportParameters();
//            params.setTrustStore(
//                    "D:\\Codes\\Claptrap\\ThriftFeature\\src\\main\\resources\\server.keystore",
//                    "123456");
//            transport = TSSLTransportFactory.getClientSocket(
//                    "localhost", 9091, 0, params);
            transport = new TSocket("localhost", 9097);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);//协议层
            Calculator.Client client = new Calculator.Client(protocol);//客户端
            perform(client);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(transport))
                transport.close();
        }
    }

    TProtocol getCompProtocolByBlock(String host, int port) throws TTransportException {
        TTransport transport = new TSocket(host, port);
        transport.open();
        return new TCompactProtocol(transport);
    }

    TProtocol getCompProtocolByNonBlock(String host, int port) throws TTransportException, IOException {
        TTransport transport = new TNonblockingSocket(host, port);
        transport.open();
        return new TCompactProtocol(transport);
    }


    //执行远程方法
    private static void perform(Calculator.Client client) throws TException {
        client.ping();
        System.out.println("ping()");

        int sum = client.add(1, 1);
        System.out.println("1+1=" + sum);

        Work work = new Work();
        work.op = Operation.DIVIDE;
        work.num1 = 9;
        work.num2 = 3;

        try {
            int quotient = client.calculate(1, work);
            System.out.println("谁能够被0除");
        } catch (InvalidOperation e) {
            e.printStackTrace();
        }

        work.op = Operation.SUBTRACT;
        work.num1 = 15;
        work.num2 = 10;
        int diff = client.calculate(1, work);
        System.out.println("15-10=" + diff);

        SharedStruct log = client.getStruct(1);
        System.out.println("check log: " + log.value);
    }


}
