package example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {


    @Test
    void testConsMethod() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("www.taobao.com");

        System.out.println(address.getHostAddress());
        System.out.println(address.getHostName());
        System.out.println(address);

        System.out.println("--------------------------------");
        InetAddress add2 = InetAddress.getByName("106.227.20.233");
        System.out.println(add2.getHostName());
        System.out.println(add2.getHostAddress());
    }

    @Test
    void testAllNames() throws UnknownHostException {
        InetAddress[] addresses = InetAddress.getAllByName("www.taobao.com");
        for (int i = 0; i < addresses.length; i++) {
            System.out.println(addresses[i]);
        }

        System.out.println("--------------------------------");
        System.out.println(InetAddress.getLocalHost());
    }

    @Test
    void testGetAddress() throws UnknownHostException {
        byte[] address = {106, (byte) 227, 20, (byte) 233};
        InetAddress add = InetAddress.getByAddress(address);
//        printAddr(add);
        add = InetAddress.getByName("106.227.20.233");
//      printAddr(add);
        InetAddress.getByAddress("taobao.com", address);
        System.out.println("------");
        add = InetAddress.getLocalHost();
        printAddr(add);


    }

    void printAddr(InetAddress address) {
        System.out.println(address.getHostName());
        System.out.println(address.getHostAddress());
        System.out.println(address.getCanonicalHostName());
        System.out.println(address.getAddress().length);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "::",
            "0.0.0.0",
            "127.0.0.1",
            "192.168.254.32",
            "www.taobao.com",
            "224.0.2.1",
            "FF01::1",
            "FF05::101",
            "::1"
    })
    void testAddrType(String host){
        try {
            InetAddress address = InetAddress.getByName(host);
            System.out.println("host="+host);
            System.out.println("本地通配地址："+address.isAnyLocalAddress());
            System.out.println("回送地址："+address.isLoopbackAddress());
            System.out.println("本地连接地址 "+address.isLinkLocalAddress());
            System.out.println("本地网站地址 "+address.isSiteLocalAddress());
            System.out.println("多播地址 "+address.isMulticastAddress());
            System.out.println("全球多播 "+address.isMCGlobal());
            System.out.println("组织多播 "+address.isMCOrgLocal());
            System.out.println("网站多播 "+address.isMCSiteLocal());
            System.out.println("子网多播 "+address.isMCLinkLocal());
            System.out.println("本地节点多播 "+address.isMCNodeLocal());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }finally {
            System.out.println("--------------------------------");
        }
    }
}
