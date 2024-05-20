package pe.edu.vallegrande.app.healthcheck;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator implements HealthIndicator {

    private static final String BYTES = "bytes";

    @Override
    public Health health() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        double freeMemoryPercent = ((double) freeMemory / (double) totalMemory) * 100;
        if (freeMemoryPercent > 25) {
            return Health.up()
                    .withDetail("free_memory", freeMemory + " " + BYTES)
                    .withDetail("total_memory", totalMemory + " " + BYTES)
                    .withDetail("free_memory_percent", freeMemoryPercent + "%")
                    .build();
        } else {
            return Health.down()
                    .withDetail("free_memory", freeMemory + " " + BYTES)
                    .withDetail("total_memory", totalMemory + " " + BYTES)
                    .withDetail("free_memory_percent", freeMemoryPercent + "%")
                    .build();
        }
    }
}