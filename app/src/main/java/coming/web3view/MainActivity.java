package coming.web3view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import coming.web3.OnConnectListener;
import coming.web3.OnSignMessageListener;
import coming.web3.OnSignPersonalMessageListener;
import coming.web3.OnSignTransactionListener;
import coming.web3.OnSignTypedMessageListener;
import coming.web3.Web3View;
import coming.web3.enity.Address;
import coming.web3.enity.EthereumMessage;
import coming.web3.enity.EthereumTypedMessage;
import coming.web3.enity.UserInfo;
import coming.web3.enity.Web3Transaction;
import coming.web3.util.Hex;
import coming.web3.widget.AWalletAlertDialog;
import trust.web3jprovider.BuildConfig;


public class MainActivity extends AppCompatActivity implements
        OnSignTransactionListener, OnSignPersonalMessageListener, OnSignTypedMessageListener, OnSignMessageListener , OnConnectListener {

    private TextView url;
    private Web3View web3;
    private AWalletAlertDialog resultDialog;

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

        web3.setWalletAddress(new Address("0x0000000000000000000000000000000000000000"));

        web3.setUserInfo(new UserInfo("9999999", "https://", "5ExtR7hfFRUYnooz9T5WdDUHgXEmggKWt3ei6JrGDPL3NtXx"));

        //
        web3.setOnSignMessageListener(this);
        web3.setOnSignPersonalMessageListener(this);
        web3.setOnSignTransactionListener(this);
        web3.setOnSignTypedMessageListener(this);
        // web3 连接钱包，请求授权
        web3.setOnConnectListener(this);
    }

    @Override
    public void onConnect() {
        Toast.makeText(getApplicationContext(),"连接钱包",Toast.LENGTH_LONG).show();
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
        web3.onSignSuccess(transaction.leafPosition,"成功");

        // 交易失败：  web3.onSignError(transaction,"失败");
        // 用户取消交易: web3.onSignCancel(transaction, "cancel")
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSignMessage(EthereumMessage message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
        web3.onSignError(message,"交易失败");
    }

    @Override
    public void onSignPersonalMessage(EthereumMessage message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignTypedMessage(EthereumTypedMessage message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void onInvalidTransaction(Web3Transaction transaction)
    {
        resultDialog = new AWalletAlertDialog(this);
        resultDialog.setIcon(AWalletAlertDialog.ERROR);
        resultDialog.setTitle(getString(R.string.invalid_transaction));

        if (transaction.recipient.equals(Address.EMPTY) && (transaction.payload == null || transaction.value != null))
        {
            resultDialog.setMessage(getString(R.string.contains_no_recipient));
        }
        else if (transaction.payload == null && transaction.value == null)
        {
            resultDialog.setMessage(getString(R.string.contains_no_value));
        }
        else
        {
            resultDialog.setMessage(getString(R.string.contains_no_data));
        }
        resultDialog.setButtonText(R.string.button_ok);
        resultDialog.setButtonListener(v -> {
            resultDialog.dismiss();
        });
        resultDialog.setCancelable(true);
        resultDialog.show();
    }

}
