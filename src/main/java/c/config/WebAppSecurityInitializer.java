package c.config;import org.springframework.core.annotation.Order;import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;//liczba w nawiasia oznacza ważność@Order(1)public class WebAppSecurityInitializer extends		AbstractSecurityWebApplicationInitializer {}