package coming.web3.enity.repository;


import coming.web3.enity.webview.Signable;

public interface DAppFunction
{
    void DAppError(Throwable error, Signable message);
    void DAppReturn(byte[] data, Signable message);
}
