Architecture Components Explanation:
1. Client Layer
  - External HTTP clients making requests to the application
  - Entry point through HTTP GET requests
2. Controller Layer (RequestController)
  - REST controller handling incoming HTTP requests
  - Annotated with @RestController
  - Entry point for the resilience patterns
3. Resilience Patterns
  Time Limiter
    Configuration: 5-second timeout
    Annotation: @TimeLimiter(name = "requestTimeLimiter")
    Interrupts long-running operations
  Circuit Breaker
    Configuration:
      Failure rate threshold: 50%
      Minimum calls: 5
      Sliding window: 10 calls
      Wait duration: 10s
    Annotation: @CircuitBreaker(name = "requestCircuitBreaker")
    Prevents cascade failures
4. Service Layer (RequestService)
  Business logic implementation
  Makes external service calls
  Wrapped with resilience patterns
5. Fallback Mechanism
  Handles both timeout and circuit breaker failures
  Returns appropriate error messages:
  Timeout: "Request timed out after 5 seconds"
  Circuit Breaker: "Circuit breaker triggered: {reason}"
6. Configuration Layer (in application.properties)

 # TimeLimiter Config
   resilience4j.timelimiter.instances.requestTimeLimiter.timeout-duration=5s
   
   # CircuitBreaker Config
   resilience4j.circuitbreaker.instances.requestCircuitBreaker.failure-rate-threshold=50
   resilience4j.circuitbreaker.instances.requestCircuitBreaker.minimum-number-of-calls=5


This architecture provides:
  Timeout protection (5 seconds)
  Circuit breaking for fault tolerance
  Graceful degradation through fallbacks
  Clear separation of concerns
  Resilient external service communication

  Build and run this service to test circuit break:
  https://github.com/flaviano-faria/spring_redis
