package coming.web3;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class UrlHandlerManager {

    private final Map<String, UrlHandler> handlers = new HashMap<>();

    public UrlHandlerManager(UrlHandler... handlers) {
        for (UrlHandler urlHandler : handlers) {
            this.handlers.put(urlHandler.getSchemeSpecificPart(), urlHandler);
        }
    }

    public void add(@NonNull UrlHandler urlHandler) {
        this.handlers.put(urlHandler.getSchemeSpecificPart(), urlHandler);
    }

    public void remove(@NonNull UrlHandler urlHandler) {
        this.handlers.remove(urlHandler.getSchemeSpecificPart());
    }

    String handle(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Uri uri = Uri.parse(url);
        return handle(uri);
    }

    String handle(Uri uri) {
        if (uri == null) {
            return null;
        }
        if (!handlers.containsKey(uri.getSchemeSpecificPart())) {
            return uri.toString();
        }
        return getHandler(uri).handle(uri);
    }

    UrlHandler getHandler(Uri uri){
        if (uri == null) {
            return null;
        }
        return handlers.get(uri.getSchemeSpecificPart());
    }
}
