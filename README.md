#AWS Lambda Function

Trying out a tutorial for creating my first lambda function. The tutorial from
[underscore.io](http://underscore.io/blog/posts/2016/02/01/aws-lambda.html) creates
a Slack integration that takes user input (a place) and returns the current time of that place.

I've slightly changed the code to use the Json4s library instead of circe as I'm more familiar with it.
Also using joda DateTime instead of java.Instant as it was easier to mock in my tests.