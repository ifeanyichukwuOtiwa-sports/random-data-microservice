package org.gxstar.randomdata.api.error;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ErrorMessage (
        String id,
        String message,
        String description,
        LocalDateTime timestamp
) implements Serializable {
    public static ErrorMessage of(final String message, final String description) {
        return new ErrorMessage(UUID.randomUUID().toString(), message, description, LocalDateTime.now());
    }

    public static byte[] parseTo(final ErrorMessage message) {
        try(final ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
            final ObjectOutputStream oOS = new ObjectOutputStream(bAOS)) {
            oOS.writeObject(message);
            return bAOS.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }
    public static ErrorMessage parseFrom(byte[] bytes) {
        try(final ByteArrayInputStream bAIS = new ByteArrayInputStream(bytes);
            final ObjectInputStream oIS = new ObjectInputStream(bAIS) ) {

            return (ErrorMessage) oIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
