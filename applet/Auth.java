package polsys;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Auth extends Authenticator {

  public Auth() {
    super();
  }

  public PasswordAuthentication getPasswordAuthentication() {
    String username, password;
    username = "socialcontact.representative@gmail.com";
    password = "SocialContact2015";
    System.out.println("authenticating. . ");
    return new PasswordAuthentication(username, password);
  }
}