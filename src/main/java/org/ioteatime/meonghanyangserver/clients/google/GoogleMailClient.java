package org.ioteatime.meonghanyangserver.clients.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleMailClient {
    @Value("${google.service-account}")
    private String gcpServiceAccount;

    @Value("${google.official-gmail}")
    private String fromEmailAddress;

    private Credential getCredentials(
            final NetHttpTransport HTTP_TRANSPORT, GsonFactory jsonFactory) {
        try {
            // Load client secrets.
            InputStream in = GoogleMailClient.class.getResourceAsStream(gcpServiceAccount);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + gcpServiceAccount);
            }
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                                    HTTP_TRANSPORT,
                                    jsonFactory,
                                    clientSecrets,
                                    Set.of(GmailScopes.GMAIL_SEND))
                            .setDataStoreFactory(
                                    new FileDataStoreFactory(Paths.get("tokens").toFile()))
                            .setAccessType("offline")
                            .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        } catch (IOException e) {
            throw new RuntimeException("Google Credential 획득 중 I/O 오류가 발생하였습니다.");
        }
    }

    public void sendMail(String toEmailAddress, String subject, String message) {
        try {
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            Gmail service =
                    new Gmail.Builder(
                                    httpTransport,
                                    jsonFactory,
                                    getCredentials(httpTransport, jsonFactory))
                            .setApplicationName("Test Mailer")
                            .build();

            // Encode as MIME message
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(fromEmailAddress));
            email.addRecipient(
                    javax.mail.Message.RecipientType.TO, new InternetAddress(toEmailAddress));
            email.setSubject(subject);
            email.setText(message);

            // Encode and wrap the MIME message into a gmail message
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] rawMessageBytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
            Message msg = new Message();
            msg.setRaw(encodedEmail);

            // Create send message
            msg = service.users().messages().send("me", msg).execute();
            System.out.println("Message id: " + msg.getId());
            System.out.println(msg.toPrettyString());

        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw new RuntimeException(
                        "Gmail Send Mail에서 GoogleJsonResponseException이 발생하였습니다.");
            }
        } catch (AddressException e) {
            throw new RuntimeException("Gmail Send Mail에서 AddressException이 발생하였습니다.");
        } catch (MessagingException e) {
            throw new RuntimeException("Gmail Send Mail에서 MessagingException이 발생하였습니다.");
        } catch (IOException e) {
            throw new RuntimeException("Gmail Send Mail에서 IOException이 발생하였습니다.");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Gmail Client GeneralSecurityException이 발생하였습니다.");
        }
    }
}
