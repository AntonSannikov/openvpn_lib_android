package de.blinkt.openvpn.notifiers;

public interface OpenVpnConnectionStateListener {
    void onEvent(String state);
}


