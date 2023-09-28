package de.blinkt.openvpn;

public class OpenVpnConnectionNetstatNotifier {
    private static OpenVpnConnectionNetstatListener _listener;

    public static void notify(String byteIn, String byteOut) {
        _listener.onEvent(byteIn, byteOut);
    }

    public static void setListener(OpenVpnConnectionNetstatListener listener) {
        _listener = listener;
    }
}


