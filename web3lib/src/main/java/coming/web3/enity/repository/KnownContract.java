package coming.web3.enity.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KnownContract {

    @SerializedName("MainNet")
    @Expose
    private final List<UnknownToken> mainNet = null;

    @SerializedName("xDAI")
    @Expose
    private final List<UnknownToken> xDAI = null;

    public List<UnknownToken> getMainNet() {
        return mainNet;
    }

    public List<UnknownToken> getXDAI() {
        return xDAI;
    }
}