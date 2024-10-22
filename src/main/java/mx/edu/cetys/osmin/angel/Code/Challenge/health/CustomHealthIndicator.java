package mx.edu.cetys.osmin.angel.Code.Challenge.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health(){
        return Health.up()
                .withDetail("Service","Service is running")
                .withDetail("Version","Osmín was here")
                .build();
    }

}
