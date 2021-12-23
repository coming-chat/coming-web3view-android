package coming.web3view;

import static coming.web3.util.Utils.formatUrl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import coming.web3.C;
import coming.web3.OnEthCallListener;
import coming.web3.OnSignMessageListener;
import coming.web3.OnSignPersonalMessageListener;
import coming.web3.OnSignTransactionListener;
import coming.web3.OnSignTypedMessageListener;
import coming.web3.OnWalletAddEthereumChainObjectListener;
import coming.web3.Web3View;
import coming.web3.enity.Address;
import coming.web3.enity.EthereumMessage;
import coming.web3.enity.EthereumTypedMessage;
import coming.web3.enity.URLLoadInterface;
import coming.web3.enity.WalletAddEthereumChainObject;
import coming.web3.enity.Web3Call;
import coming.web3.enity.Web3Transaction;
import coming.web3.util.Hex;
import trust.web3jprovider.BuildConfig;


public class MainActivity extends AppCompatActivity implements
        OnSignTransactionListener, OnSignPersonalMessageListener, OnSignTypedMessageListener,
        OnSignMessageListener, URLLoadInterface, OnWalletAddEthereumChainObjectListener, OnEthCallListener {

    private TextView url;
    private Web3View web3;
    private String loadOnInit; //Web3 needs to be fully set up and initialised before any dapp loading can be done
    // These two members are for loading a Dapp with an associated chain change.
    // Some multi-chain Dapps have a watchdog thread that checks the chain
    // This thread stays in operation until a new page load is complete.
    private String loadUrlAfterReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = findViewById(R.id.url);
        web3 = findViewById(R.id.web3view);
        findViewById(R.id.go).setOnClickListener(v -> {
            web3.loadUrl(url.getText().toString());
            web3.requestFocus();
        });
        setupWeb3();

    }

    private void setupWeb3() {
        web3.init();
        web3.setChainId(1);
        web3.setRpcUrl("https://mainnet.infura.io/v3/30c277db0eaa4085ac32ced784bc9af9");
        // 设置钱包地址
        web3.setWalletAddress(new Address("0x4e7E43067C1d896361618190D21b64F3665E5111"));
        web3.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String[] prefixCheck = url.split(":");
                if (prefixCheck.length > 1)
                {
                    Intent intent;
                    switch (prefixCheck[0])
                    {
                        case C.DAPP_PREFIX_TELEPHONE:
                            intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(url));
                            startActivity(Intent.createChooser(intent, "Call " + prefixCheck[1]));
                            return true;
                        case C.DAPP_PREFIX_MAILTO:
                            intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse(url));
                            startActivity(Intent.createChooser(intent, "Email: " + prefixCheck[1]));
                            return true;
                        case C.DAPP_PREFIX_ALPHAWALLET:
                            if(prefixCheck[1].equals(C.DAPP_SUFFIX_RECEIVE)) {
                                return true;
                            }
                            break;
                        case C.DAPP_PREFIX_WALLETCONNECT:
                            //start walletconnect
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        web3.setWebLoadCallback(this);
        web3.setOnSignMessageListener(this);
        web3.setOnSignPersonalMessageListener(this);
        web3.setOnSignTransactionListener(this);
        web3.setOnSignTypedMessageListener(this);
        web3.setOnEthCallListener(this);
        web3.setOnWalletAddEthereumChainObjectListener(this);
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        web3.resetView();
        web3.loadUrl("https://comfuture-web3.coming.chat/details/cid=500000096");
        web3.reload();

    }

    @Override
    public void onSignTransaction(Web3Transaction transaction,String url) {
        String str = new StringBuilder()
                .append(transaction.recipient == null ? "" : transaction.recipient.toString()).append(" : ")
                .append(transaction.contract == null ? "" : transaction.contract.toString()).append(" : ")
                .append(transaction.value.toString()).append(" : ")
                .append(transaction.gasPrice.toString()).append(" : ")
                .append(transaction.gasLimit).append(" : ")
                .append(transaction.nonce).append(" : ")
                .append(transaction.payload).append(" : ")
                .toString();
        String transationPayloadData = Hex.hexToUtf8(transaction.payload.replaceAll(
                "0x",""));
        // 拿到data字段转成json，获取signature字段
         Toast.makeText(this, transationPayloadData, Toast.LENGTH_LONG).show();
        // 调用walletsdk对signature交易原文进行签名todo
        // 提交交易todo
        // 交易成功回调
        web3.onSignError(transaction,transaction.payload);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSignMessage(EthereumMessage message) {
        Toast.makeText(getApplicationContext(),message.getMessage(),Toast.LENGTH_LONG);
    }

    @Override
    public void onSignPersonalMessage(EthereumMessage message) {
        Toast.makeText(getApplicationContext(),message.getMessage(),Toast.LENGTH_LONG);
    }

    @Override
    public void onSignTypedMessage(EthereumTypedMessage message) {
        Toast.makeText(getApplicationContext(),message.getMessage(),Toast.LENGTH_LONG);
    }

    @Override
    public void onWebpageLoaded(String url, String title) {

    }

    @Override
    public void onWebpageLoadComplete() {
        Toast.makeText(getApplicationContext(),"页面加载结束",Toast.LENGTH_LONG);
        web3.setWalletAddress(new Address("0x4e7E43067C1d896361618190D21b64F3665E5111"));

    }

    @Override
    public void onEthCall(Web3Call txdata) {
        Toast.makeText(getApplicationContext(),"eth_call",Toast.LENGTH_LONG);
    }

    @Override
    public void onWalletAddEthereumChainObject(WalletAddEthereumChainObject chainObject) {

    }
}
