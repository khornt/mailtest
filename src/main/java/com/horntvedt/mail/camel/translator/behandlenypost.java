package com.horntvedt.mail.camel.translator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class behandlenypost implements Processor {


    @Override
    public void process(Exchange exchange) {

        Object o = exchange.getIn().getBody(Object.class);

        String s = "fsdsff";

    }
}
