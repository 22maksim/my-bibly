package my_bibly.grpc.context.interceptor;

import io.grpc.*;
import my_bibly.grpc.context.headers.GrpcHeaderKeys;
import net.devh.boot.grpc.common.util.InterceptorOrder;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.core.annotation.Order;

@Order(InterceptorOrder.ORDER_FIRST)
@GrpcGlobalServerInterceptor
public class GrpcServerContextInterceptor implements ServerInterceptor {


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String userId = headers.get(GrpcHeaderKeys.USER_ID);
        String isBot = headers.get(GrpcHeaderKeys.IS_BOT);

        if (userId != null && !userId.isBlank() && isBot != null && !isBot.isBlank()) {
            try {

                Context context = Context.current()
                        .withValue(GrpcHeaderKeys.CTX_USER_ID, userId)
                        .withValue(GrpcHeaderKeys.CTX_IS_BOT, isBot)
                        .withValue(GrpcHeaderKeys.CTX_AUTH_ID, headers.get(GrpcHeaderKeys.AUTH_ID))
                        .withValue(GrpcHeaderKeys.CTX_APP_ID, headers.get(GrpcHeaderKeys.APP_ID))
                        .withValue(GrpcHeaderKeys.CTX_APP_VERSION, headers.get(GrpcHeaderKeys.APP_VERSION))
                        .withValue(GrpcHeaderKeys.CTX_DISTR_TYPE, headers.get(GrpcHeaderKeys.DISTR_TYPE))
                        .withValue(GrpcHeaderKeys.CTX_CLIENT_IP, headers.get(GrpcHeaderKeys.CLIENT_IP))
                        .withValue(GrpcHeaderKeys.CTX_REQUEST_ID, headers.get(GrpcHeaderKeys.REQUEST_ID))
                        .withValue(GrpcHeaderKeys.CTX_USER_ROLE, headers.get(GrpcHeaderKeys.USER_ROLE))
                        .withValue(GrpcHeaderKeys.CTX_USER_PERMISSIONS, headers.get(GrpcHeaderKeys.USER_PERMISSIONS))
                        .withValue(GrpcHeaderKeys.CTX_TRUSTED_DOMAIN, headers.get(GrpcHeaderKeys.TRUSTED_DOMAIN))
                        .withValue(GrpcHeaderKeys.CTX_TRUSTED_DOMAIN_BY_WHITELIST, headers.get(GrpcHeaderKeys.TRUSTED_DOMAIN_BY_WHITELIST))
                        .withValue(GrpcHeaderKeys.CTX_NETWORK_SEGMENT, headers.get(GrpcHeaderKeys.NETWORK_SEGMENT));

                return Contexts.interceptCall(context, call, headers, next);
            } catch (NumberFormatException ex) {
                call.close(Status.INVALID_ARGUMENT.withDescription("UserId is invalid").withCause(ex), headers);
            }
        } else {
            call.close(Status.UNAUTHENTICATED.withDescription("User id or isBot is not provided"), headers);
        }
        return new ServerCall.Listener<>() {
        };
    }
}