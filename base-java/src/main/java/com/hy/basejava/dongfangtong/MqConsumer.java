//package com.hy.basejava.dongfangtong;
//
//
//import com.tongtech.tmqi.Topic;
//import com.tongtech.tmqi.TopicConnectionFactory;
//
//import javax.jms.*;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import java.net.http.HttpClient;
//import java.util.Properties;
//
//public class MqConsumer {
//
//    public static void main(String[] args) {
//
////        receive();
//    }
//
//    public static void receive() {
//        Properties prop = new Properties();
//        String tcf="tongtech.jms.jndi.JmsContextFactory";
//        // 10261 10025 10252 10261 10024 10003
//        String url="tlq://127.0.0.1:10240";
//        prop.put("java.naming.factory.initial",tcf);
//        prop.put("java.naming.provider.url",url);
//        Context ictx = null;
//        TopicConnectionFactory qcf = null;
//        TopicConnection conn=null;
//        TopicSession session=null;
//        Topic topic=null;
//        try{
//            ictx = new InitialContext(prop);
//            qcf = (TopicConnectionFactory) ictx.lookup("TopicConnectionFactory");
//            conn=qcf.createTopicConnection();
//            conn.start();
//            topic = (Topic)ictx.lookup("MyTopic");
//            session=conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            TopicSubscriber sub=session.createSubscriber(topic);
//            TextMessage receiveMessage=(TextMessage)sub.receive();
//            System.out.println("“a subscription is received:"+receiveMessage.getText());
//        }catch(NamingException | JMSException e){
//           e.printStackTrace();
//        }finally {
//            try {
//                ictx.close();
//            } catch (NamingException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//
//
//
//}
