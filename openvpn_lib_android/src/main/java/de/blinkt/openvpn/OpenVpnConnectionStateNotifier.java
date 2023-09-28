package de.blinkt.openvpn;

public class OpenVpnConnectionStateNotifier {
    private static OpenVpnConnectionStateListener _listener;

    public static void notify(String state) {
        _listener.onEvent(state);
    }

    public static void setListener(OpenVpnConnectionStateListener listener) {
        _listener = listener;
    }
}


