package de.blinkt.openvpn.notifiers;

public interface OpenVpnLogListener {
    void onEvent(String message);
}
