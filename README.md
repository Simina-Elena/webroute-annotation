# webroute-annotation

Your task is to create a custom annotation called @WebRoute which can be used to annotate methods. Methods annotated with @WebRoute("/path") will be your HTTP request handlers. They are called whenever a request accepted by the webserver matches the "/path" value in a @WebRoute annotation.

Use reflection to find the right method for an incoming request.
