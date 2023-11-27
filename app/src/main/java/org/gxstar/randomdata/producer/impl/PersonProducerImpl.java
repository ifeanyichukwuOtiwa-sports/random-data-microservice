package org.gxstar.randomdata.producer.impl;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gxstar.randomdata.api.dto.DateOfBirthDto;
import org.gxstar.randomdata.api.dto.PersonDto;
import org.gxstar.randomdata.api.dto.PersonNameDto;
import org.gxstar.randomdata.api.error.ErrorMessage;
import org.gxstar.randomdata.producer.api.PersonProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;

import static org.gxstar.randomdatatwo.api.Messages.DateOfBirthProto;
import static org.gxstar.randomdatatwo.api.Messages.PersonNameProto;
import static org.gxstar.randomdatatwo.api.Messages.PersonProto;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonProducerImpl implements PersonProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;


    @Override
    public void send(final PersonDto personDto) {
        final String topic = "person.topic";
        final String id = personDto.email();
        final byte[] data = parse(personDto);
        log.info("sending to topic {}", topic);
        kafkaTemplate.send(topic, id, data);
    }

    @Override
    public void sendErrorMessage(final ErrorMessage message) {
        final String topic = "error.topic";
        String id = message.id();
        final byte[] data = ErrorMessage.parseTo(message);
        log.info("sending to topic {}", topic);
        kafkaTemplate.send(topic, id, data);
    }

    private byte[] parse(final PersonDto personDto) {
        final PersonProto proto = convert(personDto);
        return proto.toByteArray();
    }

    private PersonProto convert(final PersonDto personDto) {
        final DateOfBirthProto dob = convert(personDto.dob());
        final PersonNameProto name = convert(personDto.name());
        return PersonProto
                .newBuilder()
                .setCell(personDto.cell())
                .setName(name)
                .setDob(dob)
                .setEmail(personDto.email())
                .setGender(personDto.gender())
                .setNat(personDto.nat())
                .setPhone(personDto.phone())
                .build();
    }

    private static DateOfBirthProto convert(final DateOfBirthDto dob) {
        final Instant instant = dob.date().atZone(ZoneId.systemDefault()).toInstant();
        final Timestamp time = Timestamp.newBuilder()
                .setNanos(instant.getNano())
                .setSeconds(instant.getEpochSecond())
                .build();
        return DateOfBirthProto.newBuilder()
                .setAge(dob.age())
                .setDate(time)
                .build();
    }

    private PersonNameProto convert(final PersonNameDto name) {
        return PersonNameProto.newBuilder()
                .setFirst(name.first())
                .setLast(name.last())
                .setTitle(name.title())
                .build();
    }

}
