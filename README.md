# Quarkus Proxy-WASM Extension

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.proxy-wasm/quarkus-proxy-wasm?logo=apache-maven&style=flat-square)](https://central.sonatype.com/artifact/io.quarkiverse.proxy-wasm/quarkus-proxy-wasm-parent)

The Java implementation for proxy-wasm, enabling developer to run Proxy-Wasm plugins in Java.

## Overview

Proxy-Wasm is a plugin system for network proxies. It lets you write plugins that can act as request filters in a
portable, sandboxed, and language-agnostic way, thanks to WebAssembly.

This Quarkus extension allows you to use Proxy-Wasm plugins to filter requests to Jakarta REST (formerly known as JAX-RS)
endpoints.

Adding a Proxy-Wasm plugin to a JAX-RS endpoint is as simple as adding a `@ProxyWasm` annotation to a method:

```java
package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.roastedroot.proxywasm.jaxrs.ProxyWasm;

@Path("/example")
public class Example {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @ProxyWasm("waf")
    public String hello() {
        return "hello";
    }
}
```

## Docs

* [Usage Guide](./docs/modules/ROOT/pages/index.adoc)

### Docs and SDKs for plugin authors:

* [ABI specification](https://github.com/istio-ecosystem/wasm-extensions[Proxy-Wasm)
* [C++ SDK](https://github.com/proxy-wasm/proxy-wasm-cpp-sdk[Proxy-Wasm)
* [Rust SDK](https://github.com/proxy-wasm/proxy-wasm-rust-sdk[Proxy-Wasm)
* [Go SDK](https://github.com/proxy-wasm/proxy-wasm-go-sdk[Proxy-Wasm)
* [AssemblyScript SDK](https://github.com/solo-io/proxy-runtime[Proxy-Wasm)

### Popular Proxy-Wasm plugins:

* [Coraza WAF](link:https://github.com/corazawaf/coraza-proxy-wasm)
* [Kuadrant](link:https://github.com/Kuadrant/wasm-shim/)


## Building

To build the project, you need to have Maven installed. You can build the project using the following command:

```bash
mvn clean install
```
