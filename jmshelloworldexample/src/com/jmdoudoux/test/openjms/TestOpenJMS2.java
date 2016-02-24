package com.jmdoudoux.test.openjms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.javacodegeeks.examples.ContextUtil;

public class TestOpenJMS2 {

  public static void main(String[] args) {
    Context context = null;
    ConnectionFactory factory = null;
    Connection connection = null;
    Destination destination = null;
    Session session = null;
    MessageConsumer receiver = null;

    try {
      context = ContextUtil.getInitialContext();
      factory = (ConnectionFactory) context.lookup("ConnectionFactory");
      destination = (Destination) context.lookup("/queue/HelloWorldQueue");
      connection = factory.createConnection();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      receiver = session.createConsumer(destination);
      connection.start();

        Message message = receiver.receive();
        if (message instanceof TextMessage) {
          TextMessage text = (TextMessage) message;
          System.out.println("message recu= " + text.getText());
        } else if (message != null) {
          System.out.println("Aucun message dans la file");
        }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (context != null) {
        try {
          context.close();
        } catch (NamingException e) {
          e.printStackTrace();
        }
      }

      if (connection != null) {
        try {
          connection.close();
        } catch (JMSException e) {
          e.printStackTrace();
        }
      }
    }
  }
}