package coming.web3.fragmet;

import android.app.AlertDialog;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import coming.web3.OnEthCallListener;
import coming.web3.OnSignMessageListener;
import coming.web3.OnSignPersonalMessageListener;
import coming.web3.OnSignTransactionListener;
import coming.web3.OnSignTypedMessageListener;
import coming.web3.OnWalletAddEthereumChainObjectListener;
import coming.web3.Web3View;
import coming.web3.enity.repository.URLLoadInterface;
import coming.web3.enity.repository.Wallet;
import coming.web3.enity.repository.ethereum.EthereumNetworkInfo;
import coming.web3.enity.repository.realm.RealmToken;
import coming.web3.enity.webview.EthereumMessage;
import coming.web3.enity.webview.EthereumTypedMessage;
import coming.web3.enity.webview.WalletAddEthereumChainObject;
import coming.web3.enity.webview.Web3Call;
import coming.web3.enity.webview.Web3Transaction;
import coming.web3.widget.AWalletAlertDialog;
import coming.web3.widget.AddEthereumChainPrompt;
import coming.web3.widget.adapter.DappBrowserSuggestionsAdapter;
import coming.web3.widget.entity.DappBrowserSwipeLayout;
import io.realm.Realm;
import io.realm.RealmResults;

public class DappBrowserFragment extends Fragment implements OnSignTransactionListener, OnSignPersonalMessageListener,
        OnSignTypedMessageListener, OnSignMessageListener, OnEthCallListener, OnWalletAddEthereumChainObjectListener,
        URLLoadInterface {
    private static final String TAG = DappBrowserFragment.class.getSimpleName();
    private static final String DAPP_BROWSER = "DAPP_BROWSER";
    private static final String MY_DAPPS = "MY_DAPPS";
    private static final String DISCOVER_DAPPS = "DISCOVER_DAPPS";
    private static final String HISTORY = "HISTORY";
    public static final String SEARCH = "SEARCH";
    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";
    public static final String CURRENT_FRAGMENT = "currentFragment";
    public static final String DAPP_CLICK = "dapp_click";
    public static final String DAPP_REMOVE_HISTORY = "dapp_remove";
    private static final String CURRENT_URL = "urlInBar";
    private static final String WALLETCONNECT_CHAINID_ERROR = "Error: ChainId missing or not supported";
    private static final long MAGIC_BUNDLE_VAL = 0xACED00D;
    private static final String BUNDLE_FILE = "awbrowse";
    private ValueCallback<Uri[]> uploadMessage;
    private WebChromeClient.FileChooserParams fileChooserParams;
    private RealmResults<RealmToken> realmUpdate;
    private Realm realm = null;

    private DappBrowserSwipeLayout swipeRefreshLayout;
    private Web3View web3;
    private AutoCompleteTextView urlTv;
    private ProgressBar progressBar;
    private Wallet wallet;
    private EthereumNetworkInfo activeNetwork;
    private AWalletAlertDialog resultDialog;
    private DappBrowserSuggestionsAdapter adapter;
    private AlertDialog chainSwapDialog;
    private String loadOnInit; //Web3 needs to be fully set up and initialised before any dapp loading can be done
    private boolean homePressed;
    private AddEthereumChainPrompt addCustomChainDialog;
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
