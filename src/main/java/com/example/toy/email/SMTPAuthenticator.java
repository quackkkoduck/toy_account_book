package com.example.toy.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
  @Override
  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(
        // "ggoreb.kim@gmail.com", "upncasjbhguuouvz");
        "rudals23yo@gmail.com", "oswqaqooiuwtpevg");
  }
}