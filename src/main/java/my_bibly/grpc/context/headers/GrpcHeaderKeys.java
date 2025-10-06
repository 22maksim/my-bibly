package my_bibly.grpc.context.headers;

import io.grpc.Context;import io.grpc.Metadata;

public final class GrpcHeaderKeys {


    public static final Metadata.Key<String> USER_ID =
            Metadata.Key.of("x-user-id", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> AUTH_ID =
            Metadata.Key.of("x-auth-id", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> APP_ID =
            Metadata.Key.of("x-app-id", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> APP_VERSION =
            Metadata.Key.of("x-app-version", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> DISTR_TYPE =
            Metadata.Key.of("x-distr-type", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> CLIENT_IP =
            Metadata.Key.of("x-forwarded-for", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> REQUEST_ID =
            Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> USER_ROLE =
            Metadata.Key.of("x-user-role", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> USER_PERMISSIONS =
            Metadata.Key.of("x-user-permissions", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> TRUSTED_DOMAIN =
            Metadata.Key.of("x-trusted-domain", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> TRUSTED_DOMAIN_BY_WHITELIST =
            Metadata.Key.of("x-trusted-domain-by-whitelist", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> NETWORK_SEGMENT =
            Metadata.Key.of("x-network-segment", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> IS_BOT =
            Metadata.Key.of("x-is-bot", Metadata.ASCII_STRING_MARSHALLER);

    public static final Context.Key<String> CTX_USER_ID = Context.key("x-user-id");
    public static final Context.Key<String> CTX_AUTH_ID = Context.key("x-auth-id");
    public static final Context.Key<String> CTX_APP_ID = Context.key("x-app-id");
    public static final Context.Key<String> CTX_APP_VERSION = Context.key("x-app-version");
    public static final Context.Key<String> CTX_DISTR_TYPE = Context.key("x-distr-type");
    public static final Context.Key<String> CTX_CLIENT_IP = Context.key("x-forwarded-for");
    public static final Context.Key<String> CTX_REQUEST_ID = Context.key("x-request-id");

    public static final Context.Key<String> CTX_USER_ROLE = Context.key("x-user-role");
    public static final Context.Key<String> CTX_USER_PERMISSIONS = Context.key("x-user-permissions");
    public static final Context.Key<String> CTX_TRUSTED_DOMAIN = Context.key("x-trusted-domain");
    public static final Context.Key<String> CTX_TRUSTED_DOMAIN_BY_WHITELIST = Context.key("x-trusted-domain-by-whitelist");
    public static final Context.Key<String> CTX_NETWORK_SEGMENT = Context.key("x-network-segment");
    public static final Context.Key<String> CTX_IS_BOT = Context.key("isBot");

    private GrpcHeaderKeys() {}
}