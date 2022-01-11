package coming.web3.enity.repository;

public interface HomeCommsInterface
{
    void downloadReady(String ready);
    void requestNotificationPermission();
    void backupSuccess(String keyAddress);
    void resetTokens();
    void changedLocale();
    void resetTransactions();
    void openWalletConnect(String sessionId);
}
