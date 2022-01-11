package coming.web3.enity.repository;


import coming.web3.enity.webview.SignMessageType;
import coming.web3.enity.webview.Signable;

/**
 * Created by James on 30/01/2018.
 */

public class MessagePair implements Signable
{
    public final String selection;
    public final String message;

    public MessagePair(String selection, String message)
    {
        this.selection = selection;
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    // TODO: Question to JB: actually, do we add the prefix here?
    @Override
    public byte[] getPrehash() {
        return message.getBytes();
    }

    @Override
    public String getOrigin()
    {
        return null;
    }

    // TODO: I actually don't know where to return to … -Weiwu
    @Override
    public long getCallbackId() {
        return 0;
    }

    @Override
    public CharSequence getUserMessage() {
        return "";
    }

    @Override
    public SignMessageType getMessageType()
    {
        return null;
    }
}
