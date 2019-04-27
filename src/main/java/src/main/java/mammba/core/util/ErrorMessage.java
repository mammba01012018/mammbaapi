package src.main.java.mammba.core.util;

/**
 * This interface serves as the repository of all error messages.
 *
 * @author Mardolfh Del Rosario
 *
 */
public interface ErrorMessage {
    String PROFILE_ERR_ONE = "No user type exists.";
    String PROFILE_ERR_TWO = "Member cannot be null";
    String PROFILE_ERR_THREE = "Error register Mammba User";
    String PROFILE_ERR_FOUR = "Member has did not follow password format.";
    String PROFILE_ERR_FIVE = "Unable to get Member details.";
    String PROFILE_ERR_SIX = "Username already exist!";
    String PROFILE_ERR_SEVEN = "Error Accessing Data.";
    String PROFILE_ERR_EIGHT = "Email is already registered! Please use a different email.";
    String PROFILE_ERR_NINE = "Mobile number already registered!";
    String PROFILE_ERR_TEN = "Error Updating Member: ";
}
