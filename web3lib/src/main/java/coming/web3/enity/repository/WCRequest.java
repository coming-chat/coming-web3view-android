package coming.web3.enity.repository;


import static coming.web3.enity.repository.ethereum.EthereumNetworkBase.MAINNET_ID;

import coming.web3.enity.repository.walletconnect.SignType;
import coming.web3.enity.repository.walletconnect.entity.WCEthereumSignMessage;
import coming.web3.enity.repository.walletconnect.entity.WCEthereumTransaction;
import coming.web3.enity.repository.walletconnect.entity.WCPeerMeta;

/**
 * Created by JB on 10/09/2020.
 */
public class WCRequest
{

    public final long id;
    public final String sessionId;
    public final WCEthereumTransaction tx;
    public final WCEthereumSignMessage sign;
    public final WCPeerMeta peer;
    public final SignType type;
    public final long chainId;
    public final Throwable throwable;

    public WCRequest(String sessionId, long id, WCEthereumSignMessage msg)
    {
        this.sessionId = sessionId;
        this.id = id;
        this.sign = msg;
        type = SignType.MESSAGE;
        tx = null;
        peer = null;
        chainId = MAINNET_ID;
        throwable = null;
    }

    public WCRequest(String sessionId, long id, WCEthereumTransaction tx, boolean signOnly, long chainId)
    {
        this.sessionId = sessionId;
        this.id = id;
        this.sign = null;
        type = signOnly ? SignType.SIGN_TX : SignType.SEND_TX;
        this.tx = tx;
        this.chainId = chainId;
        peer = null;
        throwable = null;
    }

    public WCRequest(String sessionId, long id, WCPeerMeta pm, long chainId)
    {
        this.sessionId = sessionId;
        this.id = id;
        this.sign = null;
        this.chainId = chainId;
        type = SignType.SESSION_REQUEST;
        tx = null;
        peer = pm;
        throwable = null;
    }

    public WCRequest(String sessionId, Throwable t, long chainId)
    {
        this.sessionId = sessionId;
        this.id = 0;
        this.sign = null;
        this.chainId = chainId;
        type = SignType.FAILURE;
        tx = null;
        peer = null;
        throwable = t;
    }
}
