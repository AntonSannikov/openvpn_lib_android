package de.blinkt.openvpn.notifiers;

import androidx.annotation.Nullable;


public class OpenVpnConnectionStateNotifier {

    @Nullable
    private static OpenVpnConnectionStateListener _listener;

    public static void notify(String state) {
        if (_listener != null) {
            _listener.onEvent(state);
        }
    }

    public static void setListener(OpenVpnConnectionStateListener listener) {
        _listener = listener;
    }
}


