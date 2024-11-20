package org.ioteatime.meonghanyangserver.clients.ses;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SesClient {
    @Value("${google.official-gmail}")
    private String officialGmail;

    private final AmazonSimpleEmailService emailService;

    public void sendEmail(String email, String subject, String body) {
        final String FROM = officialGmail;
        final String TO = email;
        final String SUBJECT = subject;
        final String HTMLBODY = body;

        SendEmailRequest request =
                new SendEmailRequest()
                        .withDestination(new Destination().withToAddresses(TO))
                        .withMessage(
                                new Message()
                                        .withBody(
                                                new Body()
                                                        .withHtml(
                                                                new Content()
                                                                        .withCharset("UTF-8")
                                                                        .withData(HTMLBODY)))
                                        .withSubject(
                                                new Content()
                                                        .withCharset("UTF-8")
                                                        .withData(SUBJECT)))
                        .withSource(FROM);
        emailService.verifyEmailAddress(new VerifyEmailAddressRequest().withEmailAddress(email));
        emailService.sendEmail(request);
    }
}
