package coming.web3.enity.repository.opensea;

import coming.web3.enity.repository.ErrorEnvelope;

/**
 * Created by James on 20/12/2018.
 * Stormbird in Singapore
 */

public class OpenseaServiceError extends Exception {
    public final ErrorEnvelope error;

    public OpenseaServiceError(String message) {
        super(message);

        error = new ErrorEnvelope(message);
    }
}
