package io.quarkiverse.proxywasm.deployment;

import java.util.List;

import org.jboss.jandex.DotName;

import io.quarkiverse.proxywasm.runtime.VertxHttpRequestAdaptor;
import io.quarkiverse.proxywasm.runtime.VertxServerAdaptor;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.jaxrs.spi.deployment.AdditionalJaxRsResourceMethodAnnotationsBuildItem;
import io.roastedroot.proxywasm.jaxrs.ProxyWasm;
import io.roastedroot.proxywasm.jaxrs.cdi.ProxyWasmFeature;

class ProxyWasmProcessor {

    private static final String FEATURE = "proxy-wasm";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem resources() {
        return new AdditionalBeanBuildItem(
                ProxyWasmFeature.class, VertxServerAdaptor.class, VertxHttpRequestAdaptor.class);
    }

    @BuildStep
    public AdditionalJaxRsResourceMethodAnnotationsBuildItem annotations() {
        return new AdditionalJaxRsResourceMethodAnnotationsBuildItem(
                List.of(DotName.createSimple(ProxyWasm.class.getName())));
    }
}
