package coming.web3.enity.repository;

public class ServiceException extends Exception {
	public final Object error;

	public ServiceException(String message) {
		super(message);

		error = new ErrorEnvelope(message);
	}
}
