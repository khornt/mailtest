package com.horntvedt.mail.camel.translator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NoeGikkGalt implements Processor {

    @Override
    public void process(Exchange exchange) {

        Exception caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        String s = "dfffds";

    }


}
