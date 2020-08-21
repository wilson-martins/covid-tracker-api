package com.mc855.tracker.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.mc855.tracker.domain.Person;
import com.mc855.tracker.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private PersonRepository personRepository;

    @Value("${credentials.firebase}")
    private String firebaseConfigFilePath;

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(firebaseConfigFilePath);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://covid-tracker-android-bf039.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            log.error("Could not initialize firebase credentials");
        }
    }

    public void sendNotifications(Collection<Person> personCollection) {

        try {
            // Create a list containing up to 100 registration tokens.
            // These registration tokens come from the client FCM SDKs.
            final Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -2);
            Collection<Person> filteredPersons = personCollection.stream().filter(p -> p.getLastNotificationDt() == null || calendar.getTime().after(p.getLastNotificationDt())).collect(Collectors.toList());
            List<String> registrationTokens = filteredPersons.stream().map(Person::getGoogleIdToken).collect(Collectors.toList());
            MulticastMessage message = MulticastMessage.builder()
                    .putData("body", "Atenção: Você teve contato nos últimos dias com pessoas com Covid-19. Tome as medidas necessárias!")
                    .addAllTokens(registrationTokens)
                    .build();
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            // See the BatchResponse reference documentation
            // for the contents of response.
            log.info(response.getSuccessCount() + " messages were sent successfully");

            filteredPersons.forEach(person -> person.setLastNotificationDt(new Date()));
            this.personRepository.saveAll(filteredPersons);
        } catch (Exception e) {
            log.error("Could not send messages", e);
        }
    }
}
