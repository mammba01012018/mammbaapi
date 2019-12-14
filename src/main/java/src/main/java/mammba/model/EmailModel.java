package src.main.java.mammba.model;

public class EmailModel {
    private String toRecipient;
    private String bodyMessage;
    private String subject;

    public String getToRecipient() {
        return this.toRecipient;
    }
    public void setToRecipient(String toRecipient) {
        this.toRecipient = toRecipient;
    }
    public String getBodyMessage() {
        return this.bodyMessage;
    }
    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
