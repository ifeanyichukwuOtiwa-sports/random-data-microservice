package org.gxstar.randomdata.service;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import org.gxstar.randomdata.models.Person;
import org.gxstar.randomdata.repository.PersonRepository;
import org.gxstar.randomdatatwo.api.Individual.IndividualRequest;
import org.gxstar.randomdatatwo.api.Individual.IndividualsResponseProto;
import org.gxstar.randomdatatwo.api.Individual.IndividualResponseProto;
import org.gxstar.randomdatatwo.api.Individual.TimeRequest;
import org.gxstar.randomdatatwo.api.IndividualServiceGrpc;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndividualGrpcClientImpl implements IndividualGrpcClient {
    private final IndividualServiceGrpc.IndividualServiceBlockingStub stub;
    private final PersonRepository personRepository;

    public Map<String, List<Person>> getPersons() {
        final IndividualRequest request = IndividualRequest.newBuilder().build();
        final IndividualsResponseProto response = stub.streamIndividuals(request);
        return extractTrendMap(response.getItemsList());
    }

    private Map<String, List<Person>> extractTrendMap(final List<IndividualResponseProto> items) {
        return items
                .stream()
                .map(i -> new TempHolder(toLocalDateTime(i.getCreatedAt()), i.getEmail()))
                .collect(
                        Collectors.toMap(
                                i -> "%s".formatted(i.time().toString()),
                                i -> personRepository.findByEmail(i.email()),
                                (a, b) -> {
                                    final List<Person> combiner = new ArrayList<>(a);
                                    combiner.addAll(b);
                                    return combiner;
                                })
                );
    }

    public Map.Entry<String, List<Person>> getPersonsCreatedBefore(final LocalDateTime time) {
        final Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        final TimeRequest request = TimeRequest.newBuilder()
                .setTime(
                        Timestamp.newBuilder()
                                .setSeconds(instant.getEpochSecond())
                                .setNanos(instant.getNano())
                                .build()
                )
                .build();
        final IndividualsResponseProto response = stub.getIndividualsCreatedBefore(request);
        return extractTrendMap(response.getItemsList()).entrySet().iterator().next();
    }

    private LocalDateTime toLocalDateTime(final Timestamp before) {

        final long seconds = before.getSeconds();
        final int nanos = before.getNanos();

        final Instant instant = Instant.ofEpochSecond(seconds, nanos);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    static record TempHolder(
            LocalDateTime time,
            String email
    ) {
    }
}
