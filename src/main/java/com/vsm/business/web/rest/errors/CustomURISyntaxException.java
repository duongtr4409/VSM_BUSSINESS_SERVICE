package com.vsm.business.web.rest.errors;

import java.net.URISyntaxException;

public class CustomURISyntaxException extends URISyntaxException {
    public CustomURISyntaxException(String input, String reason, int index) {
        super(input, reason, index);
    }

    public CustomURISyntaxException(String input, String reason) {
        super(input, reason);
    }
}
