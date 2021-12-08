package coming.web3.enity;

public interface Signable {
    String getMessage();
    long getCallbackId();
    byte[] getPrehash();
    String getOrigin();
    CharSequence getUserMessage();
    SignMessageType getMessageType();
}
