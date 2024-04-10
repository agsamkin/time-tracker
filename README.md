# Service for measuring the execution time of methods

[![build and test](https://github.com/agsamkin/time-tracker/actions/workflows/build.yml/badge.svg)](https://github.com/agsamkin/time-tracker/actions/workflows/build.yml)

The service implements the ability to measure the execution time of methods and analyze the results of these measurements.
The service is implemented using Spring AOP, Spring Web, Spring JPA.

### How to use

#### <u>Measuring the execution time of methods</u>

To measure the execution time of a method, the annotation @TrackTime or the annotation @TrackAsyncTime must be specified above it.

For example:

```
@Service
public class DefaultFooBarService implements FooBarService {
    @TrackTime
    @Override
    public void foo() {
        System.out.println("foo");
    }

    @TrackAsyncTime
    @Override
    public void bar() {
        System.out.println("bar");
    }
}
```

***

#### <u>Note!</u>

If you call a method with @TrackTime (or @TrackAsyncTime) annotation from a method with @TrackTime (or @TrackAsyncTime) 
belonging to the same Spring Bean, the timing of the second method will not be performed.

***

#### <u>Analyzing the results</u>

To get statistics on method execution time use REST API.

API documentation is available by clicking here: [http://host:port/api-doc.html](). 

#### Get log method execution time

Request example:

```
GET http://localhost:8080/api/v1/time-measurements/log?from=2024-03-01T00:00:00&to=2024-03-31T23:59:59
```

Response example:

```
[
    {
        "methodInfo": {
            "className": "com.example.timetracker.service.impl.DefaultFooBarService",
            "methodName": "test3",
            "signatureShortName": "DefaultFooBarService.foo()",
            "signatureLongName": "public void com.example.timetracker.service.impl.DefaultFooBarService.foo()"
        },
        "executionTime": 1508034900,
        "createdAt": "2024-03-01T15:03:57.362718"
    },
    {
        "methodInfo": {
            "className": "com.example.timetracker.service.impl.DefaultFooBarService",
            "methodName": "bar",
            "signatureShortName": "DefaultFooBarService.bar()",
            "signatureLongName": "public void com.example.timetracker.service.impl.DefaultFooBarService.bar()"
        },
        "executionTime": 2507314500,
        "createdAt": "2024-03-01T15:04:36.751182"
    } 
]
```

#### Get total method execution time

Request example:

``` 
GET http://localhost:8080/api/v1/time-measurements/total?from=2024-03-01T00:00:00&to=2024-03-31T23:59:59
```

Response example:

```
{
    "DefaultFooBarService.foo()": 9022631100,
    "DefaultFooBarService.bar()": 5011361700
}
```

#### Get average method execution time

Request example:

```
GET http://localhost:8080/api/v1/time-measurements/average?from=2024-03-01T00:00:00&to=2024-03-31T23:59:59
```

Response example:

```
{
    "DefaultFooBarService.foo()": 2.50568085E9,
    "DefaultFooBarService.bar()": 2.255657775E9
}
```



