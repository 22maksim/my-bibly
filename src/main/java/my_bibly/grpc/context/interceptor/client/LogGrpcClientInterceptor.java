package my_bibly.grpc.context.interceptor.client;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class LogGrpcClientInterceptor implements ClientInterceptor {
    @Value("${grpc.logging.dump-messages}")
    private boolean dumpMessages;


    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {

        log.info("Outgoing gRPC call: {}", method.getFullMethodName());
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {

            @Override
            public void sendMessage(ReqT message) {
                if (dumpMessages && log.isDebugEnabled()) {
                    log.debug("Outgoing request message: {}", message);
                } else {
                    log.debug("Outgoing request message sent (content logging is disabled)");
                }
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                log.debug("Outgoing headers: {}", formatHeaders(headers));

                super.start(
                        new ForwardingClientCallListener.SimpleForwardingClientCallListener<>(responseListener) {
                            @Override
                            public void onMessage(RespT message) {
                                if (dumpMessages && log.isDebugEnabled()) {
                                    log.debug("Incoming response message: {}", message);
                                } else {
                                    log.debug("Incoming response message received (content logging is disabled)");
                                }
                                super.onMessage(message);
                            }

                            @Override
                            public void onHeaders(Metadata headers) {
                                log.debug("Incoming headers: {}", formatHeaders(headers));
                                super.onHeaders(headers);
                            }

                            @Override
                            public void onClose(Status status, Metadata trailers) {
                                if (status.isOk()) {
                                    log.info("gRPC call '{}' completed successfully.", method.getFullMethodName());
                                } else {
                                    log.warn("gRPC call '{}' failed with status: {} and trailers: {}",
                                            method.getFullMethodName(), status, formatHeaders(trailers));
                                }
                                super.onClose(status, trailers);
                            }
                        }, headers);
            }
        };
    }

    private String formatHeaders(Metadata headers) {
        if (headers == null || !log.isDebugEnabled()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String key : headers.keys()) {
            if (key.endsWith(Metadata.BINARY_HEADER_SUFFIX)) {
                sb.append(String.format("[%s]: <binary data>%n", key));
            } else {
                sb.append(String.format("[%s]: %s%n", key, headers.get(Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER))));
            }
        }
        return sb.toString();
    }
}