package de.blinkt.openvpn.notifiers;

public interface OpenVpnConnectionNetstatListener {
    void onEvent(String byteIn, String byteOut);
}
