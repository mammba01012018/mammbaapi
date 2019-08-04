package src.main.java.mammba.core.util;

/**
 * This interface serves as the repository of all error messages.
 *
 * @author Mardolfh Del Rosario
 *
 */
public interface ErrorMessage {

    //LoginServiceImpl errors
    String LOG_ERR_INVLD_LOGIN = "Invalid User login";
    String LOG_ERR_UPDT_PWD = "Unable to update User password.";

    //
    String PROFILE_ERR_NO_USR_TYP = "No user type exists.";
    String PROFILE_ERR_MBR_NULL = "Member cannot be null";
    String PROFILE_ERR_REG_ERR = "Error register User";
    String PROFILE_ERR_MBR_PWD = "Member did not follow password format.";
    String PROFILE_ERR_MBR_DTL_LOAD = "Unable to get Member details.";
    String PROFILE_ERR_PRTNR_DTL_LOAD = "Unable to get Partner details.";
    String PROFILE_ERR_USR_EXST = "Username already exist!";
    String PROFILE_ERR_ACS_DTA = "Error Accessing Data.";
    String PROFILE_ERR_EML_EXST = "Email is already registered! Please use a different email.";
    String PROFILE_ERR_MBL_REG = "Mobile number already registered!";
    String PROFILE_ERR_UPDT_MEM = "Error Updating Member: ";
    String PROFILE_ERR_EMAIL_SEND = "Email does not exists.";
    String PROFILE_ERR_EMAIL_FAIL = "There is something wrong in the email.";

    //SecurityQuestionServiceImpl errors
    String SQSTN_NO_QSTN = "No questions found.";
    String SQSTN_ERR_LOAD = "Unable to load questions.";
    String SQSTN_ERR_ANS = "Unable to validate answer for user.";
    String SQSTN_INC_QA = "Incomplete Security Question Parameters.";

}
