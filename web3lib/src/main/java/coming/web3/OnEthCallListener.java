package coming.web3;


import coming.web3.enity.webview.Web3Call;

/**
 * Created by JB on 19/02/2021.
 */
public interface OnEthCallListener
{
    void onEthCall(Web3Call txdata);
}
