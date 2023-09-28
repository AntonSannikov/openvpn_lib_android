package de.blinkt.openvpn;

public interface OpenVpnConnectionNetstatListener {
    void onEvent(String byteIn, String byteOut);
}
