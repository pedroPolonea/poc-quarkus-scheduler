package org.acme;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class XptoScheduler {
    private Logger log = LoggerFactory.getLogger(XptoScheduler.class);

    private AtomicInteger counter = new AtomicInteger();

    @Scheduled(every="10s")
    void increment() {
        log.info("M=increment, counter={}", counter.get());
        counter.incrementAndGet();
    }

    @Scheduled(cron="*/5 * * * * ?")
    void cronJob(ScheduledExecution execution) {
        log.info("M=cronJob, execution={}, counter={}", execution.getScheduledFireTime(), counter.get());
    }

    @Scheduled(cron = "{cron.expr}")
    void cronJobWithExpressionInConfig() {
        log.info("M=cronJobWithExpressionInConfig, counter={}", counter.get());
    }

    // Add -Dcron.vm.opt=15s no VM Options
    @Scheduled(every = "{cron.vm.opt}")
    void cronJobWithVMOptions() {
        log.info("M=cronJobWithVMOptions, counter={}", counter.get());
    }
}
