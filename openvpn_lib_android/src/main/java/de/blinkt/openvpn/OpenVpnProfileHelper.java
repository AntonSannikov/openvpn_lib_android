package de.blinkt.openvpn;

import android.content.Context;
import android.os.RemoteException;
import java.io.IOException;
import java.io.StringReader;
import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ProfileManager;

public class OpenVpnProfileHelper {

    public static VpnProfile getVpnProfile(Context context, String config, String name, String username, String password) throws RemoteException {
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
