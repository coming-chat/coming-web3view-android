package coming.web3.enity.webview;

import coming.web3.enity.repository.Operation;

/**
 * Created by James on 9/06/2019.
 * Stormbird in Sydney
 */

public interface AuthenticationCallback
{
    void authenticatePass(Operation callbackId);
    void authenticateFail(String fail, AuthenticationFailType failType, Operation callbackId);
    void legacyAuthRequired(Operation callbackId, String dialogTitle, String desc);
}