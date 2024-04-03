package br.rockethub.chessbackend.mail.data;

import br.rockethub.chessbackend.authentication.entities.User;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public class MailExchangeData implements Serializable {

    private User to;


    private String subject;

    private Object data;

    private String template;

    public MailExchangeData() {
        super();
    }


    public MailExchangeData(User to, String subject, Object data, @NonNull String template) {
        super();
        this.to = to;
        this.subject = subject;
        this.data = data;
        this.template = template;
    }

    public static class MailExchangeDataBuilder {
        private final MailExchangeData mailExchangeData;

        public MailExchangeDataBuilder() {
            this.mailExchangeData = new MailExchangeData();
        }

        public MailExchangeDataBuilder to(@NonNull User to) {
            this.mailExchangeData.to = to;
            return this;
        }

        public MailExchangeDataBuilder template(@NonNull String template) {
            this.mailExchangeData.template = template;
            return this;
        }

        public MailExchangeDataBuilder subject(String subject) {
            this.mailExchangeData.subject = subject;
            return this;
        }

        public MailExchangeDataBuilder data(Object data) {
            this.mailExchangeData.data = data;
            return this;
        }

        public MailExchangeData build() {

            assert this.mailExchangeData.to != null : "To field is required";
            assert this.mailExchangeData.template != null : "Template field is required";

            return this.mailExchangeData;
        }
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
