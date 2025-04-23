package io.quarkiverse.proxywasm.it;

import java.net.URI;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import org.example.internal.MainWasmModule;

import com.dylibso.chicory.wasm.WasmModule;
import com.google.gson.Gson;

import io.roastedroot.proxywasm.Plugin;
import io.roastedroot.proxywasm.PluginFactory;
import io.roastedroot.proxywasm.StartException;

/**
 * Application configuration for integration tests.
 * Provides CDI producers for various {@link PluginFactory} configurations used in tests.
 */
@ApplicationScoped
public class App {

    /**
     * Default constructor.
     */
    public App() {
    }

    private static final Gson gson = new Gson();
    private WasmModule MODULE = MainWasmModule.load();

    /**
     * Produces a {@link PluginFactory} for header manipulation tests (shared instance).
     *
     * @return A configured {@link PluginFactory}.
     * @throws StartException If plugin initialization fails.
     */
    @Produces
    public PluginFactory headerTests() throws StartException {
        return () -> Plugin.builder(MODULE)
                .withName("headerTests")
                .withShared(true)
                .withLogger(new MockLogger("headerTests"))
                .withPluginConfig(gson.toJson(Map.of("type", "headerTests")))
                .withMachineFactory(MainWasmModule::create)
                .build();
    }

    /**
     * Produces a {@link PluginFactory} for header manipulation tests (non-shared instances).
     *
     * @return A configured {@link PluginFactory}.
     * @throws StartException If plugin initialization fails.
     */
    @Produces
    public PluginFactory headerTestsNotShared() throws StartException {
        return () -> Plugin.builder(MODULE)
                .withName("headerTestsNotShared")
                .withLogger(new MockLogger("headerTestsNotShared"))
                .withPluginConfig(gson.toJson(Map.of("type", "headerTests")))
                .withMachineFactory(MainWasmModule::create)
                .build();
    }

    /**
     * Produces a {@link PluginFactory} for tick-based tests.
     *
     * @return A configured {@link PluginFactory}.
     * @throws StartException If plugin initialization fails.
     */
    @Produces
    public PluginFactory tickTests() throws StartException {
        return () -> Plugin.builder(MODULE)
                .withName("tickTests")
                .withShared(true)
                .withLogger(new MockLogger("tickTests"))
                .withPluginConfig(gson.toJson(Map.of("type", "tickTests")))
                .withMachineFactory(MainWasmModule::create)
                .build();
    }

    /**
     * Produces a {@link PluginFactory} for Foreign Function Interface (FFI) tests.
     *
     * @return A configured {@link PluginFactory}.
     * @throws StartException If plugin initialization fails.
     */
    @Produces
    public PluginFactory ffiTests() throws StartException {
        return () -> Plugin.builder(MODULE)
                .withName("ffiTests")
                .withLogger(new MockLogger("ffiTests"))
                .withPluginConfig(
                        gson.toJson(Map.of("type", "ffiTests", "function", "reverse")))
                .withForeignFunctions(Map.of("reverse", App::reverse))
                .withMachineFactory(MainWasmModule::create)
                .build();
    }

    /**
     * Reverses the byte order of the input data. Used as an FFI function in tests.
     *
     * @param data The byte array to reverse.
     * @return A new byte array with the reversed content.
     */
    public static byte[] reverse(byte[] data) {
        byte[] reversed = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            reversed[i] = data[data.length - 1 - i];
        }
        return reversed;
    }

    /**
     * Produces a {@link PluginFactory} for HTTP call tests.
     *
     * @return A configured {@link PluginFactory}.
     * @throws StartException If plugin initialization fails.
     */
    @Produces
    public PluginFactory httpCallTests() throws StartException {
        return () -> Plugin.builder(MODULE)
                .withName("httpCallTests")
                .withLogger(new MockLogger("httpCallTests"))
                .withPluginConfig(
                        gson.toJson(
                                Map.of(
                                        "type", "httpCallTests",
                                        "upstream", "web_service",
                                        "path", "/ok")))
                .withUpstreams(Map.of("web_service", new URI("http://localhost:8081")))
                .withMachineFactory(MainWasmModule::create)
                .build();
    }
}
