package de.blinkt.openvpn.notifiers;

import androidx.annotation.Nullable;


public class OpenVpnLogNotifier {

    @Nullable
    private static OpenVpnLogListener _listener;

    public static void notify(String state) {
        if (_listener != null) {
            _listener.onEvent(state);
        }
    }

    public static void setListener(OpenVpnLogListener listener) {
        _listener = listener;
    }
}
