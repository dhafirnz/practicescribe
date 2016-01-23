package nz.co.threepoints.ps.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebsocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    /**
     * Dhafir 23/01/2016: I had to comment thi smethod because it was causing soem security exceptions.
     *
     * The exception I was getting:
     * <code>
     * org.springframework.messaging.MessageDeliveryException: Failed to send message to
     * ExecutorSubscribableChannel[clientInboundChannel]; nested exception is org.springframework.security.access.AccessDeniedException: Access is denied
     * </code>
     * <p>
     *     For more details see : https://github.com/jhipster/generator-jhipster/issues/1370
     * </p>
     *
     */
//    @Override
//    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//        messages
//            // message types other than MESSAGE and SUBSCRIBE
//            .nullDestMatcher().authenticated()
//            // matches any destination that starts with /rooms/
//            .simpDestMatchers("/topic/**").authenticated()
//            // (i.e. cannot send messages directly to /topic/, /queue/)
//            // (i.e. cannot subscribe to /topic/messages/* to get messages sent to
//            // /topic/messages-user<id>)
//            .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll()
//            // catch all
//            .anyMessage().denyAll();
//    }

    /**
     * Disables CSRF for Websockets.
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
