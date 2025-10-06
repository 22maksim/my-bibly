package my_bibly.grpc.context.context;

import my_bibly.grpc.context.headers.GrpcHeaderKeys;

public class ContextHelper {

    public static boolean isBot() {
        return Boolean.parseBoolean(GrpcHeaderKeys.CTX_IS_BOT.toString());
    }

    public static int getContextUserId() {
        return Integer.parseInt(GrpcHeaderKeys.CTX_USER_ID.toString());
    }
}
