package example;

import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.IconUIResource;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkInterfaceTest {

    @Test
    void testGetAll() {
        int counter = 0;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface n = networkInterfaces.nextElement();
                System.out.println(n);
//                System.out.println(n.getDisplayName() + "  -------------> " + n.getName());
                counter++;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
    }

    @Test
    void testByName() throws SocketException {
        NetworkInterface networkInterface = NetworkInterface.getByName("eth0");
        System.out.println(networkInterface.getDisplayName());
        System.out.println(networkInterface.getInterfaceAddresses());
    }
}
