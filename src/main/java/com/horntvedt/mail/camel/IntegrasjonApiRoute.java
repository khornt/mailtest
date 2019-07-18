package com.horntvedt.mail.camel;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.horntvedt.mail.camel.translator.NoeGikkGalt;
import com.horntvedt.mail.camel.translator.behandlenypost;

@Component
public class IntegrasjonApiRoute extends RouteBuilder {


    @Value("${mailserver}")
    private String mailserver;

    @Value("${mailserverport}")
    private String mailserverport;


    @Value("passord")
    private String passord;

    @Value("brukernavn")
    private String brukernavn;


    private static final String ORIGINAL_MSG = "orginalMelding";
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrasjonApiRoute.class);

    public void configure() {


        onException(Exception.class)
            .log(LoggingLevel.ERROR, LOGGER, "Det oppstod en uventet feil: ${exception.stacktrace}")
            .maximumRedeliveries(0)
            .process(new NoeGikkGalt())
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500));


        onException(PredicateValidationException.class)
            .log(LoggingLevel.INFO, LOGGER, "Route feilet i predicate sjekk: ${exception.stacktrace}")
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
            .maximumRedeliveries(0);


        from("timer://tid?fixedRate=true&period=60000")
            .log(LoggingLevel.INFO,"Leser mail")
            .to("imaps://" + mailserver + ":" + mailserverport + "?username=" + brukernavn + "&password=" + passord)
            .process(new behandlenypost())
            .log(LoggingLevel.INFO,"Leset mail");


//        restConfiguration().component("servlet").port("8080")
//            .bindingMode(RestBindingMode.json).skipBindingOnErrorCode(false);
//
//
//        rest("/api/v1/avtale").post()
//            .type(ForespoerselDto.class)
//            .outType(ResponsDto.class)
//            .route().routeId("restPost motta bestilling route")
//            .validate(new ForespoerselValidator())
//            .setProperty(ORIGINAL_MSG, simple("in.body"))
//            .log(LoggingLevel.INFO, LOGGER, "Melding validert OK")
//            .to("direct:opprettKunde")
//            .removeHeaders("*")
//            .to("direct:opprettProdukt")
//             .to("direct:sendBrevTilKoe")
//            .end();
//
//
//
//        from("direct:sendBrevTilKoe").routeId("Send brev til brevkø")
//                .log(LoggingLevel.INFO, LOGGER, "Sender ut brev")
//                .to("activemq:queue:" + utpostkasse + "?jmsMessageType=Text");


    }
}
