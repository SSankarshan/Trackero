package com.taskero.track.exception;

public abstract class HttpCodeBasedException extends BaseCustomException {
    private final String details;  // Extra context

    // Constructor now accepts details as a parameter
    protected HttpCodeBasedException(ErrorCode errorCode, String details) {
        super(errorCode);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    // Nested exception classes for each error code
    public static class ResourceNotFoundException extends HttpCodeBasedException {
        public ResourceNotFoundException(String details) {
            super(ErrorCode.NOT_FOUND, details);  // Pass details
        }
    }

    public static class BadRequestException extends HttpCodeBasedException {
        public BadRequestException(String details) {
            super(ErrorCode.BAD_REQUEST, details);  // Pass details
        }
    }

    public static class UnauthorizedException extends HttpCodeBasedException {
        public UnauthorizedException(String details) {
            super(ErrorCode.UNAUTHORIZED, details);  // Pass details
        }
    }

    public static class ForbiddenException extends HttpCodeBasedException {
        public ForbiddenException(String details) {
            super(ErrorCode.FORBIDDEN, details);  // Pass details
        }
    }

    public static class ConflictException extends HttpCodeBasedException {
        public ConflictException(String details) {
            super(ErrorCode.CONFLICT, details);  // Pass details
        }
    }

    public static class UnprocessableEntityException extends HttpCodeBasedException {
        public UnprocessableEntityException(String details) {
            super(ErrorCode.UNPROCESSABLE_ENTITY, details);  // Pass details
        }
    }

    public static class TooManyRequestsException extends HttpCodeBasedException {
        public TooManyRequestsException(String details) {
            super(ErrorCode.TOO_MANY_REQUESTS, details);  // Pass details
        }
    }

    public static class MethodNotAllowedException extends HttpCodeBasedException {
        public MethodNotAllowedException(String details) {
            super(ErrorCode.METHOD_NOT_ALLOWED, details);  // Pass details
        }
    }

    public static class ServiceUnavailableException extends HttpCodeBasedException {
        public ServiceUnavailableException(String details) {
            super(ErrorCode.SERVICE_UNAVAILABLE, details);  // Pass details
        }
    }

    public static class InternalServerErrorException extends HttpCodeBasedException {
        public InternalServerErrorException(String details) {
            super(ErrorCode.INTERNAL_SERVER_ERROR, details);  // Pass details
        }
    }
}
