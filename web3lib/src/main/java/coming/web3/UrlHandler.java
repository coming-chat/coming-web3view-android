package coming.web3;

import android.net.Uri;

public interface UrlHandler {

    String getHostPath();

    String handle(Uri uri);

    Boolean isIntercept();
}