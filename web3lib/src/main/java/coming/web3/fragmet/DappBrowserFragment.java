package coming.web3.fragmet;

import androidx.fragment.app.Fragment;

import coming.web3.OnEthCallListener;
import coming.web3.OnSignMessageListener;
import coming.web3.OnSignPersonalMessageListener;
import coming.web3.OnSignTransactionListener;
import coming.web3.OnSignTypedMessageListener;
import coming.web3.OnWalletAddEthereumChainObjectListener;
import coming.web3.enity.repository.URLLoadInterface;
import coming.web3.enity.webview.EthereumMessage;
import coming.web3.enity.webview.EthereumTypedMessage;
import coming.web3.enity.webview.WalletAddEthereumChainObject;
import coming.web3.enity.webview.Web3Call;
import coming.web3.enity.webview.Web3Transaction;

public class DappBrowserFragment extends Fragment implements OnSignTransactionListener, OnSignPersonalMessageListener,
        OnSignTypedMessageListener, OnSignMessageListener, OnEthCallListener, OnWalletAddEthereumChainObjectListener,
        URLLoadInterface {
    @Override
    public void onEthCall(Web3Call txdata) {

    }

    @Override
    public void onSignMessage(EthereumMessage message) {

    }

    @Override
    public void onSignPersonalMessage(EthereumMessage message) {

    }

    @Override
    public void onSignTransaction(Web3Transaction transaction, String url) {

    }

    @Override
    public void onSignTypedMessage(EthereumTypedMessage message) {

    }

    @Override
    public void onWalletAddEthereumChainObject(WalletAddEthereumChainObject chainObject) {

    }

    @Override
    public void onWebpageLoaded(String url, String title) {

    }

    @Override
    public void onWebpageLoadComplete() {

    }
}
