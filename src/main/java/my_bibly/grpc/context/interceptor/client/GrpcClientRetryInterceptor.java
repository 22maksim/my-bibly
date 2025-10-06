package my_bibly.grpc.context.interceptor.client;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcClientRetryInterceptor implements ClientInterceptor {


    private static final int maxRetries = 3;
    private static final long retryDelayMillis = 1000;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {
        return new RetryableClientCall<>(next.newCall(method, callOptions), maxRetries, retryDelayMillis);
    }

    private static class RetryableClientCall<ReqT, RespT> extends ClientCall<ReqT, RespT> {

        private final ClientCall<ReqT, RespT> delegate;
        private final int maxRetries;
        private final long retryDelayMillis;
        private int attempt = 0;
        private Listener<RespT> listener;
        private Metadata headers;
        private boolean isClosed;

        public RetryableClientCall(ClientCall<ReqT, RespT> delegate, int maxRetries, long retryDelayMillis) {
            this.delegate = delegate;
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        @Override
        public void start(Listener<RespT> responseListener, Metadata headers) {
            this.listener = responseListener;
            this.headers = headers;
            delegate.start(new RetryableResponseListener(), headers);
        }

        @Override
        public void request(int number) {
            delegate.request(number);
        }

        @Override
        public void cancel(String message, Throwable cause) {
            delegate.cancel(message, cause);
        }

        @Override
        public void halfClose() {
            delegate.halfClose();
        }

        @Override
        public void sendMessage(ReqT message) {
            delegate.sendMessage(message);
        }

        @Override
        public void setMessageCompression(boolean enable) {
            delegate.setMessageCompression(enable);
        }

        private class RetryableResponseListener extends Listener<RespT> {
            @Override
            public void onHeaders(Metadata headers) {
                listener.onHeaders(headers);
            }

            @Override
            public void onMessage(RespT message) {
                listener.onMessage(message);
            }

            @Override
            public void onClose(Status status, Metadata trailers) {
                if (isClosed || !isRetryableStatus(status)) {
                    listener.onClose(status, trailers);
                    return;
                }

                if (attempt < maxRetries) {
                    attempt++;
                    System.out.println("Attempt " + attempt + " failed: " + status);

                    try {
                        Thread.sleep(retryDelayMillis);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        status = Status.fromThrowable(e);
                    }

                    start(listener, headers);
                } else {
                    listener.onClose(status, trailers);
                }

                isClosed = true;
            }

            @Override
            public void onReady() {
                listener.onReady();
            }
        }

        private boolean isRetryableStatus(Status status) {
            return status.getCode() == Status.Code.UNAVAILABLE ||
                    status.getCode() == Status.Code.DEADLINE_EXCEEDED ||
                    status.getCode() == Status.Code.INTERNAL;  // под вопросом
        }
    }
}