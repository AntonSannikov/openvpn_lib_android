package de.blinkt.openvpn;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.StringReader;
import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ProfileManager;
import de.blinkt.openvpn.listeners.OpenVpnConnectionNetstatListener;
import de.blinkt.openvpn.listeners.OpenVpnConnectionStateListener;
import de.blinkt.openvpn.listeners.OpenVpnListener;
import de.blinkt.openvpn.listeners.OpenVpnLogListener;


public class OpenVpnApi {
    public enum Notifier { CONNECTION_STATE, NETSTAT, LOG }

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    private static OpenVpnConnectionStateListener _stateListener;
    @Nullable
    private static OpenVpnConnectionNetstatListener _netstatListener;
    @Nullable
    private static OpenVpnLogListener _logListener;


    public static void notify(String[] args, Notifier notifierType) {
        switch (notifierType) {
            case CONNECTION_STATE:
                if (_stateListener != null) {
                    mainHandler.post(() -> { _stateListener.onEvent(args[0]); });
                }
                break;
            case NETSTAT:
                if (_netstatListener != null) {
                    mainHandler.post(() -> { _netstatListener.onEvent(args[0], args[1]); });
                }
                break;
            case LOG:
                if (_logListener != null) {
                    mainHandler.post(() -> { _logListener.onEvent(args[0]); });
                }
                break;
        }
    }

    public static <T extends  OpenVpnListener> void setListener(T listener) {
        if (listener instanceof OpenVpnConnectionStateListener) {
            _stateListener = (OpenVpnConnectionStateListener) listener;
        } else if (listener instanceof OpenVpnConnectionNetstatListener) {
            _netstatListener = (OpenVpnConnectionNetstatListener) listener;
        } else if (listener instanceof OpenVpnLogListener) {
            _logListener = (OpenVpnLogListener) listener;
        }
    }



    public VpnProfile createVpnProfile(Context context, String config, String name, String username, String password) throws RemoteException {
        try {
            ConfigParser cp = new ConfigParser();
            cp.parseConfig(new StringReader(config));
            VpnProfile vp = cp.convertProfile();
            vp.mName = name;
            if (vp.checkProfile(context) != de.blinkt.openvpn.R.string.no_error_found) {
                throw new RemoteException(context.getString(vp.checkProfile(context)));
            }
            vp.mProfileCreator = context.getPackageName();
            vp.mUsername = username;
            vp.mPassword = password;
            ProfileManager.setTemporaryProfile(context, vp);
            return vp;
        } catch (IOException | ConfigParser.ConfigParseError e) {
            throw new RemoteException(e.getMessage());
        }

    }

}
