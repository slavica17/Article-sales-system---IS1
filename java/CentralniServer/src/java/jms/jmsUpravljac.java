package jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class jmsUpravljac {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/Queue1")
    private Queue queue1;

    @Resource(lookup = "jms/Queue2")
    private Queue queue2;

    @Resource(lookup = "jms/Queue3")
    private Queue queue3;

    @Resource(lookup = "jms/ResponseQueue")
    private Queue responseQueue;

    public String posaljiZahtev(int brojPodsistema, int brojOperacije, String... parametri) {
    try (JMSContext context = connectionFactory.createContext()) {
        Queue odrediste;
        switch (brojPodsistema) {
            case 1:
                odrediste = queue1;
                break;
            case 2:
                odrediste = queue2;
                break;
            case 3:
                odrediste = queue3;
                break;
            default:
                return "Greška: Nepoznat podsistem";
        }

        javax.jms.TextMessage poruka = context.createTextMessage("zahtev");
        poruka.setIntProperty("operacija", brojOperacije);

        for (int i = 0; i < parametri.length; i++) {
            poruka.setStringProperty("param" + (i + 1), parametri[i]);
        }

        String correlationId = java.util.UUID.randomUUID().toString();
        poruka.setJMSCorrelationID(correlationId);
        poruka.setJMSReplyTo(responseQueue);

        context.createProducer().send(odrediste, poruka);

        javax.jms.JMSConsumer consumer = context.createConsumer(
            responseQueue, "JMSCorrelationID = '" + correlationId + "'"
        );
        javax.jms.TextMessage odgovor = (javax.jms.TextMessage) consumer.receive(5000);

        return odgovor != null ? odgovor.getText() 
                               : "Greška: Podsistem " + brojPodsistema + " nije odgovorio.";

    } catch (Exception e) {
        e.printStackTrace();
        return "Greška na Centralnom Serveru: " + e.getMessage();
    }
}
}