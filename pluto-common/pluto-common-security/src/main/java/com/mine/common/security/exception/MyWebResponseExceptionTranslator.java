package com.mine.common.security.exception;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;


/**
 * @author Jax-li
 */
@Primary
@SuppressWarnings({"rawtypes"})
public final class MyWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {

        // Try to extract a SpringSecurityException from the stacktrace
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        Exception ase = (AuthenticationException)
                throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }

        ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class,
                causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
        }

        ase = (InvalidGrantException) throwableAnalyzer
                .getFirstThrowableOfType(InvalidGrantException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new InvalidException(ase.getMessage(), ase));
        }

        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer
                .getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new MethodNotAllowedException(ase.getMessage(), ase));
        }

        ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(
                OAuth2Exception.class, causeChain);

        if (ase != null) {
            return handleOAuth2Exception((OAuth2Exception) ase);
        }

        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));

    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) {

        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }

        // 客户端异常直接返回客户端,不然无法解析
//        if (e instanceof ClientAuthenticationException) {
//            return new ResponseEntity<>(e, headers, HttpStatus.valueOf(status));
//        }
        return new ResponseEntity<>(new MyAuth2Exception(e.getMessage(), e), headers, HttpStatus.valueOf(status));

    }

    public static class ForbiddenException extends MyAuth2Exception {

        /**
         *
         */
        private static final long serialVersionUID = -3211367958906565120L;

        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "access_denied";
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.FORBIDDEN.value();
        }

    }

    private static class InvalidException extends MyAuth2Exception {

        /**
         *
         */
        private static final long serialVersionUID = -8573340446290695719L;

        public InvalidException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "invalid_exception";
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.UPGRADE_REQUIRED.value();
        }

    }

    private static class MethodNotAllowedException extends MyAuth2Exception {

        /**
         *
         */
        private static final long serialVersionUID = 2311129072383780001L;

        public MethodNotAllowedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "method_not_allowed";
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.METHOD_NOT_ALLOWED.value();
        }

    }

    private static class ServerErrorException extends MyAuth2Exception {

        /**
         *
         */
        private static final long serialVersionUID = -2551662415211152587L;

        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "server_error";
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

    }

    private static class UnauthorizedException extends MyAuth2Exception {

        /**
         *
         */
        private static final long serialVersionUID = 6493565496566723244L;

        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "unauthorized";
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.UNAUTHORIZED.value();
        }


    }

}