package technikal.task.fishmarket.services;

public class ShopUserAlreadyExistsException extends RuntimeException {
    public ShopUserAlreadyExistsException(String message) {
        super(message);
    }
}
