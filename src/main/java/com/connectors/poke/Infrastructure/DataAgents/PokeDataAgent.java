package com.connectors.poke.Infrastructure.DataAgents;

import com.connectors.poke.Infrastructure.RestTemplateCustomer;

public class PokeDataAgent extends RestTemplateCustomer {


    public String getRestTemplateHttpBusca(String url) {
        return getRestTemplateHttp(url);
    }


}
