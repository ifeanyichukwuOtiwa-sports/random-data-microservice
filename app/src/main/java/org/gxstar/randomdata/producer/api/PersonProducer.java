package org.gxstar.randomdata.producer.api;

import org.gxstar.randomdata.api.error.ErrorMessage;
import org.gxstar.randomdata.api.dto.PersonDto;

public interface PersonProducer {

    void send(PersonDto personDto);
    void sendErrorMessage(ErrorMessage message);
}
