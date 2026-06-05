
/**
 * File: ParkingModule.java
 * Author: Gabriel Twizerimana
 */

package edu.du.ict4315.parking10.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import edu.du.ict4315.parking1.controller.commands.ParkingOffice;
import edu.du.ict4315.parking5.observer.pattern.ParkingObserver;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Guice dependency injection module defining wiring, scoping rules,
 * and structural singletons for the distributed parking architecture
 */
public class ParkingModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind an inline logging observer strategy to our core interface
        bind(ParkingObserver.class).toInstance(event -> 
            System.out.println("[Guice Event Stream] " + event)
        );
    }

    @Provides
    @Singleton
    public ParkingOffice provideParkingOffice(ParkingObserver observer) {
        // Guice resolves the observer dependency automatically and creates a unified Singleton
        return new ParkingOffice("HQ_OFFICE_DENVER", observer);
    }

    @Provides
    @Singleton
    public ExecutorService provideThreadPool() {
        // Provide a fixed, reusable thread pool for managing client handlers
        return Executors.newFixedThreadPool(10);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }
}