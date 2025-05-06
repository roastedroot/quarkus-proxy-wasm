package org.example;

import jakarta.ws.rs.Path;

import io.roastedroot.proxywasm.jaxrs.ProxyWasm;

/**
 * JAX-RS resource class for handling requests to the /admin path.
 * Inherits functionality from the org.example.Anything class and applies the "waf" Wasm plugin.
 */
@Path("/admin")
@ProxyWasm("waf") // use the corsaWAF filter
public class Admin extends Anything {
    /**
     * Default constructor.
     */
    public Admin() {
        // Default constructor
    }
}
