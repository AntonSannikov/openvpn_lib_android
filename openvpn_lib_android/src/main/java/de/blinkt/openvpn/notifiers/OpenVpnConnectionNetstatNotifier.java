package de.blinkt.openvpn.notifiers;

import androidx.annotation.Nullable;

public class OpenVpnConnectionNetstatNotifier {

    @Nullable
    private static OpenVpnConnectionNetstatListener _listener;

    public static void notify(String byteIn, String byteOut) {
        if (_listener != null) {
            _listener.onEvent(byteIn, byteOut);
        }
    }

    public static void setListener(OpenVpnConnectionNetstatListener listener) {
        _listener = listener;
    }
}


