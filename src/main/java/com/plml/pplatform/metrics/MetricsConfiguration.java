package com.plml.pplatform.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Slf4jReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Configuration
public class MetricsConfiguration {

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    @Bean
    ReporterWrapper slf4jReporter(MetricRegistry metricRegistry) {
        return new ReporterWrapper(Slf4jReporter.forRegistry(metricRegistry)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build());
    }

    private class ReporterWrapper {
        private ScheduledReporter scheduledReporter;

        public ReporterWrapper(ScheduledReporter scheduledReporter) {
            this.scheduledReporter = scheduledReporter;
        }

        @PostConstruct
        private void init() {scheduledReporter.start(2, TimeUnit.MINUTES);}

        @PreDestroy
        private void stop() {
            scheduledReporter.report();
            scheduledReporter.stop();
        }
    }


}


