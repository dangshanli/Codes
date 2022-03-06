package basic.server;

import basic.gen.Calculator;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class JavaServer {
    static CalculatorHandler handler = null;
    static Calculator.Processor processor = null;

    public static void main(String[] args) throws TTransportException {

        handler = new CalculatorHandler();//处理器 业务方法的实现包装类
        processor = new Calculator.Processor(handler);
        Runnable simple = () -> {
            simpleInvoke(processor);
        };

        Runnable secure = () -> {
            secureInvoke(processor);
        };

        new Thread(simple).start();
        new Thread(secure).start();


    }

    private static void simpleInvoke(Calculator.Processor processor) {
        try {
            //服务端传输层
            TServerTransport serverTransport = new TServerSocket(9097);
            //服务端处理器
            TServer tServer = new TSimpleServer(
                    new TServer.Args(serverTransport).processor(processor));
            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("开启简单服务，单线程阻塞");
            tServer.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    // TODO: 2022/3/2 SSL服务端还有些问题 Received fatal alert: handshake_failure错误
    static void secureInvoke(Calculator.Processor processor) {
        try {
            /*
             * Use TSSLTransportParameters to setup the required SSL parameters. In this example
             * we are setting the keystore and the keystore password. Other things like algorithms,
             * cipher suites, client auth etc can be set.
             */
            TSSLTransportFactory.TSSLTransportParameters params = new TSSLTransportFactory.TSSLTransportParameters();
            // The Keystore contains the private key
            params.setKeyStore("D:\\Codes\\Claptrap\\ThriftFeature\\src\\main\\resources\\server.keystore",
                    "123456", null, null);

            /*
             * Use any of the TSSLTransportFactory to get a server transport with the appropriate
             * SSL configuration. You can use the default settings if properties are set in the command line.
             * Ex: -Djavax.net.ssl.keyStore=.keystore and -Djavax.net.ssl.keyStorePassword=thrift
             *
             * Note: You need not explicitly call open(). The underlying server socket is bound on return
             * from the factory class.
             */
            TServerTransport serverTransport = TSSLTransportFactory.getServerSocket(9091, 0, null, params);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            // Use this for a multi threaded server
            // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the secure server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
