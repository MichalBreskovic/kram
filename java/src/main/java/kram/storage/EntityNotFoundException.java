package kram.storage;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 71022948595665092L;

	public EntityNotFoundException(String message) {
		super(message);
	}

}