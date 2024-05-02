package jovelAsirot.YourHomeDB.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Record with id: " + id + " was not found ꜀( ˊ̠˂˃ˋ̠ )꜆.");
    }

    public NotFoundException(String msg) {
        super(msg);
    }

}
