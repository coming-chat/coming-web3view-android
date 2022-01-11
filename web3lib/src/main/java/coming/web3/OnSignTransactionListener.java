package coming.web3;
import coming.web3.enity.webview.Web3Transaction;

public interface OnSignTransactionListener {
    void onSignTransaction(Web3Transaction transaction, String url);
}
