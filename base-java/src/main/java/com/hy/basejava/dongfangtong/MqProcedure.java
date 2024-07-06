package com.hy.basejava.dongfangtong;


import com.tongtech.tmqi.Topic;
import com.tongtech.tmqi.TopicConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class MqProcedure {

    public static void main(String[] args) {
        send();
    }

    public static void send() {
        Properties prop = new Properties();
        String tcf="tongtech.jms.jndi.JmsContextFactory";
        // 10261 10025 10252
        String url="tlq://127.0.0.1:10261";
        prop.put("java.naming.factory.initial",tcf);
        prop.put("java.naming.provider.url",url);
        Context ictx = null;
        TopicConnectionFactory qcf = null;
        TopicConnection conn=null;
        TopicSession session=null;
        Topic topic=null;
        try{
            ictx = new InitialContext(prop);
            qcf = (TopicConnectionFactory) ictx.lookup("TopicConnectionFactory");
            conn=qcf.createTopicConnection();
            conn.start();
            topic = (Topic)ictx.lookup("MyTopic");

            session=conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicPublisher pub=session.createPublisher(topic);
            TextMessage message=session.createTextMessage();
            message.setText("Hi,how are you");
            pub.publish(message);

        }catch(NamingException | JMSException e){
           e.printStackTrace();
        }finally {
            try {
                ictx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }


    }



}
