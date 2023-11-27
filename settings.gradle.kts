rootProject.name = "random-data"
include(
        ":api",
        ":api:error-message",
        ":app",
        ":app:grpc-client",
        ":proto-generated",
        ":proto-generated:service-generated",
        ":proto-generated:message-generated"
        )
