package org.gxstar.randomdata.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gxstar.randomdata.analyse.PersonAnalyzer;
import org.gxstar.randomdata.api.dto.RandomResponse;
import org.gxstar.randomdata.api.dto.ResultInfoDto;
import org.gxstar.randomdata.api.error.ErrorMessage;
import org.gxstar.randomdata.models.Person;
import org.gxstar.randomdata.producer.api.PersonProducer;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataService {
    private static final Random rand = new Random(10);

    private final RestClient restClientWithTimeouts;
    private final PersonProducer producer;
    private final PersonService personService;
    private final PersonAnalyzer personAnalyzer;


    @Scheduled(cron = "*/30 * * * * *")
    public void getData() {
        log.info("getting info *******");
        final int results = rand.nextInt(1, 5);
        log.info("querying for results: {}", results);
        final String url = "https://randomuser.me/api/1.4/?results=%s".formatted(results);
        log.info("url: {}", url);
        RandomResponse body;

        try {
           body = restClientWithTimeouts.get()
                   .uri(url)
                   .accept(MediaType.APPLICATION_JSON)
                   .retrieve()
                   .body(RandomResponse.class);
        } catch (ResourceAccessException e) {
            log.error("Failed to retrieve", e);
            handleResourceAccessException(e);
            body = null;
        }

        Optional.ofNullable(body).ifPresent(response -> {
            final ResultInfoDto info = response.info();
            log.info("Received {} response, page {}", info.results(), info.page());
            response.results().forEach(p -> {
                log.info("Person Response: {}", p);
                personService.save(p, info);
                producer.send(p);
            });
        });

    }

    private void handleResourceAccessException(final ResourceAccessException e) {
        final String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
        final String localizedMessage = ExceptionUtils.getMessage(e);
        log.error(localizedMessage);
        log.error(rootCauseMessage);
        producer.sendErrorMessage(ErrorMessage.of(rootCauseMessage, localizedMessage));
    }


    @Scheduled(cron = "0 */30 * * * *")
    public void update() {
        final Map<LocalDateTime, List<Person>> allGroupingByMonth = personAnalyzer.getAllGroupingByMonth();
        allGroupingByMonth.forEach(
                (time, persons) -> {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("\n\t\t").append("Time: {}\n");
                    persons.forEach(p -> sb.append("\t\t\t").append("- %s %s %s%n".formatted(p.name().title(), p.name().first(), p.name().last())));
                    log.info(sb.toString(), time);
                }
        );
    }
}
