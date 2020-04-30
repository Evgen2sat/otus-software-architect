package hw4.prometheus.controllers;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.ipc.http.HttpSender;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micronaut.configuration.metrics.micrometer.annotation.MircometerTimed;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.RequestAttribute;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller("test")
//@Timed(value = "app_request_latency", histogram = true, description = "Application Request Latency")
public class TestController {

    private MeterRegistry meterRegistry;

    public TestController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

//    @Timed(value = "app_request_latency_seconds", percentiles = {0.5, 0.95, 0.99, 1}, histogram = true, description = "Application Request Latency")
    @Get
    public List<Meter> test() {
////        PrometheusMeterRegistry d = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
////        Meter.Id id = new Meter.Id("blaaaaaaaaa", Tags.of("controller"), null, null, Meter.Type.COUNTER);
////        d.newCounter(id).increment();
//        meterRegistry
//                .counter("http_server_requests_seconds").increment();
////                .counter("blaaaaaaaaaaaaaaaaaaaaaaaaa", "method", "endpoint", "http_status", "200")
////                .increment();
//
////        meterRegistry
////                .timer("TIMEEEEEEEEEEEEEEEEEEEEEEEER", "controller", "index", "action", "hello")
////                .record(() -> {
////
////                });
//
//        io.micrometer.core.instrument.Timer timer = io.micrometer.core.instrument.Timer.builder("TIMEEEEEEEEEEEEEEEEEEEEEEEER")
//                .publishPercentileHistogram()
////                .publishPercentiles(0.5, 0.95, 0.99, )
//                .register(meterRegistry);
//
//        Timer timer = meterRegistry.timer("TIMEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
//        timer.record(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(1500);
//            } catch (InterruptedException ignored) { }
//        });

//        Timer.Sample start = Timer.start(meterRegistry);
//        start.stop(Timer.builder("timed").tag("method", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").register(meterRegistry));


//        meterRegistry.timer("app_request_latency", "METHOD", "GGGGGGGET").takeSnapshot();

//        meterRegistry
//                .counter("web.access", "controller", "index", "action", "hello")
//                .increment();

        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "GET")
                .tag("endpoint", "/test")
                .publishPercentileHistogram()
                .register(this.meterRegistry)
                .record(() -> meterRegistry.getMeters());



//        return meterRegistry
//                .timer("app_request_latency", "method", "GET", "endpoint", "/test")
//                .record(() -> meterRegistry.getMeters());

//        return meterRegistry
//                .timer("web.timer", "controller", "index", "action", "hello")
//                .record(() -> meterRegistry.getMeters());

//        return meterRegistry.getMeters();
    }
}
