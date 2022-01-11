package coming.web3.enity.repository.walletconnect.entity;


import coming.web3.enity.repository.WCRequest;

/**
 * Created by JB on 6/10/2021.
 */
public interface WalletConnectCallback
{
    boolean receiveRequest(WCRequest request);
}
