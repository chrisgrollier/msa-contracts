/*
 * Creation : 20 Jun 2019
 */
package net.chrisgrollier.cloud.apps.common.exception.handler;

import org.springframework.http.ResponseEntity;

public interface RestExceptionHandler<T extends Exception> {

    ResponseEntity<ExceptionResponse> handle(T exception);
}
