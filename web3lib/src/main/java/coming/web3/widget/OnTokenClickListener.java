package coming.web3.widget;

import android.view.View;
import java.math.BigInteger;
import java.util.List;

import coming.web3.enity.repository.tokens.Token;

public interface OnTokenClickListener {
    void onTokenClick(View view, Token token, List<BigInteger> tokenIds, boolean selected);
    void onLongTokenClick(View view, Token token, List<BigInteger> tokenIds);
}
