package coming.web3view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import coming.web3.OnSignMessageListener;
import coming.web3.OnSignPersonalMessageListener;
import coming.web3.OnSignTransactionListener;
import coming.web3.OnSignTypedMessageListener;
import coming.web3.Web3View;
import coming.web3.enity.Address;
import coming.web3.enity.EthereumMessage;
import coming.web3.enity.EthereumTypedMessage;
import coming.web3.enity.Web3Transaction;
import coming.web3.util.Hex;
import trust.web3jprovider.BuildConfig;


public class MainActivity extends AppCompatActivity implements
        OnSignTransactionListener, OnSignPersonalMessageListener, OnSignTypedMessageListener, OnSignMessageListener {

    private TextView url;
    private Web3View web3;

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
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        web3.setChainId(1);
        web3.loadUrl("https://comfuture-web3.coming.chat/details/cid=500000096");
        web3.setRpcUrl("https://mainnet.infura.io/v3/30c277db0eaa4085ac32ced784bc9af9");
        // 设置钱包地址
        web3.setWalletAddress(new Address("0x4e7E43067C1d896361618190D21b64F3665E5111"));
        web3.setCid("666666");

        //
        web3.setOnSignMessageListener(this);
        web3.setOnSignPersonalMessageListener(this);
        web3.setOnSignTransactionListener(this);
        web3.setOnSignTypedMessageListener(this);
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
        web3.onSignSuccess(transaction.leafPosition,transationPayloadData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSignMessage(EthereumMessage message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignPersonalMessage(EthereumMessage message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignTypedMessage(EthereumTypedMessage message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
    }
}
