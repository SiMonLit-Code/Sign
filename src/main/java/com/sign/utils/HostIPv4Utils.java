package com.sign.utils;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 周志通
 * @version 1.0
 * @date 2020/5/29 17:39
 */
public class HostIPv4Utils {
    public static String getLocalIpv4Address() throws SocketException {
        List<String> ipList = new ArrayList<String>();
        try {
            System.out.println(InetAddress.getLocalHost());
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) {
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
            System.out.println(ipList.toString());
            System.out.println(ipList.get(ipList.size()-1));

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ipList.get(ipList.size()-1) ;
    }
}
