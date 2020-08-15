package botting.game.data;

/**
 * The bank withdraw options in the 317 client
 *
 * @author Youri Dudock
 */
public enum BankWithdraw {

    WITHDRAW_1("Withdraw-1"),
    WITHDRAW_5("Withdraw-5"),
    WITHDRAW_10("Withdraw-10"),
    WITHDRAW_ALL("Withdraw-ALL"),
    WITHDRAW_X("Withdraw-X");

    // text used to identify the option
    private String identifier;

    private BankWithdraw(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
