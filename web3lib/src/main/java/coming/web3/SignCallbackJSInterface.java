package coming.web3;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.math.BigInteger;

import coming.web3.enity.webview.Address;
import coming.web3.enity.webview.CryptoFunctions;
import coming.web3.enity.webview.EthereumMessage;
import coming.web3.enity.webview.EthereumTypedMessage;
import coming.web3.enity.webview.SignMessageType;
import coming.web3.enity.webview.WalletAddEthereumChainObject;
import coming.web3.enity.webview.Web3Call;
import coming.web3.enity.webview.Web3Transaction;
import coming.web3.util.Hex;
import coming.web3.util.Utils;

public class SignCallbackJSInterface
{
    private final WebView webView;
    @NonNull
    private final OnSignTransactionListener onSignTransactionListener;
    @NonNull
    private final OnSignMessageListener onSignMessageListener;
    @NonNull
    private final OnSignPersonalMessageListener onSignPersonalMessageListener;
    @NonNull
    private final OnSignTypedMessageListener onSignTypedMessageListener;
    @NonNull
    private final OnEthCallListener onEthCallListener;
    @NonNull
    private final OnWalletAddEthereumChainObjectListener onWalletAddEthereumChainObjectListener;

    private final OnConnectListener onConnectListener;
    public SignCallbackJSInterface(
            WebView webView,
            @NonNull OnSignTransactionListener onSignTransactionListener,
            @NonNull OnSignMessageListener onSignMessageListener,
            @NonNull OnSignPersonalMessageListener onSignPersonalMessageListener,
            @NonNull OnSignTypedMessageListener onSignTypedMessageListener,
            @NotNull OnEthCallListener onEthCallListener,
            @NonNull OnWalletAddEthereumChainObjectListener onWalletAddEthereumChainObjectListener,
            @NonNull OnConnectListener onConnectListener
            ) {
        this.webView = webView;
        this.onSignTransactionListener = onSignTransactionListener;
        this.onSignMessageListener = onSignMessageListener;
        this.onSignPersonalMessageListener = onSignPersonalMessageListener;
        this.onSignTypedMessageListener = onSignTypedMessageListener;
        this.onEthCallListener = onEthCallListener;
        this.onConnectListener = onConnectListener;
        this.onWalletAddEthereumChainObjectListener = onWalletAddEthereumChainObjectListener;
    }

    @JavascriptInterface
    public void signTransaction(
            int callbackId,
            String recipient,
            String value,
            String nonce,
            String gasLimit,
            String gasPrice,
            String payload) {
        if (value.equals("undefined") || value == null) value = "0";
        if (gasPrice == null) gasPrice = "0";
        Web3Transaction transaction = new Web3Transaction(
                TextUtils.isEmpty(recipient) ? Address.EMPTY : new Address(recipient),
                null,
                Hex.hexToBigInteger(value),
                Hex.hexToBigInteger(gasPrice, BigInteger.ZERO),
                Hex.hexToBigInteger(gasLimit, BigInteger.ZERO),
                Hex.hexToLong(nonce, -1),
                payload,
                callbackId);

        webView.post(() -> onSignTransactionListener.onSignTransaction(transaction, getUrl()));
    }

    @JavascriptInterface
    public void signMessage(int callbackId, String data) {
        webView.post(() -> onSignMessageListener.onSignMessage(new EthereumMessage(data, getUrl(), callbackId, SignMessageType.SIGN_MESSAGE)));
    }

    @JavascriptInterface
    public void signPersonalMessage(int callbackId, String data) {
        webView.post(() -> onSignPersonalMessageListener.onSignPersonalMessage(new EthereumMessage(data, getUrl(), callbackId, SignMessageType.SIGN_PERSONAL_MESSAGE)));
    }

    @JavascriptInterface
    public void signTypedMessage(int callbackId, String data) {
        webView.post(() -> {
            try
            {
                JSONObject obj = new JSONObject(data);
                String address = obj.getString("from");
                String messageData = obj.getString("data");
                CryptoFunctions cryptoFunctions = new CryptoFunctions();

                EthereumTypedMessage message = new EthereumTypedMessage(messageData, getDomainName(), callbackId, cryptoFunctions);
                onSignTypedMessageListener.onSignTypedMessage(message);
            }
            catch (Exception e)
            {
                EthereumTypedMessage message = new EthereumTypedMessage(null, "", getDomainName(), callbackId);
                onSignTypedMessageListener.onSignTypedMessage(message);
                if (BuildConfig.DEBUG) e.printStackTrace();
            }
        });
    }

    @JavascriptInterface
    public void ethCall(int callbackId, String recipient, String payload) {
        DefaultBlockParameter defaultBlockParameter;
        if (payload.equals("undefined")) payload = "0x";
        defaultBlockParameter = DefaultBlockParameterName.LATEST;

        Web3Call call = new Web3Call(
                new Address(recipient),
                defaultBlockParameter,
                payload,
                callbackId);

        webView.post(() -> onEthCallListener.onEthCall(call));
    }

    @JavascriptInterface
    public void walletAddEthereumChain(int callbackId, String msgParams) {
        //TODO: Implement custom chains from dapp browser: see OnWalletAddEthereumChainObject in class DappBrowserFragment
        //First draft: attempt to match this chain with known chains; switch to known chain if we match
        try
        {
            WalletAddEthereumChainObject chainObj = new Gson().fromJson(msgParams, WalletAddEthereumChainObject.class);
            if (!TextUtils.isEmpty(chainObj.chainId))
            {
                webView.post(() -> onWalletAddEthereumChainObjectListener.onWalletAddEthereumChainObject(chainObj));
            }
        }
        catch (JsonSyntaxException e)
        {
            if (BuildConfig.DEBUG) e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void connect(){
        webView.post(()->onConnectListener.onConnect());
    }

    private String getUrl() {
        return webView == null ? "" : webView.getUrl();
    }

    private String getDomainName() {
        return webView == null ? "" : Utils.getDomainName(webView.getUrl());
    }
}
